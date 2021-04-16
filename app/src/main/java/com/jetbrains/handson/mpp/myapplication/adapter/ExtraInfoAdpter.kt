package com.jetbrains.handson.mpp.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jetbrains.handson.mpp.myapplication.databinding.ExtraInfoItemBinding
import com.jetbrains.handson.mpp.myapplication.domain.ExtraInfo

class ExtraInfAdapter : RecyclerView.Adapter<ExtraInfAdapter.ViewHolder>() {

    var data = listOf<ExtraInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ExtraInfoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ExtraInfo) {
            binding.extraInfo = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ExtraInfoItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

