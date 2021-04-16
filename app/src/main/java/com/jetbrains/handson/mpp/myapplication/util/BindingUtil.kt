package com.jetbrains.handson.mpp.myapplication.util

import android.provider.ContactsContract
import android.provider.Telephony
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jetbrains.handson.mpp.myapplication.R

import kotlinx.android.synthetic.main.fragment_car_park_detail.*

@BindingAdapter("availableSpotsString")
fun setAvailableSpotsString(textView: TextView,availableSpot: Int){
 textView.text =textView.resources.getString(R.string.available_spot , availableSpot.toString())
}

@BindingAdapter("totalSpotsString")
fun setTotalSpotsString(textView: TextView,totalSpot: Int){
 textView.text =textView.resources.getString(R.string.total_spot , totalSpot.toString())
}

@BindingAdapter("facilityNameString")
fun setFacilityNameString(textView: TextView,facilistyName:String){
 textView.text = textView.resources.getString(R.string.facility_name,facilistyName)
}

@BindingAdapter("addressString")
fun setAddressString(textView: TextView, address:String){
 textView.text = textView.resources.getString(R.string.address,address)
}

@BindingAdapter("feeWebsiteString")
fun setFeeWebsieteString(textView: TextView, feeWebsite:String){
 textView.text = textView.resources.getString(R.string.fee_website,feeWebsite)
}