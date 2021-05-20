package com.example.haelthc.Fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.annotation.RequiresApi

import com.example.haelthc.R
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
import kotlinx.android.synthetic.main.fragment_week.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalField
import java.time.temporal.WeekFields
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class WeekFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_week, container, false)
        pieChart(root, 0.0)
        val month = arrayOf("January","February","March","April","May","Jun","July","August","September","October","November","December")
        val spinner_month = root.findViewById<Spinner>(R.id.spinner_month)
        spinner_month?.adapter = ArrayAdapter(activity!!.applicationContext, R.layout.support_simple_spinner_dropdown_item, month) as SpinnerAdapter
        spinner_month?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var keepMon = parent?.getItemAtPosition(position).toString()
                var numMon="";
                if (keepMon=="January"){ numMon = "2020/1" }
                if (keepMon=="February"){ numMon = "2020/2" }
                if (keepMon=="March"){ numMon = "2020/3" }
                if (keepMon=="April"){ numMon = "2020/4" }
                if (keepMon=="May"){ numMon = "2020/5" }
                if (keepMon=="June"){ numMon = "2020/6" }
                if (keepMon=="July"){ numMon = "2020/7" }
                if (keepMon=="August"){ numMon = "2020/8" }
                if (keepMon=="September"){ numMon = "2020/9" }
                if (keepMon=="October"){ numMon = "2020/10" }
                if (keepMon=="November"){ numMon = "2020/11" }
                if (keepMon=="December"){ numMon = "2020/12" }

                val week = arrayOf("1-7","8-14","15-21","22-28","29-31")
                val spinner_week = root.findViewById<Spinner>(R.id.spinner_week)
                spinner_week?.adapter = ArrayAdapter(activity!!.applicationContext, R.layout.support_simple_spinner_dropdown_item, week) as SpinnerAdapter
                spinner_week?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        var keepWeek = parent?.getItemAtPosition(position).toString()
                        var numWeek = "";
                        if (keepWeek == "1-7") { numWeek = "1.0"}
                        if (keepWeek == "8-14") { numWeek = "2.0"}
                        if (keepWeek == "15-21") { numWeek = "3.0"}
                        if (keepWeek == "22-28") { numWeek = "4.0"}
                        if (keepWeek == "29-31") { numWeek = "5.0"}


                        var userId = "";
                        val user = FirebaseAuth.getInstance().currentUser
                        user?.let {
                            userId = user.uid
                        }

                        FirebaseDatabase.getInstance().reference.child("OverDay").child(userId)
                            .addValueEventListener(object :
                                ValueEventListener {
                                override fun onCancelled(p0: DatabaseError) {
                                }

                                override fun onDataChange(p0: DataSnapshot) {
                                    var cal = 0.0
                                    var bCal = 0.0
                                    for (snapshot in p0.children) {
                                        println(snapshot.key)
                                        val weekFrag = snapshot.value as Map<String, Any>
                                        var tempCal = 0.0
                                        var tempBCal = 0.0
                                        val month = weekFrag["month"].toString()
                                        val week = weekFrag["week"].toString()
                                        if (month == numMon && numWeek == week) {
                                            tempCal = weekFrag["calD"].toString().toDouble()
                                            tempBCal = weekFrag["burnD"].toString().toDouble()
                                            cal += tempCal
                                            bCal += tempBCal

                                        }
                                    }
                                    var totalWeek =0.0
                                    totalWeek = cal - bCal
                                    pieChart(root, totalWeek)
//                                    overall_week.text = totalWeek.toString()+ " KCal"
                                    numOfweek.text = cal.toString()+" KCal"
                                    numBurnweek.text = bCal.toString()+ " KCal"
                                }
                            })
                    }
                }
            }
        }

        return root
    }
    private fun pieChart(root: View, total: Double){
        val pieChart = root.findViewById<PieChart>(R.id.pieweek)
        pieChart.setUsePercentValues(true)
        pieChart.holeRadius = 90f
        pieChart.description.isEnabled = false
        pieChart.centerText = ""+total+" KCal"
        pieChart.setCenterTextSize(30f)
        var value = ArrayList<PieEntry>()
        value.add(PieEntry(40f, ""))
        value.add(PieEntry(60f, ""))
        var pieDataSet = PieDataSet(value, "")
        pieDataSet.valueTextSize = 0f
        pieDataSet.colors = ColorTemplate.JOYFUL_COLORS.toMutableList()
        var pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieChart.animateXY(1000, 1000)
    }
}
