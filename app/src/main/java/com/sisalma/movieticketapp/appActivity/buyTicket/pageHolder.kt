package com.sisalma.movieticketapp.appActivity.buyTicket

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class pageHolder(activity: AppCompatActivity, fragmentArrayList: ArrayList<Fragment>): FragmentStateAdapter(activity) {
    val fragmentArrayList = fragmentArrayList

    override fun getItemCount(): Int {
        return fragmentArrayList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentArrayList[position]
    }
}