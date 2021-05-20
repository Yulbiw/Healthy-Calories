package com.example.haelthc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_foryou.*

class ForyouActivity: AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    private val minuteList : MutableList<String> = mutableListOf("10 min","20 min","30 min ","40 min","60 min")

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private lateinit var preference: LangPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.foryou_menu)

        generateMin()

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

    private fun generateMin(){
        foryou_min.setOnClickListener {
            val random = (0 until minuteList.size).random()
            foryou_min.text = minuteList[random]
            minofrun.text = foryou_min.text
            yogaofmin.text = foryou_min.text

            if(foryou_min.text == "10 min"){
                dt_run_cal.text = "Calories Burn : 99"
                dt_yoga_cal.text = "Calories Burn : 40"
            }
            else if(foryou_min.text == "20 min"){
                dt_run_cal.text = "Calories Burn : 198"
                dt_yoga_cal.text = "Calories Burn : 80"
            }
            else if(foryou_min.text == "30 min"){
                dt_run_cal.text = "Calories Burn : 298"
                dt_yoga_cal.text = "Calories Burn : 120"
            }
            else if(foryou_min.text == "40 min"){
                dt_run_cal.text = "Calories Burn : 397"
                dt_yoga_cal.text = "Calories Burn : 160"
            }
            else if(foryou_min.text == "60 min"){
                dt_run_cal.text = "Calories Burn : 596"
                dt_yoga_cal.text = "Calories Burn : 240"
            }
        }
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