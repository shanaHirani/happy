package com.jetbrains.handson.mpp.myapplication.repository

import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jetbrains.handson.mpp.myapplication.database.CarParksDatabase
import com.jetbrains.handson.mpp.myapplication.database.asDomainModel
import com.jetbrains.handson.mpp.myapplication.domain.CarPark
import com.jetbrains.handson.mpp.myapplication.network.Api
import com.jetbrains.handson.mpp.myapplication.network.Api.retrofitService
import com.jetbrains.handson.mpp.myapplication.network.NetworkResponse
import com.jetbrains.handson.mpp.myapplication.network.asDataBaseModel
import com.jetbrains.handson.mpp.myapplication.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CarParkRepository(private val database: CarParksDatabase) {

    var carParks : LiveData<List<CarPark>> =
        Transformations.map(database.carParkDao.getCarParks()) {
            it.asDomainModel()
        }


    suspend fun refreshCarPark() {
        lateinit var networkResult:NetworkResponse
        withContext(Dispatchers.IO) {
            networkResult = Api.retrofitService.getCarParks().await()
            val (carParkDataBaseList,extraInfoDatabaseList) = networkResult.asDataBaseModel()
            database.carParkDao.insertAll(carParkDataBaseList,extraInfoDatabaseList)
        }
    }
}