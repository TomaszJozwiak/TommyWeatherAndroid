package com.tommyapps.weatheronandroid

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class WeekWeatherCalculations {

    var weatherMap = ArrayList<HashMap<String, String>>()

    fun getWeekWeatherData(link: String): ArrayList<HashMap<String, String>>? {

        var apiConnection: ApiConnection = ApiConnection(link)

        var dataFromApi = apiConnection.getResponseFromApi()
        if (dataFromApi != null) {
            handleJson(dataFromApi)
            return weatherMap
        }

        return null
    }

    private fun handleJson(jsonString: String?) {

        val jsonObject = JSONObject(jsonString)
        val jsonArray = jsonObject.getJSONArray("daily")

        for (i in 0 until jsonArray.length()) {

            var jsonObjectFromArray: JSONObject = jsonArray[i] as JSONObject
            var map = HashMap<String, String>()
            map["date"] = jsonObjectFromArray.getString("dt")
            map["temperature"] = jsonObjectFromArray.getJSONObject("temp").getString("day")
            var tempArray = jsonObjectFromArray.getJSONArray("weather")
            var tempObject = tempArray[0] as JSONObject
            map["icon"] = tempObject.getString("icon")
            map["description"] = tempObject.getString("description")

            weatherMap.add(map)
        }

        example()

    }

    private fun example() {

        for (i in (0 until weatherMap.size)) {

            println(weatherMap[i]["date"])
            println(weatherMap[i]["temperature"])
            println(weatherMap[i]["icon"])
            println(weatherMap[i]["description"])

        }

    }

}