package com.example.ws54_compose_speedrun3.page

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ws54_compose_speedrun3.service.SimpleNavController
import com.example.ws54_compose_speedrun3.service.getIconIdByDescription
import com.example.ws54_compose_speedrun3.service.getTranslated
import com.example.ws54_compose_speedrun3.widget.HomeAppBar
import com.example.ws54_compose_speedrun3.widget.NavDrawerContent

object HomePage{
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun build(navController: SimpleNavController, cityData:Map<String,Any>){
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val current = cityData.get("current") as Map<String,Any>
        val forecast = cityData.get("forecast") as Map<String,Any>
        val dailyDataList = forecast.get("day") as List<Map<String,Any>>
        val hourlyDataList = forecast.get("hourly") as List<Map<String,Any>>
        val lazyListState = rememberLazyListState()
        ModalDrawer(drawerState = drawerState,drawerContent = { NavDrawerContent.build(scope,drawerState,navController,cityData)}) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = { HomeAppBar.build(title = getTranslated(value = current.get("name")), scope = scope, drawerState = drawerState) }
            )
            {
                LazyColumn(state = lazyListState, horizontalAlignment = Alignment.CenterHorizontally, contentPadding = PaddingValues(15.dp)){
                    item{
                        currentTempColumn(current)
                        Spacer(modifier = Modifier.height(20.dp))
                        hourlyTempRow(hourlyDataList)
                        Spacer(modifier = Modifier.height(20.dp))
                        dailyDataColumn(dailyDataList)
                        Spacer(modifier = Modifier.height(20.dp))
                        pm25Display(current)
                    }
                }
            }
        }
    }

    @Composable
    fun currentTempColumn(current:Map<String,Any>){
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Text(text ="${ current.get("temp_c")}C", fontSize = 40.sp)
                Image(painter = painterResource(id = getIconIdByDescription(value = current.get("description"))), contentDescription = null,Modifier.size(80.dp))
            }
            Text(text ="${ current.get("maxTemp_c")}C / ${ current.get("minTemp_c")}C", fontSize = 40.sp)
            Text(text = "${getTranslated(value = current.get("description"))}", fontSize = 30.sp)
        }
    }

    @Composable
    fun hourlyTempRow(hourlyData:List<Map<String,Any>>){
        val lazyListState = rememberLazyListState()
        LazyRow(state = lazyListState, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(35.dp)
            )
            .background(Color.LightGray))
        {
            items(hourlyData.size){
                _buildHourlyTempColumn(hourlyData[it])
            }
        }
    }

    @Composable
    fun _buildHourlyTempColumn(hour:Map<String,Any>){
        Column(
            Modifier
                .fillMaxHeight()
                .padding(15.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = hour.get("time").toString())
            Image(painter = painterResource(id = getIconIdByDescription(value = hour.get("description"))), contentDescription = null,Modifier.size(45.dp))
            Text(text = "${hour.get("temp_c")}C")
            Text(text = "${hour.get("daily_chance_of_rain")}%")
        }
    }

    @Composable
    fun dailyDataColumn(dailyData:List<Map<String,Any>>){
        Column(modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(35.dp)
            )
            .background(Color.LightGray)) {
            dailyData.forEach{
                _buildDailyDataRow(it)
            }
        }
    }

    @Composable
    fun _buildDailyDataRow(day:Map<String,Any>){
        Row(
            Modifier
                .fillMaxWidth()
                .padding(15.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = day.get("date").toString())
            Image(painter = painterResource(id = getIconIdByDescription(value = day.get("description"))), contentDescription = null,Modifier.size(45.dp))
            Text(text = "${day.get("daily_chance_of_rain")}%")
            Text(text = "${day.get("maxTemp_c")}C / ${day.get("minTemp_c")}C")
        }
    }

    @Composable
    fun pm25Display(current:Map<String,Any>){
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(35.dp))
                .background(Color.LightGray), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly, content = {
                Text(text = "PM25")
                Divider(thickness = 3.dp, modifier = Modifier.fillMaxWidth(0.9f))
                Text(text = current.get("pm25").toString() , fontSize = 40.sp)
            })
    }
}