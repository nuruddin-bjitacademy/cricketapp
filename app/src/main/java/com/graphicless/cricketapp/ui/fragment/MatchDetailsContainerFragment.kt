package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.graphicless.cricketapp.adapter.ViewPagerAdapter
import com.graphicless.cricketapp.databinding.FragmentMatchDetailsContainerBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

class MatchDetailsContainerFragment : Fragment() {

    private lateinit var _binding: FragmentMatchDetailsContainerBinding
    private val binding get() = _binding

    private val args: MatchDetailsContainerFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
        exitTransition = MaterialElevationScale(/* growing = */ false)
        reenterTransition = MaterialElevationScale(/* growing = */ true)

        (activity as AppCompatActivity).supportActionBar?.title = MyConstants.MATCH_DETAILS
        (activity as AppCompatActivity).supportActionBar?.elevation = 0f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailsContainerBinding.inflate(inflater, container, false)
        sharedElementEnterTransition = MaterialContainerTransform()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val fragmentList: List<Fragment> =
            listOf(MatchDetailsInfoFragment(), MatchDetailsSquadFragment(), MatchDetailsScoreCardFragment(), MatchDetailsOverFragment())
        val tabNameList: List<String> = listOf("Info", "Squad", "Score Card", "Over")

        val fixtureId = args.fixtureId

        val adapter = ViewPagerAdapter(this, fragmentList, fixtureId)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabNameList[position]
        }.attach()
    }

}
