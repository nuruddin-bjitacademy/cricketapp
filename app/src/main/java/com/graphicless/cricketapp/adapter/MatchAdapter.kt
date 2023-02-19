package com.graphicless.cricketapp.adapter

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ItemMatchBinding
import com.graphicless.cricketapp.temp.FixturesIncludeRuns
import com.graphicless.cricketapp.temp.joined.FixtureAndTeam
import com.graphicless.cricketapp.ui.fragment.MatchSummaryContainerOuterFragmentDirections
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import java.util.*
import java.util.concurrent.TimeUnit
import java.text.SimpleDateFormat

private const val TAG = "MatchAdapter"

class MatchAdapter(
    private val fixtures: List<FixtureAndTeam>,
    private val expandableTextView: AppCompatTextView?,
    private val stageId: Int?,
    private val lifecycleOwner: LifecycleOwner
) :
    ListAdapter<FixtureAndTeam, MatchAdapter.DataViewHolder>(DiffCallback) {

    private var isExpanded = false

    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val viewModel: CricketViewModel = CricketViewModel(application = Application())

        private val binding = ItemMatchBinding.bind(view)

        fun bind(fixture: FixtureAndTeam, lifecycleOwner: LifecycleOwner) {

            binding.tvRound.text = fixture.round
            binding.tvVenue.text = fixture.venue

            viewModel.getRunByRunId(fixture.fixtureId).observe(lifecycleOwner) {
                Log.d(TAG, "list<Run>: $it")
                var teamOneRun: FixturesIncludeRuns.Data.Run? = null
                var teamTwoRun: FixturesIncludeRuns.Data.Run? = null
                try {
                    if (it[0].teamId == fixture.teamOneId) {
                        teamOneRun = it[0]
                        try {
                            if (it[1].teamId == fixture.teamTwoId) {
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
                    if (it[1].teamId == fixture.teamOneId) {
                        teamOneRun = it[1]
                        try {
                            if (it[1].teamId == fixture.teamTwoId) {
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

                if (teamOneRun != null){
                    binding.tvScoreTeam1.text = teamOneRun.score.toString().plus("-").plus(it[0].wickets)
                    binding.tvOversTeam1.text = teamOneRun.overs.toString().plus(" overs")
                }else {
                    binding.tvScoreTeam1.visibility = View.GONE
                    binding.tvOversTeam1.visibility = View.GONE
                    binding.tvNotAvailableTeam1.visibility = View.VISIBLE
                }
                if (teamTwoRun != null){
                    binding.tvScoreTeam2.text = teamTwoRun.score.toString().plus("-").plus(it[1].wickets)
                    binding.tvOversTeam2.text = teamTwoRun.overs.toString().plus(" overs")
                }else {
                    binding.tvScoreTeam1.visibility = View.GONE
                    binding.tvOversTeam1.visibility = View.GONE
                    binding.tvNotAvailableTeam2.visibility = View.VISIBLE
                }

            }

            binding.tvNameTeam1.text = fixture.teamOneCode
            binding.tvNameTeam2.text = fixture.teamTwoCode

            Glide.with(itemView.context).load(fixture.teamOneFlag).into(binding.ivFlagTeam1)
            Glide.with(itemView.context).load(fixture.teamTwoFlag).into(binding.ivFlagTeam2)

            if(fixture.note == ""){

                val countdownTimerTextView = binding.tvNote

                // Set the target date and time for the countdown
                val targetDate = fixture.startingAT
                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
                val targetDateTime = dateFormat.parse(targetDate)
                val targetMillis = targetDateTime?.time

                // Start the countdown timer
                val countDownTimer = object : CountDownTimer(targetMillis?.minus(System.currentTimeMillis())!!, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                        val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
                        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

                        val remainingTimeText = if (days > 0) {
                            itemView.resources.getQuantityString(R.plurals.remaining_days, days.toInt(), days.toInt(), hours.toInt(), minutes.toInt())
                        } else {
                            String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
                        }
                        countdownTimerTextView.text = itemView.resources.getString(R.string.remaining_time, remainingTimeText)
                    }

                    override fun onFinish() {
                        countdownTimerTextView.text = itemView.resources.getString(R.string.countdown_finished)
                    }
                }

                countDownTimer.start()

            }else
                binding.tvNote.text = fixture.note

            binding.root.setOnClickListener{
                val direction = MatchSummaryContainerOuterFragmentDirections.actionMatchSummaryContainerOuterFragmentToMatchDetailsContainerFragment(fixture.fixtureId)
                val extras = FragmentNavigatorExtras(binding.root to "shared_element_container")
                itemView.findNavController().navigate(direction, extras)
            }

//                binding.tvStartingAt.text = fixture.startingAT
//                binding.tvStageName.text = fixture.stageName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        if (fixtures.size > 5) {
            expandableTextView?.visibility = View.VISIBLE
        }
        return DataViewHolder(layout)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val fixture = fixtures[position]
        holder.bind(fixture, lifecycleOwner)
        expandableTextView?.setOnClickListener {
            if (isExpanded) {
                val action =
                    MatchSummaryContainerOuterFragmentDirections.actionMatchSummaryContainerOuterFragmentToMatchesByStageFragment(
                        stageId!!
                    )
                holder.itemView.findNavController().navigate(action)
                return@setOnClickListener
            }
            isExpanded = true
            expandableTextView.text = MyConstants.SHOW_ALL
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return if (stageId != null && fixtures.size >= 5) {
            if (isExpanded) 5 else 2
        } else fixtures.size

    }

    companion object DiffCallback : DiffUtil.ItemCallback<FixtureAndTeam>() {
        override fun areItemsTheSame(oldItem: FixtureAndTeam, newItem: FixtureAndTeam): Boolean {
            return oldItem.fixtureId == newItem.fixtureId
        }

        override fun areContentsTheSame(oldItem: FixtureAndTeam, newItem: FixtureAndTeam): Boolean {
            return oldItem.fixtureId == newItem.fixtureId
        }
    }
}
