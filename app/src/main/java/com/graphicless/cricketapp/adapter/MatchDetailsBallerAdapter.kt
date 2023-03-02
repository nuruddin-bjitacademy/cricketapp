package com.graphicless.cricketapp.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.graphicless.cricketapp.model.FixtureDetailsScoreCard
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

class MatchDetailsBallerAdapter(private val lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<MatchDetailsBallerAdapter.PlayerStatisticsViewHolder>() {

    private var ballerList: List<FixtureDetailsScoreCard.Data.Bowling?>? = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerStatisticsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_batter, parent, false)
        return PlayerStatisticsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerStatisticsViewHolder, position: Int) {
        val playerStatistics = ballerList?.get(position)
        holder.bind(playerStatistics, lifecycleOwner)
    }

    override fun getItemCount(): Int {
        return ballerList?.size ?: 0
    }

    fun setBallerList(ballerList: List<FixtureDetailsScoreCard.Data.Bowling?>?) {
        this.ballerList = ballerList
        notifyDataSetChanged()
    }

    class PlayerStatisticsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val viewModel = CricketViewModel(application = Application())

        private val playerName: TextView = itemView.findViewById(R.id.name)
        private val runs: TextView = itemView.findViewById(R.id.runs)
        private val balls: TextView = itemView.findViewById(R.id.balls)
        private val sixes: TextView = itemView.findViewById(R.id.sixes)
        private val fours: TextView = itemView.findViewById(R.id.fours)
        private val strikeRate: TextView = itemView.findViewById(R.id.strikeRate)

        fun bind(baller: FixtureDetailsScoreCard.Data.Bowling?, lifecycleOwner: LifecycleOwner) {

            val playerId = baller?.playerId

            if (playerId != null) {
                viewModel.player.observe(lifecycleOwner) {
                    playerName.text = it.data?.fullname ?: MyConstants.NOT_AVAILABLE
                }
                viewModel.launchPlayer(playerId)
            }

            runs.text = baller?.overs.toString()
            balls.text = baller?.medians.toString()
            sixes.text = baller?.runs.toString()
            fours.text = baller?.wickets.toString()
            strikeRate.text = baller?.rate.toString()
        }
    }
}
