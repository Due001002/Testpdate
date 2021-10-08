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
    var db = FirebaseFirestore.getInstance()
    var setDataDB = db.collection("data").document("next").collection("room")
    var db_data_next = db.collection("data").document("next")
    var checkName = ""
    var dbTestCH = db.collection("data").document("next").collection("room").document("testCH")
    var G_arrayUser = mutableListOf<Map<*, *>>()
    val gkey = key()
    var backCard = mutableListOf<String>("n", "n")
    var card = mutableListOf<String>(
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
//        onTest()
        //goToPage3_Test()
        delete()
//        val intent = Intent(this, Page4_Sort::class.java)
//        startActivity(intent)
    }

    //TODO delete
    fun delete() {
//        btnOk_Page1.setOnClickListener {
//            dbTestCH.delete()
//        }
    }

    //TODO onClickInten()
    fun onTest() {
        var abc = mutableListOf<Map<String, Any>>()
        setDataDB.document("มาๆๆๆ").get().addOnSuccessListener {
            var getUser = it.get("user") as MutableList<Map<String, Any>>
            for (i in 0..getUser.size - 1) {
                abc.add(getUser[i])
                logdfix("getUser", "getUser[$i]: ${abc[i]}\n")
            }
        }
    }

    //TODO onClickInten()
    fun onClickInten() {
        var backCard = mutableListOf<String>("n","n")
        btnOk_Page1.setOnClickListener {
            var getStringEdt = edt_Page1_login.text.toString()
            db_data_next.get().addOnSuccessListener { docs ->
                if (!docs!!.exists()) {
                    var userHashMap = hashMapOf<String, Any>(
                        "name" to getStringEdt,
                        "value" to 0,
                        "point" to 500,
                        "status" to "host",
                        "text" to "",
                        "card" to backCard)
                    var a = mapOf<String, Any>("data" to "a")
                    db_data_next.set(a)
                    val intent = Intent(this, Page2_CreatRoom::class.java)
                    var cards = card as ArrayList<String>
                    intent.putExtra("card", cards)
                    intent.putExtra("userHashMap", userHashMap)
                    intent.putExtra("checkName",getStringEdt)
                    startActivity(intent)
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

    //TODO GO TO Page3
//    fun goToPage3_Test() {
//        var getArrayUser = mutableListOf<Map<*, *>>()
//        btn_X.setOnClickListener { //TODO BTN_X
//            checkName = "xaio"
//            edt_Page1_login.setText("xaio")
//            dbTestCH.get().addOnSuccessListener { docs ->
//                if (docs!!.exists()) {
//                    var arrayName = mutableListOf<Map<*, *>>(
//                        mapOf(gkey.name to checkName,
//                            gkey.status to "user",
//                            gkey.point to 500,
//                            gkey.value to 0,
//                            gkey.text to "",
//                            gkey.card to backCard),
//                        mapOf(gkey.name to "AI_1",
//                            gkey.status to "user",
//                            gkey.point to 500,
//                            gkey.value to 0,
//                            gkey.text to "",
//                            gkey.card to backCard),
//                        mapOf(gkey.name to "AI_2",
//                            gkey.status to "user",
//                            gkey.point to 500,
//                            gkey.value to 0,
//                            gkey.text to "",
//                            gkey.card to backCard)
//                    )
//                    getArrayUser.add(arrayName[0])
//                    getArrayUser.add(arrayName[1])
////                    getArrayUser.add(arrayName[2])
//                    var getDocs = docs!!.get("user") as MutableList<Map<String, Any>>
//                    for (doc in 0..getDocs.size - 1) {
//                        getArrayUser.add(getDocs[doc])
//                    }
//                    var update = mapOf<String, Any>(
//                        "user" to getArrayUser
//                    )
//                    dbTestCH.update(update).addOnSuccessListener {
//                        var arrayNameIndex = arrayName as ArrayList
//                        val intent = Intent(this, Page3_GameUi::class.java)
//                        intent.putExtra("checkName", checkName)
//                        intent.putExtra("userHashMap", arrayNameIndex)
//                        startActivity(intent)
//                    }
//                } else {
//                    var arrayName = mutableListOf<Map<*, *>>(
//                        mapOf("name" to checkName,
//                            "status" to "host")
//                    )
//                    var update = mapOf<String, Any>(
//                        "user" to arrayName,
//                        "sizeUser" to 3
//                    )
//                    dbTestCH.set(update).addOnSuccessListener {
//                        val intent = Intent(this, Page3_GameUi::class.java)
//                        intent.putExtra("checkName", checkName)
//                        startActivity(intent)
//                    }
//                }
//            }
//        }
//        //TODO BTN_N
//        btn_N.setOnClickListener {
//            checkName = "nubia"
//            edt_Page1_login.setText("nubia")
//            dbTestCH.get().addOnSuccessListener { docs ->
//                if (docs!!.exists()) {
//                    var arrayName = mutableListOf<Map<*, *>>(
//                        mapOf("name" to checkName),
//                        mapOf("name" to "AI")
//                    )
//                    getArrayUser.add(arrayName[0])
//                    getArrayUser.add(arrayName[1])
//                    var getDocs = docs!!.get("user") as MutableList<Map<String, Any>>
//                    for (doc in 0..getDocs.size - 1) {
//                        getArrayUser.add(getDocs[doc])
//                    }
//                    var update = mapOf<String, Any>(
//                        "user" to getArrayUser
//                    )
//                    dbTestCH.update(update).addOnSuccessListener {
//                        val intent = Intent(this, Page3_GameUi::class.java)
//                        intent.putExtra("checkName", checkName)
//                        startActivity(intent)
//                    }
//                } else {
//                    var arrayName = mutableListOf<Map<*, *>>(
//                        mapOf(gkey.name to checkName,
//                            gkey.status to "host",
//                            gkey.point to 500,
//                            gkey.value to 0,
//                            gkey.text to "",
//                            gkey.card to backCard)
//                    )
//                    var update = mapOf<String, Any>(
//                        gkey.user to arrayName,
//                        "sizeUser" to 0,
//                        gkey.round to 0,
//                        gkey.state to 0,
//                        gkey.card to card
//                    )
//                    var arrayNameIndex = arrayName as ArrayList
//                    dbTestCH.set(update).addOnSuccessListener {
//                        val intent = Intent(this, Page3_GameUi::class.java)
//                        intent.putExtra("checkName", checkName)
//                        intent.putExtra("userHashMap", arrayNameIndex)
//                        startActivity(intent)
//                    }
//                }
//            }
//        }
//    }

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