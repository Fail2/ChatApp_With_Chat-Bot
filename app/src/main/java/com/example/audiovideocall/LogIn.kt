package com.example.audiovideocall

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig

class LogIn : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button


    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        mAuth = FirebaseAuth.getInstance()

        edtEmail =findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btn_Login)




//        btnSignUp.setOnClickListener {
//            val intent = Intent(this,SignUp::class.java)
//            startActivity(intent)
//        }
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig()

                ZegoUIKitPrebuiltCallService.init(
                    application,
                    AppConstants.appId,
                    AppConstants.appSign,
                    email,
                    email,
                    callInvitationConfig
                )
                login(email,password);
            }

        }
    }

    private fun login(email: String,password: String)
    {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for log in user
                    val intent = Intent(this@LogIn,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LogIn,"User does not exist", Toast.LENGTH_SHORT).show();
                }
            }
    }
}