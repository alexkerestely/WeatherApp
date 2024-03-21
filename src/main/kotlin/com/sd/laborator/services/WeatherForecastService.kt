package com.sd.laborator.services

import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URL
import kotlin.math.roundToInt

@Service
class WeatherForecastService (private val timeService: TimeService) : WeatherForecastInterface {
    override fun getForecastData(longitude: Float, latitude : Float): WeatherForecastData {

        val forecastDataURL = URL("https://api.open-meteo.com/v1/forecast?latitude=$latitude&longitude=$longitude&current=temperature_2m,apparent_temperature,precipitation,rain,weather_code,wind_speed_10m,wind_direction_10m&timezone=Europe%2FLondon&forecast_days=1")

        val rawResponse: String = forecastDataURL.readText()

        val responseRootObject=JSONObject("{\"data\":${rawResponse}}")
        System.out.println(responseRootObject)

        val responseContentObject=responseRootObject.getJSONObject("data").takeUnless{it.isEmpty}

        val weatherDataObject = responseContentObject?.getJSONObject("current")


        return WeatherForecastData(
            latitude = responseContentObject!!.getFloat("latitude"),
            longitude = responseContentObject.getFloat("longitude"),
            date = timeService.getCurrentTime(),
            weatherCode = weatherDataObject!!.getInt("weather_code"),
            windDirection = weatherDataObject.getInt("wind_direction_10m"),
            windSpeed = weatherDataObject.getFloat("wind_speed_10m"),
            temperature = weatherDataObject.getFloat("temperature_2m"),
            apparentTemperature = weatherDataObject.getFloat("apparent_temperature"),
            rain = weatherDataObject.getInt("rain"),
            precipitation = weatherDataObject.getInt("precipitation")
        )



    }
}