package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.graphicless.cricketapp.databinding.FragmentLiveMatchDetailsInfoBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import com.graphicless.cricketapp.viewmodel.NetworkConnectionViewModel

private const val TAG = "LiveMatchDetailsInfoFragment"

class LiveMatchDetailsInfoFragment : Fragment() {

    private lateinit var _binding: FragmentLiveMatchDetailsInfoBinding
    private val binding get() = _binding

    private val args: MatchDetailsContainerFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()
    private val networkConnectionViewModel: NetworkConnectionViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLiveMatchDetailsInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(MyConstants.FIXTURE_ID) }?.apply {

            val fixtureId: Int = getInt(MyConstants.FIXTURE_ID)

            networkConnectionViewModel.isNetworkAvailable.observe(requireActivity()) { isNetworkAvailable ->
                if (isNetworkAvailable) {
                    binding.tvNoData.visibility = View.GONE
                    viewModel.launchLiveMatchInfo(fixtureId)
                    try {
                        viewModel.liveMatchInfo.observe(requireActivity()) {
                            if (it != null) {
                                binding.series.text =
                                    it.data?.stage?.name ?: MyConstants.NOT_AVAILABLE
                                binding.match.text =
                                    it.data?.localteam?.code.plus(" vs ")
                                        .plus(it.data?.visitorteam?.code)
                                binding.matchNo.text = it.data?.round
                                binding.localTeam.text =
                                    it.data?.localteam?.name ?: MyConstants.NOT_AVAILABLE
                                binding.visitorTeam.text =
                                    it.data?.visitorteam?.name ?: MyConstants.NOT_AVAILABLE
                                binding.status.text = it.data?.status ?: MyConstants.NOT_AVAILABLE
                                binding.toss.text =
                                    it.data?.tosswon?.name.plus(" chose ").plus(it.data?.elected)
                                        .plus(" first.")
                                binding.stadium.text =
                                    it.data?.venue?.name ?: MyConstants.NOT_AVAILABLE
                                binding.location.text =
                                    it.data?.venue?.city ?: MyConstants.NOT_AVAILABLE
                                binding.capacity.text =
                                    if (it.data?.venue?.capacity != null) it.data?.venue?.capacity.toString() else MyConstants.NOT_AVAILABLE
                                binding.floodLight.text =
                                    if (it.data?.venue?.floodlight == true) "Yes" else "No"
                                binding.firstUmpire.text =
                                    it.data?.firstumpire?.fullname ?: MyConstants.NOT_AVAILABLE
                                binding.secondUmpire.text =
                                    it.data?.secondumpire?.fullname ?: MyConstants.NOT_AVAILABLE
                                binding.tvUmpire.text =
                                    it.data?.tvumpire?.fullname ?: MyConstants.NOT_AVAILABLE
                                binding.referee.text =
                                    it.data?.referee?.fullname ?: MyConstants.NOT_AVAILABLE
                            }

                        }
                    } catch (exception: Exception) {
                        Log.e(TAG, "Live data observe: $exception")
                    }
                } else {
                    binding.tvNoData.visibility = View.VISIBLE
                }
            }
        }
    }
}