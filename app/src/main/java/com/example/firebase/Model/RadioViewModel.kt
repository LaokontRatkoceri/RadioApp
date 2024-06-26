package com.example.firebase.Model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebase.Repository
import com.example.firebase.data.Countries
import com.example.firebase.data.Radio

import retrofit2.Call
import retrofit2.Response

class RadioViewModel: ViewModel() {
    val radioList = MutableLiveData<List<Radio>>()
    val countriesList = MutableLiveData<List<Countries>>()
    private val repository = Repository()

    init {
        getAllRadio()
        getAllCountries()
    }

    fun getAllRadio(){
        repository.service.getAllRadio().enqueue(object : retrofit2.Callback<List<Radio>>{
            override fun onResponse(call: Call<List<Radio>>, response: Response<List<Radio>>) {
                radioList.postValue(response.body()!!)
            }

            override fun onFailure(call: Call<List<Radio>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getAllCountries(){
        repository.service.getAllCountries().enqueue(object : retrofit2.Callback<List<Countries>>{
            override fun onResponse(
                call: Call<List<Countries>>,
                response: Response<List<Countries>>
            ) {
                countriesList.postValue(response.body()!!)
            }

            override fun onFailure(call: Call<List<Countries>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getRadioByCountry(country: String){
        repository.service.getRadioListByCountry(country).enqueue(object : retrofit2.Callback<List<Radio>>{
            override fun onResponse(call: Call<List<Radio>>, response: Response<List<Radio>>) {
                radioList.postValue(response.body()!!)
            }

            override fun onFailure(call: Call<List<Radio>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}