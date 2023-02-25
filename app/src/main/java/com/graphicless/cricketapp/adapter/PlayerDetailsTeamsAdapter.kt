package com.graphicless.cricketapp.adapter

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ItemPlayerTeamBinding
import com.graphicless.cricketapp.Model.PlayerDetailsNew
import com.graphicless.cricketapp.utils.MyApplication
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

private const val TAG = "PlayerDetailsTeamsAdapt"
class PlayerDetailsTeamsAdapter(
    private val currentTeams: List<PlayerDetailsNew.Data.Currentteam?>?,
    private val teams: List<PlayerDetailsNew.Data.Team?>?,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<PlayerDetailsTeamsAdapter.DataViewHolder>() {
    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val binding = ItemPlayerTeamBinding.bind(view)
        val viewModel = CricketViewModel(Application())

        fun bindCurrentTeam(item: PlayerDetailsNew.Data.Currentteam?, lifecycleOwner: LifecycleOwner) {

            if (item != null) {
                try{
                    binding.teamName.text = item.name
                    item.inSquad?.seasonId?.let {seasonId ->
                        viewModel.getSeasonNameById(seasonId).observe(lifecycleOwner){seasonName ->
                            binding.seasonName.text = MyConstants.SEASON.plus(seasonName)
                        }
                    }
                    item.inSquad?.leagueId?.let {leagueId ->
                        viewModel.getLeagueNameById(leagueId).observe(lifecycleOwner){leagueName ->
                            binding.leagueName.text = leagueName
                        }
                    }
                    Glide.with(MyApplication.instance).load(item.imagePath).into(binding.flag)
                }catch (exception: Exception){
                    Log.e(TAG, "Bind team name: $exception", )
                }
            }
        }

        fun bindAllTeam(item: PlayerDetailsNew.Data.Team?, lifecycleOwner: LifecycleOwner) {

            if (item != null) {
                try{
                    binding.teamName.text = item.name
                    item.inSquad?.seasonId?.let {seasonId ->
                        viewModel.getSeasonNameById(seasonId).observe(lifecycleOwner){seasonName ->
                            binding.seasonName.text = seasonName
                        }
                    }
                    item.inSquad?.leagueId?.let {leagueId ->
                        viewModel.getLeagueNameById(leagueId).observe(lifecycleOwner){leagueName ->
                            binding.leagueName.text = leagueName
                        }
                    }
                    Glide.with(MyApplication.instance).load(item.imagePath).into(binding.flag)
                }catch (exception: Exception){
                    Log.e(TAG, "Bind team name: $exception", )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_player_team, parent, false)
        return DataViewHolder(layout)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "currentTeams: ${currentTeams?.size} and teams ${teams?.size}")
        return if(currentTeams?.size != null){
            currentTeams.size
        }else if(teams?.size != null){
            teams.size
        }else 0
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        if(currentTeams?.size != null){
            val item = currentTeams.get(position)
            holder.bindCurrentTeam(item, lifecycleOwner)
        }
        if(teams?.size != null){
            val item = teams[position]
            holder.bindAllTeam(item, lifecycleOwner)
        }
    }
}