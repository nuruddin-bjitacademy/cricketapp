package com.graphicless.cricketapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ItemOverBinding
import com.graphicless.cricketapp.temp.FixtureOver
import com.graphicless.cricketapp.utils.MyApplication

class OverAdapter(private val overs:  Map<Int, List<Double>>, private val balls: List<FixtureOver.Data.Ball?>): RecyclerView.Adapter<OverAdapter.DataViewHolder>() {
    class DataViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val binding = ItemOverBinding.bind(view)

        fun bind(over: Int, item: List<Double>?, balls: List<FixtureOver.Data.Ball?>) {
            binding.tvOver.text = MyApplication.instance.getString(R.string.over).plus(" ").plus(over)
            val layoutManager = LinearLayoutManager(MyApplication.instance, LinearLayoutManager.HORIZONTAL, false)
            val adapter = item?.let { BallAdapter(it, balls) }
            binding.rvOverBall.layoutManager = layoutManager
            binding.rvOverBall.adapter = adapter
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_over, parent, false)
        return DataViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return if(overs.isNotEmpty()) overs.size else 0
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = overs[position]
        holder.bind(position, item, balls)
    }
}