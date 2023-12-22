package com.olgunbingol.cookameal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.olgunbingol.cookameal.adapter.FeedRecyclerAdapter
import com.olgunbingol.cookameal.databinding.ActivityMainBinding
import com.olgunbingol.cookameal.databinding.ActivityTumtariflerBinding
import com.olgunbingol.cookameal.model.Tarif

class TumtariflerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTumtariflerBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var storage : FirebaseStorage
    private lateinit var tarifArrayList : ArrayList<Tarif>
    private lateinit var feedAdapter : FeedRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tumtarifler)
        binding = ActivityTumtariflerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        storage = Firebase.storage
        db = Firebase.firestore
        tarifArrayList = ArrayList<Tarif>()
        getData()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        feedAdapter = FeedRecyclerAdapter(tarifArrayList)
        binding.recyclerView.adapter = feedAdapter
    }
    private fun getData() {
        db.collection("tarifler").orderBy("date",
            Query.Direction.DESCENDING).addSnapshotListener { value, error ->

            if(error != null) {
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()

            }
            else {
                if(value != null) {
                    if(!value.isEmpty) {
                        val documents = value.documents
                        tarifArrayList.clear()
                        for(document in documents) {
                            val ad = document.get("yemekadı") as String
                            val tarif = document.get("tarif") as String
                            val downloadUrl = document.get("downloadUrl") as String
                            val Tarif = Tarif(ad,tarif, downloadUrl)
                            tarifArrayList.add(Tarif)
                        }
                    }
                    feedAdapter.notifyDataSetChanged()

                }
            }
        }


    }
}