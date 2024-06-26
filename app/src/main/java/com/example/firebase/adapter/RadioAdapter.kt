package com.example.firebase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.data.Radio
import com.example.firebase.databinding.ListradioBinding
import com.example.firebase.databinding.PlayerBinding

class RadioAdapter(val onItemClick: (Radio) -> Unit): RecyclerView.Adapter<RadioAdapter.ViewHoder>() {

    var radios: List<Radio> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHoder (val binding: ListradioBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoder {
        val binding = ListradioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoder(binding)
    }

    override fun getItemCount(): Int = radios.size

    override fun onBindViewHolder(holder: ViewHoder, position: Int) {
        val radio = radios[position]

        with(holder.binding){
            IdImage.text = radio.name
            CountryName.text = radio.country
            NameType.text = radio.changeuuid

            root.setOnClickListener {
                onItemClick(radio)
            }
        }
    }
}