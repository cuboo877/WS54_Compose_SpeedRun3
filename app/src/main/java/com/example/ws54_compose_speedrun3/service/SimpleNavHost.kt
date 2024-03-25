package com.example.ws54_compose_speedrun3.service

import android.graphics.Region
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ws54_compose_speedrun3.MainActivity
import com.example.ws54_compose_speedrun3.page.ChoosePage
import com.example.ws54_compose_speedrun3.page.HomePage

@Composable
fun SimpleNavHost(navController: SimpleNavController, allCityData:Map<String,Any>){
    Box(modifier = Modifier.fillMaxSize()){
        when(navController.currentScreen.value.first){
            MainActivity.Screen.Choose -> ChoosePage.build(navController,allCityData)
            MainActivity.Screen.Region -> RegionPage.build(navController,navController.currentScreen.value.second as Map<String,Any>,allCityData)
            MainActivity.Screen.Home -> HomePage.build(navController,navController.currentScreen.value.second as Map<String,Any>)
        }
    }
}