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
    private var dataFromApi: String? = null
    var message: String? = null

    fun getResponseFromApi(): String? {

        dataFromApi = AsyncTaskHandleJson().execute(url).get()
        if (dataFromApi != null) {
            return dataFromApi
        } else {
            message = "Problem with weather data. Please try again later"
        }
        return null
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

}