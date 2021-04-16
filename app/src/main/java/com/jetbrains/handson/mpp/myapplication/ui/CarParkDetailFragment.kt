package com.jetbrains.handson.mpp.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jetbrains.handson.mpp.myapplication.R
import com.jetbrains.handson.mpp.myapplication.adapter.ExtraInfAdapter
import com.jetbrains.handson.mpp.myapplication.databinding.FragmentCarParkDetailBinding
import com.jetbrains.handson.mpp.myapplication.viewmodels.CarParkDetailViewModel
import com.jetbrains.handson.mpp.myapplication.viewmodels.CarParkListViewModel
import com.jetbrains.handson.mpp.myapplication.viewmodels.DetailViewModelFactory

class CarParkDetailFragment : Fragment() {

    private lateinit var viewModel : CarParkDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentCarParkDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_car_park_detail,
            container,
            false
        )
        val args = CarParkDetailFragmentArgs.fromBundle(requireArguments())

        val application = requireNotNull(activity).application
        val viewModelFactory = DetailViewModelFactory(args.carPark,application)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(CarParkDetailViewModel::class.java)


        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        val adapter = ExtraInfAdapter()
        adapter.data =  viewModel.carParkDetail.value!!.extraInfoList
        binding.extraInfoList.adapter = adapter

        return binding.root
    }
}