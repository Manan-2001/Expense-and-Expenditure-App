package com.example.expensemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var mEmail: EditText
    private lateinit var mPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var mForgetPassword: TextView
    private lateinit var mSignUpHere:TextView
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth= FirebaseAuth.getInstance()
        if (mAuth!=null){
            startActivity(Intent(this@MainActivity,HomeActivity::class.java))
        }
        loginDetails()
    }
    private fun loginDetails(){
        mEmail=findViewById(R.id.email_login)
        mPass=findViewById(R.id.password_login)
        btnLogin=findViewById(R.id.btn_login)
        mForgetPassword=findViewById(R.id.forget_password)
        mSignUpHere=findViewById(R.id.signup_registration)
        btnLogin.setOnClickListener(){
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
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(applicationContext,"Login Successfull",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@MainActivity,HomeActivity::class.java))
                }else{
                    Toast.makeText(applicationContext,"Login Failed",Toast.LENGTH_SHORT).show()
                }
            }
        }

        // redirect to Registratin Activity
        mSignUpHere.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegistrationActivity::class.java))
        }
        //redirect to reset password page

        mForgetPassword.setOnClickListener{
            startActivity(Intent(this@MainActivity, ResetActivity::class.java))
        }

    }
}