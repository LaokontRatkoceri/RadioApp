package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.allViews
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.firebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        initBaseUI()

        binding.loginButton.setOnClickListener {
//            initLoginUI()
            if (binding.RegisterButton.visibility == View.GONE){
                initLoginUI()
                auth.signInWithEmailAndPassword(
                    binding.emailEdittext.text.toString(),
                    binding.passwordEdittext.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            println("Loogin successfull ${user.toString()}")



                            val intent = Intent(this, SecondActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            binding.loginButton.visibility = View.VISIBLE
                            binding.loginButton.isEnabled = true
                            binding.loginButton.visibility = View.VISIBLE
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }

            binding.emailInput.visibility = View.VISIBLE
            binding.passwordInput.visibility = View.VISIBLE
            binding.RegisterButton.visibility = View.GONE
        }
        binding.RegisterButton.setOnClickListener {
            if (binding.loginButton.visibility == View.GONE) {
                initRegisterUI()

                auth.createUserWithEmailAndPassword(
                    binding.emailEdittext.text.toString(),
                    binding.passwordEdittext.text.toString()
                ).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val user = auth.currentUser
                        println("Register successfull ${user.toString()}")
                    } else {
                        // If sign in fails, display a message to the user.
                        binding.RegisterButton.visibility = View.VISIBLE
                        binding.RegisterButton.isEnabled = true
                        binding.RegisterButton.visibility = View.VISIBLE
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
            binding.emailInput.visibility = View.VISIBLE
            binding.passwordInput.visibility = View.VISIBLE
            binding.loginButton.visibility = View.GONE
        }
            binding.emailEdittext.addTextChangedListener {
                if (it.isValidEmail()){
                    binding.RegisterButton.isEnabled = validetes()
                    binding.emailInput.error = ""
                }else{
                    binding.RegisterButton.isEnabled = validetes()
                    binding.emailInput.error = "Write your damn email right"
                }
            }

            binding.passwordEdittext.addTextChangedListener {
                if (it.isValidPassword()){
                    binding.RegisterButton.isEnabled = validetes()
                    binding.passwordInput.error = ""
                }else{
                    binding.RegisterButton.isEnabled = validetes()
                    binding.passwordInput.error = "Write your damn password right"
                }
            }

    }

    private fun validetes() = binding.emailEdittext.text.isValidEmail() && binding.passwordEdittext.text.isValidPassword()

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            println("user is null ${currentUser.email}")
        }
    }

    fun initBaseUI(){
        hideAllViews()
        binding.loginButton.visibility = View.VISIBLE
        binding.RegisterButton.visibility = View.VISIBLE
    }

    fun initLoginUI(){
        hideAllViews()
        with(binding){
            loginButton.visibility = View.VISIBLE
            loginButton.isEnabled = true
            emailInput.visibility = View.VISIBLE
            passwordInput.visibility = View.VISIBLE
        }
    }
    fun initRegisterUI(){
        hideAllViews()
        with(binding){
            RegisterButton.visibility = View.VISIBLE
            RegisterButton.isEnabled = true
            emailInput.visibility = View.VISIBLE
            passwordInput.visibility = View.VISIBLE
        }
    }

    fun hideAllViews(){
        with(binding){
            RegisterButton.visibility = View.GONE
            loginButton.visibility = View.GONE
            emailInput.visibility = View.GONE
            passwordInput.visibility = View.GONE
            ProgresBar.visibility = View.GONE

        }

    }
}