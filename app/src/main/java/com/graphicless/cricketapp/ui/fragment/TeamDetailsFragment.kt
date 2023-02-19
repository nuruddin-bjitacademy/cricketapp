package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.adapter.SquadAdapter
import com.graphicless.cricketapp.adapter.TeamsAdapter
import com.graphicless.cricketapp.databinding.FragmentNewsDetailsBinding
import com.graphicless.cricketapp.databinding.FragmentTeamDetailsBinding
import com.graphicless.cricketapp.temp.SquadByTeamAndSeason
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "TeamDetailsFragment"
class TeamDetailsFragment : Fragment() {


    private lateinit var _binding: FragmentTeamDetailsBinding
    private val binding get() = _binding

        private val args: TeamDetailsFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val teamId = args.teamId

       try {
            viewModel.getAllSeasonId("2023").observe(requireActivity()) {
                Log.d(TAG, "List of season id : $it")
                for (i in it.indices) {
                    viewModel.launchSquad(teamId, it[i])
                }
            }
        }catch (exception: Exception){
           Log.e(TAG, "tdf get squad: $exception", )
        }

        viewModel.squad.observe(requireActivity()){
            if(it!=null && it.isNotEmpty()){
                val adapter = SquadAdapter(it)
                binding.recyclerView.adapter = adapter
                viewModel.squad.removeObservers(requireActivity())
            }
        }



    }
}