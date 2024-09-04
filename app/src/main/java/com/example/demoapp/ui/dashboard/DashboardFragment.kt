package com.example.demoapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.demoapp.databinding.FragmentDashboardBinding
import com.example.demoapp.domain.model.ResultHouses
import com.example.demoapp.domain.model.localHouse.LocalHouse
import com.example.demoapp.util.Result

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val viewModel: DashboardViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add event listener to the "Add House" button
        binding.addHouseButton.setOnClickListener {
            val price = binding.priceInput.text.toString().toIntOrNull() ?: 0
            val bedrooms = binding.bedroomsInput.text.toString().toIntOrNull() ?: 0
            val bathrooms = binding.bathroomsInput.text.toString().toIntOrNull() ?: 0
            val address = binding.addressInput.text.toString()
            val city = binding.cityInput.text.toString()

            // Create a new LocalHouse object with default values
            val newHouse = LocalHouse(
                price = price,
                bedrooms = bedrooms,
                bathrooms = bathrooms,
                city = city.ifEmpty { null },
                country = null, // Default value
                currency = null, // Default value
                datePriceChanged = System.currentTimeMillis(), // Default to current time
                daysOnZillow = 0, // Default value
                group_type = null, // Default value
                homeStatus = null, // Default value
                homeStatusForHDP = null, // Default value
                homeType = null, // Default value
                imgSrc = null, // Default value
                isFeatured = false, // Default value
                isNonOwnerOccupied = false, // Default value
                isPreforeclosureAuction = false, // Default value
                isPremierBuilder = false, // Default value
                isShowcaseListing = false, // Default value
                isUnmappable = false, // Default value
                isZillowOwned = false, // Default value
                latitude = 0.0, // Default value
                livingArea = 0, // Default value
                longitude = 0.0, // Default value
                lotAreaUnit = null, // Default value
                lotAreaValue = 0.0, // Default value
                newConstructionType = null, // Default value
                openHouse = null, // Default value
                priceChange = 0, // Default value
                priceForHDP = 0, // Default value
                priceReduction = null, // Default value
                priceSuffix = null, // Default value
                providerListingID = null, // Default value
                rentZestimate = 0, // Default value
                shouldHighlight = false, // Default value
                state = null, // Default value
                streetAddress = address.ifEmpty { null },
                taxAssessedValue = 0, // Default value
                unit = null, // Default value
                videoCount = 0, // Default value
                zestimate = 0, // Default value
                zipcode = null, // Default value
                zpid = 0 // Default value
            )
            viewModel.addHouse(newHouse)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}