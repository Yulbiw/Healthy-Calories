package com.example.haelthc.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter

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
import kotlinx.android.synthetic.main.fragment_month.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class MonthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_month, container, false)
        pieChart(root, 0.0)
        val month = arrayOf("January","February","March","April","May","Jun","July","August","September","October","November","December")
        val spinnerOfmonth = root.findViewById<Spinner>(R.id.spinnerOfmonth)
        spinnerOfmonth?.adapter = ArrayAdapter(activity!!.applicationContext, R.layout.support_simple_spinner_dropdown_item, month) as SpinnerAdapter
        spinnerOfmonth?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var keepMon = parent?.getItemAtPosition(position).toString()
                var numMon = "";
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
                                if (month == numMon) {
                                    tempCal = weekFrag["calD"].toString().toDouble()
                                    tempBCal = weekFrag["burnD"].toString().toDouble()
                                    cal += tempCal
                                    bCal += tempBCal

                                }
                            }
                            var totalMonth = 0.0
                            totalMonth = cal - bCal
                            pieChart(root, totalMonth)
//                            overall_month.text = totalMonth.toString() + " KCal"
                            numCalmonth.text = cal.toString() + " KCal"
                            numBurnmonth.text = bCal.toString() + " KCal"
                        }
                    })
                }
            }
        return root
    }
    private fun pieChart(root: View, total: Double){
        val pieChart = root.findViewById<PieChart>(R.id.piemonth)
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
