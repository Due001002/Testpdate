package com.example.testpdate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent

import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_page1_login.*


class Page1_Login : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    var setDataDB = db.collection("data").document("next").collection("room")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page1_login)
        onClickInten()

    }

    //TODO onClickInten()
    private fun onClickInten() {
        btnOk_Page1.setOnClickListener {
            var getStringEdt = edt_Page1_login.text.toString()
            var ArrayUserMap = arrayListOf<Map<String, Any>>(mapOf("name" to getStringEdt,
                "value" to "",
                "point" to 500))
            var a = mapOf<String,Any>("data" to "a")
            db.collection("data").document("next").set(a)
            val intent = Intent(this, Page2_CreatRoom::class.java)
            intent.putExtra("ArrayUserMap",ArrayUserMap)
            startActivity(intent)

        }
    }

    //TODO toast
    fun toast(a: String) {
        val text = a
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }

    //TODO Logdfix
    fun logdfix(a: String, b: String) {
        Log.d(a, b)
    }
}