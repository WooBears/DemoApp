package com.example.demoapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.demoapp.R
import com.example.demoapp.databinding.FragmentNotificationsBinding
import com.example.demoapp.domain.adapter.HouseAdapter
import com.example.demoapp.domain.model.ResultHouses
import com.example.demoapp.util.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchedResultViewModel by viewModels()
    private lateinit var houseAdapter: HouseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        houseAdapter = HouseAdapter(this::onClick)
        binding.recyclerHf.adapter = houseAdapter

        // Check if filter criteria were passed
        arguments?.let {
            Log.d("NotificationsFragment", "Arguments received: $it")
            val minPrice = it.getInt("minPrice", 0)
            val maxPrice = it.getInt("maxPrice", 0)
            val bedrooms = it.getInt("bedrooms", 0)
            val bathrooms = it.getInt("bathrooms", 0)
            val streetAddress = it.getString("streetAddress", "")

            // Perform the filtered search
            performFilteredSearch(minPrice, maxPrice, bedrooms, bathrooms, streetAddress)
        }

        // Set up a TextWatcher on the search EditText
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    performSearch(it.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.ivBtnFillter.setOnClickListener {
            findNavController().navigate(R.id.filterFragment)
        }
    }

    private fun performFilteredSearch(
        minPrice: Int,
        maxPrice: Int,
        bedrooms: Int?,
        bathrooms: Int?,
        streetAddress: String
    ) {

        houseAdapter.clearHouses()
        viewModel.getFilteredHouses(minPrice, maxPrice, bedrooms, bathrooms, streetAddress).observe(viewLifecycleOwner) { result ->
            result.data?.let { houses ->
                if (result.status == Result.Status.SUCCESS){
                    houseAdapter.addHouses(houses)
                } else {
                    Log.e("NotificationsFragment", "Error: ${result.message}")
                    houseAdapter.addHouses(emptyList())
                }
            } ?: run {
                Log.e("NotificationsFragment", "No data found or error")
                houseAdapter.addHouses(emptyList())
            }
        }
    }
        private fun performSearch(query: String) {
        viewModel.getSearchHouse(query).observe(viewLifecycleOwner) { result ->
            result.data?.let { houses ->
                if (result.status == Result.Status.SUCCESS) {
                    houseAdapter.addHouses(houses)
                } else {
                    Log.e("HomeFragment", "Error: ${result.message}")
                    houseAdapter.addHouses(emptyList())
                }
            } ?: run {
                Log.e("HomeFragment", "No data found or error")
                houseAdapter.addHouses(emptyList())
            }
        }
    }


    private fun onClick(result: ResultHouses) {
        val bundle = Bundle().apply {
            putParcelable("houseData", result)
        }
        findNavController().navigate(R.id.detailsOfTheHouseFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
