package com.example.watherapi

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.watherapi.waterapi.WeatherResponse
import com.example.watherapi.waterapi.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    //baseUrl, appId, lat, lon: Ye variables OpenWeatherMap API ke liye zaroori details hold kar rahe hain. baseUrl API ka base URL hai, appId aapka API key hai, aur lat aur lon latitude aur longitude hain jin ki weather information aap fetch kar rahe hain.
    private val baseUrl = "http://api.openweathermap.org/"
    private val appId = "e1bf0d0d35b57318ecea49c075a1fc75"
    //Latitude (عرض) zameen ke equator se kisi jagah ki doori ko batata hai. Ye north ya south ki taraf measure kiya jata hai1.
    private val lat = "33.626057"
    //Longitude (طول) zameen ke prime meridian se kisi jagah ki doori ko batata hai. Ye east ya west ki taraf measure kiya jata hai
    private val lon = "73.071442"

    private lateinit var weatherData: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weatherData = findViewById(R.id.textView)
        findViewById<Button>(R.id.button).setOnClickListener {
            getCurrentData()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    //getCurrentData(): Ye method OpenWeatherMap API se current weather data fetch kar raha hai. Is method main hum Retrofit client initialize kar rahe hain
    private fun getCurrentData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //WeatherService interface ka instance create kar rahe hain, aur phir API se weather data fetch kar rahe hain.
        val service = retrofit.create(WeatherService::class.java)
        val call = service.getCurrentWeatherData(lat, lon, appId)

        call.enqueue(object : Callback<WeatherResponse> {
            //onResponse(): Ye method call hota hai jab API se response mil jata hai. Is method main hum response body se weather data fetch kar rahe hain, phir hum us data ko format kar ke weatherData TextView main display kar rahe hain.
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {

                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    val timestamp = 17132558991L // Sunset timestamp
                    val timestamp2= 1713211839L
                    val instant = Instant.ofEpochMilli(timestamp)
                    val instant1 = Instant.ofEpochMilli(timestamp2)
                    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault())
                    val formattedTime = formatter.format(instant)
                    val formattedTime2 = formatter.format(instant1)
                    Log.e("TAG", "onResponse: "+weatherResponse )
                    weatherResponse?.let {
                        val stringBuilder = "Country: ${it.sys.country}\n" +
                                "\bTemperature\b: ${it.main.temp}°C\n" +
                                "Temperature(Min): ${it.main.temp_min}°C\n" +
                                "Temperature(Max): ${it.main.temp_max}°C\n" +
//                                "Temperature(Max): ${formattedTime}\n" +
                                "Humidity: ${it.main.humidity}\n" +
//                               "sunset: ${it.sys.sunset}\n" +
                                "sunset: ${formattedTime}\n" +
                                "sunrise:${formattedTime2}\n" +
                                "cloud all: ${it.clouds.all}\n" +
                                "Wend Speed: ${it.wind.speed}\n" +
                                "Pressure: ${it.main.pressure}"
                        weatherData.text = stringBuilder
                    }
                } else {
                    weatherData.text = "Failed to fetch weather data"
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                weatherData.text = t.message
            }
        })
    }
}
