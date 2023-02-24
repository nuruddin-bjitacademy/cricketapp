package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import com.graphicless.cricketapp.adapter.ViewPagerAdapter
import com.graphicless.cricketapp.databinding.FragmentMatchSummaryContainerBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

class MatchSummaryContainerFragment : Fragment() {

    private lateinit var _binding: FragmentMatchSummaryContainerBinding
    private val binding get() = _binding

    //    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()


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
    ): View? {
        _binding = FragmentMatchSummaryContainerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.takeIf { it.containsKey(MyConstants.CATEGORY_TAB_NUMBER) }?.apply {
            when (getInt(MyConstants.CATEGORY_TAB_NUMBER)) {

                0 -> {// Upcoming
                    val fragmentList: List<Fragment> = listOf(
                        MatchesFragment(),
                        MatchesFragment(),
                        MatchesFragment(),
                        MatchesFragment(),
                        MatchesFragment()
                    )
                    val tabNameList: List<String> = listOf("BPL", "IPL", "BBL", "ODI", "T20I")

                    val viewPagerAdapter = ViewPagerAdapter(this@MatchSummaryContainerFragment, fragmentList, null, "upcoming")

                    binding.viewPager.adapter = viewPagerAdapter
                    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                        tab.text = tabNameList[position]
                    }.attach()
                }
                1 -> {// Recent
                    val fragmentList: List<Fragment> = listOf(
                        MatchesFragment(),
                        MatchesFragment(),
                        MatchesFragment(),
                        MatchesFragment(),
                        MatchesFragment()
                    )
                    val tabNameList: List<String> = listOf("BPL", "IPL", "BBL", "ODI", "T20I")

                    val viewPagerAdapter = ViewPagerAdapter(this@MatchSummaryContainerFragment, fragmentList, null, "recent")

                    binding.viewPager.adapter = viewPagerAdapter
                    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                        tab.text = tabNameList[position]
                    }.attach()
                }
                2 -> {// Previous
                    val fragmentList: List<Fragment> = listOf(
                        MatchesFragment(),
                        MatchesFragment(),
                        MatchesFragment(),
                        MatchesFragment(),
                        MatchesFragment()
                    )
                    val tabNameList: List<String> = listOf("BPL", "IPL", "BBL", "ODI", "T20I")

                    val viewPagerAdapter = ViewPagerAdapter(this@MatchSummaryContainerFragment, fragmentList, null, "previous")

                    binding.viewPager.adapter = viewPagerAdapter
                    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                        tab.text = tabNameList[position]
                    }.attach()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewPager.adapter = null
    }
}
