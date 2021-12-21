package com.example.testpdate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_page4_sort.*
import kotlin.system.exitProcess

class Page4_Sort : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    var db_next = db.collection("data").document("next")
    var dbGiveCard = db.collection("data").document("next").collection("giveCard")
    var db_data_next_room = db.collection("data").document("next").collection("room")
    var roomName = ""
    var getUserPage3 = mutableListOf<HashMap<String, Any>>(
//        hashMapOf("name" to "due", "point" to 100),
//        hashMapOf("name" to "xyz", "point" to 100),
//        hashMapOf("name" to "T_T", "point" to 200),
//        hashMapOf("name" to "GLTR", "point" to 600)
    )
    var user = mutableListOf<HashMap<String, Any>>()
    var point = mutableListOf<Int>()
    var ggetSize = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page4_sort)
        var bundle = intent.extras
        var getPoint = bundle!!.get("point") as MutableList<Int>
        ggetSize = bundle!!.getInt("getSize")
        getUserPage3 = bundle!!.get("user") as MutableList<HashMap<String, Any>>
        roomName = bundle!!.get("roomName") as String
        point.addAll(getPoint.sortedDescending())
//        var pointz =  mutableListOf<Int>()
//        pointz.add(getUserPage3[0].get("point").toString().toInt())
//        pointz.add(getUserPage3[1].get("point").toString().toInt())
//        pointz.add(getUserPage3[2].get("point").toString().toInt())
//        pointz.add(getUserPage3[3].get("point").toString().toInt())
//        point = pointz.sortedDescending() as MutableList<Int>
        logdfix("aaa", "Point: ${point}")
        logdfix("aaa", "getUserPage3: $getUserPage3")
        logdfix("aaa", "size: $ggetSize")
        imageView1.setImageResource(R.drawable.gold)
        imageView2.setImageResource(R.drawable.silver)
        imageView3.setImageResource(R.drawable.bronz)
        forFuncAddUser()
        exitApp()
    }

    //TODO forFunc
    fun forFuncAddUser() {
        if (ggetSize == 2) {
            for (i in getUserPage3) {
                if (i.containsValue(point[0].toLong()) && !user.contains(i)) {
                    user.add(i)
                }
            }
            for (i in getUserPage3) {
                if (i.containsValue(point[1].toLong()) && !user.contains(i)) {
                    user.add(i)
                }
            }
        } else if (ggetSize == 3) {
            for (i in getUserPage3) {
                if (i.containsValue(point[0].toLong()) && !user.contains(i)) {
                    user.add(i)
                }
            }
            for (i in getUserPage3) {
                if (i.containsValue(point[1].toLong()) && !user.contains(i)) {
                    user.add(i)
                }
            }
            for (i in getUserPage3) {
                if (i.containsValue(point[2].toLong()) && !user.contains(i)) {
                    user.add(i)
                }
            }
        } else if (ggetSize == 4) {
            for (i in getUserPage3) {
                if (i.containsValue(point[0].toLong()) && !user.contains(i)) {
                    user.add(i)
                }
            }
            for (i in getUserPage3) {
                 if (i.containsValue(point[1].toLong()) && !user.contains(i)) {
                    user.add(i)
                }
            }
            for (i in getUserPage3) {
                if (i.containsValue(point[2].toLong()) && !user.contains(i)) {
                    user.add(i)
                }
            }
            for (i in getUserPage3) {
                if (i.containsValue(point[3].toLong()) && !user.contains(i)) {
                    user.add(i)
                }
            }
        }
        logdfix("aaa", "user: $user")
        logdfix("aaa", "user.size: ${user.size}")
        setPlayerSortedDes()
    }

    //TODO setPlayerSortedDes
    fun setPlayerSortedDes() {
        if (ggetSize == 2) {
            if (user.get(0).containsValue(user.get(1).get("point"))) { //TODO if 1
                textView5.setText("${user.get(0).get("name")} / ${user.get(1).get("name")} : ${user.get(0).get("point")}")
            } else {
                textView5.setText("${user.get(0).get("name")} : ${user.get(0).get("point")}")
                textView6.setText("${user.get(1).get("name")} : ${user.get(1).get("point")}")
            }
        } else if (ggetSize == 3) {
            if (true) {
                if (user.get(0).containsValue(user.get(1).get("point")) && user.get(0).containsValue(user.get(2).get("point"))) {
                    textView5.setText("${user.get(0).get("name")} / ${user.get(1).get("name")} / ${user.get(2).get("name")} :${user.get(0).get("point")}")
                }else if (user.get(0).containsValue(user.get(1).get("point"))) {
                    textView5.setText("${user.get(0).get("name")} / ${user.get(1).get("name")} :${user.get(0).get("point")}")
                    textView6.setText("${user.get(2).get("name")} :${user.get(2).get("point")}")
                }else if (!user.get(0).containsValue(user.get(1).get("point")) && user.get(1).containsValue(user.get(2).get("point"))) {
                    textView5.setText("${user.get(0).get("name")} :${user.get(0).get("point")}")
                    textView6.setText("${user.get(1).get("name")} / ${user.get(2).get("name")} :${user.get(1).get("point")}")
                }else if (!user.get(0).containsValue(user.get(1).get("point")) && !user.get(1).containsValue(user.get(2).get("point"))) {
                    textView5.setText("${user.get(0).get("name")} :${user.get(0).get("point")}")
                    textView6.setText("${user.get(1).get("name")} :${user.get(1).get("point")}")
                    textView7.setText("${user.get(2).get("name")} :${user.get(2).get("point")}")
                }
            }
        } else if (ggetSize == 4) {
            if (true) {
                if (user.get(0).containsValue(user.get(1).get("point")) && user.get(0).containsValue(user.get(2).get("point")) && user.get(0).containsValue(user.get(3).get("point"))) {
                    textView5.setText("${user.get(0).get("name")} / ${user.get(1).get("name")} / ${user.get(2).get("name")} / ${user.get(2).get("name")} :${user.get(0).get("point")}")
                }else if (user.get(0).containsValue(user.get(1).get("point")) && user.get(0).containsValue(user.get(2).get("point"))) {
                    textView5.setText("${user.get(0).get("name")} / ${user.get(1).get("name")} / ${user.get(2).get("name")} :${user.get(0).get("point")}")
                    textView6.setText("${user.get(3).get("name")} :${user.get(3).get("point")}")
                }else if (user.get(0).containsValue(user.get(1).get("point"))  && user.get(2).containsValue(user.get(3).get("point"))) {
                    textView5.setText("${user.get(0).get("name")} / ${user.get(1).get("name")} :${user.get(0).get("point")}")
                    textView6.setText("${user.get(2).get("name")} / ${user.get(3).get("name")} :${user.get(2).get("point")}")
                }else if (!user.get(0).containsValue(user.get(1).get("point")) && user.get(1).containsValue(user.get(2).get("point")) && user.get(1).containsValue(user.get(3).get("point"))) {
                    textView5.setText("${user.get(0).get("name")} :${user.get(0).get("point")}")
                    textView6.setText("${user.get(1).get("name")} / ${user.get(2).get("name")} / ${user.get(3).get("name")} :${user.get(1).get("point")}")
                }else if (!user.get(0).containsValue(user.get(1).get("point")) && !user.get(1).containsValue(user.get(2).get("point")) && user.get(2).containsValue(user.get(3).get("point"))) {
                    textView5.setText("${user.get(0).get("name")} :${user.get(0).get("point")}")
                    textView6.setText("${user.get(1).get("name")} :${user.get(1).get("point")}")
                    textView7.setText("${user.get(2).get("name")} / ${user.get(3).get("name")} :${user.get(2).get("point")}")
                }else if (!user.get(0).containsValue(user.get(1).get("point")) && !user.get(1).containsValue(user.get(2).get("point")) && !user.get(2).containsValue(user.get(3).get("point"))) {
                    textView5.setText("${user.get(0).get("name")} :${user.get(0).get("point")}")
                    textView6.setText("${user.get(1).get("name")} :${user.get(1).get("point")}")
                    textView7.setText("${user.get(2).get("name")} :${user.get(2).get("point")}")
                    textView8.setText("${user.get(3).get("name")} :${user.get(3).get("point")}")
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

    //TODO BTN_EXIT_APP
    fun exitApp() {
        btnExitapp.setOnClickListener {
            //exitProcess(0)
           // System.exit(-1)
//            dbTestCH.get().addOnSuccessListener {
//                if (it.exists()) {
//                    return@addOnSuccessListener
//                } else {
//                    return@addOnSuccessListener
//                }
//            }
            finishAffinity()
        }
    }

    //TODO Logdfix
    fun logdfix(a: String, b: String) {
        Log.d(a, b)
    }

    override fun onDestroy() {
        super.onDestroy()
        dbGiveCard.get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (doc in it) {
                    dbGiveCard.document(doc.id).delete()
                }
            }
        }
        db_data_next_room.document(roomName).delete()
        db_next.delete()
    }
}