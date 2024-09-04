package com.example.demoapp.domain.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demoapp.databinding.CarItemBinding
import com.example.demoapp.domain.model.ResultHouses
import com.example.demoapp.domain.model.carModel.Car
import com.example.demoapp.domain.model.carModel.CarItem

class CarAdapter : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    private var items = arrayListOf<CarItem>()

    fun clearCar() {
        items.clear()
        notifyDataSetChanged()
    }

    fun addCar(list: List<CarItem>) {
        Log.d("CarAdapter", "Items received: ${list.size}")
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        return CarViewHolder(
            CarItemBinding.inflate(
                LayoutInflater.from(parent.context), parent,false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CarViewHolder(private var binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(carItem: CarItem){
            binding.carName.text = carItem.name.toString()
        }
    }

}