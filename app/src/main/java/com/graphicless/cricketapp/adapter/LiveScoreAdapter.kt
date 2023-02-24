package com.graphicless.cricketapp.adapter

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Application
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ItemMatchBinding
import com.graphicless.cricketapp.Model.LiveScoresIncludeRuns
import com.graphicless.cricketapp.ui.fragment.HomeFragmentDirections
import com.graphicless.cricketapp.viewmodel.CricketViewModel

private const val TAG = "LiveScoreAdapter"
class LiveScoreAdapter(
    private val liveMatches: List<LiveScoresIncludeRuns.Data?>?,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<LiveScoreAdapter.DataViewHolder>() {
    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = ItemMatchBinding.bind(view)
        val viewModel = CricketViewModel(application = Application())

        fun bind(item: LiveScoresIncludeRuns.Data, lifecycleOwner: LifecycleOwner) {

            Log.d(TAG, "item: $item")
//            val livePrefix = "\u2022"
            binding.tvLive.visibility = View.VISIBLE

            // Set up a value animator to animate the line's width
            val valueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
                duration = 1000 // Set the animation duration to 1 second
                repeatMode = ValueAnimator.REVERSE // Reverse the animation on repeat
                repeatCount = ValueAnimator.INFINITE // Repeat the animation indefinitely
                addUpdateListener {
                    // Update the line's width based on the current animation value
                    val fraction = it.animatedFraction
                    val newWidth = fraction * binding.tvLive.width
                    binding.line.layoutParams.width = newWidth.toInt()
                    binding.line.requestLayout()
                }
            }

            // Start the animator when the activity is created
            valueAnimator.start()

            try {
                binding.tvRound.text = item.round
            } catch (exception: Exception) {
                Log.e(TAG, "tvRound: $exception")
            }

            try {
                item.venueId?.let { viewModel.launchVenue(it) }
                viewModel.venue.observe(lifecycleOwner) {
                    Log.d(TAG, "bind: $it")
                    if (it != null) {
                        binding.tvVenue.text = it.city
                    }
                }
            } catch (exception: Exception) {
                Log.e(TAG, "venueId: $exception")
            }

            try {
                val teamOneRun = item.runs?.get(0)
                if (teamOneRun != null) {
                    binding.tvScoreTeam1.text = teamOneRun.score.toString().plus("-").plus(
                        item.runs?.get(0)?.wickets
                    )
                    binding.tvOversTeam1.text = item.runs?.get(0)?.overs.toString().plus(" overs")
                } else {
                    binding.tvScoreTeam2.visibility = View.GONE
                    binding.tvOversTeam2.visibility = View.GONE
                    binding.tvNotAvailableTeam1.visibility = View.VISIBLE
                }
            } catch (exception: Exception) {
                Log.e(TAG, "teamOneRun: $exception")
            }

            try {
                val teamTwoRun = item.runs?.get(1)
                if (teamTwoRun != null) {
                    binding.tvScoreTeam2.text = teamTwoRun.score.toString().plus("-").plus(
                        item.runs?.get(1)?.wickets
                    )
                    binding.tvOversTeam2.text = item.runs?.get(1)?.overs.toString().plus(" overs")
                } else {
                    binding.tvScoreTeam2.visibility = View.GONE
                    binding.tvOversTeam2.visibility = View.GONE
                    binding.tvNotAvailableTeam1.visibility = View.VISIBLE
                }
            } catch (exception: Exception) {
                Log.e(TAG, "teamTwoRun: $exception")
            }

            try {
                item.localteamId?.let { viewModel.launchTeamByTeamId(it) }
                item.localteamId?.let {
                    viewModel.team.observe(lifecycleOwner) {
                        binding.tvNameTeam1.text = it.data.code
                        Glide.with(itemView.context).load(it.data.imagePath)
                            .into(binding.ivFlagTeam1)
                    }
                }
            } catch (exception: Exception) {
                Log.e(TAG, "localteamId: $exception")
            }

            try {
                item.visitorteamId?.let { viewModel.launchTeamByTeamId2(it) }
                item.visitorteamId?.let {
                    viewModel.team2.observe(lifecycleOwner) {
                        binding.tvNameTeam2.text = it.data.code
                        Glide.with(itemView.context).load(it.data.imagePath)
                            .into(binding.ivFlagTeam2)
                    }
                }
            } catch (exception: Exception) {
                Log.e(TAG, "visitorteamId: $exception")
            }

            try {
                binding.tvNote.text = item.note
            } catch (exception: Exception) {
                Log.e(TAG, "tvNote: $exception")
            }

            binding.root.setOnClickListener {
                val direction = item.id?.let { it1 ->
                    Log.d(TAG, "fixture id : $it1")
                    HomeFragmentDirections.actionHomeFragmentToLiveMatchDetailsContainerFragment(it1)
                }
//                val extras = FragmentNavigatorExtras(binding.root to "shared_element_container")
                if (direction != null) {
                    itemView.findNavController().navigate(direction)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return DataViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return liveMatches?.size ?: 0
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = liveMatches?.get(position)
        if (item != null) {
            holder.bind(item, lifecycleOwner)
        }
    }
}