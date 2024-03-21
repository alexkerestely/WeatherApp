package com.sd.laborator.services

import com.sd.laborator.interfaces.LocationSearchInterface
import org.springframework.stereotype.Service
import java.net.URL
import org.json.JSONObject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class LocationSearchService :  LocationSearchInterface {

   override fun getLocationId(locationName: String): Array<Float> {


        val encodedLocationName = URLEncoder.encode(locationName, StandardCharsets.UTF_8.toString())

        val locationSearchURL = URL("https://geocoding-api.open-meteo.com/v1/search?name=$encodedLocationName&count=10&language=en&format=json")

        val rawResponse: String = locationSearchURL.readText()

        val responseRootObject = JSONObject("{\"data\":${rawResponse}}")

        val responseContentObject = responseRootObject.getJSONObject("data").takeUnless { it.isEmpty }
            ?.getJSONArray("results")?.getJSONObject(0)

        val latitude = responseContentObject?.getFloat("latitude") ?: -1
        val longitude = responseContentObject?.getFloat("longitude") ?: -1
        val location = Array<Float>(2) { 0f }
        location[0] = latitude.toFloat()
        location[1] = longitude.toFloat()

        return location
    }
}