package com.tommyapps.weatheronandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var cityTextView: TextView? = null
    var countryTextView: TextView? = null
    var descriptionTextView: TextView? = null
    var temperatureTextView: TextView? = null
    var pressureTextView: TextView? = null
    var humidityTextView: TextView? = null
    var windTextView: TextView? = null
    var cloudsTextView: TextView? = null
    var iconImageView: ImageView? = null

    var weatherMap = HashMap<String, String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityTextView = findViewById<TextView>(R.id.cityTextView)
        countryTextView = findViewById<TextView>(R.id.countryTextView)
        descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
        temperatureTextView = findViewById<TextView>(R.id.temperatureTextView)
        pressureTextView = findViewById<TextView>(R.id.pressureTextView)
        humidityTextView = findViewById<TextView>(R.id.humidityTextView)
        windTextView = findViewById<TextView>(R.id.windTextView)
        cloudsTextView = findViewById<TextView>(R.id.cloudsTextView)
        iconImageView = findViewById<ImageView>(R.id.iconImageView)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            weatherMap = data.getSerializableExtra("map") as HashMap<String, String>

            setAttributes(weatherMap)
        }

    }


    private fun setAttributes(map: HashMap<String, String>) {

        cityTextView?.text = map["city"]
        countryTextView?.text = map["country"]
        descriptionTextView?.text = map["description"]
        temperatureTextView?.text = map["temperature"]
        pressureTextView?.text = map["pressure"]
        humidityTextView?.text = map["humidity"]
        windTextView?.text = map["wind"]
        cloudsTextView?.text = map["clouds"]

        setIcon(map["icon"])

    }

    private fun setIcon(iconName: String?) {

        val resourceId: Int = resources.getIdentifier(iconName, "drawable", packageName)
        iconImageView?.setImageResource(resourceId)

    }

    fun changeLocalization(view: View) {

        val intent = Intent(this, ChangeLocalization::class.java)
        startActivityForResult(intent, 0)

    }

}
