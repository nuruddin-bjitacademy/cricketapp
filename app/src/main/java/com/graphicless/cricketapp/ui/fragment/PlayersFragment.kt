package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.FragmentNewsDetailsBinding
import com.graphicless.cricketapp.databinding.FragmentPlayersBinding
import com.graphicless.cricketapp.viewmodel.CricketViewModel

class PlayersFragment : Fragment() {

    private lateinit var _binding: FragmentPlayersBinding
    private val binding get() = _binding

    //    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()
    var seasonList = listOf<Int>()
    var nationalTeamList = mutableListOf<Int>()
    var localTeamList = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)

        return binding.root
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllSeasonId("2023").observe(requireActivity()){
            seasonList = it
        }

        viewModel.getAllTeam(1).observe(requireActivity()){
            for(i in it.indices){
                nationalTeamList.add(i, it[i].id)
            }
        }

        viewModel.getAllTeam(0).observe(requireActivity()){
            for(i in it.indices){
                localTeamList.add(i, it[i].id)
            }
        }

        if(seasonList.isNotEmpty()){
            for(i in seasonList.indices){
//                try {
//
//                }
            }
        }

    }*/

}