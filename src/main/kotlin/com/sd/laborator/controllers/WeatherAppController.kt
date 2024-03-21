package com.sd.laborator.controllers

import com.sd.laborator.interfaces.LocationFilterInterface
import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class WeatherAppController {

    @Autowired
    private lateinit var locationSearchService: LocationSearchInterface

    @Autowired
    private lateinit var weatherForecastService: WeatherForecastInterface

    @Autowired
    private lateinit var locationFilterService: LocationFilterInterface


    @RequestMapping("/getforecast/{location}", method = [RequestMethod.GET])
    @ResponseBody
    fun getForecast(@PathVariable location: String): String {

        val isLocationAvailable = locationFilterService.isLocationAvailable(location)

        if(isLocationAvailable) {

            val locationId: Array<Float> = locationSearchService.getLocationId(location)


            if (locationId.isEmpty()) {
                return "Nu s-au putut gasi date meteo pentru cuvintele cheie \"$location\"!"
            }

            val latitude: Float = locationId[0]
            val longitude: Float = locationId[1]

            val rawForecastData: WeatherForecastData = weatherForecastService.getForecastData(longitude, latitude)

            return rawForecastData.toString()
        }
        else return "Location $location is not available"

    }
}