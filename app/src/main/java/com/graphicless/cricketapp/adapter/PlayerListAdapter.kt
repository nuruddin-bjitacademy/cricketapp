package com.graphicless.cricketapp.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ItemSingleRowBinding
import com.graphicless.cricketapp.Model.PlayerAll
import com.graphicless.cricketapp.ui.fragment.PlayerListFragmentDirections
import com.graphicless.cricketapp.utils.MyApplication
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

class PlayerListAdapter(private val data: List<PlayerAll.Data>, private val lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<PlayerListAdapter.DataViewHolder>() {
    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = ItemSingleRowBinding.bind(view)
        val viewModel = CricketViewModel(Application())

        fun bind(item: PlayerAll.Data, lifecycleOwner: LifecycleOwner) {

            binding.name.text = item.fullname
            Glide.with(MyApplication.instance).load(item.imagePath).into(binding.image)
            binding.country.text = item.position?.name ?: MyConstants.NOT_AVAILABLE

            binding.root.setOnClickListener {
                val direction = PlayerListFragmentDirections.actionPlayerListFragmentToPlayerDetailsFragment(item.id)
                itemView.findNavController().navigate(direction)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_single_row, parent, false)
        return DataViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, lifecycleOwner)
    }
}