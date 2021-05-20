package com.example.haelthc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_healthymain.*
//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.activity_main.login_but

class Healthymain : AppCompatActivity() {

    private lateinit var myPreference: LangPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healthymain)

        val login_but = findViewById<TextView>(R.id.login_but_main)
        login_but.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // start next activity
            startActivity(intent)
        }

        register_but.setOnClickListener {
            val intent = Intent(this, Healthyregister::class.java)
            // start next activity
            startActivity(intent)
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        myPreference = LangPreference(newBase!!)
        val lang = myPreference.getLoginCount()
        super.attachBaseContext(lang?.let { ContextWrapper.wrap(newBase, it) })
    }
}
