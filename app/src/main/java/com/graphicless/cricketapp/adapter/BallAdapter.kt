package com.graphicless.cricketapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ItemOverBallBinding
import com.graphicless.cricketapp.model.FixtureOver

private const val TAG = "BallAdapter"

class BallAdapter(
    private val balls: List<Double>,
    private val ballScore: List<FixtureOver.Data.Ball?>
) : RecyclerView.Adapter<BallAdapter.DataViewHolder>() {

    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = ItemOverBallBinding.bind(view)

        fun bind(item: Double, ballScore: List<FixtureOver.Data.Ball?>) {

            try {
                val score = ballScore.filter {
                    it?.ball == item
                }
                if (score[0]?.score?.out == true) {
                    binding.ball.text = "W"
                    binding.ball.setBackgroundResource(R.drawable.boarder_over_ball_wicket)
                } else if (score[0]?.score?.six == true) {
                    binding.ball.text = "6"
                    binding.ball.setBackgroundResource(R.drawable.boarder_over_ball_six)
                } else if (score[0]?.score?.four == true) {
                    binding.ball.text = "4"
                    binding.ball.setBackgroundResource(R.drawable.boarder_over_ball_four)
                } else if (score[0]?.score?.noball != 0) {
                    binding.ball.text = "N"
                    binding.ball.setBackgroundResource(R.drawable.boarder_over_ball_no_ball)
                } else if (score[0]?.score?.legBye != 0) {
                    binding.ball.text = "L"
                    binding.ball.setBackgroundResource(R.drawable.boarder_over_ball_bye)
                } else if (score[0]?.score?.bye != 0) {
                    binding.ball.text = "B"
                    binding.ball.setBackgroundResource(R.drawable.boarder_over_ball_bye)
                } else if (score[0]?.score?.runs == 0) {
                    binding.ball.setBackgroundResource(R.drawable.boarder_over_ball_zero)
                } else {
                    binding.ball.text = score[0]?.score?.runs.toString()
                    binding.ball.setBackgroundResource(R.drawable.boarder_over_ball_other)
                }
            } catch (exception: Exception) {
                Log.d(TAG, "exception: $exception")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_over_ball, parent, false)
        return DataViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return balls.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = balls[position]
        holder.bind(item, ballScore)
    }
}