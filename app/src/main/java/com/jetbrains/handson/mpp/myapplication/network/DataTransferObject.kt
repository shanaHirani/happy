package com.jetbrains.handson.mpp.myapplication.network

import com.jetbrains.handson.mpp.myapplication.database.CarParkDataBase
import com.jetbrains.handson.mpp.myapplication.database.ExtraInfoDatabase
import com.jetbrains.handson.mpp.myapplication.domain.CarPark
import com.jetbrains.handson.mpp.myapplication.domain.ExtraInfo
import com.squareup.moshi.Json

data class NetworkResponse(
    @Json(name = "response") val networkCarParksList: List<NetworkCarPark>
)

data class NetworkCarPark(
    val facilityId: String,
    val facilityName: String,
    val totalSpots: Int,
    val availableSpots: Int,
    val calculatedAvailablilityStatus: Int,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val feeWebsite: String,
    @Json(name = "details") val extraInfoList: List<propertyImage>
)

data class propertyImage(
    val header: String,
    val label: String,
    val value: String?
)

fun NetworkResponse.asDomainModel(): List<CarPark> {
    return networkCarParksList.map {
        CarPark(
            facilityId = it.facilityId,
            facilityName = it.facilityName,
            totalSpots = it.totalSpots,
            availableSpots = it.availableSpots,
            calculatedAvailablilityStatus = it.calculatedAvailablilityStatus,
            address = it.address,
            latitude = it.latitude,
            longitude = it.longitude,
            feeWebsite = it.feeWebsite,
            extraInfoList = it.extraInfoList.map { networkExtraInfo ->
                ExtraInfo(
                    header = networkExtraInfo.header,
                    label = networkExtraInfo.label,
                    value = networkExtraInfo.value
                )
            }
        )
    }
}

fun NetworkResponse.asDataBaseModel(): Pair<List<CarParkDataBase>, List<ExtraInfoDatabase>> {
    val carParkDataBaseList = networkCarParksList.map { networkCarPark ->
        CarParkDataBase(
            carParkId = networkCarPark.facilityId.toLong(),
            facilityName = networkCarPark.facilityName,
            totalSpots = networkCarPark.totalSpots,
            availableSpots = networkCarPark.availableSpots,
            calculatedAvailablilityStatus = networkCarPark.calculatedAvailablilityStatus,
            address = networkCarPark.address,
            latitude = networkCarPark.latitude,
            longitude = networkCarPark.longitude,
            feeWebsite = networkCarPark.feeWebsite
        )
    }

    val extraInfoDatabaseList = mutableListOf<ExtraInfoDatabase>()
    for (carPark in networkCarParksList) {
        for (extraInfo in carPark.extraInfoList) {
            extraInfoDatabaseList.add(
                ExtraInfoDatabase(
                    extraInfoId = carPark.facilityId + extraInfo.header,
                    carParkId = carPark.facilityId.toLong(),
                    header = extraInfo.header,
                    label = extraInfo.label,
                    value = extraInfo.value
                )
            )
        }

    }

    return Pair(carParkDataBaseList,extraInfoDatabaseList)
}