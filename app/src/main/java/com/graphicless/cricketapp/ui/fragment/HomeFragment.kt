package com.graphicless.cricketapp.ui.fragment

import android.app.ActionBar
import android.app.Activity
import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.graphicless.cricketapp.databinding.FragmentHomeBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

private const val TAG = "HomeFragment"
class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

//    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as AppCompatActivity).supportActionBar?.title = MyConstants.HOME

        /*viewModel.getDistinctStageName.observe(requireActivity()){
            val adapter = StageAdapter(it, this)
            binding.recyclerView.adapter = adapter
        }*/
        /*viewModel.getDistinctStages.observe(requireActivity()){
            Log.d(TAG, "list<DistinctStages: ${it.size}")
            val adapter = StageAdapter(it, this)
            binding.recyclerView.adapter = adapter
        }*/

    }
}