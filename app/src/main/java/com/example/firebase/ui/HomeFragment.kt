package com.example.firebase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.Model.RadioViewModel
import com.example.firebase.adapter.RadioAdapter
import com.example.firebase.data.Radio
import com.example.firebase.databinding.FragmentHomeBinding
import com.example.firebase.ui.HomeFragmentDirections

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: RadioAdapter
    val radioItems = mutableListOf<Radio>()

    private val filteredRadioItems = mutableListOf<Radio>()



    val viewModel: RadioViewModel by viewModels<RadioViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.radioList.observe(viewLifecycleOwner){
//            if(it != null){
//                adapter.radios = it
//            }
//        }
        adapter = RadioAdapter(this::onRadioClick)
        binding.radioList.layoutManager = LinearLayoutManager(requireActivity())
        binding.radioList.adapter = adapter

        viewModel.radioList.observe(viewLifecycleOwner, Observer { radios ->
            if (radios != null) {
                radioItems.clear()
                radioItems.addAll(radios)
                updateFilteredRadios("")
            }
        })

        binding.search.addTextChangedListener { query ->
            updateFilteredRadios(query.toString())
        }

        binding.ImageCountries.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToCountriesFragment()
            findNavController().navigate(action)
        }
    }

    private fun updateFilteredRadios(query: String) {
        filteredRadioItems.clear()
        for (item in radioItems) {
            if (item.name?.startsWith(query, ignoreCase = true) == true) {
                filteredRadioItems.add(item)
            }
        }
        adapter.radios = filteredRadioItems
    }

    private fun onRadioClick(radio: Radio) {
        val action = HomeFragmentDirections.actionHomeFragmentToPlaterFragment(radio.name.toString(), radio.urlResolved.toString())
        findNavController().navigate(action)

    }


}