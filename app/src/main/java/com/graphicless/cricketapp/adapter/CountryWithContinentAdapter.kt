package com.graphicless.cricketapp.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ItemCountryBinding
import com.graphicless.cricketapp.model.CountryWithContinent
import com.graphicless.cricketapp.viewmodel.CricketViewModel

class CountryWithContinentAdapter(private val countryWithContinents: List<CountryWithContinent>) :
    ListAdapter<CountryWithContinent, CountryWithContinentAdapter.DataViewHolder>(DiffCallback) {

    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCountryBinding.bind(view)
        private val viewModel: CricketViewModel = CricketViewModel(application = Application())

        fun bind(country: CountryWithContinent) {
            binding.countryName.text = country.countryName
            binding.continentName.text = country.continentName
            Glide.with(itemView.context).load(country.countryFlag).centerCrop()
                .into(binding.image)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return DataViewHolder(layout)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val country = countryWithContinents[position]
        holder.bind(country)
    }

    override fun getItemCount(): Int {
        return countryWithContinents.size
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CountryWithContinent>() {
        override fun areItemsTheSame(
            oldItem: CountryWithContinent,
            newItem: CountryWithContinent
        ): Boolean {
            return oldItem.countryId == newItem.countryId
        }

        override fun areContentsTheSame(
            oldItem: CountryWithContinent,
            newItem: CountryWithContinent
        ): Boolean {
            return oldItem.countryId == newItem.countryId
        }
    }
}