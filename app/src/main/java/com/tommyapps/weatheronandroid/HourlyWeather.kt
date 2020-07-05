package com.tommyapps.weatheronandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HourlyWeather: AppCompatActivity() {

    private var description = ArrayList<String>()
    private var temperature = ArrayList<String>()
    private var date = ArrayList<String>()
    private var icon = ArrayList<String>()


    var hourlyWeatherArray = ArrayList<HashMap<String, String>>()
    private var longitude: String? = null
    private var latitude: String? = null
    private var localization: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hourly_weather)

        title = "Godzinna pogoda"

        val intent = intent
        longitude = intent.getStringExtra("longitude")
        latitude = intent.getStringExtra("latitude")

        val localizationHourlyWeatherTextView = findViewById<TextView>(R.id.localizationHourlyWeatherTextView)

        localization = intent.getStringExtra("localization")
        localizationHourlyWeatherTextView.text = localization

        showWeather()

        var hourlyWeatherListView: ListView = findViewById<ListView>(R.id.hourlyWeatherListView)

        val myListAdapter = MyListAdapter(this, description, temperature, date, icon)
        hourlyWeatherListView.adapter = myListAdapter
    }

    inner class MyListAdapter(private val context: Activity, private val tempDescription: ArrayList<String>, private val tempTemperature: ArrayList<String>, private val tempDate: ArrayList<String>, private val tempIcon: ArrayList<String>)
        : ArrayAdapter<String>(context, R.layout.weather_item, tempDescription) {

        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
            val inflater = context.layoutInflater
            val rowView = inflater.inflate(R.layout.weather_item, null, true)

            val weatherItemDescriptionTextView = rowView.findViewById(R.id.weatherItemDescriptionTextView) as TextView
            val weatherItemTemperatureTextView = rowView.findViewById(R.id.weatherItemTemperatureTextView) as TextView
            val weatherItemImageView = rowView.findViewById(R.id.weatherItemImageView) as ImageView
            val weatherItemDateTextView = rowView.findViewById(R.id.weatherItemDateTextView) as TextView


            weatherItemDescriptionTextView.text = tempDescription[position]
            weatherItemTemperatureTextView.text = tempTemperature[position]
            weatherItemDateTextView.text = convertDate(tempDate[position])
            val resourceId: Int = resources.getIdentifier(tempIcon[position], "drawable", packageName)
            weatherItemImageView.setImageResource(resourceId)

            return rowView
        }
    }

    private fun showWeather() {

        var hourlyWeatherCalculations: HourlyWeatherCalculations = HourlyWeatherCalculations(longitude, latitude)
        var temp = hourlyWeatherCalculations.getHourlyWeatherData()

        if (temp != null) {
            setArrays(temp)
        } else {
            Toast.makeText(applicationContext, "Problem z pobraniem pogody, spróbuj później", Toast.LENGTH_LONG).show()
        }

    }

    private fun setArrays(array: ArrayList<HashMap<String, String>>) {

        for (i in (0 until array.size)) {

            var tempDescription = array[i]["description"].toString().capitalize()
            description.add(tempDescription)

            var tempTemperature = "${roundTemperatureDegree(array[i]["temperature"].toString())} ℃"
            temperature.add(tempTemperature)

            date.add(array[i]["date"].toString())

            var tempIcon = setIconName(array[i]["icon"].toString())
            icon.add(tempIcon)

        }

    }

    private fun roundTemperatureDegree(temperatureToRound: String): String  {

        return temperatureToRound.substring(0, 2)

    }

    private fun setIconName(iconName: String): String {

        var temp: String = ""

        temp = iconName.substring(iconName.lastIndex) + iconName.substring(0, 2)
        return temp

    }

    private fun convertDate(unixTime: String): String {

        val unixSeconds: Long = unixTime.toLong()
        val date = Date((unixSeconds + 7200) * 1000L)

        var hour = SimpleDateFormat("kk", Locale.UK)

        return "${hour.format(date)}:00"

    }

    fun weekWeather(view: View) {

        val intent = Intent(this, WeekWeather::class.java)
        intent.putExtra("localization", localization)
        intent.putExtra("longitude",longitude);
        intent.putExtra("latitude", latitude);
        startActivity(intent)

    }

    fun actualWeather(view: View) {

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("localization", localization)
        intent.putExtra("longitude",longitude);
        intent.putExtra("latitude", latitude);
        startActivity(intent)

    }


}