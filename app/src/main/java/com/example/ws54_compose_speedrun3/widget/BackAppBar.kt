package com.example.ws54_compose_speedrun3.widget

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.ws54_compose_speedrun3.MainActivity
import com.example.ws54_compose_speedrun3.service.SimpleNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.ws54_compose_speedrun3.R
object BackAppBar{
    @Composable
    fun build(navController: SimpleNavController, previousData:Map<String,Any>){
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.NavDrawerContent_region)) },
            navigationIcon = {
                IconButton(onClick = { navController.push(MainActivity.Screen.Home, previousData)}) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
    }
}