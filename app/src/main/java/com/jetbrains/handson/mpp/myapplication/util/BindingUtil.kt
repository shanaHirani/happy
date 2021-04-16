package com.jetbrains.handson.mpp.myapplication.util

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.Space
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jetbrains.handson.mpp.myapplication.R
import com.jetbrains.handson.mpp.myapplication.domain.CarPark
import com.jetbrains.handson.mpp.myapplication.viewmodels.ApiStatus
import com.jetbrains.handson.mpp.myapplication.adapter.CarParkAdapter


@BindingAdapter("availableSpotsString")
fun setAvailableSpotsString(textView: TextView,availableSpot: Int){
 var spannableString = SpannableString(textView.resources.getString(R.string.available_spot , availableSpot.toString()))
 val styleSpan = StyleSpan(Typeface.BOLD)
 spannableString.setSpan(styleSpan,0,14,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
 textView.text =spannableString
}

@BindingAdapter("totalSpotsString")
fun setTotalSpotsString(textView: TextView,totalSpot: Int){
 var spannableString = SpannableString(textView.resources.getString(R.string.total_spot , totalSpot.toString()))
 val styleSpan = StyleSpan(Typeface.BOLD)
 spannableString.setSpan(styleSpan,0,10,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
 textView.text =spannableString
}

@BindingAdapter("facilityNameString")
fun setFacilityNameString(textView: TextView,facilistyName:String){
 var spannableString = SpannableString(textView.resources.getString(R.string.facility_name, facilistyName))
 val styleSpan = StyleSpan(Typeface.BOLD)
 spannableString.setSpan(styleSpan,0,14,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
 textView.text =spannableString
}

@BindingAdapter("addressString")
fun setAddressString(textView: TextView, address:String){
 var spannableString = SpannableString(textView.resources.getString(R.string.address , address))
 val styleSpan = StyleSpan(Typeface.BOLD)
 spannableString.setSpan(styleSpan,0,6,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
 textView.text =spannableString
}

@BindingAdapter("feeWebsiteString")
fun setFeeWebsieteString(textView: TextView, feeWebsite:String){
 var spannableString = SpannableString(textView.resources.getString(R.string.fee_website , feeWebsite))
 val styleSpan = StyleSpan(Typeface.BOLD)
 spannableString.setSpan(styleSpan,0,12,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
 textView.text =spannableString
}




@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data:List<CarPark>?){
 val adapter = recyclerView.adapter as CarParkAdapter
 adapter.submitList(data)
}

@BindingAdapter("parkingApiStatus")
fun bindStatus(statusImageView: ImageView, apiStatus:ApiStatus){
 when(apiStatus){
  ApiStatus.LOADING-> {
   statusImageView.visibility = View.VISIBLE
   statusImageView.setImageResource(R.drawable.loading_img)
  }
  ApiStatus.ERROR->{
   statusImageView.visibility = View.VISIBLE
   statusImageView.setImageResource(R.drawable.ic_connection_error)

  }
  ApiStatus.DONE->{
   statusImageView.visibility = View.GONE

  }
 }
}