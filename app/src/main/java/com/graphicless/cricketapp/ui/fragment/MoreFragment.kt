package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import com.graphicless.cricketapp.databinding.FragmentMoreBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.utils.SharedPreference
import com.graphicless.cricketapp.viewmodel.NetworkConnectionViewModel

private const val TAG = "MoreFragment"
class MoreFragment : Fragment() {

    private lateinit var _binding: FragmentMoreBinding
    private val binding get() = _binding

    private val netWorkConnectionViewModel: NetworkConnectionViewModel by activityViewModels()

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
    ): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        netWorkConnectionViewModel.isNetworkAvailable.observe(viewLifecycleOwner) { isNetworkAvailable ->
            if (isNetworkAvailable) {
                // Network is available, update the UI as needed
                Log.d(TAG, "network available")
            } else {
                // Network is not available, update the UI as needed
                Log.d(TAG, "network not available")
            }
        }

        val sharedPreference = SharedPreference()

        if (sharedPreference.getString("theme") != null) {
            val myTheme = sharedPreference.getString("theme")
            binding.switchTheme.isChecked = myTheme != "light"
        } else {
            binding.switchTheme.isChecked = false
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharedPreference.save("theme", "dark")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                sharedPreference.save("theme", "light")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.browseTeam.setOnClickListener{
            val direction = MoreFragmentDirections.actionMoreFragmentToTeamContainerFragment()
            view.findNavController().navigate(direction)
        }

        binding.browsePlayer.setOnClickListener {
            val direction = MoreFragmentDirections.actionMoreFragmentToPlayerListFragment()
            view.findNavController().navigate(direction)
        }
    }
}