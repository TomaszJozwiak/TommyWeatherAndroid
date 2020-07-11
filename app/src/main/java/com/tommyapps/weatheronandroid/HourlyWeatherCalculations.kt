package com.tommyapps.weatheronandroid

import org.json.JSONObject

class HourlyWeatherCalculations(private val longitude: String?, private val latitude: String?) {


    var weatherMap = ArrayList<HashMap<String, String>>()

    fun getHourlyWeatherData(): ArrayList<HashMap<String, String>>? {

        var apiConnection: ApiConnection = ApiConnection(createLink())

        var dataFromApi = apiConnection.getResponseFromApi()
        if (dataFromApi != null) {
            handleJson(dataFromApi)
            return weatherMap
        }

        return null
    }

    private fun createLink(): String {

        val apiKey: String = "8cbc4f1e6576a2478f1d70e0a17cc594"

        return "https://api.openweathermap.org/data/2.5/onecall?lat=$latitude&lon=$longitude&exclude=current,minutely,daily&appid=$apiKey&units=metric&lang=pl"

    }

    private fun handleJson(jsonString: String?) {

        val jsonObject = JSONObject(jsonString)
        val jsonArray = jsonObject.getJSONArray("hourly")

        for (i in 0 until jsonArray.length() / 2) {

            var jsonObjectFromArray: JSONObject = jsonArray[i] as JSONObject
            var map = HashMap<String, String>()
            map["date"] = jsonObjectFromArray.getString("dt")
            map["temperature"] = jsonObjectFromArray.getString("temp")
            var tempArray = jsonObjectFromArray.getJSONArray("weather")
            var tempObject = tempArray[0] as JSONObject
            map["icon"] = tempObject.getString("icon")
            map["description"] = tempObject.getString("description")

            weatherMap.add(map)
        }

    }


}