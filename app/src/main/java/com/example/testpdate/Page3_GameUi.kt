package com.example.testpdate

import android.content.Intent
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_lobby.*
import kotlinx.android.synthetic.main.activity_page3_game_ui.*

class Page3_GameUi : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    var db_next = db.collection("data").document("next")
    var dbGiveCard = db.collection("data").document("next").collection("giveCard")
    var db_data_next_room = db.collection("data").document("next").collection("room")
    var gRoomName = ""
    var gMe = hashMapOf<String, Any>()
    var gMeName = ""
    var gMeValue = 0
    var gMePoint = 0
    var gMeStatus = ""
    var gMeText = ""
    var gUser2 = hashMapOf<String, Any>()
    var gUser2Name = ""
    var gUser2Value = 0
    var gUser2Point = 0
    var gUser2Status = ""
    var gUser2Text = ""
    var gUser3 = hashMapOf<String, Any>()
    var gUser3Name = ""
    var gUser3Value = 0
    var gUser3Point = 0
    var gUser3Status = ""
    var gUser3Text = ""
    var gUser4 = hashMapOf<String, Any>()
    var gUser4Name = ""
    var gUser4Value = 0
    var gUser4Point = 0
    var gUser4Status = ""
    var gUser4Text = ""
    var pointUser4 = 0
    var getCardDB = mutableListOf<String>()
    var jao = "เจ้ามือ"
    var stage = 0
    var time  = 0
    var round = 0
    var backCard = mutableListOf<String>("n", "n")
    var gKey = key()
    var ggetSize = 0
    var checkName = ""
    var cardHost = mutableListOf<String>()
    var cardUser2 = mutableListOf<String>()
    var cardUser3 = mutableListOf<String>()
    var cardUser4 = mutableListOf<String>()
    var mapData = mutableListOf<Map<*, *>>()
    var ID1 = mutableListOf<Int>(1,2,3,4)
    var ID2 = mutableListOf<Int>(2,3,4,1)
    var ID3 = mutableListOf<Int>(3,4,1,2)
    var ID4 = mutableListOf<Int>(4,1,2,3)
    var setID = mutableListOf<Int>()
    var ID = 0
    var card = mutableListOf<String>()
