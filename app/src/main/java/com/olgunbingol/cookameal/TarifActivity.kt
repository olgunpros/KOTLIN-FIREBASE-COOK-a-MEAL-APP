package com.olgunbingol.cookameal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.olgunbingol.cookameal.databinding.ActivityMainBinding
import com.olgunbingol.cookameal.databinding.ActivityTarifBinding

class TarifActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTarifBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarif)
        binding = ActivityTarifBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
    }
    fun tumtariflerClicked(view: View) {
        val intent = Intent(this@TarifActivity, TumtariflerActivity::class.java)
        startActivity(intent)


    }
    fun videolarClicked(view: View) {
        val intent = Intent(this@TarifActivity, VideolarActivity::class.java)
        startActivity(intent)

    }
    fun menulerClicked(view: View) {
        val intent = Intent(this@TarifActivity, MenulerActivity::class.java)
        startActivity(intent)
    }
    fun blogClicked(view: View) {
        val intent = Intent(this@TarifActivity, BlogActivity::class.java)
        startActivity(intent)

    }
    fun listelerClicked(view: View) {
        val intent = Intent(this@TarifActivity, ListelerActivity::class.java)
        startActivity(intent)

    }
    fun createClicked(view: View) {
        if(auth.currentUser != null) {
            val intent = Intent(this@TarifActivity, CreateActivity::class.java)
            startActivity(intent)

        }

        else {
            val intent = Intent(this@TarifActivity, LoginActivity::class.java)
            startActivity(intent)
        }


    }

    fun loginClicked(view: View) {
        if(auth.currentUser != null) {
            Toast.makeText(this@TarifActivity,"Error! You're logged in!",Toast.LENGTH_SHORT).show()

        }
        else {

            val intent = Intent(this@TarifActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }
    fun settingsClicked(view: View) {
        val intent = Intent(this@TarifActivity,LogoutActivity::class.java)
        startActivity(intent)
    }
}