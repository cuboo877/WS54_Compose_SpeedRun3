package com.example.ws54_compose_speedrun3.service

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun readJsonFromAssets(context: Context,fileName :String):String{
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}

fun jsonToMap(jsonString:String):Map<String,Any>{
    val gson = Gson()
    val type = object : TypeToken<Map<String,Any>>(){}.type
    return gson.fromJson(jsonString,type)
}