package com.example.haelthc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
//import kotlinx.android.synthetic.main.activity_healthyregister.*
//import kotlinx.android.synthetic.main.activity_main.*

class Confirm : AppCompatActivity() {

    private lateinit var preference: LangPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        val login_but = findViewById<TextView>(R.id.login_but)

        login_but.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // start next activity
            startActivity(intent)
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        preference = LangPreference(newBase!!)
        val lang = preference.getLoginCount()
        super.attachBaseContext(lang?.let { ContextWrapper.wrap(newBase, it) })
    }
}
