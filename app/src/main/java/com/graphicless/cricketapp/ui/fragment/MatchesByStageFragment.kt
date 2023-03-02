package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.graphicless.cricketapp.adapter.MatchAdapter2
import com.graphicless.cricketapp.databinding.FragmentMatchesByStageBinding
import com.graphicless.cricketapp.viewmodel.CricketViewModel

class MatchesByStageFragment : Fragment() {
    private lateinit var _binding: FragmentMatchesByStageBinding
    private val binding get() = _binding

        private val args: MatchesByStageFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesByStageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getStageNameById(args.stageId).observe(viewLifecycleOwner){
            binding.tvStage.text = it
        }

        viewModel.getFixturesByStageId(args.stageId).observe(requireActivity()){
            val adapter = MatchAdapter2(it, null, null, requireActivity())
            binding.recyclerView.adapter = adapter
        }
    }
}