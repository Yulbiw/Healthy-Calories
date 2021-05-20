package com.example.haelthc.Fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import com.example.haelthc.DeleteActivity
import com.example.haelthc.R
import com.example.haelthc.ViewActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_day.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalField
import java.time.temporal.WeekFields
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DayFragment : Fragment() {

    lateinit var mAuth: FirebaseAuth


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_day, container, false)
        pieChart(root, 0.0)
        //calendar
        val calen_b = Calendar.getInstance()
        val year = calen_b.get(Calendar.YEAR)
        val month = calen_b.get(Calendar.MONTH)
        val day = calen_b.get(Calendar.DAY_OF_MONTH)

        val viewCal = root.findViewById<TextView>(R.id.viewCal)

        viewCal.setOnClickListener {
            val intent = Intent (getActivity(), ViewActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        val delete = root.findViewById<TextView>(R.id.viewDelete)
        delete.setOnClickListener {
            val intent = Intent (getActivity(), DeleteActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        var addTdate = root.findViewById<TextView>(R.id.addTdate)
        addTdate.setOnClickListener {
            activity?.let { it1 ->
                DatePickerDialog(it1, DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                    addTdate.text = "" + mYear + "/" + (mMonth + 1) + "/" + mDay

                    var keDate = 0.0
                    if(1<= mDay && mDay <=7){
                        keDate = 1.0
                    }
                    if(8<= mDay && mDay <=14){
                        keDate = 2.0
                    }
                    if(15<= mDay && mDay <=21){
                        keDate = 3.0
                    }
                    if(22<= mDay && mDay <=28){
                        keDate = 4.0
                    }
                    if(29<= mDay && mDay <=31){
                        keDate = 5.0
                    }

                    var userEmail = "";
                    var userId = "";
                    var totalCal = 0.0
                    var totalEx = 0.0
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let {
                        userEmail = user.email.toString()
                        userId = user.uid
                    }

                    var temp = FirebaseDatabase.getInstance().reference.child("Breakfast")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                for (snapshot in p0.children) {
                                    val userBreakfast = snapshot.value as Map<String, Any>
                                    val cal = userBreakfast["cal"].toString().toDouble()
                                    val num = userBreakfast["quantity"].toString().toDouble()
                                    val date = userBreakfast["date"].toString()
                                    val userMail = userBreakfast["userEmail"].toString()
                                    if (userMail == userEmail && ("" + mYear + "/" + (mMonth + 1) + "/" + mDay) == date) {
                                        totalCal += cal * num
                                    }
                                }
                                FirebaseDatabase.getInstance().reference.child("Lunch")
                                    .addValueEventListener(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError) {
                                        }

                                        override fun onDataChange(p0: DataSnapshot) {
                                            for (snapshot in p0.children) {
                                                val userLunch = snapshot.value as Map<String, Any>
                                                val cal = userLunch["cal"].toString().toDouble()
                                                val num = userLunch["quantity"].toString().toDouble()
                                                val date = userLunch["date"].toString()
                                                val userMail = userLunch["userEmail"].toString()
                                                if (userMail == userEmail && ("" + mYear + "/" + (mMonth + 1) + "/" + mDay) == date) {
                                                    totalCal += cal * num
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
                                                            val cal = userDinner["cal"].toString()
                                                                .toDouble()
                                                            val num =
                                                                userDinner["quantity"].toString()
                                                                    .toDouble()
                                                            val date = userDinner["date"].toString()
                                                            val userMail =
                                                                userDinner["userEmail"].toString()
                                                            if (userMail == userEmail && ("" + mYear + "/" + (mMonth + 1) + "/" + mDay) == date) {
                                                                totalCal += cal * num
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
                                                                        val cal =
                                                                            userSnack["cal"].toString()
                                                                                .toDouble()
                                                                        val num =
                                                                            userSnack["quantity"].toString()
                                                                                .toDouble()
                                                                        val date =
                                                                            userSnack["date"].toString()
                                                                        val userMail =
                                                                            userSnack["userEmail"].toString()
                                                                        if (userMail == userEmail && ("" + mYear + "/" + (mMonth + 1) + "/" + mDay) == date) {
                                                                            totalCal += cal * num
                                                                        }
                                                                    }
                                                                    System.out.println("break fast + lanunch" + totalCal)
                                                                    numCalday.text = totalCal.toString()+ " KCal"
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
                                                                                    var cal = 0.0
                                                                                    var min = 0.0
                                                                                    val date = userEx["date"].toString()
                                                                                    val userMail = userEx["userEmail"].toString()
                                                                                    if (userMail == userEmail && ("" + mYear + "/" + (mMonth + 1) + "/" + mDay) == date) {
                                                                                        cal = userEx["cal"].toString().toDouble()
                                                                                        min = userEx["minute"].toString().toDouble()
                                                                                        totalEx += cal * min
                                                                                    }
                                                                                }
                                                                                var totalDay =0.0

                                                                                totalDay = totalCal - totalEx
//                                                                                overall_day.text = totalDay.toString()+ " KCal"
                                                                                numBurnday.text = totalEx.toString()+ " KCal"
                                                                                pieChart(root, totalDay)
                                                                                var map = mutableMapOf<String,Any>()
                                                                                map["userEmail"] = userEmail
                                                                                map["calD"] = totalCal.toString()
                                                                                map["burnD"] = totalEx.toString()
                                                                                map["week"] = keDate.toString()
                                                                                map["month"] = ("" + mYear + "/" + (mMonth + 1)).toString()
                                                                                FirebaseDatabase.getInstance().reference
                                                                                    .child("OverDay")
                                                                                    .child(userId)
                                                                                    .child(("" + mYear + (mMonth + 1) + mDay))
                                                                                    .setValue(map)
                                                                                FirebaseDatabase.getInstance().reference
                                                                                    .child("OverDay")
                                                                                    .child(userId)
                                                                                    .child(("" + mYear + (mMonth + 1) + mDay))
                                                                                    .updateChildren(map)
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
                }, year, month, day)
            }?.show()
        }

        return root
    }

    private fun pieChart(root: View, total: Double){
        val pieChart = root.findViewById<PieChart>(R.id.pirChart)
        pieChart.setUsePercentValues(true)
        pieChart.holeRadius = 90f
        pieChart.description.isEnabled = false
        pieChart.centerText = ""+total+" KCal"
        pieChart.setCenterTextSize(30f)
        var value = ArrayList<PieEntry>()
        value.add(PieEntry(60f, ""))
        value.add(PieEntry(40f, ""))
        var pieDataSet = PieDataSet(value, "")
        pieDataSet.valueTextSize = 0f
        pieDataSet.colors = ColorTemplate.JOYFUL_COLORS.toMutableList()
        var pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieChart.animateXY(1000, 1000)
    }
}