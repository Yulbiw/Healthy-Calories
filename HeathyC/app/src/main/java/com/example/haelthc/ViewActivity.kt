package com.example.haelthc

import android.content.Context
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_view.*
import java.lang.Byte.decode
import android.util.Base64
import java.util.*
import kotlin.collections.ArrayList

class ViewActivity : AppCompatActivity() {

    private lateinit var preference: LangPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        val array = ArrayList<ViewItem>()
//        array.add(ViewItem(R.drawable.logo,"123","456","789"))
        var userEmail = "";
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            userEmail = user.email.toString()
        }
        FirebaseDatabase.getInstance().reference.child("Breakfast")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (snapshot in p0.children) {
                        val userBreakfast = snapshot.value as Map<String, Any>
                        val food = userBreakfast["food"].toString()
                        val num = userBreakfast["quantity"].toString()
                        val date = userBreakfast["date"].toString()
                        val location = userBreakfast["location"].toString()
                        val userMail = userBreakfast["userEmail"].toString()
                        val photo = userBreakfast["photo"].toString()
                        if (userMail == userEmail) {
                            var t1 = ""+ food +num
                            var t2 = date
                            var t3 = "Breakfast " +location
                            array.add(ViewItem(photo, t1, t2,t3))
                        }
                    }
                    FirebaseDatabase.getInstance().reference.child("Lunch")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                for (snapshot in p0.children) {
                                    val userLunch = snapshot.value as Map<String, Any>
                                    val food = userLunch["food"].toString()
                                    val num = userLunch["quantity"].toString()
                                    val date = userLunch["date"].toString()
                                    val location = userLunch["location"].toString()
                                    val userMail = userLunch["userEmail"].toString()
                                    val photo = userLunch["photo"].toString()
                                    if (userMail == userEmail) {
                                        var t1 = ""+ food +num
                                        var t2 = date
                                        var t3 = "Lunch "+location
                                        array.add(ViewItem(photo, t1, t2,t3))
                                    }
                                }
                                FirebaseDatabase.getInstance().reference.child("Dinner")
                                    .addValueEventListener(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError) {
                                        }

                                        override fun onDataChange(p0: DataSnapshot) {
                                            for (snapshot in p0.children) {
                                                val userDinner =
                                                    snapshot.value as Map<String, Any>
                                                val food = userDinner["food"].toString()
                                                val num = userDinner["quantity"].toString()
                                                val date = userDinner["date"].toString()
                                                val location = userDinner["location"].toString()
                                                val userMail = userDinner["userEmail"].toString()
                                                val photo = userDinner["photo"].toString()
                                                if (userMail == userEmail) {
                                                    var t1 = ""+ food +num
                                                    var t2 = date
                                                    var t3 = "Dinner "+location
                                                    array.add(ViewItem(photo, t1, t2,t3))
                                                }
                                            }
                                            FirebaseDatabase.getInstance().reference.child(
                                                "Snack"
                                            )
                                                .addValueEventListener(object :
                                                    ValueEventListener {
                                                    override fun onCancelled(p0: DatabaseError) {
                                                    }

                                                    override fun onDataChange(p0: DataSnapshot) {
                                                        for (snapshot in p0.children) {
                                                            val userSnack =
                                                                snapshot.value as Map<String, Any>
                                                            val food = userSnack["food"].toString()
                                                            val num = userSnack["quantity"].toString()
                                                            val date = userSnack["date"].toString()
                                                            val location = userSnack["location"].toString()
                                                            val userMail =
                                                                userSnack["userEmail"].toString()
                                                            val photo = userSnack["photo"].toString()
                                                            if (userMail == userEmail) {
                                                                var t1 = ""+ food+"" +num
                                                                var t2 = date
                                                                var t3 = "Snack "+location
                                                                array.add(ViewItem(photo, t1, t2,t3))
                                                            }
                                                        }

                                                        FirebaseDatabase.getInstance().reference.child(
                                                            "Exercise"
                                                        )
                                                            .addValueEventListener(object :
                                                                ValueEventListener {
                                                                override fun onCancelled(p0: DatabaseError) {
                                                                }

                                                                override fun onDataChange(p0: DataSnapshot) {
                                                                    for (snapshot in p0.children) {
                                                                        val userEx = snapshot.value as Map<String, Any>
                                                                        val food = userEx["exercise"].toString()
                                                                        val num = userEx["minute"].toString()
                                                                        val date = userEx["date"].toString()
                                                                        val location = userEx["location"].toString()
                                                                        val userMail = userEx["userEmail"].toString()
                                                                        val photo = userEx["photo"].toString()
                                                                        if (userMail == userEmail){
                                                                            var t1 = ""+ food +num +"min"
                                                                            var t2 = date
                                                                            var t3 = "Exercise "+location
                                                                            array.add(ViewItem(photo, t1, t2,t3))
                                                                        }
                                                                    }
                                                                    val recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
                                                                    recycler_view.adapter = ViewAdapter(array)
                                                                    recycler_view.layoutManager = LinearLayoutManager(applicationContext)
                                                                    recycler_view.setHasFixedSize(true)

                                                                    FirebaseDatabase.getInstance()
                                                                }
                                                            })
                                                    }
                                                })
                                        }
                                    })
                            }
                        })
                }
            })

    }

    override fun attachBaseContext(newBase: Context?) {
        preference = LangPreference(newBase!!)
        val lang = preference.getLoginCount()
        super.attachBaseContext(lang?.let { ContextWrapper.wrap(newBase, it) })
    }
}
