package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.adapter.TeamsAdapter
import com.graphicless.cricketapp.databinding.FragmentTeamBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

private const val TAG = "TeamFragment"

class TeamFragment : Fragment() {
    private lateinit var _binding: FragmentTeamBinding
    private val binding get() = _binding

    private val viewModel: CricketViewModel by viewModels()

    // This is for searching
    var national = 1

    private lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        exitTransition = Hold()
        exitTransition = MaterialElevationScale(/* growing = */ false)
        reenterTransition = MaterialElevationScale(/* growing = */ true)

        (activity as AppCompatActivity).supportActionBar?.title = MyConstants.TEAMS_LABEL
        (activity as AppCompatActivity).supportActionBar?.elevation = 0f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.takeIf { it.containsKey(MyConstants.CATEGORY_TAB_NUMBER) }?.apply {
            when (getInt(MyConstants.CATEGORY_TAB_NUMBER)) {

                0 -> {
                    viewModel.getAllTeam(1).observe(requireActivity()) {
                        val adapter = TeamsAdapter(it, 1, requireActivity())
                        binding.recyclerView.adapter = adapter
                        binding.progressbar.visibility = View.GONE
                    }
                    national = 1
                }
                1 -> {
                    viewModel.getAllTeam(0).observe(requireActivity()) {
                        val adapter = TeamsAdapter(it, 0, requireActivity())
                        binding.recyclerView.adapter = adapter
                        binding.progressbar.visibility = View.GONE
                    }
                    national = 0
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setIconifiedByDefault(false)
        searchView.isSubmitButtonEnabled = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission
                Log.d(TAG, "onQueryTextSubmit: $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search query text changes
                Log.d(TAG, "onQueryTextChange: $newText")
                if (newText != null && newText != "") {
                    viewModel.getAllTeamByQuery(national, newText).observe(requireActivity()) {
                        val adapter = TeamsAdapter(it, national, requireActivity())
                        binding.recyclerView.adapter = adapter
                    }
                }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                if (!searchView.isIconified) {
                    searchView.setQuery("", false)
                    searchView.isIconified = true
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_menu).visibility =
            View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        super.onPause()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_menu).visibility =
            View.VISIBLE
    }
}