package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.graphicless.cricketapp.Model.FixtureDetailsScoreCard
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.adapter.MatchDetailsBallerAdapter
import com.graphicless.cricketapp.adapter.MatchDetailsBatsManAdapter
import com.graphicless.cricketapp.databinding.FragmentMatchDetailsScoreCardBinding
import com.graphicless.cricketapp.utils.MyApplication
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import com.graphicless.cricketapp.viewmodel.NetworkConnectionViewModel

private const val TAG = "MatchDetailsScoreCardFragment"

class MatchDetailsScoreCardFragment : Fragment() {

    private lateinit var _binding: FragmentMatchDetailsScoreCardBinding
    private val binding get() = _binding

    private val args: MatchDetailsContainerFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()
    private val networkConnectionViewModel: NetworkConnectionViewModel by activityViewModels()

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

//        val loadingView =
//            LayoutInflater.from(context).inflate(R.layout.layout_loading, binding.container, false)
//        binding.container.addView(loadingView)

        binding.scrollView.visibility = View.GONE
        binding.progressbar.visibility = View.VISIBLE



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        networkConnectionViewModel.isNetworkAvailable.observe(viewLifecycleOwner){
            if(it){
                fetchDataFromApi()
            }else{
                binding.internetUnavailable.visibility = View.VISIBLE
            }
        }
    }


    private fun fetchDataFromApi(){

        arguments?.takeIf { it.containsKey(MyConstants.COMING_FROM) }?.apply {

            if(getString(MyConstants.COMING_FROM )  == "live")
            {

//                viewModel.launchFixtureScoreCard(getInt(MyConstants.FIXTURE_ID))
                viewModel.launchFixtureScoreCard(getInt(MyConstants.FIXTURE_ID))

                try{
                    viewModel.fixtureScoreCard.observe(viewLifecycleOwner) { data ->

                        val teamOneId = data.data?.localteamId
                        val teamTwoId = data.data?.visitorteamId

                        val teamOneCode = data.data?.localteam?.code
                        val teamTwoCode = data.data?.visitorteam?.code


                        var teamOneTotalRun = 0
                        var teamOneTotalWicket = 0
                        var teamOneTotalOver = 0.0
                        var teamTwoTotalRun = 0
                        var teamTwoTotalWicket = 0
                        var teamTwoTotalOver = 0.0


                        binding.scrollView.visibility = View.VISIBLE

                        if(data.data!!.runs?.size!! > 2){
                            val battingList = data.data!!.batting
                            val battingS1 = battingList?.filter { it?.scoreboard == "S1" }
                            val battingS2 = battingList?.filter { it?.scoreboard == "S2" }
                            val battingS3 = battingList?.filter { it?.scoreboard == "S3" }
                            val battingS4 = battingList?.filter { it?.scoreboard == "S4" }

                            val bowlingList = data.data!!.bowling
                            val bowlingS1 = bowlingList?.filter { it?.scoreboard == "S1" }
                            val bowlingS2 = bowlingList?.filter { it?.scoreboard == "S2" }
                            val bowlingS3 = bowlingList?.filter { it?.scoreboard == "S3" }
                            val bowlingS4 = bowlingList?.filter { it?.scoreboard == "S4" }


//                            binding.teamOne.visibility = View.GONE
//                            binding.teamTwo.visibility = View.GONE
                            binding.spinnerSeason.visibility = View.VISIBLE

                            val firstInnings = "First Innings - ".plus(data.data!!.localteam?.name)
                            val secondInnings = "Second Innings - ".plus(data.data!!.visitorteam?.name)
                            val thirdInnings = "Third Innings - ".plus(data.data!!.localteam?.name)
                            val fourthInnings = "Fourth Innings - ".plus(data.data!!.visitorteam?.name)

                            val items = listOf(firstInnings, secondInnings, thirdInnings, fourthInnings)
                            val spinnerAdapter =
                                ArrayAdapter(
                                    MyApplication.instance,
                                    android.R.layout.simple_spinner_item,
                                    items
                                )
                            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            binding.spinnerSeason.adapter = spinnerAdapter

                            binding.spinnerSeason.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long
                                    ) {
                                        when (position) {
                                            0 -> {
                                                showTestInfo( battingS1, bowlingS1)
                                            }
                                            1 -> {
                                                showTestInfo( battingS2, bowlingS2)
                                            }
                                            2 -> {
                                                showTestInfo( battingS3, bowlingS3)
                                            }
                                            3 -> {
                                                showTestInfo( battingS4, bowlingS4)
                                            }
                                        }
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                        TODO("Not yet implemented")
                                    }
                                }
                        }else{
                            try{
                                for (i in data.data!!.runs?.indices!!) {
                                    if (data.data!!.runs?.get(i)?.teamId == teamOneId) {
                                        teamOneTotalRun += data.data!!.runs?.get(i)?.score ?: 0
                                        teamOneTotalWicket += data.data!!.runs?.get(i)?.wickets ?: 0
                                        teamOneTotalOver += data.data!!.runs?.get(i)?.overs!!
                                    } else {
                                        teamTwoTotalRun += data.data!!.runs?.get(i)?.score ?: 0
                                        teamTwoTotalWicket += data.data!!.runs?.get(i)?.wickets ?: 0
                                        teamTwoTotalOver += data.data!!.runs?.get(i)?.overs!!
                                    }
                                }
                            }catch (exception: java.lang.Exception){
                                Log.e(TAG, "live runs else for: $exception", )
                            }

                            binding.teamOne.text =
                                teamOneCode.plus(" [ ").plus(teamOneTotalRun).plus("-")
                                    .plus(teamOneTotalWicket).plus(" (").plus(teamOneTotalOver)
                                    .plus(") ]")

                            binding.teamTwo.text =
                                teamTwoCode.plus(" [ ").plus(teamTwoTotalRun).plus("-")
                                    .plus(teamTwoTotalWicket).plus(" (").plus(teamTwoTotalOver)
                                    .plus(") ]")

                            showInfo(true, data)

                            binding.teamOne.setOnClickListener { _ ->
                                showInfo(true, data)

                            }

                            binding.teamTwo.setOnClickListener { _ ->
                                showInfo(false, data)

                            }
                        }
                    }
                }catch (exception: Exception){
                    Log.e(TAG, "Load teams runs: $exception", )
                }
        }


             else{
                 Log.d(TAG, "else called")
                 arguments?.takeIf { it.containsKey(MyConstants.FIXTURE_ID) }?.apply {

                     val fixtureId: Int = getInt(MyConstants.FIXTURE_ID)

                     viewModel.launchFixtureScoreCard(fixtureId)

                     try{
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
                                                     team.code.plus(" [ ").plus(teamOneTotalRun)
                                                         .plus("-")
                                                         .plus(teamOneWickets).plus(" (")
                                                         .plus(teamOneOvers)
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
                                                     team.code.plus(" [ ").plus(teamTwoTotalRun)
                                                         .plus("-")
                                                         .plus(teamTwoWickets).plus(" (")
                                                         .plus(teamTwoOvers)
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

                                 /*try {
                                     binding.container.removeViewAt(0)
                                 } catch (exception: Exception) {
                                     Log.e(TAG, "remove view from container: $exception")
                                 }*/

                             } else {
                                 /*try {
                                     binding.container.removeViewAt(0)
                                 } catch (exception: Exception) {
                                     Log.e(TAG, "remove view from container: $exception")
                                 }
                                 val noDataView = LayoutInflater.from(context)
                                     .inflate(R.layout.layout_no_data, binding.container, false)
                                 binding.container.addView(noDataView)*/
                             }


                         }
                     }catch(exception: Exception){
                         Log.e(TAG, "from local and observe score card: $exception ", )
                     }

                 }
             }
        }



    }

    //    private fun showInfo(teamOneSelected: Boolean, batting: List<FixtureDetailsById.Data.Batting?>?, teamOneId: Int, teamTwoId: Int ) {
    private fun showInfo(teamOneSelected: Boolean, fixtureDetails: FixtureDetailsScoreCard) {

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
            binding.progressbar.visibility = View.GONE
            binding.scrollView.visibility = View.VISIBLE


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
            binding.progressbar.visibility = View.GONE
            binding.scrollView.visibility = View.VISIBLE
        }
    }

    private fun showTestInfo(
        batting: List<FixtureDetailsScoreCard.Data.Batting?>?,
        bowling: List<FixtureDetailsScoreCard.Data.Bowling?>?
    ) {
        matchDetailsBatsManAdapter.setPlayerStatisticsList(batting)
        matchDetailsBallerAdapter.setBallerList(bowling)

        binding.progressbar.visibility = View.GONE
        binding.scrollView.visibility = View.VISIBLE

    }

}
