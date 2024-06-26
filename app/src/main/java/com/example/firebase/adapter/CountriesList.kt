package com.example.firebase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.data.Countries
import com.example.firebase.data.Radio
import com.example.firebase.databinding.CountriestypeBinding
import com.example.firebase.databinding.ListradioBinding

class CountriesList(val onItemClick: (Countries) -> Unit) : RecyclerView.Adapter<CountriesList.ViewHoder>() {

    var countries: List<Countries> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHoder (val binding: CountriestypeBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoder {
        val binding = CountriestypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoder(binding)
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: ViewHoder, position: Int) {
        val country = countries[position]

        with(holder.binding){
            IdImage.text = country.name
            CountryName.text = country.iso31661
            NameType.text = country.stationcount.toString()
            root.setOnClickListener {
                onItemClick(country)
            }
        }
    }
}