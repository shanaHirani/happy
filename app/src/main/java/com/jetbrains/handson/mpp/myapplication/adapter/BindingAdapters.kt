package com.jetbrains.handson.mpp.myapplication.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jetbrains.handson.mpp.myapplication.R
import com.jetbrains.handson.mpp.myapplication.domain.CarPark
import com.jetbrains.handson.mpp.myapplication.viewmodels.ApiStatus

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