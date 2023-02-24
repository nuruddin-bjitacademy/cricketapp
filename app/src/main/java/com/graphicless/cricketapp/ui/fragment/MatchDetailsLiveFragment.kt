package com.graphicless.cricketapp.ui.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.graphicless.cricketapp.databinding.FragmentMatchDetailsLiveBinding
import com.graphicless.cricketapp.utils.MyApplication
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.utils.Utils
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
//        val loadingView =
//            LayoutInflater.from(context).inflate(R.layout.layout_loading, binding.container, false)
//        binding.container.addView(loadingView)
        return binding.root
    }

    val handler = Handler()
    val delay = 5000 // delay for 5 seconds

    var fixtureId: Int? = null
    var batsmanARuns = 0
    var batsmanBRuns = 0

    var confidenceLevel = false
    var confidenceLevelTeamOne = -1
    var confidenceLevelTeamTwo = -1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.takeIf { it.containsKey(MyConstants.FIXTURE_ID) }?.apply {
            fixtureId = getInt(MyConstants.FIXTURE_ID)

            networkConnectionViewModel.isNetworkAvailable.observe(requireActivity()) {
                if (!it) {
                    handler.removeCallbacks(runnable)
                    binding.body.visibility = View.GONE
                    binding.internetUnavailable.visibility = View.VISIBLE
                } else {
                    try{
                        if (fixtureId != null)
                            handler.postDelayed(runnable, 5000)
                    }catch (exception: Exception){
                        Log.e(TAG, "internet connected launch handler: $exception", )
                    }
                    binding.internetUnavailable.visibility = View.GONE
                    binding.body.visibility = View.VISIBLE
                }
            }

        }

    }


    private val runnable = object : Runnable {
        override fun run() {

            try{
                liveDetails(fixtureId!!)
                // Post the runnable again after the delay
                handler.postDelayed(this, delay.toLong())
            }catch (exception: Exception){
                Log.e(TAG, "run: $exception", )
            }
        }
    }

    private fun liveDetails(fixtureId: Int) {




        try{
            viewModel.liveDetails(fixtureId).observe(requireActivity()) { it ->
                Log.d(TAG, "live score details: $it")

                if(confidenceLevelTeamOne == -1 && confidenceLevelTeamTwo == -1){
                    if (it != null) {
                        it.stageId?.let { it1 ->
                            try{
                                viewModel.getStandingByStageId(it1).observe(requireActivity()) {standing ->
                                    Log.d(TAG, "liveDetails: stqandings : $it")
                                    val standings = standing.data
                                    for(i in standing.data?.indices!!){
                                        if(standing.data!![i]?.teamId == it.localteamId && standing.data!![i]?.played!! != 0){
                                            confidenceLevelTeamOne = (100 * standing.data!![i]?.won!!)/ standing.data!![i]?.played!!
                                        }
                                        if(standing.data!![i]?.teamId == it.visitorteamId && standing.data!![i]?.played!! != 0){
                                            confidenceLevelTeamTwo = (100 * standing.data!![i]?.won!!)/ standing.data!![i]?.played!!
                                        }
                                    }
                                }
                            }catch (exception: java.lang.Exception){
                                Log.e(TAG, "liveDetails: get standings: $exception", )
                            }
                        }
                    }

                }else{
                    viewModel.getStandingByStageId(it!!.stageId!!).removeObservers(requireActivity())
                }

                Log.d(TAG, "teamone: $confidenceLevelTeamOne and teamtwo: $confidenceLevelTeamTwo")

                var currentBattingTeam = "S1"
                val currentBatsman: MutableList<Int> = mutableListOf()
                var mutableListIndex = 0
                var wicket = 0

                if(it?.batting?.isNotEmpty() == true){
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
                }else{
                    Log.e(TAG, "liveDetails: batting is null or empty", )
                }



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
                            lastBatsmanScore = it.batting!![it.batting?.size?.minus(1)!!]?.score!!
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
                val totalRuns: Int = batsmanARuns + batsmanBRuns

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

                var totalBalls = 0

                totalBalls = batsmanABalls + batsmanBBalls
                binding.partnershipPlayerOneRun.text =
                    batsmanARuns.toString().plus(" (").plus(batsmanABalls.toString()).plus(")")
                binding.partnershipPlayerTwoRun.text =
                    batsmanBRuns.toString().plus(" (").plus(batsmanBBalls.toString()).plus(")")

                binding.partnershipTotalRun.text =
                    totalRuns.toString().plus(" (").plus((totalBalls).toString()).plus(")")

                // Calculate the total runs and the percentage of runs for each batsman
                var batsmanAPercentage = 0
                var batsmanBPercentage = 0
                if (totalRuns > 0) {
                    batsmanAPercentage = batsmanARuns * 100 / (totalRuns)
                    batsmanBPercentage = batsmanBRuns * 100 / totalRuns
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


                ///////////////////////////////////////////////////////////////////////


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
                    val currentOver = it?.balls?.size?.minus(1)
                    currentBowlerId = currentOver?.let { it1 -> it.balls?.get(it1)?.bowler?.id }
                    currentBowlerName =
                        currentOver?.let { it1 -> it.balls?.get(it1)?.bowler?.fullname }
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
                            if (it.bowling!![i]?.id == currentBowlerId) {
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

                //////////////////////////////////////////////////////////////////////////////

                var currentRunRate = 0
                /// It'll ensure that divided by zero will never happen
                if (it != null) {
                    if (it.balls?.size!! > 0)
                        currentRunRate = (totalRuns / (it.balls?.size ?: 1)) * 6
                }

                binding.tvCrrValue.text = currentRunRate.toString()
                binding.tvPartnershipValue.text =
                    totalRuns.toString().plus(" (").plus((totalBalls).toString()).plus(")")
                if (it != null) {
                    for (i in it.batting!!.size - 1 downTo 0) {
                        if (it.batting!![i]?.fowScore != 0) {
                            binding.tvLastWicketValue.text = it.batting!![i]?.score.toString()
                            break
                        }
                    }
                }


            }
        }catch (exception: java.lang.Exception){
            Log.e(TAG, "liveDetails: $exception", )
        }
    }


    /*override fun onResume() {
        super.onResume()
        if (fixtureId != null)
            handler.postDelayed(runnable, 0)
    }*/

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

}
