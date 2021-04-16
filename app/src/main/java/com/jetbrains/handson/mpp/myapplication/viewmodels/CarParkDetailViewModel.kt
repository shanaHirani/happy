package com.jetbrains.handson.mpp.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jetbrains.handson.mpp.myapplication.domain.CarPark

class CarParkDetailViewModel( carPark: CarPark,
                              application: Application): ViewModel() {



    private val _carParkDetail =MutableLiveData<CarPark>()
    val carParkDetail: LiveData<CarPark>
        get() = _carParkDetail

    init {
        _carParkDetail.value =carPark
    }
}

class DetailViewModelFactory(
    private val carParkDetail: CarPark,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarParkDetailViewModel::class.java)) {
            return CarParkDetailViewModel(carParkDetail, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}