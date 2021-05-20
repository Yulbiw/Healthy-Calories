package com.example.haelthc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_healthyregister.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class Healthyregister : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    private lateinit var preference: LangPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healthyregister)

//        submit_reg.setOnClickListener {
//            val intent = Intent(this, Confirm::class.java)
//            // start next activity
//            startActivity(intent)
//        }
        mAuth = FirebaseAuth.getInstance()

        val name_reg = findViewById<EditText>(R.id.name_reg)
        val email_reg = findViewById<EditText>(R.id.email_reg)
        val pass_reg = findViewById<EditText>(R.id.pass_reg)
        val confirmpass_reg = findViewById<EditText>(R.id.confirmpass_reg)
        val submit_reg = findViewById<TextView>(R.id.submit_reg)

        submit_reg.setOnClickListener{
            val name = name_reg.text.toString().trim { it <= ' ' }
            if( name.isEmpty() ){
                name_reg.error = "Please enter your name!!!"
                return@setOnClickListener
            }

            val email = email_reg.text.toString().trim{ it <= ' '}
            if (email.isEmpty()){
                email_reg.error = "Please enter your email!!!"
                return@setOnClickListener
            }

            val password = pass_reg.text.toString().trim{ it <= ' '}
            if (password.isEmpty()){
                pass_reg.error="Please enter your password!!!"
                return@setOnClickListener
            }

            val confirmpass = confirmpass_reg.text.toString().trim { it <= ' ' }
            if (confirmpass.isEmpty()){
                confirmpass_reg.error="Please confirm your password!!!"
                return@setOnClickListener
            }

            if((!password.isEmpty() && !confirmpass.isEmpty())){
                if(confirmpass != password){
                    confirmpass_reg.error="Password and confirm password does not match!!!"
                    return@setOnClickListener
                }
            }
            mAuth!!.createUserWithEmailAndPassword(email,password).addOnCompleteListener { situation ->
                if(situation.isSuccessful){
                    val user = mAuth!!.currentUser
                    val updateUser = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                    user?.updateProfile(updateUser)?.addOnCompleteListener{
                        task -> if(task.isSuccessful) Log.d("Update User: ", "Successfully")
                    }
                    //sent name to setting
                    val inten2t = Intent(this,SettingActivity::class.java)
                    inten2t.putExtra("user_name",name)

                    val intent = Intent(this, Confirm::class.java)
                    // start next activity
                    startActivity(intent)
                    finish()
                }
                else{
                    if(password.length < 6){
                        pass_reg.error="Please enter your password more than 6 characters!!!"
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
