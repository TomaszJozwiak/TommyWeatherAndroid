package com.tommyapps.weatheronandroid

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.choose_location.*

class ChangeLocalization: AppCompatActivity() {

    private val locationOperations: LocationOperations = LocationOperations(this)
    var countryAutoCompleteTextView: AutoCompleteTextView? = null
    var cityEditText: EditText? = null

    var cityName: String = ""
    var countryCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_location)

        countryAutoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.countryAutoCompleteTextView)
        cityEditText = findViewById<EditText>(R.id.cityEditText)

        setCountryNamesToTextView()

    }

    private fun setCountryNamesToTextView() {

        val country = locationOperations.parseXML()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,  country)
        countryAutoCompleteTextView?.threshold = 0
        countryAutoCompleteTextView?.setAdapter(adapter)
        countryAutoCompleteTextView?.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus -> if(hasFocus) countryAutoCompleteTextView?.showDropDown() })

    }

    fun showWeather(view: View) {

        if (checkCountryName() && checkCity()) {

            Toast.makeText(applicationContext, "${createLink()}", Toast.LENGTH_SHORT).show()

        }

    }

    private fun checkCountryName(): Boolean {

        countryCode = locationOperations.getCountryCode(countryAutoCompleteTextView?.text.toString())
        if (countryCode == "") {

            Toast.makeText(applicationContext, "Niepoprawne państwo", Toast.LENGTH_LONG).show()
            return false

        }

        return true

    }

    private fun checkCity(): Boolean {

        cityName = cityEditText?.text.toString()
        if (cityName == "") {

            Toast.makeText(applicationContext, "Niepoprawne miasto", Toast.LENGTH_LONG).show()
            return false

        }

        return true

    }

    private fun createLink(): String {

        return "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "," + countryCode + "&units=metric&lang=pl&mode=xml&APPID=8cbc4f1e6576a2478f1d70e0a17cc594"

    }
}
