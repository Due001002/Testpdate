package com.example.testpdate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_page2_creat_room.*

class Page2_CreatRoom : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    var setDataDB = db.collection("data").document("next").collection("room")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page2_creat_room)
        btnBackPage1()
        setSpinner()
        btn_creatRoom()
    }

    //TODO BTN_Creat
    fun btn_creatRoom() {
        var bundle = intent.extras
        var getArrayUserMap = bundle!!.getString("ArrayUserMap")
        logdfix("getArrayUserMap","$getArrayUserMap")

        btnCreat_page2.setOnClickListener {
            var intent = Intent(this, Page3_GameUi::class.java)
            startActivity(intent)
        }
    }

    //TODO onClickInten()
    fun setSpinner() {
        var arrays = mutableListOf<Int>(1, 2, 3, 4)
        spn_selectPlayer.adapter =
            ArrayAdapter<Int>(this, android.R.layout.simple_list_item_1, arrays)
        spn_selectPlayer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //edt_page2.text = "${arrays.get(p2)}"
                logdfix("spn", "Selec_spn:${arrays.get(p2)}")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    //TODO BTN_Back
    private fun btnBackPage1() {
        btnBack_page2.setOnClickListener {
            finish()
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