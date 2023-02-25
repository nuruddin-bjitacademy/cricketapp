package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.adapter.SquadAdapter
import com.graphicless.cricketapp.adapter.SquadAllSeasonAdapter
import com.graphicless.cricketapp.databinding.FragmentTeamDetailsBinding
import com.graphicless.cricketapp.utils.Utils
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import com.graphicless.cricketapp.viewmodel.NetworkConnectionViewModel

private const val TAG = "TeamDetailsFragment"

class TeamDetailsFragment( private val teamId: Int) : Fragment() {


    private lateinit var _binding: FragmentTeamDetailsBinding
    private val binding get() = _binding

//    private val args: TeamDetailsFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()
    private val networkConnectionViewModel: NetworkConnectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val teamId = teamId

        viewModel.getTeamById(teamId).observe(requireActivity()) {
            (activity as AppCompatActivity).supportActionBar?.title =
                java.lang.StringBuilder(it.name).append(" Team Squad")
        }

        networkConnectionViewModel.isNetworkAvailable.observe(requireActivity()) { networkAvailable ->

            if (networkAvailable) {
                binding.tvNoData.visibility = View.GONE
                binding.progressbar.visibility = View.VISIBLE
                itemSelected("current")
                currentSquad(teamId)

                binding.tvCurrentSeason.setOnClickListener {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.recyclerView.adapter = null
                    itemSelected("current")
                    currentSquad(teamId)
                }
                binding.tvAll.setOnClickListener {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.recyclerView.adapter = null
                    itemSelected("all")
                    allSquad(teamId)
                }
            } else {
                binding.recyclerView.adapter = null
                binding.progressbar.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
                binding.tvCurrentSeason.setOnClickListener {
                    itemSelected("current")
                }
                binding.tvAll.setOnClickListener {
                    itemSelected("all")
                }
            }
        }
    }

    private fun itemSelected(item: String) {
        try {
            val unselectedBackground =
                ResourcesCompat.getDrawable(resources, R.drawable.boarder_rectangle_round, null)
            val unselectedTextColor =
                ResourcesCompat.getColor(resources, R.color.button_text_unselected, null)
            val selectedBackground = ResourcesCompat.getDrawable(
                resources,
                R.drawable.boarder_rectangle_round_solid,
                null
            )
            val selectedTextColor =
                ResourcesCompat.getColor(resources, R.color.button_text_selected, null)

            when (item) {
                "current" -> {
                    binding.tvCurrentSeason.background = selectedBackground
                    binding.tvCurrentSeason.setTextColor(selectedTextColor)
                    binding.tvAll.background = unselectedBackground
                    binding.tvAll.setTextColor(unselectedTextColor)
                }
                "all" -> {
                    binding.tvAll.background = selectedBackground
                    binding.tvAll.setTextColor(selectedTextColor)
                    binding.tvCurrentSeason.background = unselectedBackground
                    binding.tvCurrentSeason.setTextColor(unselectedTextColor)
                }
            }
        } catch (exception: java.lang.Exception) {
            Log.e(TAG, "itemSelected: $exception")
        }
    }

    private fun currentSquad(teamId: Int) {
        try {
            viewModel.getAllSeasonId("2023").observe(requireActivity()) {
                for (i in it.indices) {
                    viewModel.launchSquad(teamId, it[i])
                }
                viewModel.squad.observe(requireActivity()) {
                    if (it != null && it.isNotEmpty()) {
                        val adapter = SquadAdapter(it)
                        binding.recyclerView.adapter = adapter
                        if (binding.recyclerView.adapter?.itemCount == 0) {
                            binding.tvNoData.visibility = View.VISIBLE
                            binding.progressbar.visibility = View.GONE
                        }
                        viewModel.squad.removeObservers(requireActivity())
                        binding.progressbar.visibility = View.GONE
                    }
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, "$TAG get squad: $exception")
        }
    }

    private fun allSquad(teamId: Int) {
        viewModel.launchSquadAll(teamId)
        viewModel.squadAll.observe(requireActivity()) {
            val adapter = SquadAllSeasonAdapter(it)
            binding.recyclerView.adapter = adapter
            if (binding.recyclerView.adapter?.itemCount == 0) {
                binding.tvNoData.visibility = View.VISIBLE
                binding.progressbar.visibility = View.GONE
            }
            binding.progressbar.visibility = View.GONE
        }
    }
}