package com.graphicless.cricketapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ItemSingleRowBinding
import com.graphicless.cricketapp.temp.Teams
import com.graphicless.cricketapp.ui.fragment.TeamContainerFragmentDirections
import com.graphicless.cricketapp.utils.MyApplication

class TeamsAdapter(private val data: List<Teams.Data>): RecyclerView.Adapter<TeamsAdapter.DataViewHolder>() {
    class DataViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemSingleRowBinding.bind(view)

        fun bind(item: Teams.Data) {
            binding.name.text = item.name
            Glide.with(MyApplication.instance).load(item.imagePath).into(binding.image)

            binding.root.setOnClickListener {
                val direction = TeamContainerFragmentDirections.actionTeamContainerFragmentToTeamDetailsFragment(item.id)
                itemView.findNavController().navigate(direction)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_single_row, parent, false)
        return DataViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }
}