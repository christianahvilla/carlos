package com.christianahvilla.carlos

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.layout_detail.*


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class DetailActivity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var mMap: GoogleMap
    private var locationManager: LocationManager? = null
    val MY_PERMISSIONS_REQUEST_LOCATION = 99


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        tv_detail_client.text = intent.getStringExtra("client")
        tv_detail_domain.text = intent.getStringExtra("domain")
        tv_detail_price.text = intent.getIntExtra("price", 0).toString()
        tv_detail_kind.text = intent.getStringExtra("kind")

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION)

            }
        } else {
            return
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@DetailActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        mMap.isMyLocationEnabled = true

                        mMap.setOnMyLocationClickListener { location ->
                            val center = CameraUpdateFactory.newLatLng(
                                LatLng(
                                    location.latitude,
                                    location.longitude
                                )
                            )
                            val zoom = CameraUpdateFactory.zoomTo(15f)
                            mMap.moveCamera(center)
                            mMap.animateCamera(zoom)
                        }
                    }
                } else {
                    Toast.makeText(this, "Permiso Denegado", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.isMyLocationEnabled = true

        mMap.setOnMyLocationClickListener { location ->
            val center = CameraUpdateFactory.newLatLng(
                LatLng(
                    location.latitude,
                    location.longitude
                )
            )
            val zoom = CameraUpdateFactory.zoomTo(15f)
            mMap.moveCamera(center)
            mMap.animateCamera(zoom)
        }

        val client = intent.getStringExtra("client")
        val kind = intent.getStringExtra("kind")
        val number = intent.getStringExtra("number")
        val street = intent.getStringExtra("street")
        val zipCode = intent.getStringExtra("zipCode")
        val state = intent.getStringExtra("state")
        val neighborhood = intent.getStringExtra("neighborhood")

        val address = getLocationFromAddress(this,"$number, $street, $neighborhood, $state, $zipCode")
        println("$number, $street, $neighborhood, $state, $zipCode")
        mMap.addMarker(MarkerOptions().position(address!!).title("$client, $kind"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
    }

    private fun getLocationFromAddress(context: Context?, strAddress: String?): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var point: LatLng? = null
        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location: Address = address[0]
            location.latitude
            location.longitude
            point = LatLng(location.getLatitude(), location.getLongitude())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return point
    }

}