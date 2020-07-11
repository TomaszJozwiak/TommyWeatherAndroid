package com.tommyapps.weatheronandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ChangeLocalization: AppCompatActivity() {

    private val locationOperations: LocationOperations = LocationOperations(this)
    var countryAutoCompleteTextView: AutoCompleteTextView? = null
    var cityEditText: EditText? = null
    private lateinit var saveDefaultLocationCheckBox: CheckBox

    var cityName: String = ""
    var countryCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_location)

        countryAutoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.countryAutoCompleteTextView)
        cityEditText = findViewById<EditText>(R.id.cityEditText)
        saveDefaultLocationCheckBox = findViewById<CheckBox>(R.id.saveDefaultLocationCheckBox)

        setCountryNamesToTextView()

    }

    private fun setCountryNamesToTextView() {

        val country = locationOperations.parseXML()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,  country)
        countryAutoCompleteTextView?.threshold = 0
        countryAutoCompleteTextView?.setAdapter(adapter)

    }

    fun showWeather(view: View) {

        if (checkCountryName() && checkCity()) {

            saveDefaultLocalization()

            val currentWeatherCalculations: CurrentWeatherCalculations = CurrentWeatherCalculations(cityName, countryCode)
            val map: HashMap<String, String>? = currentWeatherCalculations.getCurrentWeatherData()

            if (map != null) {
                sendIntent(map)
            } else {
                Toast.makeText(applicationContext, "Problem z pobraniem pogody, spróbuj później", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun sendIntent(map: HashMap<String, String>) {

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("map", map)
        setResult(0, intent)
        finish()

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

    private fun saveDefaultLocalization() {

        if (saveDefaultLocationCheckBox.isChecked) {

            val localizationSharedPreferences: LocalizationSharedPreferences = LocalizationSharedPreferences(this)
            localizationSharedPreferences.clearSharedPreference()
            localizationSharedPreferences.save("city", cityName)
            localizationSharedPreferences.save("country", countryCode)

        }

    }

}
