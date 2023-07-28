package com.example.finalproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    val BASE_URL = "https://data.petabencana.id/"
    var data: Intent? = null

    private val settingsActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                data = result.data
                val isNightModeEnabled = data?.getBooleanExtra("night_mode", false) ?: false

                if (isNightModeEnabled) {
                    // Aktifkan mode malam
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    // Aktifkan mode light
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                recreate()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //getTheData()
        getTheDummy()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val bottomSheet : View = findViewById(R.id.bottom_sheet)
        val bottomSheetBehavior : BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
        val btnX : ImageButton = findViewById(R.id.btnClose)
        val searchBar : LinearLayout = findViewById(R.id.searchbarLayout)
        val setting : ImageButton = findViewById(R.id.setting)


        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.isDraggable = false
                    btnX.visibility = View.VISIBLE
                    searchBar.visibility = View.GONE
                } else {
                    btnX.setOnClickListener {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        btnX.visibility = View.GONE
                        bottomSheetBehavior.isDraggable = true
                    }
                    searchBar.visibility = View.VISIBLE
                }


            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        setting.setOnClickListener {
            val intent = Intent(this, ThemeSelection::class.java)
            if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                intent.putExtra("night_mode", true)
            }
            else{
                intent.putExtra("night_mode", false)
            }
            settingsActivityResult.launch(intent)
        }

    }

    private fun getTheDummy() {
        val banjir1 = BanjirDummy("5", "RW 09", "Jakarta", "3174040004009000", "2022-12-29 00:24:23", "GROGOL", 1)
        val banjir2 = BanjirDummy("6", "RW 10", "Jakarta", "3248288010130201", "2023-07-26 13:53:52", "PONDOK LABU", 1)
        val banjir3 = BanjirDummy("7", "RW 11", "Jakarta", "3568288012130202", "2023-07-28 13:53:52", "PONDOK LABU", 1)
        val list : MutableList<BanjirDummy> = mutableListOf(banjir1, banjir2, banjir3)
        val listView : ListView = findViewById(R.id.listView)
        val adapter = AdapterDummy(this@MainActivity, list)
        listView.adapter = adapter
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMinZoomPreference(14.0f)

        val listofMarker = mutableListOf<LatLng>()
        val jakarta = LatLng(-6.158925, 106.7917869997)
        val jakarta2 = LatLng(-6.1556540002, 106.7950980004)
        val jakarta3 = LatLng(-6.1529824, 106.794929299)
        listofMarker.add(jakarta)
        listofMarker.add(jakarta2)
        listofMarker.add(jakarta3)

        for (i in listofMarker){
            mMap.addMarker(
                MarkerOptions()
                .position(i)
                .title("Banjir"))
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(jakarta))
    }

    private fun getTheData() {
        // Buat instance Retrofit dan interface untuk API
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // Ganti URL dengan URL dari API yang sesuai
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        // Panggil API dan proses responsnya
        val callData = retrofit.getBencana()
        callData.enqueue(object : Callback<List<Banjir>?> {
            override fun onResponse(call: Call<List<Banjir>?>, response: Response<List<Banjir>?>) {
                if (response.isSuccessful){
                    val list = response.body()
                    if (list != null) {
                        val listView : ListView = findViewById(R.id.listView)
                        val adapter = Adapter(this@MainActivity, list)
                        listView.adapter = adapter
                    }
                }
                else{
                    Log.e("Not Successful", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Banjir>?>, t: Throwable) {
                Log.e("onFailure", "onFailure: ${t.message}")
            }
        })
    }
}