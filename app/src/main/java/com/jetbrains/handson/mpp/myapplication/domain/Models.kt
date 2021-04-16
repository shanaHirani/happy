package com.jetbrains.handson.mpp.myapplication.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CarPark(
    val facilityId: String,
    val facilityName: String,
    val totalSpots: Int,
    val availableSpots: Int,
    val calculatedAvailablilityStatus: Int,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val feeWebsite: String,
    val extraInfoList: List<ExtraInfo>
):Parcelable

@Parcelize
data class ExtraInfo(
    val header: String,
    val label: String,
    val value: String?
):Parcelable