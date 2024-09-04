package com.example.demoapp.ui.realEsate

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demoapp.R
import com.example.demoapp.databinding.FragmentRealEstateBinding
import com.example.demoapp.domain.adapter.HouseAdapter
import com.example.demoapp.domain.model.ResultHouses
import com.example.demoapp.ui.dashboard.DashboardViewModel
import com.example.demoapp.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RealEstateFragment : Fragment() {

    private lateinit var binding: FragmentRealEstateBinding
    private val viewModel: RealEstateViewModel by viewModels()
    private lateinit var houseAdapter: HouseAdapter
    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRealEstateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        houseAdapter = HouseAdapter(this::onClick)
        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.rvRecycler.layoutManager = gridLayoutManager
        binding.rvRecycler.adapter = houseAdapter

        binding.rvRecycler.isClickable = false


        dashboardViewModel.houses.observe(viewLifecycleOwner) { houses ->
            houseAdapter.addHouses(houses)
        }

        bindUi("houston")

    }

    private fun bindUi(location: String) = lifecycleScope.launch(Dispatchers.Main) {
        try {
            val houses = viewModel.getHouses(location)
            houses.observe(viewLifecycleOwner, Observer { result ->
                Log.d("RealEstateFragment", "Result status: ${result.status}")
                when(result.status){
                    Result.Status.SUCCESS -> {
                        Log.d("RealEstateFragment", "Data received: ${result.data}")
                        result.data?.let { house ->
                            houseAdapter.addHouses(house.results)
                        }
                    }
                    Result.Status.LOADING -> {

                    }
                    Result.Status.ERROR -> {
                        Log.e("RealEstateFragment", "Error occurred: ${result.message}")
                        Toast.makeText(requireContext(), "Something went wrong please check your internet connection", Toast.LENGTH_LONG).show()
                        bindCachedHouses()
                    }
                }
            })
        } catch (e: Exception){
            Log.e("RealEstateFragment", "Exception occurred: ${e.message}")
            Toast.makeText(context,"Something went wrong please check your internet connection",Toast.LENGTH_LONG).show()
            bindCachedHouses()
        }

    }

    private fun bindCachedHouses() = viewModel.getAllHousesCached().observe(viewLifecycleOwner, Observer { result ->
        when (result.status) {
            Result.Status.SUCCESS -> {
                val data = result.data
                if (!data.isNullOrEmpty()) {
                    houseAdapter.addHouses(data)
                } else {
                    Log.e("RealEstateFragment Cached:", "No cached data found")
                }
            }
            Result.Status.LOADING -> {

                Log.d("RealEstateFragment Cached:", "Loading cached data")
            }
            Result.Status.ERROR -> {
                Log.e("RealEstateFragment Cached:", "Error loading cached data: ${result.message}")
                Toast.makeText(requireContext(), "Something went wrong, please check your internet connection", Toast.LENGTH_LONG).show()

            }
        }
    })

    private fun onClick(result: ResultHouses) {
        val bundle = Bundle().apply {
            putParcelable("houseData", result)
        }
        findNavController().navigate(R.id.detailsOfTheHouseFragment, bundle)
    }
}