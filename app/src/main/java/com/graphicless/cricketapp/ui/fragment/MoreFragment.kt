package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.FragmentMoreBinding
import com.graphicless.cricketapp.databinding.FragmentRankingContainerBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

private const val TAG = "MoreFragment"
class MoreFragment : Fragment() {

    private lateinit var _binding: FragmentMoreBinding
    private val binding get() = _binding

    //    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
        exitTransition = MaterialElevationScale(/* growing = */ false)
        reenterTransition = MaterialElevationScale(/* growing = */ true)

        (activity as AppCompatActivity).supportActionBar?.title = MyConstants.MORE
        (activity as AppCompatActivity).supportActionBar?.elevation = 0f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.isNetworkAvailable.observe(viewLifecycleOwner) { isNetworkAvailable ->
            if (isNetworkAvailable) {
                // Network is available, update the UI as needed
                Log.d(TAG, "network available")
            } else {
                // Network is not available, update the UI as needed
                Log.d(TAG, "network not available")
            }
        }

        binding.browseTeam.setOnClickListener{
            val direction = MoreFragmentDirections.actionMoreFragmentToTeamContainerFragment()
            view.findNavController().navigate(direction)
        }
    }



}