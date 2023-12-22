package com.olgunbingol.cookameal

import android.Manifest

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.olgunbingol.cookameal.databinding.ActivityCreateBinding
import java.util.UUID

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    var selectedPicture : Uri? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore : FirebaseFirestore
    private lateinit var storage : FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        registerLauncer()
        auth = Firebase.auth
        storage = Firebase.storage
        firestore = Firebase.firestore

    }
    fun imageViewClicked(view: View) {
    if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Snackbar.make(view,"Galeri izni gerekli",Snackbar.LENGTH_INDEFINITE).setAction("İzin ver") {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

            }.show()
        }
        else {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    }
else {
    val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activityResultLauncher.launch(intentToGallery)
    }






    }
    fun paylasClicked(view: View) {
        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"
        val reference = storage.reference
        val imageReference = reference.child("Tarifler").child(imageName)
        if(selectedPicture != null) {
            imageReference.putFile(selectedPicture!!).addOnSuccessListener {
             val uploadPictureReference = storage.reference.child("Tarifler").child(imageName)
                uploadPictureReference.downloadUrl.addOnSuccessListener {
                    val downloadUrl = it.toString()

                        val postMap = hashMapOf<String, Any>()
                        postMap.put("downloadUrl", downloadUrl)
                        postMap.put("yemekadı", binding.adText.text.toString())
                        postMap.put("tarif", binding.tarifText.text.toString())
                        postMap.put("time",Timestamp.now())

                        firestore.collection("tarifler").add(postMap).addOnSuccessListener {
                            finish()

                        }.addOnFailureListener {
                            Toast.makeText(
                                this@CreateActivity,
                                it.localizedMessage,
                                Toast.LENGTH_LONG
                            ).show()



                    }

                }

            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()

            }
        }







    }
    private fun registerLauncer() {
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == RESULT_OK) {
                val intentFromResult = result.data
                if(intentFromResult != null) {
                    selectedPicture = intentFromResult.data
                    selectedPicture?.let {
                        binding.imageView.setImageURI(it)

                    }
                }
            }

        }
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result->
            if(result == false) {
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)

            }
            else {
                Toast.makeText(this@CreateActivity,"Permission needed!", Toast.LENGTH_LONG).show()
            }

        }

    }
}