package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.adapter.StageAdapter
import com.graphicless.cricketapp.databinding.FragmentMatchesBinding
import com.graphicless.cricketapp.utils.MyApplication
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

private const val TAG = "MatchesFragment"

class MatchesFragment : Fragment() {

    private lateinit var _binding: FragmentMatchesBinding
    private val binding get() = _binding

    //    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        exitTransition = Hold()
//        exitTransition = MaterialElevationScale(/* growing = */ false)
//        reenterTransition = MaterialElevationScale(/* growing = */ true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(MyConstants.CATEGORY_TAB_NUMBER) }?.apply {
            when (getInt(MyConstants.CATEGORY_TAB_NUMBER)) {
                0 -> {
                    itemSelected("t20i")
                    upcomingMatchSummaryByLeagueId(3)

                    binding.tvBbc.setOnClickListener {
                        itemSelected("bbc")
                        upcomingMatchSummaryByLeagueId(5)
                    }
                    binding.tvT20i.setOnClickListener {
                        itemSelected("t20i")
                        upcomingMatchSummaryByLeagueId(3)
                    }
                    binding.tvT20c.setOnClickListener {
                        itemSelected("t20c")
                        upcomingMatchSummaryByLeagueId(10)
                    }
                }
                1 -> {

                    itemSelected("t20i")
                    previousMatchSummaryByLeagueId(3)

                    binding.tvBbc.setOnClickListener {
                        itemSelected("bbc")
                        previousMatchSummaryByLeagueId(5)
                    }
                    binding.tvT20i.setOnClickListener {
                        itemSelected("t20i")
                        previousMatchSummaryByLeagueId(3)
                    }
                    binding.tvT20c.setOnClickListener {
                        itemSelected("t20c")
                        previousMatchSummaryByLeagueId(10)
                    }
                }
            }
        }
    }

    private fun itemSelected(item: String) {
        val unselectedBackground = ResourcesCompat.getDrawable(resources, R.drawable.boarder_rectangle, null)
        val unselectedTextColor = ResourcesCompat.getColor(resources,R.color.tab_text, null)
        val selectedBackground = ResourcesCompat.getColor(resources,R.color.action_bar, null)
        val selectedTextColor = ResourcesCompat.getColor(resources,R.color.tab_text_selected, null)
        when (item) {
            "t20i" -> {
                binding.tvT20i.setBackgroundColor(selectedBackground)
                binding.tvT20i.setTextColor(selectedTextColor)
                binding.tvBbc.background = unselectedBackground
                binding.tvBbc.setTextColor(unselectedTextColor)
                binding.tvT20c.background = unselectedBackground
                binding.tvT20c.setTextColor(unselectedTextColor)
            }
            "bbc" -> {
                binding.tvBbc.setBackgroundColor(selectedBackground)
                binding.tvBbc.setTextColor(selectedTextColor)
                binding.tvT20i.background = unselectedBackground
                binding.tvT20i.setTextColor(unselectedTextColor)
                binding.tvT20c.background = unselectedBackground
                binding.tvT20c.setTextColor(unselectedTextColor)
            }
            "t20c" -> {
                binding.tvT20c.setBackgroundColor(selectedBackground)
                binding.tvT20c.setTextColor(selectedTextColor)
                binding.tvBbc.background = unselectedBackground
                binding.tvBbc.setTextColor(unselectedTextColor)
                binding.tvT20i.background = unselectedBackground
                binding.tvT20i.setTextColor(unselectedTextColor)
            }
        }
    }

    private fun upcomingMatchSummaryByLeagueId(leagueId: Int){
        viewModel.getUpcomingMatchSummaryByLeagueId(leagueId).observe(requireActivity()) {
            Log.d(TAG, "getUpcomingMatchSummaryByLeagueId total: ${it.size}")
            Log.d(TAG, "getUpcomingMatchSummaryByLeagueIds: $it")
            val adapter = StageAdapter(it, requireActivity(), MyApplication.instance)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun previousMatchSummaryByLeagueId(leagueId: Int){
        viewModel.getPreviousMatchSummaryByLeagueId(leagueId).observe(requireActivity()) {
            Log.d(TAG, "previousMatchSummaryByLeagueId total: ${it.size}")
            val adapter = StageAdapter(it, requireActivity(), MyApplication.instance)
            binding.recyclerView.adapter = adapter
        }
    }

}