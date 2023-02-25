package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.graphicless.cricketapp.adapter.ViewPagerAdapter
import com.graphicless.cricketapp.databinding.FragmentTeamDetailsContainerBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.utils.Utils
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import com.graphicless.cricketapp.viewmodel.NetworkConnectionViewModel

class TeamDetailsContainerFragment : Fragment() {

    private lateinit var _binding: FragmentTeamDetailsContainerBinding
    private val binding get() = _binding

    private val args: TeamDetailsContainerFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()
    private val networkConnectionViewModel: NetworkConnectionViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
        exitTransition = MaterialElevationScale(/* growing = */ false)
        reenterTransition = MaterialElevationScale(/* growing = */ true)

        (activity as AppCompatActivity).supportActionBar?.title = MyConstants.TEAM_DETAILS
        (activity as AppCompatActivity).supportActionBar?.elevation = 0f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamDetailsContainerBinding.inflate(inflater, container, false)
        sharedElementEnterTransition = MaterialContainerTransform()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        networkConnectionViewModel.isNetworkAvailable.observe(requireActivity()){networkAvailable->
            if(networkAvailable){
                val fragmentList: List<Fragment> =
                    listOf(TeamMatchesFragment(args.teamId), TeamDetailsFragment(args.teamId))
                val tabNameList: List<String> = listOf("Matches", "Squad")

                val fixtureId = args.teamId

                val adapter = ViewPagerAdapter(this, fragmentList, fixtureId, null)
                binding.viewPager.adapter = adapter

                TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                    tab.text = tabNameList[position]
                }.attach()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewPager.adapter = null
    }

}