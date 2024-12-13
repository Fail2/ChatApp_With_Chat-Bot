package com.example.audiovideocall

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig

class SignUp : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var btnLogIn: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance()

        // Initialize UI elements
        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSignUp = findViewById(R.id.btn_Signup)
        btnLogIn = findViewById(R.id.btn_Login)

        // Check if the user is already signed in
        if (mAuth.currentUser != null) {
            // Navigate to MainActivity
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        // Sign Up button click listener
        btnSignUp.setOnClickListener {
            val name = edtName.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                signUp(name, email, password)
            }
        }
        btnLogIn.setOnClickListener {
            val intent = Intent(this@SignUp,LogIn::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun signUp(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // User created successfully, get UID
                    val uid = mAuth.currentUser?.uid!!
                    // Add user details to the database
                    addUserToDatabase(name, email, uid)
                    // Navigate to MainActivity
//                    val intent = Intent(this@SignUp, MainActivity::class.java)
//                    finish()
//                    startActivity(intent)
                } else {
                    // Handle error
                    Toast.makeText(this, "Sign Up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference("User")
        val user = User(name, email, uid)

        mDbRef.child(uid).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "You are Welcome", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to add user: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
