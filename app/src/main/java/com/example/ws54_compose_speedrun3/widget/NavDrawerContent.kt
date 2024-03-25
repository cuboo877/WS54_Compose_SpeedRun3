package com.example.ws54_compose_speedrun3.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ws54_compose_speedrun3.MainActivity
import com.example.ws54_compose_speedrun3.service.SimpleNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.ws54_compose_speedrun3.R
object NavDrawerContent{
    @Composable
    fun build(scope:CoroutineScope, drawerState: DrawerState, navController: SimpleNavController, previousData:Map<String,Any>){
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp), horizontalAlignment = Alignment.Start) {
            IconButton(onClick = {scope.launch { drawerState.close() } }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .clip(RoundedCornerShape(35.dp))
                .paint(
                    painter = painterResource(
                        id = R.drawable.icon
                    ), contentScale = ContentScale.Crop
                )
                .clickable { navController.push(MainActivity.Screen.Region, previousData) }, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center)
            {
                Text(text = stringResource(id = R.string.NavDrawerContent_region))
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .clip(RoundedCornerShape(35.dp))
                .paint(
                    painter = painterResource(
                        id = R.drawable.icon
                    ), contentScale = ContentScale.Crop
                )
                .clickable { navController.push(MainActivity.Screen.Choose) }, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center)
            {
                Text(text = stringResource(id = androidx.compose.ui.R.string.navigation_menu))
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
            }
        }
    }
}