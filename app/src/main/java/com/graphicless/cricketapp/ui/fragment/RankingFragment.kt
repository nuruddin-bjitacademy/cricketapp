package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.FragmentRankingContainerBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

class RankingFragment : Fragment() {


    private lateinit var _binding: FragmentRankingContainerBinding
    private val binding get() = _binding

    //    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
        exitTransition = MaterialElevationScale(/* growing = */ false)
        reenterTransition = MaterialElevationScale(/* growing = */ true)

        (activity as AppCompatActivity).supportActionBar?.title = MyConstants.MATCHES
        (activity as AppCompatActivity).supportActionBar?.elevation = 0f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRankingContainerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.takeIf { it.containsKey(MyConstants.CATEGORY_TAB_NUMBER) }?.apply {
            when (getInt(MyConstants.CATEGORY_TAB_NUMBER)) {

                0 -> {// Upcoming

                }
                1 -> {// Recent

                }
                2 -> {// Previous
                }
            }
        }
    }

}