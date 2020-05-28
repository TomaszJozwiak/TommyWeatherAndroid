package com.tommyapps.weatheronandroid

class Country {

    var name: String = ""
    var code: String = ""

    constructor() {}

    constructor(initPlace: Int, initVariable: String) {

        when(initPlace) {
            1 -> name = initVariable
            2 -> code = initVariable
        }
    }

    constructor(initName: String, initCode: String) {
        name = initName
        code = initCode
    }

}