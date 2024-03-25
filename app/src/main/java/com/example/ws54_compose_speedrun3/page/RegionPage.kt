import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ws54_compose_speedrun3.R
import com.example.ws54_compose_speedrun3.service.SimpleNavController
import com.example.ws54_compose_speedrun3.service.getTranslated
import com.example.ws54_compose_speedrun3.widget.BackAppBar
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

object RegionPage{
    @Composable
    fun build(navController: SimpleNavController, previousData:Map<String,Any>, allCityData:Map<String,Any>){
        val openDialog = remember {
            mutableStateOf(false)
        }
        val lazyListState = rememberLazyListState()
        // -----   -----  -----  -----  -----  -----  -----  -----  -----  -----  -----  -----
        val unselectedCityDataList = remember {
            mutableStateOf(allCityData.values.toList() as List<Map<String,Any>>)
        }
        val selectedCityDataList = remember {
            mutableStateOf(listOf<Map<String,Any>>())
        }



        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { openDialog.value = true}) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    if(openDialog.value){
                        AlertDialog(
                            title = { Text(text = stringResource(id = R.string.add_a_region)) },
                            onDismissRequest = { openDialog.value = false },
                            confirmButton = {},
                            text = {
                                LazyColumn(
                                    state = lazyListState,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.padding(15.dp),
                                    content = {
                                        items(unselectedCityDataList.value.size){
                                            val city = unselectedCityDataList.value[it]
                                            val current = city.get("current") as Map<String,Any>
                                            Text(text = current.get("name").toString(), fontSize = 20.sp,modifier = Modifier.clickable {
                                                unselectedCityDataList.value = unselectedCityDataList.value.minus(city)
                                                selectedCityDataList.value = selectedCityDataList.value.plus(city)
                                                openDialog.value = false
                                            })
                                        }
                                    }
                                )
                            }
                        )
                    }
                }
            },
            topBar = {
                BackAppBar.build(navController = navController, previousData = previousData)
            },
            content = {
                it
                val userAgent = "test"
                Configuration.getInstance().userAgentValue = userAgent
                val regions = remember {
                    mutableStateListOf<Region>()
                }

                selectedCityDataList.value.forEach{
                    val current = it.get("current") as Map<String,Any>
                    regions.add(   Region(
                        name = getTranslated(value = current.get("name").toString()),
                        temp = (current.get("temp_c") as Double).toInt(),
                        rain = (current.get("daily_chance_of_rain") as Double).toInt(),
                        description = getTranslated(value = current.get("description").toString()),
                        lat = current.get("lat") as Double,
                        lon = current.get("lon") as Double
                    ))
                }
                val geoPoint = remember {
                    mutableStateOf(GeoPoint(23.5,121.5))
                }
                AndroidView(
                    factory = {
                            context ->
                        MapView(context).apply {
                            setTileSource(TileSourceFactory.MAPNIK)
                            setMultiTouchControls(true)
                            controller.setCenter(geoPoint.value)
                            controller.setZoom(9.5)
                        }
                    },
                    update = {
                            mapView ->
                        for(region in regions){
                            val marker = Marker(mapView)
                            marker.position = GeoPoint(region.lat,region.lon)
                            marker.setAnchor(Marker.ANCHOR_CENTER , Marker.ANCHOR_BOTTOM)
                            marker.title = region.name
                            marker.snippet = "${region.temp}C / ${region.rain}% / ${region.description}"
                            mapView.overlays.add(marker)
                            marker.setOnMarkerClickListener{
                                    it, view ->
                                marker.showInfoWindow()
                                true
                            }
                        }
                    }
                )
            }
        )
    }
}

data class Region(
    val name:String,
    val temp:Int,
    val rain:Int,
    val description:String,
    val lat:Double,
    val lon:Double
)