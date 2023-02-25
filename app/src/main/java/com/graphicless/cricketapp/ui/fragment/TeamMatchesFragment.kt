package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.graphicless.cricketapp.adapter.FixturesByTeamIdAdapter
import com.graphicless.cricketapp.databinding.FragmentTeamMatchesBinding
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import com.graphicless.cricketapp.viewmodel.NetworkConnectionViewModel

private const val TAG = "TeamMatchesFragment"
class TeamMatchesFragment(private val teamId: Int) : Fragment() {

    private lateinit var _binding: FragmentTeamMatchesBinding
    private val binding get() = _binding

    //    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "team id : $teamId")
        viewModel.launchFixturesByTeamId(teamId)
        viewModel.fixturesByTeamId.observe(requireActivity()){
            val adapter = FixturesByTeamIdAdapter(it.data?.fixtures, requireActivity())
            binding.recyclerView.adapter = adapter
            binding.progressbar.visibility = View.GONE
            if(binding.recyclerView.adapter?.itemCount == 0){
                binding.tvNoData.visibility = View.VISIBLE
            }
            viewModel.fixturesByTeamId.removeObservers(requireActivity())
        }
    }

}