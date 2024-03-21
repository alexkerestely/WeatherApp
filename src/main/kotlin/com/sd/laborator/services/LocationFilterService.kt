package com.sd.laborator.services

import org.springframework.stereotype.Service
import com.sd.laborator.interfaces.LocationFilterInterface
import org.json.JSONObject
import java.io.File

@Service
class LocationFilterService : LocationFilterInterface {

   override fun isLocationAvailable(locationName: String) : Boolean {

        val blacklistedCities: String = File("/home/student/Downloads/SD_Laborator_03/WeatherApp/src/main/kotlin/com/sd/laborator/references/blacklist.json").readText()
        val JSONCitiesArray = JSONObject(blacklistedCities).getJSONArray("cities")
        //val citiesArray = Array<String>(JSONCitiesArray.length()) {""}

        for (i in 0..JSONCitiesArray.length()-1)
            if (locationName == JSONCitiesArray.getJSONObject(i).getString("name"))
                return false

        return true
    }
}