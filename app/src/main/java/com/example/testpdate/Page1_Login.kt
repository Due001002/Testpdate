package com.example.testpdate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_page1_login.*
import java.util.ArrayList


class Page1_Login : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance() //TODO เอาไว้อ้างอิงไฟเบส
    var setDataDB = db.collection("data").document("next").collection("room")   //TODO เอาไว้ระบุตำแหน่งไฟเบส
    var db_data_next = db.collection("data").document("next")       //TODO เอาไว้ระบุตำแหน่งไฟเบส
    var card = mutableListOf<String>(       //TODO เป็นตัวแปรเก็บค่ากาด
        "1fj", "2fj", "3fj", "4fj", "5fj", "6fj", "7fj", "8fj", "9fj", "10fj",
        "1kl", "2kl", "3kl", "4kl", "5kl", "6kl", "7kl", "8kl", "9kl", "10kl",
        "1pb", "2pb", "3pb", "4pb", "5pb", "6pb", "7pb", "8pb", "9pb", "10pb",
        "1pr", "2pr", "3pr", "4pr", "5pr", "6pr", "7pr", "8pr", "9pr", "10pr",
        "jfj", "jkl", "jpb", "jpr",
        "kfj", "kkl", "kpb", "kpr",
        "qfj", "qkl", "qpb", "qpr"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page1_login)
        onClickInten()
    }

    //TODO onClickInten()
    fun onClickInten() {    //TODO เป็นฟังชั่น
        var backCard = mutableListOf<String>("n","n")
        btnOk_Page1.setOnClickListener {
            var getStringEdt = edt_Page1_login.text.toString()      //TODO อ่านค่าจากช่องใส่ชื่อไปเก็บใน getstringedt
            db_data_next.get().addOnSuccessListener { docs ->       //TODO เรียกดูค่าจากฐานข้อมูล db data next  docsตั่งชื่อไว้เข้าฐานข้อมูล
                if (!docs!!.exists()) { //TODO ถ้าฐานขอมูลยังไม่มีข้อมูล
                    if (edt_Page1_login.text.toString() == "") {    //TODO เช็คช่องใส่ชื่อมีข้อมูลไหมถ้าไม่มีให้ขึ้นข้อความ
                        toast("โปรดตั้งชื่อเพื่อเข้าร่วมเกม")
                    } else {            //TODO ถ้าช่องใส่ชื่อมีข้อมูล
                        var userHashMap = hashMapOf<String, Any>(   //TODO เก็บลงฐานข้อมูลแบบเฉพราะ hashmap
                            "name" to getStringEdt,
                            "value" to 0,
                            "point" to 500,
                            "status" to "host",
                            "text" to "",
                            "card" to backCard)
                        var a = mapOf<String, Any>("data" to "a")   //TODO mapofใช้ hashmap ก็ได้ ไปสร้างในฐานข้อมูลไว้ใน a
                        db_data_next.set(a)         //TODO
                        val intent = Intent(this, Page2_CreatRoom::class.java)
                        var cards = card as ArrayList<String>       //TODO นำข้อมูลใน card ไปเก็บมน cards แบบอาเร
                        intent.putExtra("card", cards)        //TODO ส่งตัวแปร cards ไปไว้ในkey card
                        intent.putExtra("userHashMap", userHashMap)     //TODO เป็นข้อมูลผู้เล่น
                        intent.putExtra("checkName",getStringEdt)       //TODO เป็นชื่อของผู้เล่นในช่องใส่ชื่อ
                        startActivity(intent)       //TODO เปลี่ยนไปหน้า creatroom
                    }
                } else {                //TODO ถ้าในฐานข้อมูลมีข้อมูลแล้วจะมาเข้าอันนี้
                    if (edt_Page1_login.text.toString() == "") {    //TODO ถ้าในกรอบยังไม่ใส่ข้อมูลจะขึ้นข้อความ
                        toast("โปรดตั้งชื่อเพื่อเข้าร่วมเกม")
                    } else {
                        var userHashMap = hashMapOf<String, Any>(
                            "name" to getStringEdt,
                            "value" to 0,
                            "point" to 500,
                            "status" to "user",
                            "text" to "",
                            "card" to backCard)
                        val intent = Intent(this, Page2_SelectRoom::class.java)
                        intent.putExtra("userHashMap", userHashMap)
                        intent.putExtra("checkName",getStringEdt)
                        startActivity(intent)
                    }
                }
            }
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