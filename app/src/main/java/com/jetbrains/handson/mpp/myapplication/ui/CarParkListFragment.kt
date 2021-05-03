package com.jetbrains.handson.mpp.myapplication.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jetbrains.handson.mpp.myapplication.R
import com.jetbrains.handson.mpp.myapplication.adapter.CarParkAdapter
import com.jetbrains.handson.mpp.myapplication.databinding.FragmentCarParkListBinding
import com.jetbrains.handson.mpp.myapplication.viewmodels.CarParkDetailViewModel
import com.jetbrains.handson.mpp.myapplication.viewmodels.CarParkListViewModel
import com.jetbrains.handson.mpp.myapplication.viewmodels.CarParkListViewModelFactory
import com.jetbrains.handson.mpp.myapplication.viewmodels.DetailViewModelFactory

class CarParkListFragment : Fragment() {

    private lateinit var viewModel: CarParkListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCarParkListBinding.inflate(inflater)

        val application = requireNotNull(activity).application
        val viewModelFactory = CarParkListViewModelFactory(application)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(CarParkListViewModel::class.java)


        binding.lifecycleOwner = this
        binding.viewModel = viewModel
         val adapter = CarParkAdapter(CarParkAdapter.OnClickListener({
             viewModel.displayCarParkDetail(it)
         }))
        binding.carParkList.adapter = adapter

        viewModel.status.observe(viewLifecycleOwner, Observer {

        })


        viewModel.navigateToSelectedCarPark.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(
                    CarParkListFragmentDirections
                        .actionCarParkListFragmentToCarParkDetailFragment(it)
                )
                viewModel.displayCarParkDetailComplete()
            }
        })

        viewModel.eventShowMap.observe(viewLifecycleOwner, Observer { navigateToMap ->
            if (navigateToMap) {
                this.findNavController().navigate(
                    R.id.action_carParkListFragment_to_mapFragment
                )
                viewModel.onShowMapComplete()
            }

        })

        return binding.root
    }


}