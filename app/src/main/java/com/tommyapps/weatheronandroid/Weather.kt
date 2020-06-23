package com.tommyapps.weatheronandroid

class Weather {

    var longitude: String = ""
    var latitude: String = ""
    var description: String = ""
    var icon: String = ""
    var temperature: String = ""
    var pressure: String = ""
    var humidity: String = ""
    var wind: String = ""
    var clouds: String = ""
    var country: String = ""
    var city: String = ""

    constructor() {}

    constructor(initLongitude: String,
                initLatitude: String,
                initDescription: String,
                initIcon: String,
                initTemperature: String,
                initPressure: String,
                initHumidity: String,
                initWind: String,
                initClouds: String,
                initCountry: String,
                initCity: String) {

        this.longitude = initLongitude
        this.latitude = initLatitude
        this.description = initDescription
        this.icon = initIcon
        this.temperature = initTemperature
        this.pressure = initPressure
        this.humidity = initHumidity
        this.wind = initWind
        this.clouds = initClouds
        this.country = initCountry
        this.city = initCity
    }

}