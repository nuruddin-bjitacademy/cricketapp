package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import com.graphicless.cricketapp.adapter.TeamRankingAdapter
import com.graphicless.cricketapp.databinding.FragmentRankingBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

private const val TAG = "RankingFragment"

class RankingFragment : Fragment() {

    private lateinit var _binding: FragmentRankingBinding
    private val binding get() = _binding

    private val viewModel: CricketViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
        exitTransition = MaterialElevationScale(/* growing = */ false)
        reenterTransition = MaterialElevationScale(/* growing = */ true)

        (activity as AppCompatActivity).supportActionBar?.title = MyConstants.TEAM_RANKINGS
        (activity as AppCompatActivity).supportActionBar?.elevation = 0f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.takeIf { it.containsKey(MyConstants.COMING_FROM) }?.apply {
            Log.d(TAG, "onViewCreated: coming from: ${getString(MyConstants.COMING_FROM)}")
            when (getString(MyConstants.COMING_FROM)) {
                "odi" -> {
                    arguments?.takeIf { it.containsKey(MyConstants.CATEGORY_TAB_NUMBER) }?.apply {
                        when (getInt(MyConstants.CATEGORY_TAB_NUMBER)) {

                            0 -> {// Men
                                viewModel.getTeamRankings("ODI", "men").observe(requireActivity()) {
                                    Log.d(TAG, "odi men: $it")
                                    val adapter = TeamRankingAdapter(it)
                                    binding.recyclerView.adapter = adapter
                                    binding.progressbar.visibility = View.GONE
                                    if (binding.recyclerView.adapter?.itemCount == 0) {
                                        binding.tvNoData.visibility = View.VISIBLE
                                    }
                                }
                            }
                            1 -> {// Women
                                viewModel.getTeamRankings("ODI", "women")
                                    .observe(requireActivity()) {
                                        Log.d(TAG, "odi men: $it")
                                        val adapter = TeamRankingAdapter(it)
                                        binding.recyclerView.adapter = adapter
                                        binding.progressbar.visibility = View.GONE
                                        if (binding.recyclerView.adapter?.itemCount == 0) {
                                            binding.tvNoData.visibility = View.VISIBLE
                                        }
                                    }
                            }
                        }
                    }
                }
                "t20" -> {
                    arguments?.takeIf { it.containsKey(MyConstants.CATEGORY_TAB_NUMBER) }?.apply {
                        when (getInt(MyConstants.CATEGORY_TAB_NUMBER)) {

                            0 -> {// Men
                                viewModel.getTeamRankings("T20I", "men")
                                    .observe(requireActivity()) {
                                        Log.d(TAG, "T20 men: $it")
                                        val adapter = TeamRankingAdapter(it)
                                        binding.recyclerView.adapter = adapter
                                        binding.progressbar.visibility = View.GONE
                                        if (binding.recyclerView.adapter?.itemCount == 0) {
                                            binding.tvNoData.visibility = View.VISIBLE
                                        }
                                    }
                            }
                            1 -> {// Women
                                viewModel.getTeamRankings("T20I", "women")
                                    .observe(requireActivity()) {
                                        Log.d(TAG, "T20 women: $it")
                                        val adapter = TeamRankingAdapter(it)
                                        binding.recyclerView.adapter = adapter
                                        binding.progressbar.visibility = View.GONE
                                        if (binding.recyclerView.adapter?.itemCount == 0) {
                                            binding.tvNoData.visibility = View.VISIBLE
                                        }
                                    }
                            }
                        }
                    }
                }
                "test" -> {
                    arguments?.takeIf { it.containsKey(MyConstants.CATEGORY_TAB_NUMBER) }?.apply {
                        when (getInt(MyConstants.CATEGORY_TAB_NUMBER)) {

                            0 -> {// Men
                                viewModel.getTeamRankings("TEST", "men")
                                    .observe(requireActivity()) {
                                        Log.d(TAG, "TEST men: $it")
                                        val adapter = TeamRankingAdapter(it)
                                        binding.recyclerView.adapter = adapter
                                        binding.progressbar.visibility = View.GONE
                                        if (binding.recyclerView.adapter?.itemCount == 0) {
                                            binding.tvNoData.visibility = View.VISIBLE
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        }
    }
}