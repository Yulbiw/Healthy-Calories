package com.example.haelthc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
//import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    private lateinit var preference: LangPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val register = findViewById<TextView>(R.id.goto_register)
        register.setOnClickListener {
            val intent = Intent(this, Healthyregister::class.java)
            // start next activity
            startActivity(intent)
        }

        mAuth = FirebaseAuth.getInstance()

        val email_log = findViewById<EditText>(R.id.email_log)
        val pass_log =findViewById<EditText>(R.id.pass_log)
        val login_but_login = findViewById<TextView>(R.id.login_but_login)

        login_but_login.setOnClickListener{
            val email = email_log.text.toString().trim{ it <= ' '}
            if (email.isEmpty()){
                email_log.error = "Please enter your email!!!"
                return@setOnClickListener
            }

            val password = pass_log.text.toString().trim{ it <= ' '}
            if (password.isEmpty()){
                pass_log.error="Please enter your password!!!"
                return@setOnClickListener
            }
            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { situation ->
                if(situation.isSuccessful){
                    val intent = Intent(this, Healthyactivity::class.java)
                    // start next activity
                    startActivity(intent)
                    finish()
                }
                else{
                    if(password.length < 6){
                        pass_log.error="Please enter your password more than 6 characters!!!"
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Incorrect Email or Password!!!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        preference = LangPreference(newBase!!)
        val lang = preference.getLoginCount()
        super.attachBaseContext(lang?.let { ContextWrapper.wrap(newBase, it) })
    }

}
