package com.example.firebase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.Model.RadioViewModel
import com.example.firebase.adapter.CountriesList
import com.example.firebase.adapter.RadioAdapter
import com.example.firebase.data.Countries
import com.example.firebase.databinding.CountriesFragmentBinding
import com.example.firebase.databinding.CountriestypeBinding
import com.example.firebase.databinding.PlayerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CountriesFragment: Fragment() {

    private lateinit var binding: CountriesFragmentBinding
    private lateinit var adapter: CountriesList

    val viewModel: RadioViewModel by viewModels<RadioViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CountriesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CountriesList(this::onClick)
        binding.CountriesList.layoutManager = LinearLayoutManager(requireActivity())
        binding.CountriesList.adapter = adapter

        viewModel.countriesList.observe(viewLifecycleOwner){
            if (it != null){
                adapter.countries = it
            }
        }
    }

    private fun onClick(country: Countries){
        val action = CountriesFragmentDirections.actionCountriesFragmentToCountriesDetails(country.name.toString())
        findNavController().navigate(action)
    }
}