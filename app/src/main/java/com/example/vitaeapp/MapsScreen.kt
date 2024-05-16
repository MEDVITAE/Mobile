package com.example.vitaeapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vitaeapp.api.RetrofitServices
import com.example.vitaeapp.classes.CepMaps
import com.example.vitaeapp.classes.Location
import com.example.vitaeapp.classes.ObjMarker
import com.example.vitaeapp.classes.Resposta
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import retrofit2.Call
import retrofit2.Response


object GlobalVariables {
    var isLoading by mutableStateOf(false)
}

@Composable
fun SetIsLoading(newValue: Boolean) {
    GlobalVariables.isLoading = newValue
}

@Composable
fun GetIsLoading(): Boolean {
    return GlobalVariables.isLoading
}

@Composable
fun ChamaMaps(navController: NavHostController, token: String, id: Int) {
    val locationLondon = LatLng(
        -23.557978, -46.661789
    )
    val cameraPositionState = rememberCameraPositionState {
        this.position = CameraPosition.fromLatLngZoom(
            /* target = */ locationLondon,
            /* zoom = */ 15f
        )
    }


    val mapProperties = MapProperties(
        isBuildingEnabled = false,
        isIndoorEnabled = false,
        isTrafficEnabled = false,
        mapType = MapType.TERRAIN,
        maxZoomPreference = 21f,
        minZoomPreference = 3f,
    )

    val mapUiSettings = MapUiSettings(
        compassEnabled = true,
        myLocationButtonEnabled = true,
        rotationGesturesEnabled = true,
        scrollGesturesEnabled = true,
        scrollGesturesEnabledDuringRotateOrZoom = true,
        tiltGesturesEnabled = true,
        zoomControlsEnabled = false,
        zoomGesturesEnabled = true,
    )

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = mapUiSettings,
    ) {
        var markerClick by remember { mutableStateOf(false) }
        var nav by remember { mutableStateOf("") }
        var markersData by remember { mutableStateOf<List<ClusterItem>>(emptyList()) }
        var size = conectaBanco(token).size
        markersData = criaObjetosMarcadores(token)
        if (markersData.isNotEmpty() && markersData.size == size && GetIsLoading()) {
            Clustering(
                items = markersData,
                onClusterItemClick = { markerItem ->
                    nav = markerItem.title.toString()
                    markerClick = true
                    false // Retorna true para indicar que o evento de clique foi consumido
                },
                )

        }
        if (markerClick) {
            navController.navigate("DetalheHemo/${token}/${id}/${nav.toInt()}")
        }

    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun conectaBanco(token: String): List<CepMaps> {
    var isLoading by remember { mutableStateOf(false) }
    var ceps by remember { mutableStateOf<List<CepMaps>>(emptyList()) }

    val apiMaps = RetrofitServices.getCepsHemo()
    val get = apiMaps.getCeps(
        token
    )
    val erroApi = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        get.enqueue(object : retrofit2.Callback<List<CepMaps>> {
            override fun onResponse(
                call: Call<List<CepMaps>>,
                response: Response<List<CepMaps>>
            ) {
                if (response.isSuccessful) {
                    val obj = response.body()
                    if (obj != null) {
                        ceps = obj
                        isLoading = true

                    } else {
                        erroApi.value = "Erro ao buscar detalhes"
                    }
                } else {
                    erroApi.value = "Erro na solicitação: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<CepMaps>>, t: Throwable) {
                isLoading = false
                // Lógica para lidar com falha na solicitação
            }
        })
    }
    if (isLoading) {
        return ceps
    }

    return ceps
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun buscarDetalhes(ceps: List<CepMaps>): SnapshotStateList<Location> {
    val markers = remember {
        mutableStateListOf<Location>()
    }
    val apiMaps = RetrofitServices.getLatUser()
    var erroApi = ""

    for (cep in ceps) {
        val get = apiMaps.getLatLngFromAddress(
            cep.cep,
            "AIzaSyC7961y6WzpcHtQnSa7myhWc2jl21GfDnA"
        )

        LaunchedEffect(Unit) {
            get.enqueue(object : retrofit2.Callback<Resposta> {
                override fun onResponse(
                    call: Call<Resposta>,
                    response: Response<Resposta>
                ) {
                    if (response.isSuccessful) {
                        val list = response.body()
                        if (list != null && list.results.isNotEmpty()) {
                            markers.add(list.results[0].geometry.location)
                        } else {
                            erroApi = "Erro ao buscar detalhes"
                        }
                    } else {
                        erroApi = "Erro na solicitação: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<Resposta>, t: Throwable) {
                    erroApi = "Erro na solicitação"
                }
            })
        }

    }
    if (markers.isNotEmpty()) {
        return markers
    }
    return markers
}

@Composable
fun criaObjetosMarcadores(token: String): List<ClusterItem> {
    val listaCeps = conectaBanco(token)
    val listaDeLatLng = buscarDetalhes(ceps = listaCeps)
    val marcadores = mutableListOf<ObjMarker>()
    if (listaDeLatLng != null && listaCeps != null) {
        if (listaDeLatLng.size > 1) {
            if (listaDeLatLng.size == listaCeps.size) {
                for (i in listaCeps.indices) {
                    marcadores.add(ObjMarker(listaDeLatLng[i], listaCeps[i].fkHospital))
                }
                SetIsLoading(newValue = true)

            }
        }
    }
    return marcadores.toList()

}

