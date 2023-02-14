package com.graphicless.cricketapp.adapter

import android.app.Application
import android.content.ClipData.Item
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ItemCountryBinding
import com.graphicless.cricketapp.model.Country
import com.graphicless.cricketapp.viewmodel.CricketViewModel


private const val TAG = "CountryAdapter"
class CountryAdapter(private val countries: List<Country>, val lifecycleOwner: LifecycleOwner) :
    ListAdapter<Country, CountryAdapter.DataViewHolder>(DiffCallback) {
    private val viewModel: CricketViewModel = CricketViewModel(application = Application())
    /*private val continentName = MutableLiveData<String>()

    init {
        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                viewModel.getContinentName().observe(lifecycleOwner, Observer {
                    continentName.value = it
                })
            }
        })
    }*/

    class DataViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemCountryBinding.bind(view)
        private val viewModel: CricketViewModel = CricketViewModel(application = Application())

        fun bind(country: Country, lifecycleOwner: LifecycleOwner) {
            binding.countryName.text = country.name
//            viewModel.getContinentName(country.continent_id).observe(lifecycleOwner, Observer {
//                binding.continentName.text = it
//            })
            Glide.with(view.context).load(country.image_path).centerCrop()
                .into(binding.image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return DataViewHolder(layout)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country, lifecycleOwner)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "total country: ${countries.size}")
        return countries.size
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.id == newItem.id
        }
    }
}