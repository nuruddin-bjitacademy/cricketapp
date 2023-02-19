package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.adapter.MatchDetailsBallerAdapter
import com.graphicless.cricketapp.adapter.MatchDetailsBatsManAdapter
import com.graphicless.cricketapp.databinding.FragmentMatchDetailsScoreCardBinding
import com.graphicless.cricketapp.temp.FixtureScoreCard
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import retrofit2.HttpException

private const val TAG = "MatchDetailsScoreCardFragment"

class MatchDetailsScoreCardFragment : Fragment() {

    private lateinit var _binding: FragmentMatchDetailsScoreCardBinding
    private val binding get() = _binding

    private val args: MatchDetailsContainerFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()

    private lateinit var matchDetailsBatsManAdapter: MatchDetailsBatsManAdapter
    private lateinit var matchDetailsBallerAdapter: MatchDetailsBallerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailsScoreCardBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        matchDetailsBatsManAdapter = MatchDetailsBatsManAdapter(this)
        binding.recyclerView.adapter = matchDetailsBatsManAdapter

        binding.recyclerViewBaller.layoutManager = LinearLayoutManager(context)
        matchDetailsBallerAdapter = MatchDetailsBallerAdapter(this)
        binding.recyclerViewBaller.adapter = matchDetailsBallerAdapter

        val loadingView =
            LayoutInflater.from(context).inflate(R.layout.layout_loading, binding.container, false)
        binding.container.addView(loadingView)

        binding.scrollView.visibility = View.GONE

        fetchDataFromApi()

        return binding.root
    }


    private fun fetchDataFromApi(){

        arguments?.takeIf { it.containsKey(MyConstants.FIXTURE_ID) }?.apply {

            val fixtureId: Int = getInt(MyConstants.FIXTURE_ID)

            viewModel.launchFixtureScoreCard(fixtureId)

            viewModel.fixtureScoreCard.observe(viewLifecycleOwner) {

                val teamOneId = it.data?.localteamId
                val teamTwoId = it.data?.visitorteamId


                if (it.data?.runs?.size!! == 2) {

                    binding.scrollView.visibility = View.VISIBLE

                    try {
                        if (teamOneId != null) {
                            val teamOneTotalRun =
                                if (it.data?.runs?.get(0)?.teamId == teamOneId) it.data?.runs?.get(
                                    0
                                )?.score else it.data?.runs?.get(
                                    1
                                )?.score
                            val teamOneWickets =
                                if (it.data?.runs?.get(0)?.teamId == teamOneId) it.data?.runs?.get(
                                    0
                                )?.wickets else it.data?.runs?.get(
                                    1
                                )?.wickets
                            val teamOneOvers =
                                if (it.data?.runs?.get(0)?.teamId == teamOneId) it.data?.runs?.get(
                                    0
                                )?.overs else it.data?.runs?.get(
                                    1
                                )?.overs

                            viewModel.getLocalTeamById(teamOneId)
                                .observe(requireActivity()) { team ->
                                    binding.teamOne.text =
                                        team.code.plus(" [ ").plus(teamOneTotalRun).plus("-")
                                            .plus(teamOneWickets).plus(" (").plus(teamOneOvers)
                                            .plus(") ]")
                                }
                        }
                    } catch (exception: Exception) {
                        Log.e("error", "ex mdof team one info $exception")
                    }

                    try {
                        if (teamTwoId != null) {
                            val teamTwoTotalRun =
                                if (it.data?.runs?.get(0)?.teamId == teamTwoId) it.data?.runs?.get(
                                    0
                                )?.score else it.data?.runs?.get(
                                    1
                                )?.score
                            val teamTwoWickets =
                                if (it.data?.runs?.get(0)?.teamId == teamTwoId) it.data?.runs?.get(
                                    0
                                )?.wickets else it.data?.runs?.get(
                                    1
                                )?.wickets
                            val teamTwoOvers =
                                if (it.data?.runs?.get(0)?.teamId == teamTwoId) it.data?.runs?.get(
                                    0
                                )?.overs else it.data?.runs?.get(
                                    1
                                )?.overs
                            viewModel.getVisitorTeamById(teamTwoId)
                                .observe(requireActivity()) { team ->
                                    binding.teamTwo.text =
                                        team.code.plus(" [ ").plus(teamTwoTotalRun).plus("-")
                                            .plus(teamTwoWickets).plus(" (").plus(teamTwoOvers)
                                            .plus(") ]")
                                }
                        }
                    } catch (exception: Exception) {
                        Log.e("error", "ex mdof team two info $exception")
                    }

                    showInfo(true, it)

                    binding.teamOne.setOnClickListener { _ ->
                        showInfo(true, it)

                    }

                    binding.teamTwo.setOnClickListener { _ ->
                        showInfo(false, it)

                    }

                    try {
                        binding.container.removeViewAt(0)
                    } catch (exception: Exception) {
                        Log.e(TAG, "remove view from container: $exception")
                    }

                }else {
                    try {
                        binding.container.removeViewAt(0)
                    } catch (exception: Exception) {
                        Log.e(TAG, "remove view from container: $exception")
                    }
                    val noDataView = LayoutInflater.from(context)
                        .inflate(R.layout.layout_no_data, binding.container, false)
                    binding.container.addView(noDataView)
                }
            }

        }

    }

    //    private fun showInfo(teamOneSelected: Boolean, batting: List<FixtureDetailsById.Data.Batting?>?, teamOneId: Int, teamTwoId: Int ) {
    private fun showInfo(teamOneSelected: Boolean, fixtureDetails: FixtureScoreCard) {

        val batting = fixtureDetails.data?.batting
        val bowling = fixtureDetails.data?.bowling
        val teamOneId = fixtureDetails.data?.localteamId
        val teamTwoId = fixtureDetails.data?.visitorteamId


        if (teamOneSelected) {
            binding.teamTwo.background =
                ResourcesCompat.getDrawable(resources, R.drawable.boarder_rectangle_round, null)
            binding.teamOne.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.boarder_rectangle_round_solid,
                null
            )
            val teamOneBatting = batting?.filter { it ->
                it?.teamId == teamOneId
            }
            matchDetailsBatsManAdapter.setPlayerStatisticsList(teamOneBatting)

            val teamOneBowling = bowling?.filter {
                it?.teamId == teamOneId
            }

            matchDetailsBallerAdapter.setBallerList(teamOneBowling)


        } else {
            binding.teamOne.background =
                ResourcesCompat.getDrawable(resources, R.drawable.boarder_rectangle_round, null)
            binding.teamTwo.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.boarder_rectangle_round_solid,
                null
            )
            val teamTwoBatting = batting?.filter { it ->
                it?.teamId == teamTwoId
            }
            matchDetailsBatsManAdapter.setPlayerStatisticsList(teamTwoBatting)

            val teamTwoBowling = bowling?.filter {
                it?.teamId == teamTwoId
            }

            matchDetailsBallerAdapter.setBallerList(teamTwoBowling)
        }
    }
}
