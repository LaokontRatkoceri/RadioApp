package com.example.firebase.data

import com.google.gson.annotations.SerializedName

data class Countries (

        @SerializedName("name"         ) var name         : String? = null,
        @SerializedName("iso_3166_1"   ) var iso31661     : String? = null,
        @SerializedName("stationcount" ) var stationcount : Int?    = null

)