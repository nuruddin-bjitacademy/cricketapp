package com.graphicless.cricketapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.BattingCareerBinding
import com.graphicless.cricketapp.model.PlayerDetailsBatting
import com.graphicless.cricketapp.utils.SharedPreference
import java.util.*

private const val TAG = "PlayerDetailsCareerAdap"

class PlayerDetailsBattingCareerAdapter(
    private val careerBattingTypeList: MutableList<String>,
    private val careerBattingList: MutableList<PlayerDetailsBatting>
) : RecyclerView.Adapter<PlayerDetailsBattingCareerAdapter.DataViewHolder>() {
    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = BattingCareerBinding.bind(view)
        private val currentTheme = SharedPreference().getString("theme")

        fun bind(itemType: String, itemList: PlayerDetailsBatting) {

            if (currentTheme != "light") {
                binding.root.setBackgroundColor(getRandomLightColor(0))
            } else {
                binding.root.setBackgroundColor(getRandomLightColor(200))
            }

            binding.labelType.text = itemType
            binding.matches.text = itemList.matches.toString()
            binding.runs1.text = itemList.runs.toString()
            binding.innings.text = itemList.innings.toString()
            binding.balls1.text = itemList.balls.toString()
            binding.highScore.text = itemList.highScore.toString()
            binding.s100.text = itemList.s100.toString()
            binding.s50.text = itemList.s50.toString()
            binding.average.text = itemList.average.toString()
            binding.notOut.text = itemList.notOut.toString()
            binding.s4.text = itemList.s4.toString()
            binding.s6.text = itemList.s6.toString()
            binding.strikeRate.text = itemList.strikeRate.toString()
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
            LayoutInflater.from(parent.context).inflate(R.layout.batting_career, parent, false)
        return DataViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return careerBattingList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val itemType = careerBattingTypeList[position]
        val itemList = careerBattingList[position]
        holder.bind(itemType, itemList)
    }
}