package com.example.demoapp.domain.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demoapp.R
import com.example.demoapp.databinding.ItemPropertyBinding
import com.example.demoapp.domain.model.House
import com.example.demoapp.domain.model.ResultHouses

class HouseAdapter (
    private val onClick: (ResultHouses) -> Unit
): RecyclerView.Adapter<HouseAdapter.HouseViewHolder>() {

    private var items = arrayListOf<ResultHouses>()

    fun clearHouses() {
        items.clear()
        notifyDataSetChanged()
    }

    fun addHouses(list: List<ResultHouses>) {
        Log.d("HouseAdapter", "Items received: ${list.size}")
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HouseAdapter.HouseViewHolder {
        return HouseViewHolder(
            ItemPropertyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HouseAdapter.HouseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class HouseViewHolder(private var binding: ItemPropertyBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(resultHouses: ResultHouses) {
            Log.d("HouseAdapter", "Binding house: ${resultHouses.imgSrc}, ${resultHouses.price}, ${resultHouses.livingArea}, ${resultHouses.streetAddress}")

            // Check if imgSrc is not null and not empty
            if (!resultHouses.imgSrc.isNullOrEmpty()) {
                Glide.with(binding.root)
                    .load(resultHouses.imgSrc)
                    .into(binding.propertyImage)
            } else {
                // Set a placeholder or hide the image view if imgSrc is null
                binding.propertyImage.setImageResource(R.drawable.green_bg2)
            }

            // Check if price is valid
            binding.propertyPrice.text = if (resultHouses.price > 0) resultHouses.price.toString() else "N/A"

            // Check if livingArea is valid
            binding.propertySize.text = if (resultHouses.livingArea > 0) resultHouses.livingArea.toString() else "N/A"

            // Check if streetAddress is not null
            binding.propertyLocation.text = resultHouses.streetAddress ?: "No address available"

            binding.root.setOnClickListener {
                onClick(resultHouses)
            }
        }

    }
}