package com.example.demoapp.ui.vehicle

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demoapp.R
import com.example.demoapp.databinding.FragmentVehicleBinding
import com.example.demoapp.domain.adapter.CarAdapter
import com.example.demoapp.domain.adapter.HouseAdapter
import com.example.demoapp.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class VehicleFragment : Fragment() {

    private lateinit var binding: FragmentVehicleBinding
    private val viewModel: VehicleViewModel by viewModels()
    private lateinit var carAdapter: CarAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVehicleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        carAdapter = CarAdapter()
        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.vRecycler.layoutManager = gridLayoutManager
        binding.vRecycler.adapter = carAdapter

        // Disable RecyclerView clicks initially
        binding.vRecycler.isClickable = false


        bindUi()
    }
    private fun bindUi() = lifecycleScope.launch(Dispatchers.Main) {
        try {
            val houses = viewModel.getCar()
            houses.observe(viewLifecycleOwner, Observer { result ->
                Log.d("VehicleFragment", "Result status: ${result.status}")
                when(result.status){
                    Result.Status.SUCCESS -> {
                        Log.d("VehicleFragment", "Data received: ${result.data}")
                        result.let { car ->
                            car.data?.let { carAdapter.addCar(it) }
                        }

                    }
                    Result.Status.LOADING -> {

                    }
                    Result.Status.ERROR -> {
                        Log.e("VehicleFragment", "Error occurred: ${result.message}")
                        Toast.makeText(requireContext(), "Something went wrong please check your internet connection", Toast.LENGTH_LONG).show()
                    }
                }
            })
        } catch (e: Exception){
            Log.e("VehicleFragment", "Exception occurred: ${e.message}")
            Toast.makeText(context,"Something went wrong please check your internet connection",
                Toast.LENGTH_LONG).show()
        }

    }

}