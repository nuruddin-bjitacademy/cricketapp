package com.graphicless.cricketapp.adapter

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ItemMatchBinding
import com.graphicless.cricketapp.Model.FixturesByTeamId
import com.graphicless.cricketapp.Model.FixturesIncludeRuns
import com.graphicless.cricketapp.ui.fragment.TeamDetailsContainerFragmentDirections
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private const val TAG = "FixturesByTeamIdAdapter"

class FixturesByTeamIdAdapter(
    private val fixtures: List<FixturesByTeamId.Data.Fixture?>?,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<FixturesByTeamIdAdapter.DataViewHolder>() {
    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = ItemMatchBinding.bind(view)
        val viewModel = CricketViewModel(Application())

        fun bind(fixture: FixturesByTeamId.Data.Fixture?, lifecycleOwner: LifecycleOwner) {
            Log.d(TAG, "bind: fixture: $fixture")

            if (fixture != null) {


                binding.tvRound.text = fixture.round
                viewModel.getVenueNameByFixtureId(fixture.id!!).observe(lifecycleOwner){
                    binding.tvVenue.text = it
                }

                viewModel.getRunByRunId(fixture.id!!).observe(lifecycleOwner) {
                    Log.d(TAG, "list<Run>: $it")
                    var teamOneRun: FixturesIncludeRuns.Data.Run? = null
                    var teamTwoRun: FixturesIncludeRuns.Data.Run? = null
                    try {
                        if (it[0].teamId == fixture.localteamId) {
                            teamOneRun = it[0]
                            try {
                                if (it[1].teamId == fixture.visitorteamId) {
                                    teamTwoRun = it[1]
                                }
                            } catch (exception: Exception) {
                                Log.d(TAG, "run[0]=teamOne(inner) exception: $exception")
                            }
                        }
                    } catch (exception: Exception) {
                        Log.d(TAG, "run[0]=teamOne exception: $exception")
                    }
                    try {
                        if (it[1].teamId == fixture.localteamId) {
                            teamOneRun = it[1]
                            try {
                                if (it[1].teamId == fixture.visitorteamId) {
                                    teamTwoRun = it[1]
                                }
                            } catch (exception: Exception) {
                                Log.d(TAG, "run[1]=teamOne(inner) exception: $exception")
                            }
                        }
                    } catch (exception: Exception) {
                        Log.d(TAG, "run[1]=teamOne exception: $exception")
                    }

                    Log.d(TAG, "teamOneRun: $teamOneRun")
                    Log.d(TAG, "teamTwoRun: $teamTwoRun")

                    if (teamOneRun != null) {
                        binding.tvScoreTeam1.text =
                            teamOneRun.score.toString().plus("-").plus(it[0].wickets)
                        binding.tvOversTeam1.text = teamOneRun.overs.toString().plus(" overs")
                    } else {
                        binding.tvScoreTeam1.visibility = View.GONE
                        binding.tvOversTeam1.visibility = View.GONE
                        binding.tvNotAvailableTeam1.visibility = View.VISIBLE
                    }
                    if (teamTwoRun != null) {
                        binding.tvScoreTeam2.text =
                            teamTwoRun.score.toString().plus("-").plus(it[1].wickets)
                        binding.tvOversTeam2.text = teamTwoRun.overs.toString().plus(" overs")
                    } else {
                        binding.tvScoreTeam1.visibility = View.GONE
                        binding.tvOversTeam1.visibility = View.GONE
                        binding.tvNotAvailableTeam2.visibility = View.VISIBLE
                    }

                }

                fixture.localteamId?.let { viewModel.launchTeamByTeamId(it) }
                viewModel.team.observe(lifecycleOwner){
                    binding.tvNameTeam1.text = it.data.code
                    Glide.with(itemView.context).load(it.data.imagePath).into(binding.ivFlagTeam1)
                }

                fixture.visitorteamId?.let { viewModel.launchTeamByTeamId2(it)}
                viewModel.team2.observe(lifecycleOwner){
                    binding.tvNameTeam2.text = it.data.code
                    Glide.with(itemView.context).load(it.data.imagePath).into(binding.ivFlagTeam2)
                }


                if (fixture.note == "") {

                    /*val countdownTimerTextView = binding.tvNote

                    // Set the target date and time for the countdown
                    val targetDate = fixture.startingAt
                    val dateFormat =
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
                    val targetDateTime = dateFormat.parse(targetDate)
                    val targetMillis = targetDateTime?.time

                    // Start the countdown timer
                    val countDownTimer = object :
                        CountDownTimer(targetMillis?.minus(System.currentTimeMillis())!!, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                            val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
                            val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                            val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

                            val remainingTimeText = if (days > 0) {
                                itemView.resources.getQuantityString(
                                    R.plurals.remaining_days,
                                    days.toInt(),
                                    days.toInt(),
                                    hours.toInt(),
                                    minutes.toInt()
                                )
                            } else {
                                String.format(
                                    Locale.getDefault(),
                                    "%02d:%02d:%02d",
                                    hours,
                                    minutes,
                                    seconds
                                )
                            }
                            countdownTimerTextView.text = itemView.resources.getString(
                                R.string.remaining_time,
                                remainingTimeText
                            )
                        }

                        override fun onFinish() {
                            countdownTimerTextView.text =
                                itemView.resources.getString(R.string.countdown_finished)
                        }
                    }

                    countDownTimer.start()*/
                    binding.tvNote.text = MyConstants.UPCOMING

                } else
                    binding.tvNote.text = fixture.note

                binding.root.setOnClickListener {
                    val direction =
                        TeamDetailsContainerFragmentDirections.actionTeamDetailsContainerFragmentToMatchDetailsContainerFragment2(
                            fixture.id!!
                        )
                    val extras = FragmentNavigatorExtras(binding.root to "shared_element_container")
                    itemView.findNavController().navigate(direction, extras)
                }

//                binding.tvStartingAt.text = fixture.startingAT
//                binding.tvStageName.text = fixture.stageName
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return DataViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return fixtures?.size ?: 0
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = fixtures?.get(position)
        holder.bind(item, lifecycleOwner)
    }
}