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

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(MyConstants.FIXTURE_ID) }?.apply {
            val fixtureId: Int = getInt(MyConstants.FIXTURE_ID)

            try {
                viewModel.fixtureScoreCard.observe(viewLifecycleOwner) {
                    val teamOneId = it.data?.localteamId
                    val teamTwoId = it.data?.visitorteamId

                    if (it.data?.runs?.size!! >= 2) {

                        val teamOneTotalRun =
                            if (it.data?.runs?.get(0)?.teamId == teamOneId) it.data?.runs?.get(0)?.score else it.data?.runs?.get(
                                1
                            )?.score
                        val teamTwoTotalRun =
                            if (it.data?.runs?.get(0)?.teamId == teamTwoId) it.data?.runs?.get(0)?.score else it.data?.runs?.get(
                                1
                            )?.score
                        val teamOneWickets =
                            if (it.data?.runs?.get(0)?.teamId == teamOneId) it.data?.runs?.get(0)?.wickets else it.data?.runs?.get(
                                1
                            )?.wickets
                        val teamTwoWickets =
                            if (it.data?.runs?.get(0)?.teamId == teamTwoId) it.data?.runs?.get(0)?.wickets else it.data?.runs?.get(
                                1
                            )?.wickets
                        val teamOneOvers =
                            if (it.data?.runs?.get(0)?.teamId == teamOneId) it.data?.runs?.get(0)?.overs else it.data?.runs?.get(
                                1
                            )?.overs
                        val teamTwoOvers =
                            if (it.data?.runs?.get(0)?.teamId == teamTwoId) it.data?.runs?.get(0)?.overs else it.data?.runs?.get(
                                1
                            )?.overs




                        if (teamOneId != null) {
                            viewModel.getTeamNameByFixtureId(teamOneId)
                                .observe(requireActivity()) { team ->
                                    binding.teamOne.text =
                                        team.code.plus(teamOneTotalRun).plus("-")
                                            .plus(teamOneWickets).plus(" (").plus(teamOneOvers)
                                            .plus(")")
                                    /*Glide.with(MyApplication.instance)
                                        .load(team.imagePath)
                                        .into(object : CustomTarget<Drawable>() {
                                            override fun onResourceReady(
                                                resource: Drawable,
                                                transition: Transition<in Drawable>?
                                            ) {
                                                resource.setBounds(0, 0, 60, 60)
                                                binding.teamOne.setCompoundDrawables(
                                                    resource,
                                                    null,
                                                    null,
                                                    null
                                                )
                                            }

                                            override fun onLoadCleared(placeholder: Drawable?) {
                                                // Do nothing
                                            }
                                        })*/
                                }
                        }

                        if (teamTwoId != null) {
                            viewModel.getTeamNameByFixtureId(teamTwoId)
                                .observe(requireActivity()) { team ->
                                    binding.teamTwo.text =
                                        team.code.plus(teamTwoTotalRun).plus("-")
                                            .plus(teamTwoWickets).plus(" (").plus(teamTwoOvers)
                                            .plus(")")
                                    /*Glide.with(MyApplication.instance)
                                        .load(team.imagePath)
                                        .into(object : CustomTarget<Drawable>() {
                                            override fun onResourceReady(
                                                resource: Drawable,
                                                transition: Transition<in Drawable>?
                                            ) {
                                                resource.setBounds(0, 0, 60, 60)
                                                binding.teamTwo.setCompoundDrawables(
                                                    resource,
                                                    null,
                                                    null,
                                                    null
                                                )
                                            }

                                            override fun onLoadCleared(placeholder: Drawable?) {
                                                // Do nothing
                                            }
                                        })*/
                                }
                        }

                    }

                    showInfo(true, it)

                    binding.teamOne.setOnClickListener { _ ->
                        showInfo(true, it)

                    }

                    binding.teamTwo.setOnClickListener { _ ->
                        showInfo(false, it)

                    }
                }

                viewModel.launchFixtureScoreCard(fixtureId)
            } catch (e: HttpException) {
                if (e.code() == 400) {
                    // Handle the error
                    Log.e(TAG, "Bad Request")
                } else {
                    // Handle other errors
                    Log.e(TAG, e.message())
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
