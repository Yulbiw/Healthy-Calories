package com.example.haelthc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private lateinit var preference: LangPreference

    //up to database
    lateinit var nameFood: EditText
    lateinit var calorieuUp :EditText
    lateinit var butUpdate :TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_menu)

        //up to database
        nameFood = findViewById(R.id.ubname_ac)
        calorieuUp = findViewById(R.id.cal_up)
        butUpdate = findViewById(R.id.update_but_update)

        butUpdate.setOnClickListener {
            saveFood()
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    //up to database
    fun saveFood(){
        val foodName = nameFood.text.toString()
        val numCal = calorieuUp.text.toString()
        if (foodName.isEmpty()){
            nameFood.error = "Please enter food name!"
            return
        }
        if (numCal.isEmpty()){
            calorieuUp.error = "Please enter food calorie!"
            return
        }

        var map = mutableMapOf<String,Any>()
         map["food"] = foodName
         map["cal"] = numCal

        FirebaseDatabase.getInstance().reference
            .child("Food")
            .child(foodName)
            .setValue(map)
        Toast.makeText(applicationContext,"Saved Successfully!", Toast.LENGTH_LONG).show()
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_activity -> {
                Toast.makeText(this, "Activity", Toast.LENGTH_SHORT).show()
                val intent3 = Intent(this, Healthyactivity::class.java)
                startActivity(intent3)
            }
            R.id.nav_trend -> {
                Toast.makeText(this, "Trend", Toast.LENGTH_SHORT).show()
                val intent4 = Intent(this, TrendActivity::class.java)
                startActivity(intent4)
            }
            R.id.nav_foryou -> {
                Toast.makeText(this, "For you", Toast.LENGTH_SHORT).show()
                val intent5 = Intent(this, ForyouActivity::class.java)
                startActivity(intent5)
            }
            R.id.nav_setting -> {
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show()
                val intent6 = Intent(this, SettingActivity::class.java)
                startActivity(intent6)
            }
            R.id.nav_update -> {
                Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show()
                val intent8 = Intent(this, UpdateActivity::class.java)
                startActivity(intent8)
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Logout successfully", Toast.LENGTH_SHORT).show()
                val intent7 = Intent(this, Healthymain::class.java)
                startActivity(intent7)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun attachBaseContext(newBase: Context?) {
        preference = LangPreference(newBase!!)
        val lang = preference.getLoginCount()
        super.attachBaseContext(lang?.let { ContextWrapper.wrap(newBase, it) })
    }
}
