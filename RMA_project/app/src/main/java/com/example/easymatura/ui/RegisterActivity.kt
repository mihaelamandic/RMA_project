package com.example.easymatura.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.easymatura.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth



class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            if(binding.editTextTextEmailAddress.text.isNullOrBlank() || binding.editTextTextPassword.text.isNullOrBlank() || binding.editTextTextConfirmPassword.text.isNullOrBlank()){
                Toast.makeText(this, "Please fill the required fields", Toast.LENGTH_SHORT).show()
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(binding.editTextTextEmailAddress.text).matches()){
                Toast.makeText(this, "Please enter a valid e-mail address", Toast.LENGTH_SHORT).show()
            }
            if(binding.editTextTextPassword.text.toString() != binding.editTextTextConfirmPassword.text.toString()){
                Toast.makeText(this, "Password and Confirm password do not match.", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.createUserWithEmailAndPassword(binding.editTextTextEmailAddress.text.toString(), binding.editTextTextPassword.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            val user = auth.currentUser
                            val intent = Intent(this, MaturaActivity::class.java);
                            startActivity(intent)
                        } else {

                            Toast.makeText(baseContext, "Registration failed.",
                                Toast.LENGTH_SHORT).show()

                        }
                    }
            }
        }

    }


}