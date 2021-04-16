package com.jetbrains.handson.mpp.myapplication.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.jetbrains.handson.mpp.myapplication.domain.CarPark
import com.jetbrains.handson.mpp.myapplication.domain.ExtraInfo

@Entity
data class CarParkDataBase(
    @PrimaryKey (autoGenerate = false)
    val carParkId: Long,
    val facilityName: String,
    val totalSpots: Int,
    val availableSpots: Int,
    val calculatedAvailablilityStatus: Int,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val feeWebsite: String
)

@Entity
data class ExtraInfoDatabase(
    @PrimaryKey (autoGenerate = false)
    var extraInfoId: String,
    val carParkId: Long,
    val header: String,
    val label: String,
    val value: String?
)

data class CarParkWithExtraInfoDataBase(
    @Embedded
    val carParkDataBase: CarParkDataBase,
    @Relation(
        parentColumn = "carParkId",
        entityColumn = "carParkId"
    )
    val extraInfoList: List<ExtraInfoDatabase>
)

fun List<CarParkWithExtraInfoDataBase>.asDomainModel(): List<CarPark> {
    return map {
        CarPark(
            facilityId = it.carParkDataBase.carParkId.toString(),
            facilityName = it.carParkDataBase.facilityName,
            totalSpots = it.carParkDataBase.totalSpots,
            availableSpots = it.carParkDataBase.availableSpots,
            calculatedAvailablilityStatus = it.carParkDataBase.calculatedAvailablilityStatus,
            address = it.carParkDataBase.address,
            latitude = it.carParkDataBase.latitude,
            longitude = it.carParkDataBase.longitude,
            feeWebsite = it.carParkDataBase.feeWebsite,
            extraInfoList = it.extraInfoList.map {
                ExtraInfo(
                    header = it.header,
                    label = it.label,
                    value = it.value
                )
            }
        )
    }
}
