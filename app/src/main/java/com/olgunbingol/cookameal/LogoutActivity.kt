package com.olgunbingol.cookameal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LogoutActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)
        auth = Firebase.auth
    }
    fun signoutClicked(view: View) {
        if(auth.currentUser != null) {
            auth.signOut()
            Toast.makeText(this, "Succesfull!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LogoutActivity, TarifActivity::class.java)
            startActivity(intent)
        }
        else {
            val intent = Intent(this@LogoutActivity,LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Please Log In first!", Toast.LENGTH_SHORT).show()
        }
    }
}