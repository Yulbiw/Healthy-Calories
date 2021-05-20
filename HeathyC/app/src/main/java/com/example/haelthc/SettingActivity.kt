package com.example.haelthc

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
//import com.example.vicky.multilanguageexample.MyPreference
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_setting.*
import java.io.ByteArrayOutputStream
import java.util.*

class SettingActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private lateinit var preference: LangPreference
    val languageList = arrayOf("en", "th")
    lateinit var context: Context
    var currentPhotoPath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_menu)

        context = this
        preference = LangPreference(this)
        val spinner = findViewById<Spinner>(R.id.change_language)
        val lang = preference.getLoginCount()
        val index = languageList.indexOf(lang)
        if(index >= 0){
            spinner.setSelection(index)
        }
        spinner.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,languageList)

        //spinner
        val ops = resources.getStringArray(R.array.arrGender)
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ops)
        gender_set.adapter = adapter
        gender_set
        gender_set.onItemSelectedListener = object :
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
                 itemSex.text = ops[position]
            }
        }

        //calendar
        val calen = Calendar.getInstance()
        val year = calen.get(Calendar.YEAR)
        val month = calen.get(Calendar.MONTH)
        val day = calen.get(Calendar.DAY_OF_MONTH)

        val bod_set = findViewById<TextView>(R.id.bod_set)
        bod_set.setOnClickListener{
            val bdate = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view, Year, Month, Day ->
                    bod_set.setText(""+Day +"/" +(Month+1) +"/" +Year)
                }, year,month,day)
            bdate.show()
        }

        //camera
        val add_photo_set = findViewById<ImageView>(R.id.add_photo_set)
        add_photo_set.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,123)

        }

        //up to db
        var userName = ""; var userEmail = ""; var userId = "";
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            userName = user.displayName.toString()
            userEmail = user.email.toString()
            userId = user.uid.toString()
            email_set.text = user.email.toString()
            nametext_set.text=user.displayName.toString()
        }
        update_set.setOnClickListener {
            preference.setLoginCount(languageList[spinner.selectedItemPosition])
            //val photo
            val name = nametext_set.text.toString()
            val gender = itemSex.text.toString()
            val bday = bod_set.text.toString()
            val hight = hight_set.text.toString()
            val weight = weight_set.text.toString()
//            val email = userEmail.toString()

            var map = mutableMapOf<String,Any>()
//            map["userId"] = userId
            map["userEmail"] = userEmail
            map["name"] = name
            map["gender"] = gender
            map["bday"] = bday
            map["hight"] = hight
            map["weight"] = weight
            map["photo"] = currentPhotoPath.toString()

            FirebaseDatabase.getInstance().reference
                .child("Setting")
                .child(userId)
                .setValue(map)
            Toast.makeText(applicationContext,"Saved Successfully!", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Healthyactivity::class.java))
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
        val camer = findViewById<ImageView>(R.id.add_photo_set)
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
