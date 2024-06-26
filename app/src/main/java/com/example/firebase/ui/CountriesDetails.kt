package com.example.firebase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.Model.RadioViewModel
import com.example.firebase.adapter.RadioAdapter
import com.example.firebase.data.Radio
import androidx.navigation.fragment.findNavController
import com.example.firebase.databinding.CountriesFragmentBinding
import com.example.firebase.databinding.FragmentHomeBinding

class CountriesDetails: Fragment() {

    private lateinit var binding: CountriesFragmentBinding
    val args: CountriesDetailsArgs by navArgs()
    private lateinit var adapter: RadioAdapter
    val viewModel: RadioViewModel by viewModels<RadioViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CountriesFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRadioByCountry(args.country)

        viewModel.radioList.observe(viewLifecycleOwner){
            if(it != null){
                adapter.radios = it
            }
        }

        adapter = RadioAdapter(this::onClick)
        binding.CountriesList.layoutManager = LinearLayoutManager(requireActivity())
        binding.CountriesList.adapter = adapter

    }

    private fun onClick(radio: Radio){
        val action = CountriesDetailsDirections.actionCountriesDetailsToPlayerCoutriesFragment(radio.name.toString(), radio.urlResolved.toString())
        findNavController().navigate(action)
    }
}