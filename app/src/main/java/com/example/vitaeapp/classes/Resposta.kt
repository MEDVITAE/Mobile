package com.example.vitaeapp.classes

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class GeoLocation(
    val addressComponents: List<AddressComponent>,
    val formattedAddress: String,
    val geometry: Geometry,
    val placeId: String,
    val postcodeLocalities: List<String>,
    val types: List<String>,
    val status: String
)

data class Resposta(
    val results: List<GeoLocation>
)

    data class ObjMarker(
        val location: Location,
        val fkHemo: Int
    ):ClusterItem {
        override fun getPosition(): LatLng {
          return  LatLng(location.lat,location.lng)
        }
        override fun getTitle(): String? {
         return  fkHemo.toString()
        }

        override fun getSnippet(): String? = fkHemo.toString()

        override fun getZIndex(): Float? = 1f

    }

data class AddressComponent(
    val longName: String,
    val shortName: String,
    val types: List<String>
)

data class Geometry(
    val bounds: Bounds,
    val location: Location,
    val locationType: String,
    val viewport: Viewport
)

data class Bounds(
    val northeast: Northeast,
    val southwest: Southwest
)

data class Northeast(
    val lat: Double,
    val lng: Double
)

data class Southwest(
    val lat: Double,
    val lng: Double
)

data class Location(
    val lat: Double,
    val lng: Double
)

data class Viewport(
    val northeast: Northeast,
    val southwest: Southwest
)

