package com.graphicless.cricketapp.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.graphicless.cricketapp.databinding.FragmentMatchDetailsLiveBinding
import com.graphicless.cricketapp.utils.MyApplication
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import com.graphicless.cricketapp.viewmodel.NetworkConnectionViewModel


private const val TAG = "MatchDetailsLiveFragment"

class MatchDetailsLiveFragment : Fragment() {

    private lateinit var _binding: FragmentMatchDetailsLiveBinding
    private val binding get() = _binding

    private val args: MatchDetailsContainerFragmentArgs by navArgs()
    private val viewModel: CricketViewModel by viewModels()
    private val networkConnectionViewModel: NetworkConnectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailsLiveBinding.inflate(inflater, container, false)
        binding.body.visibility = View.GONE
        return binding.root
    }

    val handler = Handler()
    val delay = 10000 // delay for 5 seconds

    var fixtureId: Int? = null
    var batsmanARuns = 0
    var batsmanBRuns = 0

    var confidenceLevelTeamOne = -1
    var confidenceLevelTeamTwo = -1

    var winningPercentageTeamOne = 50
    var winningPercentageTeamTwo = 50
    var drawPercentage = 0

    var totalPartnershipRuns = 0
    var totalPartnershipBalls = 0

    var count = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.takeIf { it.containsKey(MyConstants.FIXTURE_ID) }?.apply {
            fixtureId = getInt(MyConstants.FIXTURE_ID)

            networkConnectionViewModel.isNetworkAvailable.observe(requireActivity()) {
                if (!it) {
                    handler.removeCallbacks(runnable)
                    binding.body.visibility = View.GONE
                    binding.internetUnavailable.visibility = View.VISIBLE
                    binding.progressbar?.visibility = View.GONE
                } else {
                    try {
                        if (fixtureId != null)
                            handler.postDelayed(runnable, 5000)
                    } catch (exception: Exception) {
                        Log.e(TAG, "internet connected launch handler: $exception")
                    }
                    binding.internetUnavailable.visibility = View.GONE
                }
            }
        }
    }

    private val runnable = object : Runnable {
        override fun run() {

            try {
                liveDetails(fixtureId!!)
                count++
                // Post the runnable again after the delay
                handler.postDelayed(this, delay.toLong())
            } catch (exception: Exception) {
                Log.e(TAG, "run: $exception")
            }
        }
    }

    private fun liveDetails(fixtureId: Int) {

        try {
            viewModel.liveDetails(fixtureId).observe(requireActivity()) { it ->
                Log.d(TAG, "live score details: $it")

                if (it != null) {
                    binding.tvRound?.text = it.round
                    for (i in it.runs?.indices!!) {
                        if (it.runs!![i]?.teamId == it.localteamId) {
                            binding.tvScoreTeam1?.text =
                                it.runs!![i]?.score.toString().plus(" - ").plus(
                                    it.runs!![i]?.wickets
                                )
                            binding.tvOversTeam1?.text = it.runs!![i]?.overs.toString()
                        } else {
                            binding.tvScoreTeam2?.text =
                                it.runs!![i]?.score.toString().plus(" - ").plus(
                                    it.runs!![i]?.wickets
                                )
                            binding.tvOversTeam2?.text = it.runs!![i]?.overs.toString()
                        }
                    }
                    binding.ivFlagTeam1?.let { it1 ->
                        Glide.with(MyApplication.instance).load(it.localteam?.imagePath).into(
                            it1
                        )
                    }
                    binding.ivFlagTeam2?.let { it1 ->
                        Glide.with(MyApplication.instance).load(it.visitorteam?.imagePath).into(
                            it1
                        )
                    }
                    binding.tvNameTeam1?.text = it.localteam?.code
                    binding.tvNameTeam2?.text = it.localteam?.code
                    binding.tvNote?.text = it.note
                    binding.progressbar?.visibility = View.GONE
                    binding.body.visibility = View.VISIBLE
                }

                var currentBattingTeam = "S1"
                val currentBatsman: MutableList<Int> = mutableListOf()
                var mutableListIndex = 0
                var wicket = 0

                if (it?.batting?.isNotEmpty() == true) {
                    for (i in it.batting?.indices!!) {
                        if (it.batting!![i]?.scoreboard == "S2") {
                            currentBattingTeam = "S2"
                            break
                        }
                    }

                    for (i in it.batting?.indices!!) {
                        if (it.batting!![i]?.scoreboard == currentBattingTeam && it.batting!![i]?.fowScore == 0) {
                            currentBatsman.add(mutableListIndex, i)
                            mutableListIndex++
                        }
                        if (it.batting!![i]?.scoreboard == currentBattingTeam) {
                            wicket++
                        }
                    }

                    for (i in it.batting?.indices!!) {
                        if (it.batting!![i]?.scoreboard == currentBattingTeam) {
                            currentBatsman.add(mutableListIndex, i)
                            mutableListIndex++
                        }
                    }
                } else {
                    Log.e(TAG, "liveDetails: batting is null or empty")
                }

                if (count % 2 == 0) {
                    binding.cvCurrentPartnership.animate().alpha(0f).setDuration(500)
                        .withEndAction {
                            binding.cvCurrentPartnership.visibility = View.INVISIBLE
                        }
                    binding.cvWinningPrediction.visibility = View.VISIBLE
                    binding.cvWinningPrediction.animate().alpha(1f).duration = 500

                    if (it != null) {
                        binding.tvTeam1?.text = it.localteam?.code
                        binding.tvTeam2?.text = it.visitorteam?.code
                    }

                    // Calculating winning percentage
                    if (confidenceLevelTeamOne == -1 && confidenceLevelTeamTwo == -1) {
                        if (it != null) {
                            it.stageId?.let { it1 ->
                                try {
                                    viewModel.getStandingByStageId(it1)
                                        .observe(requireActivity()) { standing ->
                                            Log.d(TAG, "liveDetails: standings : $standing")
                                            if (standing.data?.isEmpty() == true) {
                                                Log.d(TAG, "liveDetails: data empty")
                                                confidenceLevelTeamOne = 0
                                                confidenceLevelTeamTwo = 0
                                                viewModel.getStandingByStageId(it.stageId!!)
                                                    .removeObservers(requireActivity())
                                            } else {
                                                for (i in standing.data?.indices!!) {
                                                    if (standing.data!![i]?.teamId == it.localteamId && standing.data!![i]?.played!! != 0) {
                                                        confidenceLevelTeamOne =
                                                            ((100 * standing.data!![i]?.won!!) / standing.data!![i]?.played!!).toInt()
                                                    }
                                                    if (standing.data!![i]?.teamId == it.visitorteamId && standing.data!![i]?.played!! != 0) {
                                                        confidenceLevelTeamTwo =
                                                            ((100 * standing.data!![i]?.won!!) / standing.data!![i]?.played!!).toInt()
                                                    }
                                                }
                                            }
                                            viewModel.getStandingByStageId(it.stageId!!)
                                                .removeObservers(requireActivity())
                                        }
                                } catch (exception: java.lang.Exception) {
                                    Log.e(TAG, "liveDetails: get standings: $exception")
                                }
                            }
                        }
                    }

                    // If there is no data of previous winning
                    if (confidenceLevelTeamOne == 0 && confidenceLevelTeamTwo == 0) {
                        Log.d(
                            TAG,
                            "$winningPercentageTeamOne, $winningPercentageTeamTwo, $drawPercentage"
                        )
                        winningPercentageTeamOne = 50
                        winningPercentageTeamTwo = 50
                        drawPercentage = 0
                        binding.winningPercentageTeamA.text =
                            winningPercentageTeamOne.toString().plus("%")
                        binding.winningPercentageTeamB.text =
                            winningPercentageTeamTwo.toString().plus("%")
                        binding.winningPercentageDraw.text = drawPercentage.toString().plus("%")

                        // Get the current layout params for the winning color bars
                        val teamAColorBarLayoutParams =
                            binding.teamAColorBar.layoutParams as LinearLayout.LayoutParams
                        val teamBColorBarLayoutParams =
                            binding.teamBColorBar.layoutParams as LinearLayout.LayoutParams
                        val drawColorBarLayoutParams =
                            binding.drawColorBar.layoutParams as LinearLayout.LayoutParams

                        // Update the layout params for the winning color bars
                        teamAColorBarLayoutParams.weight = winningPercentageTeamOne.toFloat()
                        teamBColorBarLayoutParams.weight = winningPercentageTeamTwo.toFloat()
                        drawColorBarLayoutParams.weight = drawPercentage.toFloat()

                        // Set the updated layout params for the winning color bars
                        binding.teamAColorBar.layoutParams = teamAColorBarLayoutParams
                        binding.teamBColorBar.layoutParams = teamBColorBarLayoutParams
                        binding.drawColorBar.layoutParams = drawColorBarLayoutParams

                    } else {
                        winningPercentageTeamOne = (confidenceLevelTeamOne / 2)
                        winningPercentageTeamTwo = (confidenceLevelTeamTwo / 2)
                        drawPercentage = 100 - winningPercentageTeamOne - winningPercentageTeamTwo

                        binding.winningPercentageTeamA.text =
                            winningPercentageTeamOne.toString().plus("%")
                        binding.winningPercentageTeamB.text =
                            winningPercentageTeamTwo.toString().plus("%")
                        binding.winningPercentageDraw.text = drawPercentage.toString().plus("%")

                        // Get the current layout params for the winning color bars
                        val teamAColorBarLayoutParams =
                            binding.teamAColorBar.layoutParams as LinearLayout.LayoutParams
                        val teamBColorBarLayoutParams =
                            binding.teamBColorBar.layoutParams as LinearLayout.LayoutParams
                        val drawColorBarLayoutParams =
                            binding.drawColorBar.layoutParams as LinearLayout.LayoutParams

                        // Update the layout params for the winning color bars
                        teamAColorBarLayoutParams.weight = winningPercentageTeamOne.toFloat()
                        teamBColorBarLayoutParams.weight = winningPercentageTeamTwo.toFloat()
                        drawColorBarLayoutParams.weight = drawPercentage.toFloat()

                        // Set the updated layout params for the winning color bars
                        binding.teamAColorBar.layoutParams = teamAColorBarLayoutParams
                        binding.teamBColorBar.layoutParams = teamBColorBarLayoutParams
                        binding.drawColorBar.layoutParams = drawColorBarLayoutParams
                    }
                } else {

                    binding.cvWinningPrediction.animate().alpha(0f).setDuration(500).withEndAction {
                        binding.cvWinningPrediction.visibility = View.INVISIBLE
                    }
                    binding.cvCurrentPartnership.visibility = View.VISIBLE
                    binding.cvCurrentPartnership.animate().alpha(1f).duration = 500

                    var currentTotalScore = 0
                    try {
                        if (it != null) {
                            currentTotalScore =
                                if (it.runs?.size == 1) it.runs?.get(0)?.score!! else it.runs?.get(1)?.score!!
                        }
                    } catch (exception: Exception) {
                        Log.e(TAG, "current total score: $exception")
                    }

                    var lastBatsmanScore = 0
                    if (it != null) {
                        if (it.batting != null) {
                            try {
                                lastBatsmanScore =
                                    it.batting!![it.batting?.size?.minus(1)!!]?.score!!
                            } catch (exception: Exception) {
                                Log.e(TAG, "lastBatsmanScore: $exception")
                            }
                        }
                    }

                    var lastFowScore = 0
                    if (it != null) {
                        for (i in it.batting?.indices!!) {
                            if (it.batting != null && it.batting!![i]?.scoreboard == currentBattingTeam) {
                                if (it.batting!![i]?.fowScore!! > lastFowScore) {
                                    lastFowScore = it.batting!![i]?.fowScore!!
                                }
                            }
                        }
                    }

                    binding.tvCurrentPartnershipWicket.text = "Wicket ".plus((wicket - 1))

                    batsmanARuns = currentTotalScore - lastFowScore
                    batsmanBRuns = lastBatsmanScore
                    totalPartnershipRuns = batsmanARuns + batsmanBRuns

                    try {
                        if (it != null) {
                            it.batting!![currentBatsman[0]]?.playerId?.let { it1 ->
                                viewModel.launchPlayer(
                                    it1
                                )
                            }
                        }
                        viewModel.player.observe(requireActivity()) { player ->
                            binding.currentPartnershipPlayerOne.text =
                                player.data?.fullname ?: MyConstants.NOT_AVAILABLE
                        }
                    } catch (exception: Exception) {
                        Log.e(TAG, "player one observe: $exception")
                    }

                    try {
                        if (it != null) {
                            it.batting!![currentBatsman[1]]?.playerId?.let { it1 ->
                                viewModel.launchPlayer2(
                                    it1
                                )
                            }
                        }
                        viewModel.player2.observe(requireActivity()) { player ->
                            binding.currentPartnershipPlayerTwo.text =
                                player.data?.fullname ?: MyConstants.NOT_AVAILABLE
                        }
                    } catch (exception: Exception) {
                        Log.e(TAG, "player two observe: $exception")
                    }

                    var batsmanABalls = 0
                    var batsmanBBalls = 0
                    try {
                        if (it != null) {
                            batsmanABalls = it.batting!![currentBatsman[0]]?.ball!!
                        }
                    } catch (exception: Exception) {
                        Log.e(TAG, "batsmanABalls: $batsmanABalls")
                    }
                    try {
                        if (it != null) {
                            batsmanBBalls = it.batting!![currentBatsman[1]]?.ball!!
                        }
                    } catch (exception: Exception) {
                        Log.e(TAG, "batsmanBBalls: $batsmanABalls")
                    }

                    totalPartnershipBalls = batsmanABalls + batsmanBBalls
                    binding.partnershipPlayerOneRun.text =
                        batsmanARuns.toString().plus(" (").plus(batsmanABalls.toString()).plus(")")
                    binding.partnershipPlayerTwoRun.text =
                        batsmanBRuns.toString().plus(" (").plus(batsmanBBalls.toString()).plus(")")

                    binding.partnershipTotalRun.text =
                        totalPartnershipRuns.toString().plus(" (")
                            .plus((totalPartnershipBalls).toString()).plus(")")

                    // Calculate the total runs and the percentage of runs for each batsman
                    var batsmanAPercentage = 0
                    var batsmanBPercentage = 0
                    if (totalPartnershipRuns > 0) {
                        batsmanAPercentage = batsmanARuns * 100 / (totalPartnershipRuns)
                        batsmanBPercentage = batsmanBRuns * 100 / totalPartnershipRuns
                    }


                    // Get the current layout params for the color bars
                    val batsmanAColorBarLayoutParams =
                        binding.batsmanAColorBar.layoutParams as LinearLayout.LayoutParams
                    val batsmanBColorBarLayoutParams =
                        binding.batsmanBColorBar.layoutParams as LinearLayout.LayoutParams

                    // Update the layout params for the color bars
                    batsmanAColorBarLayoutParams.weight = batsmanAPercentage.toFloat()
                    batsmanBColorBarLayoutParams.weight = batsmanBPercentage.toFloat()

                    // Set the updated layout params for the color bars
                    binding.batsmanAColorBar.layoutParams = batsmanAColorBarLayoutParams
                    binding.batsmanBColorBar.layoutParams = batsmanBColorBarLayoutParams

                }

                try {
                    val batsManOneId = it?.batting!![currentBatsman[0]]?.playerId
                    val batsManOneRuns = it.batting!![currentBatsman[0]]?.score
                    val batsManOneBalls = it.batting!![currentBatsman[0]]?.ball
                    val batsManOneFours = it.batting!![currentBatsman[0]]?.fourX
                    val batsManOneSixes = it.batting!![currentBatsman[0]]?.sixX
                    val batsManOneStrikeRate = it.batting!![currentBatsman[0]]?.rate

                    binding.batsmanOneRuns.text = batsManOneRuns.toString()
                    binding.batsmanOneBalls.text = batsManOneBalls.toString()
                    binding.batsmanOneFours.text = batsManOneFours.toString()
                    binding.batsmanOneSixes.text = batsManOneSixes.toString()
                    binding.batsmanOneStrikeRate.text = batsManOneStrikeRate.toString()

                    if (batsManOneId != null) {
                        viewModel.launchPlayer(batsManOneId)
                    }
                } catch (exception: java.lang.Exception) {
                    Log.e(TAG, "batsManOne details: $exception")
                }

                try {
                    val batsManTwoId = it?.batting!![currentBatsman[1]]?.playerId
                    val batsManTwoRuns = it.batting!![currentBatsman[1]]?.score
                    val batsManTwoBalls = it.batting!![currentBatsman[1]]?.ball
                    val batsManTwoFours = it.batting!![currentBatsman[1]]?.fourX
                    val batsManTwoSixes = it.batting!![currentBatsman[1]]?.sixX
                    val batsManTwoStrikeRate = it.batting!![currentBatsman[1]]?.rate

                    binding.batsmanTwoRuns.text = batsManTwoRuns.toString()
                    binding.batsmanTwoBalls.text = batsManTwoBalls.toString()
                    binding.batsmanTwoFours.text = batsManTwoFours.toString()
                    binding.batsmanTwoSixes.text = batsManTwoSixes.toString()
                    binding.batsmanTwoStrikeRate.text = batsManTwoStrikeRate.toString()

                    if (batsManTwoId != null) {
                        viewModel.launchPlayer2(batsManTwoId)
                    }
                } catch (exception: java.lang.Exception) {
                    Log.e(TAG, "batsManTwo details: $exception")
                }

                try {
                    viewModel.player.observe(requireActivity()) {
                        binding.batsmanOneName.text = it.data?.fullname ?: MyConstants.NOT_AVAILABLE
                    }
                } catch (exception: Exception) {
                    Log.e(TAG, "batsman one name error: $exception")
                }

                try {
                    viewModel.player2.observe(requireActivity()) {
                        binding.batsmanTwoName.text = it.data?.fullname ?: MyConstants.NOT_AVAILABLE
                    }
                } catch (exception: Exception) {
                    Log.e(TAG, "batsman two name error: $exception")
                }

                var currentBowlerId: Int? = null
                var currentBowlerName: String? = null
                if (it != null) {
                    Log.d(TAG, "liveDetails: balls size: ${it.balls?.size}")
                }
                try {
                    val currentBall = it?.balls?.size?.minus(1)
                    currentBowlerId = currentBall?.let { it1 -> it.balls?.get(it1)?.bowler?.id }
                    currentBowlerName =
                        currentBall?.let { it1 -> it.balls?.get(it1)?.bowler?.fullname }
                } catch (exception: Exception) {
                    Log.e(TAG, "currentOver: $exception ")
                }

                var bowlerOvers = 0.0
                var bowlerMedians = 0
                var bowlerRuns = 0
                var bowlerWickets = 0
                var bowlerEconomyRate = 0.0
                try {
                    if (it != null) {
                        for (i in it.bowling?.indices!!) {
                            if (it.bowling!![i]?.playerId == currentBowlerId) {
                                bowlerOvers = it.bowling!![i]?.overs!!
                                bowlerMedians = it.bowling!![i]?.medians!!
                                bowlerRuns = it.bowling!![i]?.runs!!
                                bowlerWickets = it.bowling!![i]?.wickets!!
                                bowlerEconomyRate = it.bowling!![i]?.rate!!
                            }
                        }
                    }
                } catch (exception: java.lang.Exception) {
                    Log.e(TAG, "bowler data error: $exception")
                }

                binding.bowlerName.text = currentBowlerName
                binding.bowlerOvers.text = bowlerOvers.toString()
                binding.bowlerMedians.text = bowlerMedians.toString()
                binding.bowlerRuns.text = bowlerRuns.toString()
                binding.bowlerWickets.text = bowlerWickets.toString()
                binding.bowlerEconomyRate.text = bowlerEconomyRate.toString()

                var currentRunRate = 0
                /// It'll ensure that divided by zero will never happen
                if (it != null) {
                    if (it.balls?.size!! > 0) {
                        val totalTeamRun = it.runs?.get(it.runs!!.size - 1)?.score
                        if (totalTeamRun != null) {
                            currentRunRate = (totalTeamRun / (it.balls?.size ?: 1)) * 6
                        }
                        Log.d(TAG, " total : $totalTeamRun, ball size: $it.ball?.size")
                    }

                }

                binding.tvCrrValue.text = currentRunRate.toString()
                binding.tvPartnershipValue.text =
                    totalPartnershipRuns.toString().plus(" (")
                        .plus((totalPartnershipBalls).toString()).plus(")")
                if (it != null) {
                    for (i in it.batting!!.size - 1 downTo 0) {
                        if (it.batting!![i]?.fowScore != 0) {
                            binding.tvLastWicketValue.text = it.batting!![i]?.score.toString()
                            break
                        }
                    }
                }
            }
        } catch (exception: java.lang.Exception) {
            Log.e(TAG, "liveDetails: $exception")
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

}
