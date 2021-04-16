package com.jetbrains.handson.mpp.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.jetbrains.handson.mpp.myapplication.database.CarParkDataBase
import com.jetbrains.handson.mpp.myapplication.database.getDatabase
import com.jetbrains.handson.mpp.myapplication.domain.CarPark
import com.jetbrains.handson.mpp.myapplication.network.Api
import com.jetbrains.handson.mpp.myapplication.network.asDomainModel
import com.jetbrains.handson.mpp.myapplication.repository.CarParkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

class CarParkListViewModel(application: Application) : ViewModel() {

    private val _navigateToSelectedCarPark = MutableLiveData<CarPark>()
    val navigateToSelectedCarPark: LiveData<CarPark>
        get() = _navigateToSelectedCarPark

    private val _eventShowMap = MutableLiveData<Boolean>()
    val eventShowMap: LiveData<Boolean>
        get() = _eventShowMap

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status


    private val _testText = MutableLiveData<String>()
    val testText: LiveData<String>
        get() = _testText

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val carParkRepository = CarParkRepository(database)

    init {
        _navigateToSelectedCarPark.value = null
        _eventShowMap.value = false
        // _status.value = ApiStatus.LOADING
        _status.value = ApiStatus.DONE
        coroutineScope.launch {
            carParkRepository.refreshCarPark()
        }
    }

    val carParks = carParkRepository.carParks

    fun onShowMap() {
        _eventShowMap.value = true
    }

    fun onRefresh() {
        coroutineScope.launch {
            carParkRepository.refreshCarPark()
        }
    }

    fun onShowMapComplete() {
        _eventShowMap.value = false
    }

    fun displayCarParkDetail(carPark: CarPark) {
        _navigateToSelectedCarPark.value = carPark
    }

    fun displayCarParkDetailComplete() {
        _navigateToSelectedCarPark.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

class CarParkListViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarParkListViewModel::class.java)) {
            return CarParkListViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
