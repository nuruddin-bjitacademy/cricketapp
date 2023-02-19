package com.graphicless.cricketapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ItemSingleRowBinding
import com.graphicless.cricketapp.temp.SquadByTeamAndSeason
import com.graphicless.cricketapp.utils.MyApplication
import com.graphicless.cricketapp.utils.MyConstants

class SquadAdapter(private var squad: List<SquadByTeamAndSeason.Data.Squad?>): RecyclerView.Adapter<SquadAdapter.DataViewHolder>() {
    class DataViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val binding = ItemSingleRowBinding.bind(view)

        fun bind(item: SquadByTeamAndSeason.Data.Squad?) {
            if (item != null) {
                binding.name.text = item.fullname
            }else{
                binding.name.text = MyConstants.NOT_AVAILABLE
            }

            if (item != null) {
                Glide.with(MyApplication.instance).load(item.imagePath).into(binding.image)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_single_row, parent, false)

        return DataViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return squad.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = squad[position]
        holder.bind(item)
    }

    fun reCall(squadList: List<SquadByTeamAndSeason.Data.Squad?>) {
        squad = squadList
        notifyDataSetChanged()
    }
}