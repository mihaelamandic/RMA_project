package com.example.easymatura.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.easymatura.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            if(binding.editTextTextEmailAddress.text.isNullOrBlank() || binding.editTextTextPassword.text.isNullOrBlank()){
                Toast.makeText(this, "Please fill the required fields", Toast.LENGTH_SHORT).show()
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(binding.editTextTextEmailAddress.text).matches()){
                Toast.makeText(this, "Please enter a valid e-mail address", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(binding.editTextTextEmailAddress.text.toString(), binding.editTextTextPassword.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val intent = Intent(this, RegisterActivity::class.java); //TODO MaturaActivity
                            startActivity(intent)
                        } else {
                            Toast.makeText(baseContext, "Log in failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java);
            startActivity(intent)
        }
    }

    /*public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this, MaturaActivity::class.java);
            startActivity(intent)
        }
    }*/


}