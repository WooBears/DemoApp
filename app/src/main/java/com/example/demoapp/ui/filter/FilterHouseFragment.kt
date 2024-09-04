package com.example.demoapp.ui.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.demoapp.R
import com.example.demoapp.databinding.FragmentFilterHouseBinding
import com.example.demoapp.databinding.FragmentHomeBinding
import com.example.demoapp.domain.adapter.HouseAdapter
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.navigation.fragment.findNavController

class FilterHouseFragment : Fragment() {

    private var _binding: FragmentFilterHouseBinding? = null
    private val binding get() = _binding!!

    private var minPrice: Int = 0
    private var maxPrice: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterHouseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seekBarMinPrice = binding.seekBarMinPrice
        val seekBarMaxPrice = binding.seekBarMaxPrice

        // Initial price range values
        minPrice = seekBarMinPrice.progress
        maxPrice = seekBarMaxPrice.progress

        updatePriceText(minPrice, maxPrice)

        seekBarMinPrice.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                minPrice = progress
                if (minPrice > maxPrice) {
                    maxPrice = minPrice
                    seekBarMaxPrice.progress = maxPrice
                }
                updatePriceText(minPrice, maxPrice)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBarMaxPrice.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                maxPrice = progress
                if (maxPrice < minPrice) {
                    minPrice = maxPrice
                    seekBarMinPrice.progress = minPrice
                }
                updatePriceText(minPrice, maxPrice)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.btnApplyFilter.setOnClickListener {
            val bedrooms = binding.spinnerBedrooms.selectedItem.toString().toIntOrNull()
            val bathrooms = binding.spinnerBathrooms.selectedItem.toString().toIntOrNull()
            val streetAddress = binding.etStreetAddress.text.toString()

            performFilter(
                minPrice = minPrice,
                maxPrice = maxPrice,
                bedrooms = bedrooms,
                bathrooms = bathrooms,
                streetAddress = streetAddress
            )
        }
    }

    private fun performFilter(
        minPrice: Int,
        maxPrice: Int,
        bedrooms: Int?,
        bathrooms: Int?,
        streetAddress: String
    ) {
        val bundle = Bundle().apply {
            putInt("minPrice", minPrice)
            putInt("maxPrice", maxPrice)
            putInt("bedrooms", bedrooms ?: 0)
            putInt("bathrooms", bathrooms ?: 0)
            putString("streetAddress", streetAddress)
        }

        findNavController().navigate(R.id.navigation_notifications, bundle)
    }

    private fun updatePriceText(minPrice: Int, maxPrice: Int) {
        binding.tvPriceValue.text = "Price: $${String.format("%,d", minPrice)} - $${String.format("%,d", maxPrice)}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
