package com.jetbrains.handson.mpp.myapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jetbrains.handson.mpp.myapplication.databinding.CarParkItemBinding
import com.jetbrains.handson.mpp.myapplication.domain.CarPark

class CarParkAdapter(private val onClickListener: OnClickListener):
    ListAdapter<CarPark, CarParkAdapter.CarParkViewHolder>(DiffCallback()) {

    class DiffCallback: DiffUtil.ItemCallback<CarPark>(){
        override fun areItemsTheSame(oldItem: CarPark, newItem: CarPark): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CarPark, newItem: CarPark): Boolean {
            return oldItem.facilityId == newItem.facilityId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarParkViewHolder {
        return return CarParkViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CarParkViewHolder, position: Int) {
        val carPark = getItem(position)
        holder.bind(onClickListener,carPark)
    }

    class CarParkViewHolder(private var binding: CarParkItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(onClickListener: OnClickListener,carPark:CarPark){
            binding.carParkItem.setOnClickListener {
                onClickListener.onClick(carPark)
            }
            binding.carPark = carPark
            binding.availableSpots.text = "5"
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CarParkViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =CarParkItemBinding.inflate(layoutInflater, parent, false)
                return CarParkViewHolder(binding)
            }
        }

    }

    class OnClickListener(val clickListener:(x:CarPark)->Unit){
        fun onClick(carPark: CarPark) = clickListener(carPark)
    }
}