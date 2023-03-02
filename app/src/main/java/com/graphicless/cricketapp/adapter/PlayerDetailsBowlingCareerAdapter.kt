package com.graphicless.cricketapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.BowlingCareerBinding
import com.graphicless.cricketapp.model.PlayerDetailsBowling
import com.graphicless.cricketapp.utils.SharedPreference
import java.util.*

class PlayerDetailsBowlingCareerAdapter(
    private val careerBowlingTypeList: MutableList<String>,
    private val careerBowlingList: MutableList<PlayerDetailsBowling>
) : RecyclerView.Adapter<PlayerDetailsBowlingCareerAdapter.DataViewHolder>() {

    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = BowlingCareerBinding.bind(view)
        private val currentTheme = SharedPreference().getString("theme")

        fun bind(itemType: String, item: PlayerDetailsBowling) {

            if (currentTheme != "light") {
                binding.root.setBackgroundColor(getRandomLightColor(0))
            } else {
                binding.root.setBackgroundColor(getRandomLightColor(200))
            }

            binding.labelType.text = itemType
            binding.matches.text = item.matches.toString()
            binding.overs.text = item.overs.toString()
            binding.innings.text = item.innings.toString()
            binding.average.text = item.average.toString()
            binding.econRate.text = item.econRate.toString()
            binding.medians.text = item.medians.toString()
            binding.runs.text = item.runs.toString()
            binding.wickets.text = item.wickets.toString()
            binding.wide.text = item.wide.toString()
            binding.noBalls.text = item.noball.toString()
            binding.strikeRate.text = item.strikeRate.toString()
            binding.fourWickets.text = item.fourWickets.toString()
            binding.fiveWickets.text = item.fiveWickets.toString()
            binding.tenWickets.text = item.tenWickets.toString()
        }

        private fun getRandomLightColor(extra: Int): Int {
            val random = Random()
            val red = random.nextInt(56) + extra
            val green = random.nextInt(56) + extra
            val blue = random.nextInt(56) + extra
            return Color.rgb(red, green, blue)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.bowling_career, parent, false)
        return DataViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return careerBowlingList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val itemType = careerBowlingTypeList[position]
        val itemList = careerBowlingList[position]
        holder.bind(itemType, itemList)
    }
}