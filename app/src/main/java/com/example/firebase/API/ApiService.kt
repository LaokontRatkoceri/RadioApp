package com.example.firebase.API

import com.example.firebase.data.Countries
import com.example.firebase.data.Radio
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import java.io.IOException

interface ApiService {

//    @Headers("x-rapidapi-key: " + "16feaa8b3cmshb8d3d42fc666b42p1aac15jsncc4452d539cc")
    @GET("json/stations/bycountry/Kosovo")
    fun getAllRadio(): Call<List<Radio>>

    @GET("json/countries")
    fun getAllCountries(): Call<List<Countries>>

    @GET("/json/stations/bycountry/{country}")
    fun getRadioListByCountry(@Path("country") country:String): Call<List<Radio>>


}