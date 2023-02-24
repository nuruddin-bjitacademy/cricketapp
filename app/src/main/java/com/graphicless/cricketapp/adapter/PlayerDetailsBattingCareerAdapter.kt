package com.graphicless.cricketapp.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.BattingCareerBinding
import com.graphicless.cricketapp.Model.PlayerDetailsBatting
import java.util.*

private const val TAG = "PlayerDetailsCareerAdap"
class PlayerDetailsBattingCareerAdapter(
    private val careerBattingTypeList: MutableList<String>,
    private val careerBattingList: MutableList<PlayerDetailsBatting>
) : RecyclerView.Adapter<PlayerDetailsBattingCareerAdapter.DataViewHolder>() {
    class DataViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val binding = BattingCareerBinding.bind(view)

        fun bind(itemType: String, itemList: PlayerDetailsBatting) {
            Log.d(TAG, "bind: $itemType")
            binding.labelType.text = itemType
            Log.d(TAG, "bind: label type text : ${binding.labelType.text}")
            binding.root.setBackgroundColor(getRandomLightColor())

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
        private fun getRandomLightColor(): Int {
            val random = Random()
            val red = random.nextInt(56) + 200 // 200-255
            val green = random.nextInt(56) + 200 // 200-255
            val blue = random.nextInt(56) + 200 // 200-255
            return Color.rgb(red, green, blue)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.batting_career, parent, false )
        return DataViewHolder(layout)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "careerBattingList.size: ${careerBattingList.size}")
        return careerBattingList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val itemType = careerBattingTypeList[position]
        val itemList = careerBattingList[position]
        holder.bind(itemType, itemList)
    }


}