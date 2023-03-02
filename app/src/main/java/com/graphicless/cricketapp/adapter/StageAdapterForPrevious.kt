package com.graphicless.cricketapp.adapter

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.*
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ItemStageBinding
import com.graphicless.cricketapp.model.StageName
import com.graphicless.cricketapp.model.map.StageByLeague
import com.graphicless.cricketapp.viewmodel.CricketViewModel


private const val TAG = "StageAdapter"

class StageAdapterForPrevious(
    private val fixtures: List<StageByLeague>,
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context
) : ListAdapter<StageName, StageAdapterForPrevious.DataViewHolder>(DiffCallback) {

    class DataViewHolder(view: View) : ViewHolder(view) {
        private val viewModel: CricketViewModel = CricketViewModel(application = Application())

        private val binding = ItemStageBinding.bind(view)

        fun bind(fixture: StageByLeague, lifecycleOwner: LifecycleOwner, context: Context) {

            binding.tvStage.text = fixture.stageName.plus(", ").plus(fixture.seasonName)

            viewModel.getFixturesByStageId(fixture.stageId).removeObservers(lifecycleOwner)
            viewModel.getFixturesByStageId(fixture.stageId).distinctUntilChanged()
                .observe(lifecycleOwner) {

                    val adapter =
                        MatchAdapter(it, binding.expandable, fixture.stageId, lifecycleOwner)

                    binding.rvMatchesByStage.layoutManager =
                        LinearLayoutManager(context, VERTICAL, false)
                    binding.rvMatchesByStage.adapter = adapter
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_stage, parent, false)
        return DataViewHolder(layout)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val lifecycleOwner: LifecycleOwner = this.lifecycleOwner
        val fixture = fixtures[position]
        holder.bind(fixture, lifecycleOwner, context)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return fixtures.size
    }

    companion object DiffCallback : DiffUtil.ItemCallback<StageName>() {
        override fun areItemsTheSame(oldItem: StageName, newItem: StageName): Boolean {
            return oldItem.stageName == newItem.stageName
        }

        override fun areContentsTheSame(oldItem: StageName, newItem: StageName): Boolean {
            return oldItem.stageName == newItem.stageName
        }

    }
}
