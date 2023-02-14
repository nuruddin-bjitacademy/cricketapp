package com.graphicless.cricketapp.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.graphicless.cricketapp.utils.MyConstants

class ViewPagerAdapter(fragment: Fragment, private val  fragmentList: List<Fragment>, private val fixtureId: Int?) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        val fragment = fragmentList[position]
        fragment.arguments = Bundle().apply {
            putInt(MyConstants.CATEGORY_TAB_NUMBER, position)
            if (fixtureId != null) {
                putInt(MyConstants.FIXTURE_ID, fixtureId)
            }
        }
        return fragment
    }
}