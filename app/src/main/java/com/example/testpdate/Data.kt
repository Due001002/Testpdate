package com.example.testpdate

data class Data(var data: String)
data class key(
    var name: String = "name",
    val value: String = "value",
    val point: String = "point",
    val status: String = "status",
    val text: String = "text",
    val card: String = "card",
    val round: String = "round",
    val state: String = "state",
    val user:String = "user",
    val giveCard:String = "giveCard"
)
data class dataClassCard(var card:Int, var value:Int)