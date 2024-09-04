package com.example.demoapp.domain.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demoapp.ui.realEsate.RealEstateFragment
import com.example.demoapp.ui.vehicle.VehicleFragment


class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> RealEstateFragment()
           1 -> VehicleFragment()
           else -> RealEstateFragment()
       }
    }
}