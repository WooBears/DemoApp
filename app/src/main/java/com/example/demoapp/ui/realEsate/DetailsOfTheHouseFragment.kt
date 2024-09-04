package com.example.demoapp.ui.realEsate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.demoapp.R
import com.example.demoapp.databinding.FragmentDetailsOfTheHouseBinding
import com.example.demoapp.domain.model.ResultHouses

class DetailsOfTheHouseFragment : Fragment() {

    private lateinit var binding: FragmentDetailsOfTheHouseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsOfTheHouseBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result: ResultHouses? = arguments?.getParcelable("houseData")

        result?.let {
            Glide.with(binding.root).load(result.imgSrc).into(binding.houseImage)
            binding.housePrice.text = "Price: $" + it.price.toString()
            binding.houseBedrooms.text = "Bedrooms: " + it.bedrooms.toString()
            binding.houseBathrooms.text = "Bathrooms " + it.bathrooms.toString()
            binding.houseAddress.text = "Address: ${it.country.toString()},${it.city.toString()}, ${it.streetAddress.toString()}"
            binding.houseStatus.text = "Status: " + it.homeStatus.toString()
            binding.houseType.text = "Type: " + it.homeType.toString()
            binding.houseArea.text = "Area: " + it.livingArea.toString() + "m^2"
            binding.houseUnit.text = "Unit: " + it.unit.toString()

        }
    }
}