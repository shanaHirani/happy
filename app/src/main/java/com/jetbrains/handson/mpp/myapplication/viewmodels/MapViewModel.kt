package com.jetbrains.handson.mpp.myapplication.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jetbrains.handson.mpp.myapplication.database.getDatabase
import com.jetbrains.handson.mpp.myapplication.domain.CarPark
import com.jetbrains.handson.mpp.myapplication.repository.CarParkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MapViewModel(application: Application): ViewModel() {


    private val _eventShowCarParkList = MutableLiveData<Boolean>()
    val eventShowCarParkList : LiveData<Boolean>
        get() = _eventShowCarParkList

    private val _navigateToSelectedCarPark = MutableLiveData<CarPark>()
    val navigateToSelectedCarPark: LiveData<CarPark>
        get() = _navigateToSelectedCarPark

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val carParkRepository = CarParkRepository(database)

    val carParks = carParkRepository.carParks

    init {
        _navigateToSelectedCarPark.value = null
        _eventShowCarParkList.value = false
    }



    fun onShowList(){
        _eventShowCarParkList.value=true
    }

    fun onRefresh(){
        coroutineScope.launch {
            carParkRepository.refreshCarPark()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onShowListComplete(){
        _eventShowCarParkList.value=false
    }

    fun navigateToSelectedCarPark(title:String) {
        _navigateToSelectedCarPark.value = carParks.value?.find {title.contains(it.facilityName)}
    }

    fun displayCarParkDetailComplete() {
        _navigateToSelectedCarPark.value = null
    }
}

class MapViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}