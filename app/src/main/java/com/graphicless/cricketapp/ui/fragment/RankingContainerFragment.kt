package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import com.graphicless.cricketapp.adapter.ViewPagerAdapter
import com.graphicless.cricketapp.databinding.FragmentRankingContainerBinding
import com.graphicless.cricketapp.utils.MyConstants

class RankingContainerFragment : Fragment() {

    private lateinit var _binding: FragmentRankingContainerBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
        exitTransition = MaterialElevationScale(/* growing = */ false)
        reenterTransition = MaterialElevationScale(/* growing = */ true)

        (activity as AppCompatActivity).supportActionBar?.title = MyConstants.MATCHES
        (activity as AppCompatActivity).supportActionBar?.elevation = 0f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankingContainerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.takeIf { it.containsKey(MyConstants.CATEGORY_TAB_NUMBER) }?.apply {
            when (getInt(MyConstants.CATEGORY_TAB_NUMBER)) {

                0 -> {// ODI
                    val fragmentList: List<Fragment> = listOf(RankingFragment(), RankingFragment())
                    val tabNameList: List<String> = listOf("Men", "Women")

                    val viewPagerAdapter =
                        ViewPagerAdapter(this@RankingContainerFragment, fragmentList, null, "odi")

                    binding.viewPager.adapter = viewPagerAdapter
                    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                        tab.text = tabNameList[position]
                    }.attach()
                }
                1 -> {// T20
                    val fragmentList: List<Fragment> = listOf(RankingFragment(), RankingFragment())
                    val tabNameList: List<String> = listOf("Men", "Women")

                    val viewPagerAdapter =
                        ViewPagerAdapter(this@RankingContainerFragment, fragmentList, null, "t20")

                    binding.viewPager.adapter = viewPagerAdapter
                    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                        tab.text = tabNameList[position]
                    }.attach()
                }
                2 -> {// TEST
                    val fragmentList: List<Fragment> = listOf(RankingFragment())
                    val tabNameList: List<String> = listOf("Men")

                    val viewPagerAdapter =
                        ViewPagerAdapter(this@RankingContainerFragment, fragmentList, null, "test")

                    binding.viewPager.adapter = viewPagerAdapter
                    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                        tab.text = tabNameList[position]
                    }.attach()
                }
            }
        }
    }
}