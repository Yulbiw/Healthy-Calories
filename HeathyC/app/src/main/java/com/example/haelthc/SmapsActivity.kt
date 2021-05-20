package com.example.haelthc

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.haelthc.GeoLocation.getAddress
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_smaps.*

class SmapsActivity : AppCompatActivity() {
    private var mMap: GoogleMap? = null
    var mapFragment: SupportMapFragment? = null
    var etplace: EditText? = null
    var tvaddress: TextView? = null
    var btSubmit: TextView? = null
    private lateinit var preference: LangPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smaps)

        etplace = findViewById(R.id.lolo_s)
        btSubmit = findViewById(R.id.bt_location_search_s)
        tvaddress = findViewById(R.id.tv_address_s)
        btSubmit!!.setOnClickListener(View.OnClickListener {
            val address = etplace!!.getText().toString()
            val geolocation = GeoLocation()
            getAddress(address, applicationContext, GeoHandler())

        })
        recode_map_button_s.setOnClickListener {
            Toast.makeText(applicationContext, "Saved Successfully!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, TrendActivity::class.java)
            startActivity(intent)
        }
    }

    private inner class GeoHandler : Handler(), OnMapReadyCallback {
        override fun handleMessage(msg: Message) {
            val address: String?
            address = when (msg.what) {
                1 -> {
                    val bundle = msg.data
                    bundle.getString("address")
                }
                else -> null
            }
            tvaddress!!.text = address
            mapFragment = supportFragmentManager.findFragmentById(R.id.map_s) as SupportMapFragment?
            mapFragment?.getMapAsync(this)
        }

        override fun onMapReady(googleMap: GoogleMap?) {
            mMap = googleMap
            val mGPS: String = (findViewById(R.id.tv_address_s) as TextView).text.toString()
            val splitword = mGPS?.split(",")?.toTypedArray()
            val current_loc = LatLng(splitword?.get(0)?.toDouble(), splitword?.get(1)?.toDouble())
            mMap!!.addMarker(MarkerOptions().position(current_loc).title("Current Position"))
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(current_loc, 16F))
            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(17F), 1500, null)
            splitword?.get(0)?.toDouble()
            splitword?.get(1)?.toDouble()
            // Used Geocoder reverse engineering for get the Address from Array
            val geocoder: Geocoder = Geocoder(applicationContext)
            // Use ? to catch not null with let function()
            val list = splitword?.get(0)?.toDouble()?.let {
                geocoder.getFromLocation(it, splitword.get(1).toDouble(), 1)
            }
            // if list not null
            if (list != null) {
                val address: String = list.get(0).getAddressLine(0)
                google_location_s.append("  $address")
//                tv_address.visibility = View.GONE
            } else {
                google_location_s.append("Null")
                tv_address_s.visibility = View.GONE
            }
            val recSDate: Bundle? = intent.extras
            val dateka = recSDate!!.getString("dateS")
            var userEmail = "";
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                userEmail = user.email.toString()
            }

            FirebaseDatabase.getInstance().reference.child("Snack")
                .addValueEventListener(object :
                    ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        for (snapshot in p0.children) {
                            val pushKey = snapshot.key.toString()
                            val breakbase = snapshot.value as Map<String, Any>
                            val date = breakbase["date"].toString()
                            val mail = breakbase["userEmail"].toString()
                            if (dateka == date && mail == userEmail) {
                                var map = mutableMapOf<String, Any>()
                                map["location"] = google_location_s.text.toString()
                                FirebaseDatabase.getInstance().reference.child("Snack")
                                    .child(pushKey)
                                    .updateChildren(map)
                            }
                        }

                    }
                })
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        preference = LangPreference(newBase!!)
        val lang = preference.getLoginCount()
        super.attachBaseContext(lang?.let { ContextWrapper.wrap(newBase, it) })
    }
}

