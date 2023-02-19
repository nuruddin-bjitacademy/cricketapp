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
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.adapter.OverAdapter
import com.graphicless.cricketapp.databinding.FragmentMatchDetailsOverBinding
import com.graphicless.cricketapp.temp.FixtureOver
import com.graphicless.cricketapp.utils.MyApplication
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import retrofit2.HttpException

private const val TAG = "MatchDetailsOverFragment"

class MatchDetailsOverFragment : Fragment() {

    private lateinit var _binding: FragmentMatchDetailsOverBinding
    private val binding get() = _binding

    private val args: MatchDetailsContainerFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailsOverBinding.inflate(inflater, container, false)
        val loadingView =
            LayoutInflater.from(context).inflate(R.layout.layout_loading, binding.container, false)
        binding.container.addView(loadingView)
        fetchDataFromApi()
        return binding.root
    }

    private fun fetchDataFromApi() {

//        binding.container.visibility =View.VISIBLE

        arguments?.takeIf { it.containsKey(MyConstants.FIXTURE_ID) }?.apply {

            val fixtureId: Int = getInt(MyConstants.FIXTURE_ID)

            Log.d(TAG, "fixture id : $fixtureId")

            viewModel.launchFixtureOver(fixtureId)

            viewModel.fixtureOver.observe(viewLifecycleOwner) {

                if (it?.data?.runs?.size!! == 2) {

                    val teamOneId = it.data?.localteamId
                    val teamTwoId = it.data?.visitorteamId

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

                    // removes the view of the container otherwise it will update the view continuously
                    try {
                        binding.container.removeViewAt(0)
                    } catch (exception: Exception) {
                        Log.e(TAG, "remove view from container: $exception")
                    }

                } else {
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

//        binding.container.removeViewAt(0)
//        val contentView = LayoutInflater.from(context)
//            .inflate(R.layout.fragment_match_details_over, binding.container, false)
//        binding.container.addView(contentView)
    }
    // end of onCreate

    private fun showInfo(teamOneSelected: Boolean, it: FixtureOver?) {
        val teamOneId = it?.data?.localteamId
        val teamTwoId = it?.data?.visitorteamId
        val balls: MutableList<Double> = mutableListOf()

        if (teamOneSelected) {
            binding.teamTwo.background =
                ResourcesCompat.getDrawable(resources, R.drawable.boarder_rectangle_round, null)
            binding.teamOne.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.boarder_rectangle_round_solid,
                null
            )
            val teamOneOver = it?.data?.balls?.filter { ball ->
                ball?.teamId == teamOneId
            }
            Log.d(TAG, "team one over: ${it?.data?.balls?.size}")
            if (teamOneOver != null) {
                for (ball in teamOneOver.indices) {
                    teamOneOver[ball]?.let { it1 ->
                        it1.ball?.let { it2 ->
                            balls.add(
                                ball,
                                it2
                            )
                        }
                    }
                }
                showOversWithBalls(balls, teamOneOver)
            }
        } else {
            binding.teamOne.background =
                ResourcesCompat.getDrawable(resources, R.drawable.boarder_rectangle_round, null)
            binding.teamTwo.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.boarder_rectangle_round_solid,
                null
            )
            val teamTwoOver = it?.data?.balls?.filter { ball ->
                ball?.teamId == teamTwoId
            }
            if (teamTwoOver != null) {
                for (ball in teamTwoOver.indices) {
                    teamTwoOver[ball]?.let { it1 ->
                        it1.ball?.let { it2 ->
                            balls.add(
                                ball,
                                it2
                            )
                        }
                    }
                }
                showOversWithBalls(balls, teamTwoOver)
            }
        }

    }

    private fun showOversWithBalls(
        balls: List<Double>,
        teamOneOver: List<FixtureOver.Data.Ball?>
    ) {
        val groupedBalls = groupBallsByIntPart(balls)
        Log.d(TAG, "group balls: $groupedBalls")
        val adapter = OverAdapter(groupedBalls, teamOneOver)
        binding.rvOver.adapter = adapter
        groupedBalls.forEach { (intPart, ballsWithSameIntPart) ->
            Log.d(
                TAG,
                "Balls with integer part $intPart: ${ballsWithSameIntPart.joinToString(", ")}"
            )
        }
    }

    private fun groupBallsByIntPart(balls: List<Double>): Map<Int, List<Double>> {
        return balls.groupBy { ball -> ball.toInt() }
    }

}
