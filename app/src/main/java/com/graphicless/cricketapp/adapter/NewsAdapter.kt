package com.graphicless.cricketapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ItemNewsBinding
import com.graphicless.cricketapp.Model.News
import com.graphicless.cricketapp.ui.fragment.HomeFragmentDirections
import com.graphicless.cricketapp.utils.MyApplication
import com.graphicless.cricketapp.utils.Utils


class NewsAdapter(private val localNews: List<News.Article?>?) :
    ListAdapter<News.Article, NewsAdapter.DataViewHolder>(DiffCallback) {

    private lateinit var layout: View

    class DataViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val binding = ItemNewsBinding.bind(view)

        fun bind(localNews: News.Article) {

            val article = localNews
            val publishedAt = article.publishedAt
            val source = article.source
            val title = article.title
            val urlToImage = article.urlToImage
            val author = article.author

            val timePrefix = "\u2022"
//            binding.time.text = timePrefix.plus(" ").plus(Utils().DateToTimeFormat(publishedAt))
            binding.title.text = title

            if(author != null)
                binding.author.text = author
            else
                binding.author.text = "Author Name Unavailable"

            if (source != null) {
                binding.source.text = source.name
            }

            binding.root.setOnClickListener {
                val direction = HomeFragmentDirections.actionHomeFragmentToWebViewFragment(localNews.url!!)
                view.findNavController()
                    .navigate(direction)
            }

            if (urlToImage != "") {
                Glide
                    .with(MyApplication.instance)
                    .load(urlToImage)
                    .centerCrop()
                    .into(binding.img)
            }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<News.Article>() {
        override fun areItemsTheSame(oldItem: News.Article, newItem: News.Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: News.Article, newItem: News.Article): Boolean {
            return oldItem.url == newItem.url
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            layout = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_news, parent, false)
        return DataViewHolder(layout)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val businessArticles = localNews?.get(position)
        if (businessArticles != null) {
            holder.bind(businessArticles)
        }
    }

    override fun getItemCount(): Int {
        return localNews?.size ?: 0
    }
}