//    var card = mutableListOf<String>(
//        "1fj", "2fj", "3fj", "4fj", "5fj", "6fj", "7fj", "8fj", "9fj", "10fj",
//        "1kl", "2kl", "3kl", "4kl", "5kl", "6kl", "7kl", "8kl", "9kl", "10kl",
//        "1pb", "2pb", "3pb", "4pb", "5pb", "6pb", "7pb", "8pb", "9pb", "10pb",
//        "1pr", "2pr", "3pr", "4pr", "5pr", "6pr", "7pr", "8pr", "9pr", "10pr",
//        "jfj", "jkl", "jpb", "jpr",
//        "kfj", "kkl", "kpb", "kpr",
//        "qfj", "qkl", "qpb", "qpr"
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page3_game_ui)
//      mapData.add(getUserMapPage2[0])
        var bundle = intent.extras
        var getCheckName = bundle!!.getString("checkName") as String
        var getUserMapPage2 = bundle!!.get("userHashMap") as HashMap<String, Any>
            ID = bundle!!.getInt("id")
        checkName = getCheckName
        gMe = getUserMapPage2
        logdfix("mapData", "mapData: $mapData")
        btnNext_page3UI.isVisible = false
        //delete()
        checkStatus()
        begin()
    }

    //TODO CheckStatusForGetData
    fun checkStatus() {
        var bundle = intent.extras
        if (gMe.get(gKey.status) == "host") {
            var getRoomName = bundle!!.get("roomNamePage2_CreatRoom") as String
                card = bundle!!.get("card") as MutableList<String>
            gRoomName = getRoomName
            logdfix("card","card: $card")
        } else {
            var getRoomName = bundle!!.get("roomNamePage2_SelectRoom") as String
            gRoomName = getRoomName
        }
    }

    //TODO SetSizeUser
    fun begin() {
        btnGiveCard.isVisible = false
        db_data_next_room.document(gRoomName).addSnapshotListener { docs, error ->
            if (docs!!.exists()) {
                var sizeUser = docs!!.get("sizeUser").toString().toInt()
                logdfix("aaa", "sizeUser: $sizeUser")
                if (sizeUser == 2) {
                    l2.isVisible = false
                    l3.isVisible = false
                    addUser2()
                } else if (sizeUser == 3) {
                    l2.isVisible = false
                    //addUser3()
                    setUi()
                } else if (sizeUser == 4) {
                    addUser4()
                }
            }
        }
    }

    //TODO AddUser2
    fun addUser2() {
        var ArrayUser = mutableListOf<HashMap<String, Any>>(gMe)
        db_data_next_room.document(gRoomName).addSnapshotListener { docs, error ->
            if (docs!!.exists()) {
                var getSizeUser = docs!!.get("user") as MutableList<HashMap<String, Any>>
                ggetSize = getSizeUser.size
                for (doc in getSizeUser) {
                    logdfix("addUser", "name: ${doc}")
                    if (gMe.isNotEmpty() && gUser2.isNotEmpty()) {
                        logdfix("addUser", "IN")
                        return@addSnapshotListener
                    }
                    if (doc.containsValue(gMe["name"].toString()) || doc.containsValue(gUser2["name"].toString())){
                        if (!ArrayUser.contains(doc)) {
                            ArrayUser.add(doc)
                        }
                    } else if (gUser2.isEmpty()) {
                        gUser2 = doc
                    }
                    logdfix("addUser", "ME: ${gMe["name"].toString()}")
                    logdfix("addUser", "User2: ${gUser2["name"].toString()}")
                    setUi()
                }
            } else {

            }
        }
    }

    //TODO AddUser3
    fun addUser3() {
        logdfix("addUser", "****** OPEN ADD 3******")
        var ArrayUser = mutableListOf<HashMap<String, Any>>(gMe)
        db_data_next_room.document(gRoomName).addSnapshotListener { docs, error ->
            if (docs!!.exists()) {
                var getSizeUser = docs!!.get("user") as MutableList<HashMap<String, Any>>
                ggetSize = getSizeUser.size
                if (gMe.isEmpty() || gUser2.isEmpty() || gUser3.isEmpty()) {
                    for (doc in getSizeUser) {
                        if (gMe.isNotEmpty() && gUser2.isNotEmpty() && gUser3.isNotEmpty()) {
                            logdfix("addUser", "IN")
                            return@addSnapshotListener
                        }
                        if (doc.containsValue(gMe["name"].toString()) || doc.containsValue(gUser2["name"].toString()) || doc.containsValue(
                                gUser3["name"].toString())
                        ) {
                            if (!ArrayUser.contains(doc)) {
                                ArrayUser.add(doc)
                            }
                            logdfix("addUser", "name: ${doc["name"].toString()}")
                        } else if (gUser2.isEmpty()) {
                            gUser2 = doc
                        } else if (gUser3.isEmpty()) {
                            gUser3 = doc
                        }
                        logdfix("addUser", "ME: ${gMe["name"].toString()}")
                        logdfix("addUser", "User2: ${gUser2["name"].toString()}")
                        logdfix("addUser", "User3: ${gUser3["name"].toString()}")
                        logdfix("addUser", "User4: ${gUser4["name"].toString()}")
                        setUi()
                    }
                } else {

                }
            } else {

            }
        }
    }

    //TODO AddUser4
    fun addUser4() {
        var ArrayUser = mutableListOf<HashMap<String, Any>>(gMe)
        db_data_next_room.document(gRoomName).addSnapshotListener { docs, error ->
            if (docs!!.exists()) {
                var getSizeUser = docs!!.get("user") as MutableList<HashMap<String, Any>>
                ggetSize = getSizeUser.size
                for (doc in getSizeUser) {
                    if (gMe.isNotEmpty() && gUser2.isNotEmpty() && gUser3.isNotEmpty() && gUser4.isNotEmpty()) {
                        logdfix("addUser", "IN")
                        return@addSnapshotListener
                    }
                    if (doc.containsValue(gMe["name"].toString()) || doc.containsValue(gUser2["name"].toString()) || doc.containsValue(
                            gUser3["name"].toString()) || doc.containsValue(gUser4["name"].toString())
                    ) {
                        if (!ArrayUser.contains(doc)) {
                            ArrayUser.add(doc)
                        }
                        logdfix("addUser", "name: ${doc["name"].toString()}")
                    } else if (gUser2.isEmpty()) {
                        gUser2 = doc
                    } else if (gUser3.isEmpty()) {
                        gUser3 = doc
                    } else if (gUser4.isEmpty()) {
                        gUser4 = doc
                    }
                    logdfix("addUser", "ME: ${gMe}")
                    logdfix("addUser", "User2: ${gUser2}")
                    logdfix("addUser", "User3: ${gUser3}")
                    logdfix("addUser", "User4: ${gUser4}")
                    setUi()
                }
            } else {

            }
        }
    }

    //TODO testUi
    fun setUi() {
        db_data_next_room.document(gRoomName).addSnapshotListener { docs, error ->
            if (docs!!.exists()) {
                if (gMe.get("status").toString() == "host") {
                    nextState()
                    btnNext_page3UI.isVisible = true
                }
            }
            if (ID == 1 || ID == 2 || ID == 3 || ID == 4) { //TODO Check ID
                if (ID == 1) {
                    setID = ID1
                }else if (ID == 2) {
                    setID = ID2
                }else if (ID == 3) {
                    setID = ID3
                }else if (ID == 4) {
                    setID = ID4
                }
            }

            if (docs!!.exists()) { // TODO SET UI
                var getSizeUser = docs!!.get("user") as MutableList<HashMap<String, Any>>
                round = docs!!.get(gKey.round).toString().toInt()
                stage = docs!!.get(gKey.stage).toString().toInt()
                time  = docs!!.get(gKey.time).toString().toInt()
                ggetSize = getSizeUser.size
                for (doc in getSizeUser) {
                    var getCard = doc!!.get("card") as MutableList<String>
                    if (doc.containsValue(gMe["name"].toString())) { //TODO gMe
                        gMe = doc
                        gMeName = doc.get(gKey.name).toString()
                        gMeValue = doc.get("value").toString().toInt()
                        gMePoint = doc.get("point").toString().toInt()
                        gMeStatus = doc.get("status").toString()
                        var text = doc.get("text").toString()
                        if (gMeStatus == "host") {
                            txtName1_page3UI.setTextColor(Color.parseColor("#DD0A0A"))
                            txtPoint1_page3UI.setTextColor(Color.parseColor("#DD0A0A"))
                            txtStatus1_page3UI.setTextColor(Color.parseColor("#DD0A0A"))
                            txtName1_page3UI.setText("$gMeName")
                            txtPoint1_page3UI.setText("คะแนน: $gMePoint")
                            txtStatus1_page3UI.setText(text)
                            if (stage == 0) {
                                imgView1_3_page3UI.setImageResource(R.drawable.backphai)
                                imgView2_3_page3UI.setImageResource(R.drawable.backphai)
                                imgView3_3_page3UI.setImageResource(R.drawable.backphai)
                                imgView4_3_page3UI.setImageResource(R.drawable.backphai)
                            }
                            if (stage >= 1) {
                                txtName1_page3UI.setText("$gMeName /$gMeValue")
                                txtPoint1_page3UI.setText("คะแนน: $gMePoint")
                                txtStatus1_page3UI.setText(text)
                                imgView1_1_page3UI.setImageResource(getCardShow(getCard[0]))
                                imgView1_2_page3UI.setImageResource(getCardShow(getCard[1]))
                                if (getCard.size == 2 && stage == 1) {
                                    btnGiveCard.isVisible = true
                                    giveCard()
                                }
                                if (stage == 2) {
                                    btnGiveCard.isVisible = false
                                    if (getCard.size == 3) {
                                        imgView1_3_page3UI.setImageResource(getCardShow(getCard[2]))
                                    }
                                }
                                if (getCard.size == 3 && stage == 3) {
                                    imgView1_1_page3UI.setImageResource(getCardShow(getCard[0]))
                                    imgView1_2_page3UI.setImageResource(getCardShow(getCard[1]))
                                    imgView1_3_page3UI.setImageResource(getCardShow(getCard[2]))
                                }
                            }
                        } else {
                            txtName1_page3UI.setText("$gMeName")
                            txtPoint1_page3UI.setText("คะแนน: $gMePoint")
                            txtStatus1_page3UI.setText(text)
                            if (stage == 0) {
                                imgView1_3_page3UI.setImageResource(R.drawable.backphai)
                                imgView2_3_page3UI.setImageResource(R.drawable.backphai)
                                imgView3_3_page3UI.setImageResource(R.drawable.backphai)
                                imgView4_3_page3UI.setImageResource(R.drawable.backphai)
                            }
                            if (stage >= 1) {
                                txtName1_page3UI.setText("$gMeName / $gMeValue")
                                txtPoint1_page3UI.setText("คะแนน: $gMePoint")
                                txtStatus1_page3UI.setText(text)
                                imgView1_1_page3UI.setImageResource(getCardShow(getCard[0]))
                                imgView1_2_page3UI.setImageResource(getCardShow(getCard[1]))
                                if (getCard.size == 2 && stage == 1) {
                                    btnGiveCard.isVisible = true
                                    giveCard()
                                }
                                if (stage == 2) {
                                    btnGiveCard.isVisible = false
                                    if (getCard.size == 3) {
                                        imgView1_3_page3UI.setImageResource(getCardShow(getCard[2]))
                                    }
                                }
                                if (getCard.size == 3 && stage == 3) {
                                    imgView1_1_page3UI.setImageResource(getCardShow(getCard[0]))
                                    imgView1_2_page3UI.setImageResource(getCardShow(getCard[1]))
                                    imgView1_3_page3UI.setImageResource(getCardShow(getCard[2]))
                                }
                            }
                        }
                        if (stage == 1) {
                            txtRound_page3UI.setText("รอบ:$round /ขั้นตอน:$stage /เวลา: $time")
                            if (time > 0) {
                                btnGiveCard.isVisible = true
                            } else {
                                btnGiveCard.isVisible = false
                            }
                        } else {
                            txtRound_page3UI.setText("รอบ:$round /ขั้นตอน:$stage")
                        }
                        if (round == 5) {
                            sendDataToPage4()
                        }
                    } else if (doc.containsValue(gUser2["name"].toString())) { //TODO user2
                        gUser4 = doc
                        gUser4Name = doc["name"].toString()
                        gUser4Point = doc["point"].toString().toInt()
                        gUser4Value = doc.get("value").toString().toInt()
                        gUser4Status = doc.get("status").toString()
                        if (gUser4Status == "host") {
                            txtName2_page3UI.setTextColor(Color.parseColor("#DD0A0A"))
                            txtPoint2_page3UI.setTextColor(Color.parseColor("#DD0A0A"))
                            txtStatus2_page3UI.setTextColor(Color.parseColor("#DD0A0A"))
                        }
                        var text = doc.get("text").toString()
                        txtName2_page3UI.setText("${gUser4Name}")
                        txtPoint2_page3UI.setText("คะแนน: ${gUser4Point}")
                        txtStatus2_page3UI.setText("$text")
                        if (gUser4Value == 8 || gUser4Value == 9) {
                            if (getCard.size == 2) {
                                imgView2_1_page3UI.setImageResource(getCardShow(getCard[0]))
                                imgView2_2_page3UI.setImageResource(getCardShow(getCard[1]))
                                txtName2_page3UI.setText("${gUser4Name} / ${gUser4Value}")
                            } else {
                                imgView2_1_page3UI.setImageResource(getCardShow(getCard[0]))
                                imgView2_2_page3UI.setImageResource(getCardShow(getCard[1]))
                                imgView2_3_page3UI.setImageResource(getCardShow(getCard[2]))
                                txtName2_page3UI.setText("${gUser4Name} / ${gUser4Value}")
                            }

                        }
                        if (stage >= 3) {
                            txtName2_page3UI.setText("${gUser4Name} / ${gUser4Value}")
                            txtPoint2_page3UI.setText("คะแนน: ${gUser4Point}")
                            imgView2_1_page3UI.setImageResource(getCardShow(getCard[0]))
                            imgView2_2_page3UI.setImageResource(getCardShow(getCard[1]))
                            if (getCard.size == 3) {
                                imgView2_1_page3UI.setImageResource(getCardShow(getCard[0]))
                                imgView2_2_page3UI.setImageResource(getCardShow(getCard[1]))
                                imgView2_3_page3UI.setImageResource(getCardShow(getCard[2]))
                            }
                        }
                    } else if (doc.containsValue(gUser3["name"].toString())) { //TODO user3
                        gUser3 = doc
                        gUser3Name = doc["name"].toString()
                        gUser3Point = doc["point"].toString().toInt()
                        gUser3Value = doc.get("value").toString().toInt()
                        gUser3Status = doc.get("status").toString()
                        if (gUser3Status == "host") {
                            txtName3_page3UI.setTextColor(Color.parseColor("#DD0A0A"))
                            txtPoint3_page3UI.setTextColor(Color.parseColor("#DD0A0A"))
                            txtStatus3_page3UI.setTextColor(Color.parseColor("#DD0A0A"))
                        }
                        var text = doc.get("text").toString()
                        txtName3_page3UI.setText("${gUser3Name}")
                        txtPoint3_page3UI.setText("คะแนน: ${gUser3Point}")
                        txtStatus3_page3UI.setText("$text")

                        if (gUser3Value == 8 || gUser3Value == 9) {
                            if (getCard.size == 2) {
                                txtName3_page3UI.setText("${gUser3Name} / ${gUser3Value}")
                                imgView3_1_page3UI.setImageResource(getCardShow(getCard[0]))
                                imgView3_2_page3UI.setImageResource(getCardShow(getCard[1]))
                            } else {
                                txtName3_page3UI.setText("${gUser3Name} / ${gUser3Value}")
                                imgView3_1_page3UI.setImageResource(getCardShow(getCard[0]))
                                imgView3_2_page3UI.setImageResource(getCardShow(getCard[1]))
                                imgView3_3_page3UI.setImageResource(getCardShow(getCard[2]))
                            }
                        }
                        if (stage >= 3) {
                            txtName3_page3UI.setText("${gUser3Name} / ${gUser3Value}")
                            txtPoint3_page3UI.setText("คะแนน: ${gUser3Point}")
                            imgView3_1_page3UI.setImageResource(getCardShow(getCard[0]))
                            imgView3_2_page3UI.setImageResource(getCardShow(getCard[1]))
                            if (getCard.size == 3) {
                                imgView3_1_page3UI.setImageResource(getCardShow(getCard[0]))
                                imgView3_2_page3UI.setImageResource(getCardShow(getCard[1]))
                                imgView3_3_page3UI.setImageResource(getCardShow(getCard[2]))
                            }
                        }
                    } else if (doc.containsValue(gUser4["name"].toString())) {  //TODO user4
                        gUser2 = doc
                        gUser2Name = doc["name"].toString()
                        gUser2Point = doc["point"].toString().toInt()
                        gUser2Value = doc.get("value").toString().toInt()
                        gUser2Status = doc.get("status").toString()
                        if (gUser2Status == "host") {
                            txtName4_page3UI.setTextColor(Color.parseColor("#DD0A0A"))
                            txtPoint4_page3UI.setTextColor(Color.parseColor("#DD0A0A"))
                            txtStatus4_page3UI.setTextColor(Color.parseColor("#DD0A0A"))
                        }
                        var text = doc.get("text").toString()
                        txtName4_page3UI.setText("${gUser2Name}")
                        txtPoint4_page3UI.setText("Point: ${gUser2Point}")
                        txtStatus4_page3UI.setText("$text")

                        if (gUser2Value == 8 || gUser2Value == 9) {
                            if (getCard.size == 2) {
                                txtName4_page3UI.setText("${gUser2Name} / ${gUser2Value}")
                                imgView4_1_page3UI.setImageResource(getCardShow(getCard[0]))
                                imgView4_2_page3UI.setImageResource(getCardShow(getCard[1]))
                            } else {
                                txtName4_page3UI.setText("${gUser2Name} / ${gUser2Value}")
                                imgView4_1_page3UI.setImageResource(getCardShow(getCard[0]))
                                imgView4_2_page3UI.setImageResource(getCardShow(getCard[1]))
                                imgView4_3_page3UI.setImageResource(getCardShow(getCard[2]))
                            }
                        }
                        if (stage >= 3) {
                            txtName4_page3UI.setText("${gUser2Name} / ${gUser2Value}")
                            txtPoint4_page3UI.setText("คะแนน: ${gUser2Point}")
                            imgView4_1_page3UI.setImageResource(getCardShow(getCard[0]))
                            imgView4_2_page3UI.setImageResource(getCardShow(getCard[1]))

                            if (getCard.size == 3) {
                                imgView4_1_page3UI.setImageResource(getCardShow(getCard[0]))
                                imgView4_2_page3UI.setImageResource(getCardShow(getCard[1]))
                                imgView4_3_page3UI.setImageResource(getCardShow(getCard[2]))
                            }
                        }
                    }
                }
            } else {
//                txtName1_page3UI.setText("")
//                txtPoint1_page3UI.setText("")
//                txtStatus1_page3UI.setText("")
//                txtName2_page3UI.setText("")
//                txtPoint2_page3UI.setText("")
//                txtStatus2_page3UI.setText("")
//                txtName3_page3UI.setText("")
//                txtPoint3_page3UI.setText("")
//                txtStatus3_page3UI.setText("")
//                txtName4_page3UI.setText("")
//                txtPoint4_page3UI.setText("")
//                txtStatus4_page3UI.setText("")
//                imgView1_1_page3UI.setImageResource(R.drawable.backphai)
//                imgView1_2_page3UI.setImageResource(R.drawable.backphai)
//                imgView1_3_page3UI.setImageResource(R.drawable.backphai)
//                imgView2_1_page3UI.setImageResource(R.drawable.backphai)
//                imgView2_2_page3UI.setImageResource(R.drawable.backphai)
//                imgView2_3_page3UI.setImageResource(R.drawable.backphai)
//                imgView3_1_page3UI.setImageResource(R.drawable.backphai)
//                imgView3_2_page3UI.setImageResource(R.drawable.backphai)
//                imgView3_3_page3UI.setImageResource(R.drawable.backphai)
//                imgView4_1_page3UI.setImageResource(R.drawable.backphai)
//                imgView4_2_page3UI.setImageResource(R.drawable.backphai)
//                imgView4_3_page3UI.setImageResource(R.drawable.backphai)
                var intent = Intent(this, Page1_Login::class.java)
                startActivity(intent)
            }
        }
    }


    //TODO GiveCard
    fun giveCard() {
        btnGiveCard.setOnClickListener {
            var a = mapOf(gKey.giveCard to 1)
            dbGiveCard.document(gMe[gKey.name].toString()).set(a)
            btnGiveCard.isVisible = false
        }
    }

    //TODO nextState
    fun nextState() {
        btnNext_page3UI.setOnClickListener {
            db_data_next_room.document(gRoomName).get().addOnSuccessListener {
                var getRound = it!!.get("${gKey.round}").toString().toInt()
                var getState = it!!.get("${gKey.stage}").toString().toInt()
                getCardDB = it!!.get("${gKey.card}") as MutableList<String>
                var sumState = getState + 1
                stage = sumState
                var setData = mapOf<String, Any>("${gKey.stage}" to sumState)
                if (stage <= 3) {
                    when (stage) {
//                        0 -> {
//                            dbTestCH.update(setData);state0_ReStart()
//                        }
                        1 -> {
                            db_data_next_room.document(gRoomName).update(setData); state1_DealerCard()
                        }
                        2 -> {
                            db_data_next_room.document(gRoomName).update(setData);state2_GiveCard()
                        }
                        3 -> {
                            db_data_next_room.document(gRoomName).update(setData);state3_BattleCard()
                        }
                    }
                } else {
                    stage = 0
                    if (stage == 0) {
                        state0_ReStart()
                    }
                    getRound += 1
                    var setupData = mapOf(
                        gKey.round to getRound,
                        gKey.stage to 0
                    )
                    db_data_next_room.document(gRoomName).update(setupData)
                }
            }
        }
    }

    //TODO DealerCard Stage:1
    fun state1_DealerCard() {
        var totalHost = 0
        var totalUser2 = 0
        var totalUser3 = 0
        var totalUser4 = 0

        if (ggetSize == 2) { //TODO SIZE == 2
            cardHost.clear()
            cardUser2.clear()
            getCardDB.shuffle()
            cardHost.add(getCardDB[0])
            cardHost.add(getCardDB[1])
            cardUser2.add(getCardDB[2])
            cardUser2.add(getCardDB[3])
            var rsIdCardMe_1 = getCardString(cardHost[0])
            var rsIdCardMe_2 = getCardString(cardHost[1])
            var rsIdCardMe_3 = 0
            var rsIdCardUser2_1 = getCardString(cardUser2[0])
            var rsIdCardUser2_2 = getCardString(cardUser2[1])
            var rsIdCardUser2_3 = 0
            getCardDB.removeAt(0)
            getCardDB.removeAt(0)
            getCardDB.removeAt(0)
            getCardDB.removeAt(0)
            totalHost = calculatorValue(rsIdCardMe_1.value, rsIdCardMe_2.value)
            totalUser2 = calculatorValue(rsIdCardUser2_1.value, rsIdCardUser2_2.value)
            var updateUser1 = mutableListOf<Map<Any, Any>>(
                mapOf(
                    gKey.name to gMe.get("name").toString(),
                    gKey.value to totalHost,
                    gKey.point to gMe.get("point").toString(),
                    gKey.status to gMe.get("status").toString(),
                    gKey.text to jao,
                    gKey.card to cardHost
                ),
                mapOf(
                    gKey.name to gUser2.get("name").toString(),
                    gKey.value to totalUser2,
                    gKey.point to gUser2.get("point").toString(),
                    gKey.status to gUser2.get("status").toString(),
                    gKey.text to gUser2.get("text").toString(),
                    gKey.card to cardUser2
                )
            )

            var update = mapOf(
                gKey.card to getCardDB,
                gKey.user to updateUser1
            )
            db_data_next_room.document(gRoomName).update(update).addOnSuccessListener {timeCount()}

        } else if (ggetSize == 3) { //TODO SIZE == 3
            cardHost.clear()
            cardUser2.clear()
            cardUser3.clear()
            getCardDB.shuffle()
            cardHost.add(getCardDB[0])
            cardHost.add(getCardDB[1])
            cardUser2.add(getCardDB[2])
            cardUser2.add(getCardDB[3])
            cardUser3.add(getCardDB[4])
            cardUser3.add(getCardDB[5])
//        cardUser4.add(getCardDB[6])
//        cardUser4.add(getCardDB[7])
            var rsIdCardMe_1 = getCardString(cardHost[0])
            var rsIdCardMe_2 = getCardString(cardHost[1])
            var rsIdCardMe_3 = 0
            var rsIdCardUser2_1 = getCardString(cardUser2[0])
            var rsIdCardUser2_2 = getCardString(cardUser2[1])
            var rsIdCardUser2_3 = 0
            var rsIdCardUser3_1 = getCardString(cardUser3[0])
            var rsIdCardUser3_2 = getCardString(cardUser3[1])
            var rsIdCardUser3_3 = 0
            getCardDB.removeAt(0)
            getCardDB.removeAt(0)
            getCardDB.removeAt(0)
            getCardDB.removeAt(0)
            getCardDB.removeAt(0)
            getCardDB.removeAt(0)
            totalHost = calculatorValue(rsIdCardMe_1.value, rsIdCardMe_2.value)
            totalUser2 = calculatorValue(rsIdCardUser2_1.value, rsIdCardUser2_2.value)
            totalUser3 = calculatorValue(rsIdCardUser3_1.value, rsIdCardUser3_2.value)

            var updateUser1 = mutableListOf<Map<Any, Any>>(
                mapOf(
                    gKey.name to gMe.get("name").toString(),
                    gKey.value to totalHost,
                    gKey.point to gMe.get("point").toString(),
                    gKey.status to gMe.get("status").toString(),
                    gKey.text to jao,
                    gKey.card to cardHost
                ),
                mapOf(
                    gKey.name to gUser2.get("name").toString(),
                    gKey.value to totalUser2,
                    gKey.point to gUser2.get("point").toString(),
                    gKey.status to gUser2.get("status").toString(),
                    gKey.text to gUser2.get("text").toString(),
                    gKey.card to cardUser2
                ),
                mapOf(
                    gKey.name to gUser3.get("name").toString(),
                    gKey.value to totalUser3,
                    gKey.point to gUser3.get("point").toString(),
                    gKey.status to gUser3.get("status").toString(),
                    gKey.text to gUser3.get("text").toString(),
                    gKey.card to cardUser3
                )
            )

            var update = mapOf(
                gKey.card to getCardDB,
                gKey.user to updateUser1
            )
            db_data_next_room.document(gRoomName).update(update).addOnSuccessListener {timeCount()}
        } else if (ggetSize == 4) { //TODO Size == 4
            cardHost.clear()
            cardUser2.clear()
            cardUser3.clear()
            cardUser4.clear()
            getCardDB.shuffle()
            cardHost.add(getCardDB[0])
            cardHost.add(getCardDB[1])
            cardUser2.add(getCardDB[2])
            cardUser2.add(getCardDB[3])
            cardUser3.add(getCardDB[4])
            cardUser3.add(getCardDB[5])
            cardUser4.add(getCardDB[6])
            cardUser4.add(getCardDB[7])
            var rsIdCardMe_1 = getCardString(cardHost[0])
            var rsIdCardMe_2 = getCardString(cardHost[1])
            var rsIdCardUser2_1 = getCardString(cardUser2[0])
            var rsIdCardUser2_2 = getCardString(cardUser2[1])
            var rsIdCardUser3_1 = getCardString(cardUser3[0])
            var rsIdCardUser3_2 = getCardString(cardUser3[1])
            var rsIdCardUser4_1 = getCardString(cardUser4[0])
            var rsIdCardUser4_2 = getCardString(cardUser4[1])
            getCardDB.removeAt(0)
            getCardDB.removeAt(0)
            getCardDB.removeAt(0)
            getCardDB.removeAt(0)
            getCardDB.removeAt(0)
            getCardDB.removeAt(0)
            getCardDB.removeAt(0)
            getCardDB.removeAt(0)
            totalHost = calculatorValue(rsIdCardMe_1.value, rsIdCardMe_2.value)
            totalUser2 = calculatorValue(rsIdCardUser2_1.value, rsIdCardUser2_2.value)
            totalUser3 = calculatorValue(rsIdCardUser3_1.value, rsIdCardUser3_2.value)
            totalUser4 = calculatorValue(rsIdCardUser4_1.value, rsIdCardUser4_2.value)

            var updateUser1 = mutableListOf<Map<Any, Any>>(
                mapOf(
                    gKey.name to gMe.get("name").toString(),
                    gKey.value to totalHost,
                    gKey.point to gMe.get("point").toString(),
                    gKey.status to gMe.get("status").toString(),
                    gKey.text to jao,
                    gKey.card to cardHost
                ),
                mapOf(
                    gKey.name to gUser2.get("name").toString(),
                    gKey.value to totalUser2,
                    gKey.point to gUser2.get("point").toString(),
                    gKey.status to gUser2.get("status").toString(),
                    gKey.text to gUser2.get("text").toString(),
                    gKey.card to cardUser2
                ),
                mapOf(
                    gKey.name to gUser3.get("name").toString(),
                    gKey.value to totalUser3,
                    gKey.point to gUser3.get("point").toString(),
                    gKey.status to gUser3.get("status").toString(),
                    gKey.text to gUser3.get("text").toString(),
                    gKey.card to cardUser3
                ),
                mapOf(
                    gKey.name to gUser4.get("name").toString(),
                    gKey.value to totalUser4,
                    gKey.point to gUser4.get("point").toString(),
                    gKey.status to gUser4.get("status").toString(),
                    gKey.text to gUser4.get("text").toString(),
                    gKey.card to cardUser4
                )
            )

            var update = mapOf(
                gKey.card to getCardDB,
                gKey.user to updateUser1
            )
            db_data_next_room.document(gRoomName).update(update).addOnSuccessListener{timeCount()}
        }
    }

    //TODO state2_GiveCard
    fun state2_GiveCard() {
        db_data_next_room.document(gRoomName).get().addOnSuccessListener { result ->
            toast("กำลังแจกไพ่ รอจนกว่าจะแจกไพ่เสร็จ")
            var valuegMeNew = 0
            var valuegUser2New = 0
            var valuegUser3New = 0
            var valuegUser4New = 0
            var getUser = result!!.get(gKey.user) as MutableList<HashMap<String, Any>>
            var getSize = result!!.get("sizeUser").toString().toInt()
            var getCard = result!!.get("card") as MutableList<String>
            if (getSize == 2) { //TODO size == 2
                var cardHostGiveCard = cardHost
                var cardUser2GiveCard = cardUser2
                dbGiveCard.document(gMeName).get().addOnSuccessListener {
                    if (it.exists()) {
                        var stateGiveCard = it!!.get(gKey.giveCard).toString().toInt()
                        if (stateGiveCard == 1) {
                            var getvalueCard = getCardString(getCard[0])
                            valuegMeNew = getvalueCard.value
                            cardHostGiveCard.add(getCard[0])
                            getCard.removeAt(0)
                            logdfix("GiveCard", "cardHost: $cardHostGiveCard")
                        }
                    } else {
                        logdfix("GiveCard", "No Data")
                    }
                }.addOnSuccessListener {
                    dbGiveCard.document(gUser2Name).get().addOnSuccessListener {
                        if (it.exists()) {
                            var stateGiveCard = it!!.get(gKey.giveCard).toString().toInt()
                            if (stateGiveCard == 1) {
                                var getvalueCard = getCardString(getCard[0])
                                valuegUser2New = getvalueCard.value
                                cardUser2GiveCard.add(getCard[0])
                                getCard.removeAt(0)
                                logdfix("GiveCard", "cardUser2: $cardUser2GiveCard")
                            }
                        } else {

                        }
                        for (user in 0..getUser.size - 1) {
                            logdfix("GiveCard", "for user: $user")
                            if (getUser[user].containsValue(gMeName)) {
                                getUser[user][gKey.card] = cardHostGiveCard
                                getUser[user][gKey.value] = calculatorValue(gMeValue, valuegMeNew)
                                logdfix("GiveCard", "Host to user ${getUser[user]}")
                            } else if (getUser[user].containsValue(gUser2Name)) {
                                getUser[user][gKey.card] = cardUser2GiveCard
                                getUser[user][gKey.value] =
                                    calculatorValue(gUser2Value, valuegUser2New)
                                logdfix("GiveCard", "gUser2Name to user ${getUser[user]}")
                            } else {
                                logdfix("GiveCard", "for else: $user ")
                            }
                        }
                        var updateUser = mapOf(
                            gKey.user to getUser,
                            gKey.card to getCard
                        )
                        db_data_next_room.document(gRoomName).update(updateUser).addOnSuccessListener {
                            toast("แจกไพ่สำเร็จแล้ว พร้อมเริ่มขั้นตอนต่อไป")
                        }
                    }
                }
            } else if (getSize == 3) {  //TODO size == 3
                var cardHostGiveCard = cardHost
                var cardUser2GiveCard = cardUser2
                var cardUser3GiveCard = cardUser3
                dbGiveCard.document(gMeName).get().addOnSuccessListener {
                    if (it.exists()) {
                        var stateGiveCard = it!!.get(gKey.giveCard).toString().toInt()
                        if (stateGiveCard == 1) {
                            var getvalueCard = getCardString(getCard[0])
                            valuegMeNew = getvalueCard.value
                            cardHostGiveCard.add(getCard[0])
                            getCard.removeAt(0)
                        }
                    } else {
                        var a = 0 //TODO Me_ไม่ขอไพ่
                    }
                    dbGiveCard.document(gUser2Name).get().addOnSuccessListener {
                        if (it.exists()) {
                            var stateGiveCard = it!!.get(gKey.giveCard).toString().toInt()
                            if (stateGiveCard == 1) {
                                var getvalueCard = getCardString(getCard[0])
                                valuegUser2New = getvalueCard.value
                                cardUser2GiveCard.add(getCard[0])
                                getCard.removeAt(0)
                            }
                        } else {
                            var a = 0 //TODO user2_ไม่ขอไพ่
                        }
                        dbGiveCard.document(gUser3Name).get().addOnSuccessListener {
                            if (it.exists()) {
                                var stateGiveCard = it!!.get(gKey.giveCard).toString().toInt()
                                if (stateGiveCard == 1) {
                                    var getvalueCard = getCardString(getCard[0])
                                    valuegUser3New = getvalueCard.value
                                    cardUser3GiveCard.add(getCard[0])
                                    getCard.removeAt(0)
                                }
                            } else {
                                var a = 0 //TODO user3_ไม่ขอไพ่
                            }
                            for (user in 0..getUser.size - 1) {
                                logdfix("GiveCard", "for user: $user")
                                if (getUser[user].containsValue(gMeName)) {
                                    getUser[user][gKey.card] = cardHostGiveCard
                                    getUser[user][gKey.value] =
                                        calculatorValue(gMeValue, valuegMeNew)
                                    logdfix("GiveCard", "Host to user ${getUser[user]}")
                                } else if (getUser[user].containsValue(gUser2Name)) {
                                    getUser[user][gKey.card] = cardUser2GiveCard
                                    getUser[user][gKey.value] =
                                        calculatorValue(gUser2Value, valuegUser2New)
                                    logdfix("GiveCard", "gUser2Name to user ${getUser[user]}")
                                } else if (getUser[user].containsValue(gUser3Name)) {
                                    getUser[user][gKey.card] = cardUser3GiveCard
                                    getUser[user][gKey.value] =
                                        calculatorValue(gUser3Value, valuegUser3New)
                                    logdfix("GiveCard", "gUser3Name to user ${getUser[user]}")
                                } else {
                                    logdfix("GiveCard", "for else: $user ")
                                }
                            }
                            var updateUser = mapOf(
                                gKey.user to getUser,
                                gKey.card to getCard
                            )
                            db_data_next_room.document(gRoomName).update(updateUser).addOnSuccessListener {
                                toast("แจกไพ่สำเร็จแล้ว")
                            }
                        }
                    }
                }
            } else if (getSize == 4) {  //TODO size == 4
                var cardHostGiveCard = cardHost
                var cardUser2GiveCard = cardUser2
                var cardUser3GiveCard = cardUser3
                var cardUser4GiveCard = cardUser4
                dbGiveCard.document(gMeName).get().addOnSuccessListener {
                    if (it.exists()) {
                        var stateGiveCard = it!!.get(gKey.giveCard).toString().toInt()
                        if (stateGiveCard == 1) {
                            var getvalueCard = getCardString(getCard[0])
                            valuegMeNew = getvalueCard.value
                            cardHostGiveCard.add(getCard[0])
                            getCard.removeAt(0)
                        }
                    } else {
                        var a = 0 //TODO Me_ไม่ขอไพ่
                    }
                    dbGiveCard.document(gUser2Name).get().addOnSuccessListener {
                        if (it.exists()) {
                            var stateGiveCard = it!!.get(gKey.giveCard).toString().toInt()
                            if (stateGiveCard == 1) {
                                var getvalueCard = getCardString(getCard[0])
                                valuegUser2New = getvalueCard.value
                                cardUser2GiveCard.add(getCard[0])
                                getCard.removeAt(0)
                            }
                        } else {
                            var a = 0 //TODO user2_ไม่ขอไพ่
                        }
                        dbGiveCard.document(gUser3Name).get().addOnSuccessListener {
                            if (it.exists()) {
                                var stateGiveCard = it!!.get(gKey.giveCard).toString().toInt()
                                if (stateGiveCard == 1) {
                                    var getvalueCard = getCardString(getCard[0])
                                    valuegUser3New = getvalueCard.value
                                    cardUser3GiveCard.add(getCard[0])
                                    getCard.removeAt(0)
                                }
                            } else {
                                var a = 0 //TODO user3_ไม่ขอไพ่
                            }
                            dbGiveCard.document(gUser4Name).get().addOnSuccessListener {
                                if (it.exists()) {
                                    var stateGiveCard = it!!.get(gKey.giveCard).toString().toInt()
                                    if (stateGiveCard == 1) {
                                        var getvalueCard = getCardString(getCard[0])
                                        valuegUser4New = getvalueCard.value
                                        cardUser4GiveCard.add(getCard[0])
                                        getCard.removeAt(0)
                                    }
                                } else {
                                    var a = 0 //TODO user3_ไม่ขอไพ่
                                }
                                for (user in 0..getUser.size - 1) {
                                    logdfix("GiveCard", "for user: $user")
                                    if (getUser[user].containsValue(gMeName)) {
                                        getUser[user][gKey.card] = cardHostGiveCard
                                        getUser[user][gKey.value] =
                                            calculatorValue(gMeValue, valuegMeNew)
                                        logdfix("GiveCard", "Host to user ${getUser[user]}")
                                    } else if (getUser[user].containsValue(gUser2Name)) {
                                        getUser[user][gKey.card] = cardUser2GiveCard
                                        getUser[user][gKey.value] =
                                            calculatorValue(gUser2Value, valuegUser2New)
                                        logdfix("GiveCard", "gUser2Name to user ${getUser[user]}")
                                    } else if (getUser[user].containsValue(gUser3Name)) {
                                        getUser[user][gKey.card] = cardUser3GiveCard
                                        getUser[user][gKey.value] =
                                            calculatorValue(gUser3Value, valuegUser3New)
                                        logdfix("GiveCard", "gUser3Name to user ${getUser[user]}")
                                    } else if (getUser[user].containsValue(gUser4Name)) {
                                        getUser[user][gKey.card] = cardUser4GiveCard
                                        getUser[user][gKey.value] =
                                            calculatorValue(gUser4Value, valuegUser4New)
                                        logdfix("GiveCard", "gUser3Name to user ${getUser[user]}")
                                    } else {
                                        logdfix("GiveCard", "for else: $user ")
                                    }
                                }
                                var updateUser = mapOf(
                                    gKey.user to getUser,
                                    gKey.card to getCard
                                )
                                db_data_next_room.document(gRoomName).update(updateUser).addOnSuccessListener {
                                    toast("แจกไพ่สำเร็จแล้ว")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //TODO battleCard
    fun state3_BattleCard() {
        var totalPointHost = gMePoint
        var totalPointUser2 = gUser2Point
        var totalPointUser3 = gUser3Point
        var totalPointUser4 = gUser4Point
        val win = "ชนะ"
        val lose = "แพ้"
        val draw = "เสมอ"
        var textUser2 = ""
        var textUser3 = ""
        var textUser4 = ""
//        val getStringCardHost1 = "${cardHost.get(0).get(1)}${cardHost.get(0).get(2)}"
//        val getStringCardHost2 = "${cardHost.get(1).get(1)}${cardHost.get(1).get(2)}"
//        val getStringCardUser2 = "${cardUser2.get(0).get(1)}${cardHost.get(0).get(2)}"
//        val getStringCardUser2_2 = "${cardUser2.get(1).get(1)}${cardHost.get(1).get(2)}"
//        val getStringCardUser3 = "${cardUser3.get(0).get(1)}${cardHost.get(0).get(2)}"
//        val getStringCardUser3_2 = "${cardUser3.get(1).get(1)}${cardHost.get(1).get(2)}"

        //TODO Calculator
        if (ggetSize == 2) { //TODO Size == 2
            if (gMeValue > gUser2Value || gMeValue < gUser2Value || gMeValue == gUser2Value) {
                if (gMeValue > gUser2Value) {
                    totalPointHost += 50
                    totalPointUser2 -= 50
                    textUser2 = lose
                } else if (gMeValue < gUser2Value) {
                    totalPointHost -= 50
                    totalPointUser2 += 50
                    textUser2 = win
                } else if (gMeValue == gUser2Value) {
                    textUser2 = draw
                }
            }

            var updateDataUser = listOf<Map<String, Any>>(
                mapOf(
                    gKey.name to gMe.get("name").toString(),
                    gKey.value to gMeValue,
                    gKey.point to totalPointHost,
                    gKey.status to gMe.get("status").toString(),
                    gKey.text to jao,
                    gKey.card to cardHost
                ),
                mapOf(
                    gKey.name to gUser2.get("name").toString(),
                    gKey.value to gUser2Value,
                    gKey.point to totalPointUser2,
                    gKey.status to gUser2.get("status").toString(),
                    gKey.text to textUser2,
                    gKey.card to cardUser2
                )
            )
            var setDataCal = mapOf(
                gKey.user to updateDataUser
            )
            db_data_next_room.document(gRoomName).update(setDataCal)

        } else if (ggetSize == 3) { //TODO Size == 3
            if (gMeValue > gUser2Value || gMeValue < gUser2Value || gMeValue == gUser2Value) {
                if (gMeValue > gUser2Value) {
                    totalPointHost += 50
                    totalPointUser2 -= 50
                    textUser2 = lose
                } else if (gMeValue < gUser2Value) {
                    totalPointHost -= 50
                    totalPointUser2 += 50
                    textUser2 = win
                } else if (gMeValue == gUser2Value) {
                    textUser2 = draw
                }
            }

            if (gMeValue > gUser3Value || gMeValue < gUser3Value || gMeValue == gUser3Value) {
                if (gMeValue > gUser3Value) {
                    totalPointHost += 50
                    totalPointUser3 -= 50
                    textUser3 = lose
                } else if (gMeValue < gUser3Value) {
                    totalPointHost -= 50
                    totalPointUser3 += 50
                    textUser3 = win
                } else if (gMeValue == gUser3Value) {
                    textUser3 = draw
                }
            }
            var updateDataUser = listOf<Map<String, Any>>(
                mapOf(
                    gKey.name to gMe.get("name").toString(),
                    gKey.value to gMeValue,
                    gKey.point to totalPointHost,
                    gKey.status to gMe.get("status").toString(),
                    gKey.text to jao,
                    gKey.card to cardHost
                ),
                mapOf(
                    gKey.name to gUser2.get("name").toString(),
                    gKey.value to gUser2Value,
                    gKey.point to totalPointUser2,
                    gKey.status to gUser2.get("status").toString(),
                    gKey.text to textUser2,
                    gKey.card to cardUser2
                ),
                mapOf(
                    gKey.name to gUser3.get("name").toString(),
                    gKey.value to gUser3Value,
                    gKey.point to totalPointUser3,
                    gKey.status to gUser3.get("status").toString(),
                    gKey.text to textUser3,
                    gKey.card to cardUser3
                )
            )
            var setDataCal = mapOf(
                gKey.user to updateDataUser
            )
            db_data_next_room.document(gRoomName).update(setDataCal)

        } else if (ggetSize == 4) { //TODO SIZE == 4
            if (gMeValue > gUser2Value || gMeValue < gUser2Value || gMeValue == gUser2Value) {
                if (gMeValue > gUser2Value) {
                    totalPointHost += 50
                    totalPointUser2 -= 50
                    textUser2 = lose
                } else if (gMeValue < gUser2Value) {
                    totalPointHost -= 50
                    totalPointUser2 += 50
                    textUser2 = win
                } else if (gMeValue == gUser2Value) {
                    textUser2 = draw
                }
            }

            if (gMeValue > gUser3Value || gMeValue < gUser3Value || gMeValue == gUser3Value) {
                if (gMeValue > gUser3Value) {
                    totalPointHost += 50
                    totalPointUser3 -= 50
                    textUser3 = lose
                } else if (gMeValue < gUser3Value) {
                    totalPointHost -= 50
                    totalPointUser3 += 50
                    textUser3 = win
                } else if (gMeValue == gUser3Value) {
                    textUser3 = draw
                }
            }

            if (gMeValue > gUser4Value || gMeValue < gUser4Value || gMeValue == gUser4Value) {
                if (gMeValue > gUser4Value) {
                    totalPointHost += 50
                    totalPointUser4 -= 50
                    textUser4 = lose
                } else if (gMeValue < gUser4Value) {
                    totalPointHost -= 50
                    totalPointUser4 += 50
                    textUser4 = win
                } else if (gMeValue == gUser4Value) {
                    textUser4 = draw
                }
            }

            var updateDataUser = listOf<Map<String, Any>>(
                mapOf(
                    gKey.name to gMe.get("name").toString(),
                    gKey.value to gMeValue,
                    gKey.point to totalPointHost,
                    gKey.status to gMe.get("status").toString(),
                    gKey.text to jao,
                    gKey.card to cardHost
                ),
                mapOf(
                    gKey.name to gUser2.get("name").toString(),
                    gKey.value to gUser2Value,
                    gKey.point to totalPointUser2,
                    gKey.status to gUser2.get("status").toString(),
                    gKey.text to textUser2,
                    gKey.card to cardUser2
                ),
                mapOf(
                    gKey.name to gUser3.get("name").toString(),
                    gKey.value to gUser3Value,
                    gKey.point to totalPointUser3,
                    gKey.status to gUser3.get("status").toString(),
                    gKey.text to textUser3,
                    gKey.card to cardUser3
                ),
                mapOf(
                    gKey.name to gUser4.get("name").toString(),
                    gKey.value to gUser4Value,
                    gKey.point to totalPointUser4,
                    gKey.status to gUser3.get("status").toString(),
                    gKey.text to textUser4,
                    gKey.card to cardUser4
                )
            )
            var setDataCal = mapOf(
                gKey.user to updateDataUser
            )
            db_data_next_room.document(gRoomName).update(setDataCal)
        }

    }

    //TODO State0_ReStart
    fun state0_ReStart() {
        if (ggetSize == 2) { //TODO SIZE == 2
            var updateDataUser = listOf<Map<String, Any>>(
                mapOf(
                    gKey.name to gMe.get("name").toString(),
                    gKey.value to 0,
                    gKey.point to gMePoint,
                    gKey.status to gMeStatus,
                    gKey.text to jao,
                    gKey.card to backCard
                ),
                mapOf(
                    gKey.name to gUser2.get("name").toString(),
                    gKey.value to 0,
                    gKey.point to gUser2Point,
                    gKey.status to gUser2Status,
                    gKey.text to "",
                    gKey.card to backCard
                )
            )
            var setupDataUser = mapOf(
                gKey.card to card,
                gKey.user to updateDataUser
            )
            db_data_next_room.document(gRoomName).update(setupDataUser)

        }else if (ggetSize == 3) { //TODO SIZE == 3
            var updateDataUser = listOf<Map<String, Any>>(
                mapOf(
                    gKey.name to gMe.get("name").toString(),
                    gKey.value to 0,
                    gKey.point to gMePoint,
                    gKey.status to gMeStatus,
                    gKey.text to jao,
                    gKey.card to backCard
                ),
                mapOf(
                    gKey.name to gUser2.get("name").toString(),
                    gKey.value to 0,
                    gKey.point to gUser2Point,
                    gKey.status to gUser2Status,
                    gKey.text to "",
                    gKey.card to backCard
                ),
                mapOf(
                    gKey.name to gUser3.get("name").toString(),
                    gKey.value to 0,
                    gKey.point to gUser3Point,
                    gKey.status to gUser3Status,
                    gKey.text to "",
                    gKey.card to backCard
                )
            )
            var setupDataUser = mapOf(
                gKey.card to card,
                gKey.user to updateDataUser
            )
            db_data_next_room.document(gRoomName).update(setupDataUser)
        } else if (ggetSize == 4) { //TODO SIZE == 4
            var updateDataUser = listOf<Map<String, Any>>(
                mapOf(
                    gKey.name to gMe.get("name").toString(),
                    gKey.value to 0,
                    gKey.point to gMePoint,
                    gKey.status to gMeStatus,
                    gKey.text to jao,
                    gKey.card to backCard
                ),
                mapOf(
                    gKey.name to gUser2.get("name").toString(),
                    gKey.value to 0,
                    gKey.point to gUser2Point,
                    gKey.status to gUser2Status,
                    gKey.text to "",
                    gKey.card to backCard
                ),
                mapOf(
                    gKey.name to gUser3.get("name").toString(),
                    gKey.value to 0,
                    gKey.point to gUser3Point,
                    gKey.status to gUser3Status,
                    gKey.text to "",
                    gKey.card to backCard
                ),
                mapOf(
                    gKey.name to gUser4.get("name").toString(),
                    gKey.value to 0,
                    gKey.point to gUser4Point,
                    gKey.status to gUser4Status,
                    gKey.text to "",
                    gKey.card to backCard
                )
            )
            var setupDataUser = mapOf(
                gKey.card to card,
                gKey.user to updateDataUser
            )
            db_data_next_room.document(gRoomName).update(setupDataUser)
        }
        dbGiveCard.get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (doc in it) {
                    dbGiveCard.document(doc.id).delete()
                }
            }
        }
    }

    //TODO calculatorValue
    fun calculatorValue(a: Int, b: Int): Int {
        var total = a + b
        if (total > 9) {
            total = total - 10
        }
        return total
    }

    //TODO getCard_String
    fun getCardShow(a: String): Int {
        var resource = 0
        when (a) {
            "1fj" -> resource = R.drawable.flowerjik1
            "2fj" -> resource = R.drawable.flowerjik2
            "3fj" -> resource = R.drawable.flowerjik3
            "4fj" -> resource = R.drawable.flowerjik4
            "5fj" -> resource = R.drawable.flowerjik5
            "6fj" -> resource = R.drawable.flowerjik6
            "7fj" -> resource = R.drawable.flowerjik7
            "8fj" -> resource = R.drawable.flowerjik8
            "9fj" -> resource = R.drawable.flowerjik9
            "10fj" -> resource = R.drawable.flowerjik10
            "1kl" -> resource = R.drawable.khaolam1
            "2kl" -> resource = R.drawable.khaolam2
            "3kl" -> resource = R.drawable.khaolam3
            "4kl" -> resource = R.drawable.khaolam4
            "5kl" -> resource = R.drawable.khaolam5
            "6kl" -> resource = R.drawable.khaolam6
            "7kl" -> resource = R.drawable.khaolam7
            "8kl" -> resource = R.drawable.khaolam8
            "9kl" -> resource = R.drawable.khaolam9
            "10kl" -> resource = R.drawable.khaolam10
            "1pb" -> resource = R.drawable.phoblack1
            "2pb" -> resource = R.drawable.phoblack2
            "3pb" -> resource = R.drawable.phoblack3
            "4pb" -> resource = R.drawable.phoblack4
            "5pb" -> resource = R.drawable.phoblack5
            "6pb" -> resource = R.drawable.phoblack6
            "7pb" -> resource = R.drawable.phoblack7
            "8pb" -> resource = R.drawable.phoblack8
            "9pb" -> resource = R.drawable.phoblack9
            "10pb" -> resource = R.drawable.phoblack10
            "1pr" -> resource = R.drawable.phored1
            "2pr" -> resource = R.drawable.phored2
            "3pr" -> resource = R.drawable.phored3
            "4pr" -> resource = R.drawable.phored4
            "5pr" -> resource = R.drawable.phored5
            "6pr" -> resource = R.drawable.phored6
            "7pr" -> resource = R.drawable.phored7
            "8pr" -> resource = R.drawable.phored8
            "9pr" -> resource = R.drawable.phored9
            "10pr" -> resource = R.drawable.phored10
            "jfj" -> resource = R.drawable.jackflowerjik
            "jkl" -> resource = R.drawable.jackkhaolam
            "jpb" -> resource = R.drawable.jackphoblack
            "jpr" -> resource = R.drawable.jackphored
            "kfj" -> resource = R.drawable.kingflowerjik
            "kkl" -> resource = R.drawable.kingkhaolam
            "kpb" -> resource = R.drawable.kingphoblack
            "kpr" -> resource = R.drawable.kingphored
            "qfj" -> resource = R.drawable.queenflowerjik
            "qkl" -> resource = R.drawable.queenkhaolam
            "qpb" -> resource = R.drawable.queenphoblack
            "qpr" -> resource = R.drawable.queenphored
            else -> resource = R.drawable.backphai
        }
        return resource
    }

    //TODO getCard_String
    fun getCardString(a: String): dataClassCard {
        var resource = 0
        var value = 0
        when (a) {
            "1fj" -> {
                resource = R.drawable.flowerjik1; value = 1
            }
            "2fj" -> {
                resource = R.drawable.flowerjik2; value = 2
            }
            "3fj" -> {
                resource = R.drawable.flowerjik3; value = 3
            }
            "4fj" -> {
                resource = R.drawable.flowerjik4; value = 4
            }
            "5fj" -> {
                resource = R.drawable.flowerjik5; value = 5
            }
            "6fj" -> {
                resource = R.drawable.flowerjik6; value = 6
            }
            "7fj" -> {
                resource = R.drawable.flowerjik7; value = 7
            }
            "8fj" -> {
                resource = R.drawable.flowerjik8; value = 8
            }
            "9fj" -> {
                resource = R.drawable.flowerjik9; value = 9
            }
            "10fj" -> {
                resource = R.drawable.flowerjik10; value = 10
            }
            "1kl" -> {
                resource = R.drawable.khaolam1; value = 1
            }
            "2kl" -> {
                resource = R.drawable.khaolam2; value = 2
            }
            "3kl" -> {
                resource = R.drawable.khaolam3; value = 3
            }
            "4kl" -> {
                resource = R.drawable.khaolam4; value = 4
            }
            "5kl" -> {
                resource = R.drawable.khaolam5; value = 5
            }
            "6kl" -> {
                resource = R.drawable.khaolam6; value = 6
            }
            "7kl" -> {
                resource = R.drawable.khaolam7; value = 7
            }
            "8kl" -> {
                resource = R.drawable.khaolam8; value = 8
            }
            "9kl" -> {
                resource = R.drawable.khaolam9; value = 9
            }
            "10kl" -> {
                resource = R.drawable.khaolam10; value = 10
            }
            "1pb" -> {
                resource = R.drawable.phoblack1; value = 1
            }
            "2pb" -> {
                resource = R.drawable.phoblack2; value = 2
            }
            "3pb" -> {
                resource = R.drawable.phoblack3; value = 3
            }
            "4pb" -> {
                resource = R.drawable.phoblack4; value = 4
            }
            "5pb" -> {
                resource = R.drawable.phoblack5; value = 5
            }
            "6pb" -> {
                resource = R.drawable.phoblack6; value = 6
            }
            "7pb" -> {
                resource = R.drawable.phoblack7; value = 7
            }
            "8pb" -> {
                resource = R.drawable.phoblack8; value = 8
            }
            "9pb" -> {
                resource = R.drawable.phoblack9; value = 9
            }
            "10pb" -> {
                resource = R.drawable.phoblack10; value = 10
            }
            "1pr" -> {
                resource = R.drawable.phored1; value = 1
            }
            "2pr" -> {
                resource = R.drawable.phored2; value = 2
            }
            "3pr" -> {
                resource = R.drawable.phored3; value = 3
            }
            "4pr" -> {
                resource = R.drawable.phored4; value = 4
            }
            "5pr" -> {
                resource = R.drawable.phored5; value = 5
            }
            "6pr" -> {
                resource = R.drawable.phored6; value = 6
            }
            "7pr" -> {
                resource = R.drawable.phored7; value = 7
            }
            "8pr" -> {
                resource = R.drawable.phored8; value = 8
            }
            "9pr" -> {
                resource = R.drawable.phored9; value = 9
            }
            "10pr" -> {
                resource = R.drawable.phored10; value = 10
            }
            "jfj" -> {
                resource = R.drawable.jackflowerjik; value = 0
            }
            "jkl" -> {
                resource = R.drawable.jackkhaolam; value = 0
            }
            "jpb" -> {
                resource = R.drawable.jackphoblack; value = 0
            }
            "jpr" -> {
                resource = R.drawable.jackphored; value = 0
            }
            "kfj" -> {
                resource = R.drawable.kingflowerjik; value = 0
            }
            "kkl" -> {
                resource = R.drawable.kingkhaolam; value = 0
            }
            "kpb" -> {
                resource = R.drawable.kingphoblack; value = 0
            }
            "kpr" -> {
                resource = R.drawable.kingphored; value = 0
            }
            "qfj" -> {
                resource = R.drawable.queenflowerjik; value = 0
            }
            "qkl" -> {
                resource = R.drawable.queenkhaolam; value = 0
            }
            "qpb" -> {
                resource = R.drawable.queenphoblack; value = 0
            }
            "qpr" -> {
                resource = R.drawable.queenphored; value = 0
            }
            else -> {
                resource = R.drawable.backphai; value = 0
            }
        }
        return dataClassCard(resource, value)
    }

    //TODO SEND_DATA_TO_PAGE4
    fun sendDataToPage4() {
        var arrayPoint = mutableListOf<Int>()
        var arrayUser = mutableListOf<HashMap<String, Any>>()
        if (ggetSize == 2) {
            arrayUser.add(gMe)
            arrayUser.add(gUser2)
            arrayPoint.add(gMePoint)
            arrayPoint.add(gUser2Point)
        } else if (ggetSize == 3) {
            arrayUser.add(gMe)
            arrayUser.add(gUser2)
            arrayUser.add(gUser3)
            arrayPoint.add(gMePoint)
            arrayPoint.add(gUser2Point)
            arrayPoint.add(gUser3Point)
        } else if (ggetSize == 4) {
            arrayUser.add(gMe)
            arrayUser.add(gUser2)
            arrayUser.add(gUser3)
            arrayUser.add(gUser4)
            arrayPoint.add(gMePoint)
            arrayPoint.add(gUser2Point)
            arrayPoint.add(gUser3Point)
            arrayPoint.add(gUser4Point)
        }
        var arraypoint = arrayListOf<Int>()
        arraypoint.addAll(arrayPoint.sortedDescending())
        logdfix("aaa", "arraypoint: $arraypoint")
        val intent = Intent(this, Page4_Sort::class.java)
        intent.putExtra("roomName",gRoomName)
        intent.putExtra("getSize", ggetSize)
        intent.putExtra("point", arraypoint)
        intent.putExtra("user", arrayUser as ArrayList<HashMap<String, Any>>)
        startActivity(intent)
    }

    //TODO ContTime
    fun timeCount() {
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var time = millisUntilFinished / 1000
                var db = mapOf("time" to time)
                db_data_next_room.document(gRoomName).update(db)
            }
            override fun onFinish() {
                var db = mapOf("time" to 0)
                db_data_next_room.document(gRoomName).update(db)
            }
        }.start()
    }

    //TODO ReadTime
    fun readTimeDB() {
        db_data_next_room.document(gRoomName).addSnapshotListener { docs, error ->
            time = docs!!.get("${gKey.time}").toString().toInt()
        }
    }

    //TODO Delete
//    fun delete() {
//        btnN_page3UI.setOnClickListener {
//            db_data_next_room.document(gRoomName).delete()
//            dbGiveCard.get().addOnSuccessListener {
//                for (doc in it!!) {
//                    dbGiveCard.document(doc.id).delete()
//                }
//            }
//        }
//        btnX_page3UI.setOnClickListener {
//            var abc = mutableListOf<Map<String, Any>>()
//            dbTestCH.get().addOnSuccessListener {
////                var G_user2 = ""
////                var G_user3 = ""
////                var G_user4 = ""
////                var G_Me = ""
//                var getUser = it!!.get("user") as MutableList<Map<String, Any>>
//                var a = hashMapOf<String, Any>("name" to "us2", "point" to 500)
//                var b = hashMapOf<String, Any>("name" to "us3", "point" to 500)
//                var c = hashMapOf<String, Any>("name" to "us4", "point" to 500)
//                var d = hashMapOf<String, Any>("name" to "us5", "point" to 500)
//                abc.add(getUser[0])
//                abc.add(a)
//                abc.add(b)
//                abc.add(c)
//                abc.add(d)
//                var m = mapOf<String, Any>(
//                    "user" to abc
//                )
//                dbTestCH.update(m)
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