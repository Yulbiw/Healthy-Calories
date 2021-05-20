package com.example.haelthc

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_breakfast.*
import kotlinx.android.synthetic.main.activity_snack.*
import java.io.ByteArrayOutputStream
import java.util.*

class SnackActivity: AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private lateinit var preference: LangPreference
    lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.snack_menu)

        //camera
        val add_photo_s = findViewById<ImageView>(R.id.add_photo_s)
        add_photo_s.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,123)
        }

        //calendar
        val calen_sn = Calendar.getInstance()
        val year = calen_sn.get(Calendar.YEAR)
        val month = calen_sn.get(Calendar.MONTH)
        val day = calen_sn.get(Calendar.DAY_OF_MONTH)

        val add_date_sn = findViewById<TextView>(R.id.add_date_sn)
        add_date_sn.setOnClickListener{
            val thisdate = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view, snYear, snMonth, snDay ->
                    add_date_sn.setText(""+snYear +"/" +(snMonth+1) +"/" +snDay)
                }, year,month,day)
            thisdate.show()
        }

        //search from database
        val add_but_mes = findViewById<TextView>(R.id.add_but_mes)
        add_but_mes.setOnClickListener {
            SearchData()
        }
        var add_but_numsn = findViewById<TextView>(R.id.add_num_sn)
        val item3ofsn = findViewById<TextView>(R.id.item3ofsn)
        add_but_numsn.setOnClickListener {
            item3ofsn.text = "x"+num_food_sn.text
        }

        //up to db
        var userName = ""; var userEmail = ""; var userId = "";
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            userName = user.displayName.toString()
            userEmail = user.email.toString()
        }

        rec_sn.setOnClickListener {
            //val photo
            val food = itemofsn.text.toString()
            val cal = item2ofsn.text.toString()
            val quantit = num_food_sn.text.toString()
            val date = add_date_sn.text.toString()
            val location = ""

            var map = mutableMapOf<String,Any>()
//            map["userId"] = userId
            map["userEmail"] = userEmail
            map["food"] = food
            map["cal"] = cal
            map["quantity"] = quantit
            map["date"] = date
            map["location"] = location
            map["photo"] = currentPhotoPath.toString()

            FirebaseDatabase.getInstance().reference
                .child("Snack")
                .push()
                .setValue(map)
            Toast.makeText(applicationContext,"Saved Successfully!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, SmapsActivity::class.java)
            intent.putExtra("dateS",date)
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

    //get data from database
    fun SearchData(){
        FirebaseDatabase.getInstance().reference
            .child("Food")
//            .child("Food").child(search_food_b.text.toString())
//            .equalTo(search_food_b.text.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    if( p0.hasChild(search_food_sn.text.toString()) ){
                        var map = p0.child(search_food_sn.text.toString()).value as Map<String, Any>
                        itemofsn.text=map["food"].toString()
                        item2ofsn.text=map["cal"].toString()
                    }else {
                        itemofsn.text = "There is no this menu available."
                        item2ofsn.text= ""
                    }
                }

            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val camer = findViewById<ImageView>(R.id.add_photo_s)
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