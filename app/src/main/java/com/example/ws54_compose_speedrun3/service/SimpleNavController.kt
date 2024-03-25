package com.example.ws54_compose_speedrun3.service

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.ws54_compose_speedrun3.MainActivity

class SimpleNavController (private val startDestination:MainActivity.Screen){
    private val navigationStack = mutableStateListOf<Pair<MainActivity.Screen,Any?>>(startDestination to null)

    val currentScreen: State<Pair<MainActivity.Screen,Any?>> get() = navigationStack.last().let { mutableStateOf(it) }

    fun push(destination:MainActivity.Screen, argument:Any? = null){
        navigationStack += destination to argument
    }
}