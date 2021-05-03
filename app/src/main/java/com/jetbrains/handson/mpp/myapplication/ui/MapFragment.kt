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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jetbrains.handson.mpp.myapplication.R
import com.jetbrains.handson.mpp.myapplication.databinding.FragmentMapBinding
import com.jetbrains.handson.mpp.myapplication.viewmodels.CarParkListViewModel
import com.jetbrains.handson.mpp.myapplication.viewmodels.CarParkListViewModelFactory
import com.jetbrains.handson.mpp.myapplication.viewmodels.MapViewModel
import com.jetbrains.handson.mpp.myapplication.viewmodels.MapViewModelFactory

class MapFragment : Fragment() ,OnMapReadyCallback{

    private lateinit var viewModel : MapViewModel
    lateinit var mapFragment : SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMapBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_map,
            container,
            false
        )

        val application = requireNotNull(activity).application
        val viewModelFactory = MapViewModelFactory(application)
        viewModel = ViewModelProvider(
            this, viewModelFactory).get(MapViewModel::class.java)

        mapFragment = childFragmentManager?.findFragmentById(R.id.google_map_1) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        viewModel.eventShowCarParkList.observe(viewLifecycleOwner, Observer { navigateToList->
            if(navigateToList){
                this.findNavController().navigate(
                    R.id.action_mapFragment_to_carParkListFragment
                )
                viewModel.onShowListComplete()
            }
        })

        viewModel.carParks.observe(viewLifecycleOwner, Observer {
            addMarkers(googleMap)
        })

        viewModel.navigateToSelectedCarPark.observe(viewLifecycleOwner, Observer { carPark->
            if (carPark != null) {
                this.findNavController().navigate(
                    MapFragmentDirections
                        .actionMapFragmentToCarParkDetailFragment(carPark)
                )
                viewModel.displayCarParkDetailComplete()
            }
        })

        return binding.root
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        addMarkers(googleMap)
        zoomInSydney(googleMap)
        setShowDetails(googleMap)
    }

    private fun setShowDetails(googleMap: GoogleMap) {
        googleMap.setOnInfoWindowClickListener { marker ->
            viewModel.navigateToSelectedCarPark(marker.title)
        }
    }

    private fun zoomInSydney(googleMap: GoogleMap) {
        val latitude = -33.962149
        val longitude = 151.132641
        val sydneyLatLng = LatLng(latitude, longitude)
        val zoomLevel = 10f
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydneyLatLng, zoomLevel))
    }

    private fun addMarkers(googleMap: GoogleMap) {
        val app = requireNotNull(activity).application
        if(viewModel.carParks.value != null)
        for(carPark in viewModel.carParks.value!!){
            val latitude2 = carPark.latitude
            val longitude2 = carPark.longitude
            val homeLatLng2 = LatLng(latitude2, longitude2)
            val a  = MarkerOptions().position(homeLatLng2)
                .title(app.applicationContext.getString(R.string.facility_name,carPark.facilityName))
                .snippet(app.applicationContext.getString(R.string.available_spot,carPark.availableSpots.toString()))

            googleMap.addMarker(a)
        }

    }
}