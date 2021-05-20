package com.example.haelthc

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_exercise.*
import java.io.ByteArrayOutputStream
import java.util.*

class ExerciseActivity: AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private lateinit var preference: LangPreference
    lateinit var currentPhotoPath: String

    //up to database
    lateinit var inputmin: EditText
    //    lateinit var add_but_ex_loca :TextView
    lateinit var rec_but :Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_menu)

        //camera
        val add_photo_e = findViewById<ImageView>(R.id.add_photo_e)
        add_photo_e.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,123)
        }

        //spinner
        val exercise = resources.getStringArray(R.array.arrExercise)
        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,exercise)
        spinner_ex.adapter = adapter
        spinner_ex
        spinner_ex.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                itemex.text = exercise[position]

                if (itemex.text=="Running"){
                    item2ex.text = "10.0"
                }
                if (itemex.text=="Walking"){
                    item2ex.text = "5.0"
                }
                if (itemex.text=="Cycling"){
                    item2ex.text = "8.7"
                }
                if (itemex.text=="Swimming"){
                    item2ex.text = "7.4"
                }
                if (itemex.text=="Yoga"){
                    item2ex.text = "5.0"
                }
            }
        }


        //calendar
        val calen_ex = Calendar.getInstance()
        val year = calen_ex.get(Calendar.YEAR)
        val month = calen_ex.get(Calendar.MONTH)
        val day = calen_ex.get(Calendar.DAY_OF_MONTH)

        val add_date_ex = findViewById<TextView>(R.id.add_date_ex)
        add_date_ex.setOnClickListener{
            val thisdate = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view, exYear, exMonth, exDay ->
                    add_date_ex.setText(""+exYear +"/" +(exMonth+1) +"/" +exDay)
                }, year,month,day)
            thisdate.show()
        }


        var userName = ""; var userEmail = ""; var userId = "";
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            userName = user.displayName.toString()
            userEmail = user.email.toString()
        }

        //up to database
        inputmin = findViewById(R.id.inputmin)
        rec_but = findViewById(R.id.rec_but)

        rec_but.setOnClickListener {
//            saveExercise()
            val mininput = inputmin.text.toString()
            val date = add_date_ex.text.toString()
            val location = ""
            val exercise = itemex.text.toString()
            val cal = item2ex.text.toString()

            var map = mutableMapOf<String,Any>()
//            map["userId"] = userId
            map["userEmail"] = userEmail
            map["exercise"] = exercise
            map["cal"] = cal
            map["minute"] = mininput
            map["date"] = date
            map["location"] = location
            map["photo"] = currentPhotoPath.toString()

            FirebaseDatabase.getInstance().reference
                .child("Exercise")
                .push()
                .setValue(map)
//            Toast.makeText(applicationContext,"Saved Successfully!", Toast.LENGTH_LONG).show()
            val intent = Intent(this,MapsActivity::class.java)
            intent.putExtra("date",date)
            startActivity(intent)
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val camer = findViewById<ImageView>(R.id.add_photo_e)
        if (requestCode == 123){
            var caam = data?.extras?.get("data") as Bitmap
            camer.setImageBitmap(caam)

            val byte = ByteArrayOutputStream()
            caam.compress(Bitmap.CompressFormat.PNG, 100, byte)
            currentPhotoPath = Base64.encodeToString(byte.toByteArray(), Base64.DEFAULT)
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