package com.example.vibratebreath

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.vibratebreath.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.CameraPosition

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    /*
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap;

        // GENERAMOS UNA LOCALIZACION
        val padmaYoga = LatLng(-33.44327491640722, -70.58550890680742)
        val espacioYeken = LatLng(-33.434541868909136, -70.59142723358758)
        val centroBudista = LatLng(-33.434903253825006, -70.57569314531838)

        //AGREGAMOS UN MARCADOR EN EL MAPA
        mMap.addMarker(MarkerOptions().position(padmaYoga).title("PADMA Yoga"))
        mMap.addMarker(MarkerOptions().position(espacioYeken).title("Espacio Yeken"))
        mMap.addMarker(MarkerOptions().position(centroBudista).title("Centro Budista"))
        //POSICIONAMOS LA CAMARA EN LA LOCALIZACION
        mMap.moveCamera(CameraUpdateFactory.newLatLng(padmaYoga))

        // Construye una CameraPosition.
        val cameraPosition = CameraPosition.Builder()
            .target(padmaYoga) // Establece el centro del mapa en PADMA Yoga
            .zoom(17f) // Establece el zoom
            .bearing(90f) // Establece la orientación de la cámara al este
            .tilt(30f) // Establece la inclinación de la cámara a 30 grados
            .build() // Crea una CameraPosition a partir del constructor
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

}