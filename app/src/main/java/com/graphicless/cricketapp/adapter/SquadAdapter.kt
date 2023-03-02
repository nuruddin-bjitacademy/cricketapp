package com.graphicless.cricketapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ItemSingleRowBinding
import com.graphicless.cricketapp.model.SquadByTeamAndSeason
import com.graphicless.cricketapp.ui.fragment.TeamDetailsContainerFragmentDirections
import com.graphicless.cricketapp.utils.MyApplication
import com.graphicless.cricketapp.utils.MyConstants

private const val TAG = "SquadAdapter"

class SquadAdapter(private var squad: List<SquadByTeamAndSeason.Data.Squad?>) :
    RecyclerView.Adapter<SquadAdapter.DataViewHolder>() {
    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = ItemSingleRowBinding.bind(view)

        fun bind(item: SquadByTeamAndSeason.Data.Squad?) {
            if (item != null) {
                binding.name.text = item.fullname
            } else {
                binding.name.text = MyConstants.NOT_AVAILABLE
            }

            if (item != null) {
                Glide.with(MyApplication.instance).load(item.imagePath).into(binding.image)
            }

            binding.root.setOnClickListener {
                val direction = item?.id?.let { it1 ->
                    TeamDetailsContainerFragmentDirections.actionTeamDetailsContainerFragmentToPlayerDetailsFragment(
                        it1
                    )
                }
                if (direction != null) {
                    try {
                        itemView.findNavController().navigate(direction)
                    } catch (exception: Exception) {
                        Log.e(TAG, "Team details to Player details: $exception")
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_single_row, parent, false)

        return DataViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return squad.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = squad[position]
        holder.bind(item)
    }
}