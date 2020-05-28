package com.tommyapps.weatheronandroid

import android.content.Context
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class LocationOperations(context: Context) {

    private val insideContext = context
    var arrayList = ArrayList<Country>()

    fun parseXML(): ArrayList<String> {

        var parseFactory = XmlPullParserFactory.newInstance()
        var parser = parseFactory.newPullParser()
        var inputText = insideContext.assets.open("countryIsoCodes.xml")

        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(inputText, null)

        return processParsing(parser)
    }

    private fun processParsing(parser: XmlPullParser): ArrayList<String> {

        var eventType = parser.eventType
        var current: Country? = null

        while (eventType != XmlPullParser.END_DOCUMENT) {

            var initName = ""

            when(eventType) {

                XmlPullParser.START_DOCUMENT -> arrayList = ArrayList<Country>()
                XmlPullParser.START_TAG -> {
                    initName = parser.name

                    if ("record".equals(initName)) {
                        current = Country()
                        arrayList.add(current)
                    } else if (current != null) {
                        if ("countryName".equals(initName)) {
                            current.name = parser.nextText()
                        } else if ("countryISOcode".equals(initName)){
                            current.code = parser.nextText()
                        }
                    }
                }
            }

            eventType = parser.next()
        }

        val countryNamesArray = ArrayList<String>()

        arrayList.forEach { countryNamesArray.add(it.name) }

        return countryNamesArray
    }

    fun getCountryCode(countryName: String): String {

        arrayList.forEach {
            if (countryName == it.name) {
                return it.code
            }
        }

        return ""
    }

}