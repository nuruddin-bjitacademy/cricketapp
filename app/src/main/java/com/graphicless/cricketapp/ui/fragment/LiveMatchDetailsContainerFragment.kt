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
import com.graphicless.cricketapp.Model.LiveMatchInfo
import com.graphicless.cricketapp.adapter.ViewPagerAdapter
import com.graphicless.cricketapp.databinding.FragmentMatchDetailsContainerBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import com.graphicless.cricketapp.viewmodel.NetworkConnectionViewModel

class LiveMatchDetailsContainerFragment : Fragment() {

    private lateinit var _binding: FragmentMatchDetailsContainerBinding
    private val binding get() = _binding

    private val args: MatchDetailsContainerFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()
    private val networkConnectionViewModel: NetworkConnectionViewModel by activityViewModels()

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

        networkConnectionViewModel.isNetworkAvailable.observe(requireActivity()){
            if(it){
                val fragmentList: List<Fragment> =
                    listOf(MatchDetailsLiveFragment(), LiveMatchDetailsInfoFragment(), MatchDetailsSquadFragment(), MatchDetailsScoreCardFragment(), MatchDetailsOverFragment())
                val tabNameList: List<String> = listOf("Live", "Info", "Squad", "Score Card", "Over")

                val fixtureId = args.fixtureId

                val adapter = ViewPagerAdapter(this, fragmentList, fixtureId, "live")
                binding.viewPager.adapter = adapter

                TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                    tab.text = tabNameList[position]
                }.attach()
            }else{

            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewPager.adapter = null
    }

}
