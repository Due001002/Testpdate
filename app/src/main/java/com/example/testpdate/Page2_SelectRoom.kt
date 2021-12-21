package com.example.testpdate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_page2_select_room.*

class Page2_SelectRoom : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    var sizePlayers = 0
    var getStringEdt = ""
    var G_dataUserArray = mutableListOf<Map<String, Any>>()
    var db_data_next_room = db.collection("data").document("next").collection("room")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page2_select_room)
        selectRoom()
        btnBack()

    }

    //TODO BTN_Back
    fun btnBack() {
        btnBack_Page2_SelectRoom.setOnClickListener {
            finish()
        }
    }

    //TODO selectRoom
    fun selectRoom() {
        var bundle = intent.extras
        var getTextEdt = ""
        var getCheckName = bundle!!.getString("checkName") as String
        var getUserMapPage1 = bundle!!.get("userHashMap") as HashMap<String, Any>
        var getID = bundle!!.get("id") as Int
        db_data_next_room.addSnapshotListener { docs, error -> //TODO addSnapshot_RealTime
            if (docs!!.isEmpty) {
            } else {
                var getDoc = docs!!.documents.get(0).get("roomName").toString()
                textView_Page2_SelectRoom.text = getDoc
                getTextEdt = textView_Page2_SelectRoom.text.toString()
            }
        }
        btnNext_Page2_SelectRoom.setOnClickListener { //TODO btnOnClick
//            var abc = mutableListOf<Map<String,Any>>()
            var getText = textView_Page2_SelectRoom.text.toString()
            db_data_next_room.document(getText).get().addOnSuccessListener { //TODO GetData_Once
                var getUser = it.get("user") as MutableList<Map<String, Any>>
                for (i in 0..getUser.size - 1) {
                    G_dataUserArray.add(getUser[i])
//                    logdfix("getUser","getUser[$i]: ${abc[i]}\n")
                }
                G_dataUserArray.add(getUserMapPage1)
                getStringEdt = textView_Page2_SelectRoom.text.toString()
                var updateDataUser = mapOf<String, Any>(
                    "user" to G_dataUserArray
                )
                db_data_next_room.document(getStringEdt).update(updateDataUser)
                var intent = Intent(this, Page3_GameUi::class.java)
                intent.putExtra("userHashMap", getUserMapPage1)
                intent.putExtra("checkName", getCheckName)
                intent.putExtra("roomNamePage2_SelectRoom", getText)
                intent.putExtra("id", getID)
                startActivity(intent)
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

//    //TODO onDestroy
//    override fun onDestroy() {
//        super.onDestroy()
//        toast("onDestroy")
//        setDataDB.document(getStringEdt).delete().addOnSuccessListener {
//            toast("Destroy !!! delete")
//        }
//    }
}
