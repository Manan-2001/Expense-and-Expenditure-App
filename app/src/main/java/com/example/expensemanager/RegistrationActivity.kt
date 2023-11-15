package com.example.expensemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.auth.*

import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class RegistrationActivity : AppCompatActivity() {
    private lateinit var mEmail: EditText
    private lateinit var mPass: EditText
    private lateinit var btnReg: Button
    private lateinit var mSignin: TextView
    private lateinit var mauth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mauth= FirebaseAuth.getInstance()
        registration()
    }
    private fun  registration(){
       mEmail=findViewById(R.id.email_registration)
         mPass=findViewById(R.id.password_registration)
         btnReg=findViewById(R.id.btn_registration)
         mSignin=findViewById(R.id.sign_here)
        btnReg.setOnClickListener {
            val email: String = mEmail.text.toString().trim()
            val pass:String=mPass.text.toString().trim()
            if (TextUtils.isEmpty(email)){
                mEmail.setError("Mandotary to Enter Email...")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(pass)){
                mPass.setError("Mandotary to Enter Password...")
                return@setOnClickListener
            }
            mauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Registration Completed", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegistrationActivity,HomeActivity::class.java))
                } else {
                    Toast.makeText(applicationContext, "Registration Failed", Toast.LENGTH_SHORT).show()
                } }
        }
       mSignin.setOnClickListener{
           startActivity(Intent(this@RegistrationActivity,MainActivity::class.java))
       }
    }

}