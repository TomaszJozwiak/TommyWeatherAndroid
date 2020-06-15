package com.tommyapps.weatheronandroid

import android.os.AsyncTask
import android.util.Xml
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.HttpURLConnection
import java.net.URL


class ApiConnection(link: String) {

    var url: String = link
    var dataFromApi: String? = null
    var message: String? = null
    var weatherMap = HashMap<String, String>()

    fun show() {

        dataFromApi = AsyncTaskHandleJson().execute(url).get()
        if (dataFromApi != null) {
            handleJson(dataFromApi)
        } else {
            message = "Problem with weather data. Please try again later"
        }
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String?): String? {

            var text: String? = null
            var connection = URL(url).openConnection() as HttpURLConnection
            try{
                connection.connect()
                text = connection.inputStream.use { it.reader().use { reader -> reader.readText()} }
            } finally {
                connection.disconnect()
            }
            return text

        }
    }

    private fun handleJson(jsonString: String?) {

        val jsonObject = JSONObject(jsonString)
        val jsonArray = jsonObject.getJSONArray("weather")
        val jsonObjectFromArray: JSONObject = jsonArray[0] as JSONObject


        var weather = Weather ( jsonObjectFromArray.getString("description"),
            jsonObjectFromArray.getString("icon"),
            jsonObject.getJSONObject("main").getString("temp"),
            jsonObject.getJSONObject("main").getString("pressure"),
            jsonObject.getJSONObject("main").getString("humidity"),
            jsonObject.getJSONObject("wind").getString("speed"),
            jsonObject.getJSONObject("clouds").getString("all"),
            jsonObject.getJSONObject("sys").getString("country"),
            jsonObject.getString("name")
        )

        setMap(weather)
        example()
    }


    private fun setMap(weather: Weather) {

        weatherMap["description"] = (weather.description).capitalize()
        weatherMap["icon"] = setIconName(weather.icon)
        weatherMap["temperature"] = "${roundTemperatureDegree(weather.temperature)} ℃"
        weatherMap["humidity"] = "${weather.humidity} %"
        weatherMap["pressure"] =  "${weather.pressure} hPa"
        weatherMap["wind"] = "${weather.wind} m/s"
        weatherMap["clouds"] = "${weather.clouds} %"
        weatherMap["country"] = weather.country
        weatherMap["city"] = weather.city

    }

    private fun roundTemperatureDegree(temperatureToRound: String): String  {

        return temperatureToRound.substring(0, 2)

    }

    private fun setIconName(iconName: String): String {

        var temp: String = ""

        temp = iconName.substring(iconName.lastIndex) + iconName.substring(0, 2)
        return temp

    }

    private fun example() {

            println(weatherMap["description"])
            println(weatherMap["icon"])
            println(weatherMap["temperature"])
            println(weatherMap["pressure"])
            println(weatherMap["humidity"])
            println(weatherMap["wind"])
            println(weatherMap["clouds"])
            println(weatherMap["country"])
            println(weatherMap["city"])

    }

}