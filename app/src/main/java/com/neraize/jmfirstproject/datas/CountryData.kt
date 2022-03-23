package com.neraize.jmfirstproject.datas

import java.io.Serializable

class CountryData(
    val id:Int,
    val name:String,
    val possibility:String,
    val information:String,
    val latitude:Double,
    val longitude:Double,

):Serializable {

}
