package com.graphicless.cricketapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ItemTeamRankingBinding
import com.graphicless.cricketapp.model.TeamRankingsLocal
import com.graphicless.cricketapp.utils.MyApplication

class TeamRankingAdapter(private val teamRankings: List<TeamRankingsLocal>) :
    RecyclerView.Adapter<TeamRankingAdapter.DataViewHolder>() {
    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = ItemTeamRankingBinding.bind(view)

        fun bind(item: TeamRankingsLocal) {

            binding.name.text = item.teamName
            binding.position.text = item.position.toString()
            binding.matches.text = item.matches.toString()
            binding.points.text = item.points.toString()
            binding.ratings.text = item.ratings.toString()

            Glide.with(MyApplication.instance).load(item.flag).into(binding.flag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_team_ranking, parent, false)
        return DataViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return teamRankings.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = teamRankings[position]
        holder.bind(item)
    }
}