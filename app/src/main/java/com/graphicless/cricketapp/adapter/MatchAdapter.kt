package com.graphicless.cricketapp.adapter

import android.app.Application
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
import com.graphicless.cricketapp.ui.fragment.FixtureFragmentDirections
import com.graphicless.cricketapp.ui.fragment.MatchesFragmentDirections
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

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

                if (teamOneRun != null)
                    binding.tvScoreTeam1.text = teamOneRun.score.toString().plus("-").plus(
                        teamOneRun.wickets
                    ).plus(" (").plus(teamOneRun.overs).plus(") ")
                else binding.tvScoreTeam1.text = "N/A"
                if (teamTwoRun != null)
                    binding.tvScoreTeam2.text = teamTwoRun.score.toString().plus("-").plus(
                        teamTwoRun.wickets
                    ).plus("(").plus(teamTwoRun.overs).plus(")")
                else binding.tvScoreTeam2.text = "N/A"

            }

            binding.tvNameTeam1.text = fixture.teamOneCode
            binding.tvNameTeam2.text = fixture.teamTwoCode

            Glide.with(itemView.context).load(fixture.teamOneFlag).into(binding.ivFlagTeam1)
            Glide.with(itemView.context).load(fixture.teamTwoFlag).into(binding.ivFlagTeam2)

            binding.tvNote.text = fixture.note

            binding.root.setOnClickListener{
                val direction = FixtureFragmentDirections.actionFixtureFragmentToMatchDetailsContainerFragment(fixture.fixtureId)
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
                    FixtureFragmentDirections.actionFixtureFragmentToMatchesByStageFragment(
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
