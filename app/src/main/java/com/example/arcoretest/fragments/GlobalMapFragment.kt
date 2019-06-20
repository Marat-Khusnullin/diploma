package com.example.arcoretest.fragments

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.arcoretest.MapsPresenter
import com.example.arcoretest.R
import com.example.arcoretest.TestData
import com.example.arcoretest.WaterFactory
import com.example.arcoretest.interfaces.LocationCallbackInterface
import com.example.arcoretest.model.LocationClassModel
import com.gc.materialdesign.views.ButtonRectangle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.*

class GlobalMapFragment : Fragment(), OnMapReadyCallback, LocationCallbackInterface {



    private var mMap: GoogleMap? = null
    private var locationManager: LocationManager? = null
    private val locationListener: LocationListener? = null
    private var marker: Marker? = null
    private var mapsPresenter: MapsPresenter? = null
    private var electrButton: ButtonRectangle? = null
    private var waterButton: ButtonRectangle? = null
    private var gasButton: ButtonRectangle? = null
    private var dataButton: ButtonRectangle? = null
    private var electList: LinkedList<Polyline>? = null
    private var waterList: LinkedList<Polyline>? = null
    private var gasList: LinkedList<Polyline>? = null
    private var dataList: LinkedList<Polyline>? = null

    private var isElecClicked: Boolean = false
    private var isWaterClicked: Boolean = false
    private var isGasClicked: Boolean = false
    private var isDataClicked: Boolean = false


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.global_map_fragment_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
        val mapFragment = this.childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        electrButton = activity!!.findViewById(R.id.button_electricity)
        waterButton = activity!!.findViewById(R.id.button_water)
        gasButton = activity!!.findViewById(R.id.button_gas)
        dataButton = activity!!.findViewById(R.id.button_data)

        electList = LinkedList()
        waterList = LinkedList()
        dataList = LinkedList()
        gasList = LinkedList()

        waterButton!!.setOnClickListener(View.OnClickListener {
            isWaterClicked = !isWaterClicked
            if(isWaterClicked) {
                var list : List<PolylineOptions> = LinkedList()
                fillWater(list)
            }
            else {
                for(element in waterList!!)
                    element.remove()
            }

        })

        electrButton!!.setOnClickListener(View.OnClickListener {
            isElecClicked = !isElecClicked
            if(isElecClicked) {
                var list : List<PolylineOptions> = LinkedList()
                fillElectricity(list)
            }
            else {
                for(element in electList!!)
                    element.remove()
            }

        })

        gasButton!!.setOnClickListener(View.OnClickListener {
            isGasClicked = !isGasClicked
            if(isGasClicked) {
                var list : List<PolylineOptions> = LinkedList()
                fillGas(list)
            } else {
                for(element in gasList!!)
                    element.remove()
            }

        })

        dataButton!!.setOnClickListener(View.OnClickListener {
            isDataClicked = !isDataClicked
            if(isDataClicked) {
                var list : List<PolylineOptions> = LinkedList()
                fillData(list)
            } else {
                for(element in dataList!!)
                    element.remove()
            }
        })
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
        marker = mMap!!.addMarker(MarkerOptions().position(LatLng(1.0, 1.0)))
        var locationClass = LocationClassModel(activity, this);
        locationClass.updateLocation()

    }

    override fun updateLocation(location: Location?) {
        val myLocation = LatLng(55.792149, 49.122263)
        marker!!.position = myLocation
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(myLocation))
    }

    fun fillWater(list: List<PolylineOptions>){
        var testData = TestData()
        var list1: List<PolylineOptions> = WaterFactory.getWaterPolylineList(testData.testServerWaterObjects);
        for(item in list1) {
            waterList!!.add(mMap!!.addPolyline(item))
        }
    }

    fun fillElectricity(list: List<PolylineOptions>){

    }

    fun fillGas(list: List<PolylineOptions>){

    }

    fun fillData(list: List<PolylineOptions>){

    }

}