package com.tommyapps.weatheronandroid

import org.json.JSONObject

class CurrentWeatherCalculations(private val cityName: String?, private val countryCode: String?) {

    var weatherMap = HashMap<String, String>()

    fun getCurrentWeatherData(): HashMap<String, String>? {

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

        return "http://api.openweathermap.org/data/2.5/weather?q=$cityName,$countryCode&appid=$apiKey&units=metric&lang=pl"

    }

    private fun handleJson(jsonString: String?) {

        val jsonObject = JSONObject(jsonString)
        val jsonArray = jsonObject.getJSONArray("weather")
        val jsonObjectFromArray: JSONObject = jsonArray[0] as JSONObject


        var weather = Weather ( jsonObject.getJSONObject("coord").getString("lon"),
            jsonObject.getJSONObject("coord").getString("lat"),
            jsonObjectFromArray.getString("description"),
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

        weatherMap["longitude"] = (weather.longitude)
        weatherMap["latitude"] = (weather.latitude)
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

        println(weatherMap["longitude"])
        println(weatherMap["latitude"])
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