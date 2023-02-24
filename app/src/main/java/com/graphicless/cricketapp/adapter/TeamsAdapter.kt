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
import com.graphicless.cricketapp.Model.Teams
import com.graphicless.cricketapp.ui.fragment.TeamContainerFragmentDirections
import com.graphicless.cricketapp.utils.MyApplication
import com.graphicless.cricketapp.viewmodel.CricketViewModel

private const val TAG = "TeamsAdapter"
class TeamsAdapter(private val data: List<Teams.Data>, private val national: Int, private val lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<TeamsAdapter.DataViewHolder>() {
    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = ItemSingleRowBinding.bind(view)
        val viewModel = CricketViewModel(Application())

        fun bind(item: Teams.Data, national: Int, lifecycleOwner: LifecycleOwner) {
            binding.name.text = item.name
            Glide.with(MyApplication.instance).load(item.imagePath).into(binding.image)

            if (national == 0) {
                item.countryId?.let {
                    viewModel.getCountryNameById(it).observe(lifecycleOwner){ countryName ->
                        binding.country.text = StringBuilder("Country: ").append(countryName)
                        binding.country.visibility = View.VISIBLE
                    }
                }
            }

            binding.root.setOnClickListener {
                val direction =
                TeamContainerFragmentDirections.actionTeamContainerFragmentToTeamDetailsContainerFragment(
                    item.id
                )
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
        holder.bind(item, national, lifecycleOwner)
    }
}