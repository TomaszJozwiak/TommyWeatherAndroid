package com.tommyapps.weatheronandroid

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.week_weather.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class WeekWeather: AppCompatActivity() {

    private val language = arrayOf<String>("C","C++","Java",".Net","Kotlin","Ruby","Rails")

    private var description = ArrayList<String>()
    private var temperature = ArrayList<String>()
    private var date = ArrayList<String>()
    private var icon = ArrayList<String>()


    var weekWeatherArray = ArrayList<HashMap<String, String>>()
    private var longitude: String? = null
    private var latitude: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.week_weather)

        title = "Tygodniowa pogoda"

        val intent = intent
        longitude = intent.getStringExtra("longitude")
        latitude = intent.getStringExtra("latitude")

        val localizationTextView = findViewById<TextView>(R.id.localizationTextView)

        localizationTextView.text = (intent.getStringExtra("localization"))

        showWeather()

        var listView: ListView = findViewById<ListView>(R.id.listView)

        val myListAdapter = MyListAdapter(this, description, temperature, date, icon)
        listView.adapter = myListAdapter
    }

    inner class MyListAdapter(private val context: Activity, private val tempDescription: ArrayList<String>, private val tempTemperature: ArrayList<String>, private val tempDate: ArrayList<String>, private val tempIcon: ArrayList<String>)
        : ArrayAdapter<String>(context, R.layout.weather_item, tempDescription) {

        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
            val inflater = context.layoutInflater
            val rowView = inflater.inflate(R.layout.weather_item, null, true)

            val weekWeatherDescriptionTextView = rowView.findViewById(R.id.weekWeatherDescriptionTextView) as TextView
            val weekWeatherTemperatureTextView = rowView.findViewById(R.id.weekWeatherTemperatureTextView) as TextView
            val weekWeatherImageView = rowView.findViewById(R.id.weekWeatherImageView) as ImageView
            val weekWeatherDateTextView = rowView.findViewById(R.id.weekWeatherDateTextView) as TextView


            weekWeatherDescriptionTextView.text = tempDescription[position]
            weekWeatherTemperatureTextView.text = tempTemperature[position]
            weekWeatherDateTextView.text = convertDate(tempDate[position])
            val resourceId: Int = resources.getIdentifier(tempIcon[position], "drawable", packageName)
            weekWeatherImageView.setImageResource(resourceId)

            return rowView
        }
    }

    private fun showWeather() {

        var weekWeatherCalculations: WeekWeatherCalculations = WeekWeatherCalculations()
        var temp = weekWeatherCalculations.getWeekWeatherData(createLink())

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
        val date = Date(unixSeconds * 1000L)

        var day = SimpleDateFormat("dd")
        var month = SimpleDateFormat("MMMM")
        var year = SimpleDateFormat("yyyy")

        return "${day.format(date)} ${setMonthName(month.format(date))} ${year.format(date)}"

    }

    private fun setMonthName(monthName: String): String {

        var monthNameInPolish: String = ""

        when(monthName) {

            "January" -> monthNameInPolish = "Styczeń"
            "February" -> monthNameInPolish = "Luty"
            "March" -> monthNameInPolish = "Marzec"
            "April" -> monthNameInPolish = "Kwiecień"
            "May" -> monthNameInPolish = "Maj"
            "June" -> monthNameInPolish = "Czerwiec"
            "July" -> monthNameInPolish = "Lipiec"
            "August" -> monthNameInPolish = "Sierpień"
            "September" -> monthNameInPolish = "Wrzesień"
            "October" -> monthNameInPolish = "Październik"
            "November" -> monthNameInPolish = "Listopad"
            "December" -> monthNameInPolish = "Grudzień"
        }

        return monthNameInPolish

    }



    private fun createLink(): String {

        val apiKey: String = "8cbc4f1e6576a2478f1d70e0a17cc594"

        return "https://api.openweathermap.org/data/2.5/onecall?lat=$latitude&lon=$longitude&exclude=current,minutely,hourly&appid=$apiKey&units=metric&lang=pl"

    }

}