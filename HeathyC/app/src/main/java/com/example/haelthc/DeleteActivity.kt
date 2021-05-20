package com.example.haelthc

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_delete.*
import java.util.*

class DeleteActivity : AppCompatActivity() {

    private lateinit var preference: LangPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        //calendar
        val calen = Calendar.getInstance()
        val year = calen.get(Calendar.YEAR)
        val month = calen.get(Calendar.MONTH)
        val day = calen.get(Calendar.DAY_OF_MONTH)

        val delete_date = findViewById<TextView>(R.id.delete_date)
        delete_date.setOnClickListener{
            val thisdate = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view, Year, Month, Day ->
                    delete_date.setText(""+Year +"/" +(Month+1) +"/" +Day)
                    var keepDelete="";
                    val list = arrayOf("Breakfast","Lunch","Dinner","Snack","Exercise")
                    var adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list)
                    delete_spinner.adapter = adapter
                    delete_spinner
                    delete_spinner.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }

                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            keepDelete= list[position]

                            if (keepDelete=="Breakfast"){
                                var userEmail = "";
                                val user = FirebaseAuth.getInstance().currentUser
                                user?.let {
                                    userEmail = user.email.toString()
                                }
                                val button = findViewById<TextView>(R.id.but_delete)
                                button.setOnClickListener {
                                    FirebaseDatabase.getInstance().reference.child("Breakfast")
                                        .addValueEventListener(object :
                                            ValueEventListener {
                                            override fun onCancelled(p0: DatabaseError) {
                                            }

                                            override fun onDataChange(p0: DataSnapshot) {
                                                for (snap in p0.children) {
                                                    val key = snap.key.toString()
                                                    val Demap = snap.value as Map<String, Any>
                                                    val date = Demap["date"].toString()
                                                    val mail = Demap["userEmail"].toString()
                                                    if ((""+Year +"/" +(Month+1) +"/" +Day) == date && mail == userEmail) {
                                                        FirebaseDatabase.getInstance().reference.child("Breakfast")
                                                            .child(key)
                                                            .removeValue()
                                                        Toast.makeText(applicationContext, "Delete Successfully!", Toast.LENGTH_LONG).show()
                                                    }
                                                }

                                            }
                                        })
                                }
                            }
                            if (keepDelete=="Lunch"){
                                var userEmail = "";
                                val user = FirebaseAuth.getInstance().currentUser
                                user?.let {
                                    userEmail = user.email.toString()
                                }
                                val button = findViewById<TextView>(R.id.but_delete)
                                button.setOnClickListener {
                                    FirebaseDatabase.getInstance().reference.child("Lunch")
                                        .addValueEventListener(object :
                                            ValueEventListener {
                                            override fun onCancelled(p0: DatabaseError) {
                                            }

                                            override fun onDataChange(p0: DataSnapshot) {
                                                for (snap in p0.children) {
                                                    val key = snap.key.toString()
                                                    val Demap = snap.value as Map<String, Any>
                                                    val date = Demap["date"].toString()
                                                    val mail = Demap["userEmail"].toString()
                                                    if ((""+Year +"/" +(Month+1) +"/" +Day) == date && mail == userEmail) {
                                                        FirebaseDatabase.getInstance().reference.child("Lunch")
                                                            .child(key)
                                                            .removeValue()
                                                        Toast.makeText(applicationContext, "Delete Successfully!", Toast.LENGTH_LONG).show()
                                                    }
                                                }

                                            }
                                        })
                                }
                            }
                            if (keepDelete=="Dinner"){
                                var userEmail = "";
                                val user = FirebaseAuth.getInstance().currentUser
                                user?.let {
                                    userEmail = user.email.toString()
                                }
                                val button = findViewById<TextView>(R.id.but_delete)
                                button.setOnClickListener {
                                    FirebaseDatabase.getInstance().reference.child("Dinner")
                                        .addValueEventListener(object :
                                            ValueEventListener {
                                            override fun onCancelled(p0: DatabaseError) {
                                            }

                                            override fun onDataChange(p0: DataSnapshot) {
                                                for (snap in p0.children) {
                                                    val key = snap.key.toString()
                                                    val Demap = snap.value as Map<String, Any>
                                                    val date = Demap["date"].toString()
                                                    val mail = Demap["userEmail"].toString()
                                                    if ((""+Year +"/" +(Month+1) +"/" +Day) == date && mail == userEmail) {
                                                        FirebaseDatabase.getInstance().reference.child("Dinner")
                                                            .child(key)
                                                            .removeValue()
                                                        Toast.makeText(applicationContext, "Delete Successfully!", Toast.LENGTH_LONG).show()
                                                    }
                                                }

                                            }
                                        })
                                }
                            }
                            if (keepDelete=="Snack"){
                                var userEmail = "";
                                val user = FirebaseAuth.getInstance().currentUser
                                user?.let {
                                    userEmail = user.email.toString()
                                }
                                val button = findViewById<TextView>(R.id.but_delete)
                                button.setOnClickListener {
                                    FirebaseDatabase.getInstance().reference.child("Snack")
                                        .addValueEventListener(object :
                                            ValueEventListener {
                                            override fun onCancelled(p0: DatabaseError) {
                                            }

                                            override fun onDataChange(p0: DataSnapshot) {
                                                for (snap in p0.children) {
                                                    val key = snap.key.toString()
                                                    val Demap = snap.value as Map<String, Any>
                                                    val date = Demap["date"].toString()
                                                    val mail = Demap["userEmail"].toString()
                                                    if ((""+Year +"/" +(Month+1) +"/" +Day) == date && mail == userEmail) {
                                                        FirebaseDatabase.getInstance().reference.child("Snack")
                                                            .child(key)
                                                            .removeValue()
                                                        Toast.makeText(applicationContext, "Delete Successfully!", Toast.LENGTH_LONG).show()
                                                    }
                                                }

                                            }
                                        })
                                }
                            }
                            if (keepDelete=="Exercise"){
                                var userEmail = "";
                                val user = FirebaseAuth.getInstance().currentUser
                                user?.let {
                                    userEmail = user.email.toString()
                                }
                                val button = findViewById<TextView>(R.id.but_delete)
                                button.setOnClickListener {
                                    FirebaseDatabase.getInstance().reference.child("Exercise")
                                        .addValueEventListener(object :
                                            ValueEventListener {
                                            override fun onCancelled(p0: DatabaseError) {
                                            }

                                            override fun onDataChange(p0: DataSnapshot) {
                                                for (snap in p0.children) {
                                                    val key = snap.key.toString()
                                                    val Demap = snap.value as Map<String, Any>
                                                    val date = Demap["date"].toString()
                                                    val mail = Demap["userEmail"].toString()
                                                    if ((""+Year +"/" +(Month+1) +"/" +Day) == date && mail == userEmail) {
                                                        FirebaseDatabase.getInstance().reference.child("Exercise")
                                                            .child(key)
                                                            .removeValue()
                                                        Toast.makeText(applicationContext, "Delete Successfully!", Toast.LENGTH_LONG).show()
                                                    }
                                                }

                                            }
                                        })
                                }
                            }
                        }
                    }
                }, year,month,day)
            thisdate.show()
        }



    }

    override fun attachBaseContext(newBase: Context?) {
        preference = LangPreference(newBase!!)
        val lang = preference.getLoginCount()
        super.attachBaseContext(lang?.let { ContextWrapper.wrap(newBase, it) })
    }
}
