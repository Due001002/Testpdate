package com.example.testpdate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    var fbft = FirebaseFirestore.getInstance()
    var arrayData = mutableListOf<String>()
    val db = Firebase.firestore
    val dbroomName = db.collection("roomName")
    var recyclerView: RecyclerView? = null
    var edt: EditText? = null

    lateinit var btnAdd: Button
    lateinit var btnRemove: Button
    lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        creatViewProject()
        setRecyclerview()
        addRecycle()
        removeRecycle()
        getDataRecycler()
    }

    fun creatViewProject() {
        edt = findViewById<EditText>(R.id.edtInputData)
        btnAdd = findViewById<Button>(R.id.btn_insert)
        btnRemove = findViewById<Button>(R.id.btn_remove)
    }

    fun addRecycle() {
        btnAdd.setOnClickListener {
            var getTextedt = edt!!.text.toString()
            arrayData.add(0, getTextedt)
            myAdapter.notifyItemInserted(0)
            edt!!.text.clear()
        }
    }

    fun removeRecycle() {                      
        btnRemove.setOnClickListener {
            var aaa = arrayData
            var getIndex = -500
            logdfix("strabc", "aaa.size.start= ${aaa.size}")
            var getTextedt = edt!!.text.toString()
            edt!!.text.clear()
            for (str in 0..aaa.size - 1) {
                if (aaa[str] == getTextedt) {
                    logdfix("strabc", "index:$str = ${aaa[str]}")
                    getIndex = str
                } else {
                    logdfix("strabc", "${aaa[str]} != $getTextedt ")
                }
            }
            if (getIndex != -500) {
                arrayData.removeAt(getIndex)
                myAdapter.notifyItemRemoved(getIndex)
                logdfix("strabc", "aaa.size.end= ${aaa.size}")
            } else {
                toast("No Found Data Want Delete!!")
            }
        }
    }

    //TODO setRecyclerview()
    fun setRecyclerview() {
//        for (i in 1..10) {
//            arrayData.add(i.toString())
//            var datadb = mapOf<String, Any>("roomname" to i)
//            dbroomName.document("RoomName$i").set(datadb)
//        }

        var datamap = mutableListOf<Map<String,Any>>(
            mapOf("name" to "a","point" to 10,"value" to 1),
            mapOf("name" to "b","point" to 50,"value" to 5),
            mapOf("name" to "c","point" to 30,"value" to 2)
        )
        var arrayDataString = mutableListOf<String>("a","b","c","d","e","f","g","h","i")
        arrayDataString.shuffle()
        var ReversMap = datamap
        var ajkfjr = mutableListOf<Int>(5,6,77,8,9,1,6,8,9)
        var gerj = ajkfjr.sortDescending()
        logdfix("ReversMap","${ReversMap[0]} \n ${ReversMap[1]} \n ${ReversMap[2]}")
        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        //recyclerView!!.setLayoutManager(GridLayoutManager(this, 1))
        myAdapter = MyAdapter(arrayDataString)
        recyclerView!!.setAdapter(myAdapter)
        myAdapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                toast("click: ${datamap[position]}")
            }
        })
        recycle_setOnClick()
    }

    //TODO recycle_setOnClick
    private fun recycle_setOnClick() {

    }

    //TODO getDataRecycler()
    fun getDataRecycler() {

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


