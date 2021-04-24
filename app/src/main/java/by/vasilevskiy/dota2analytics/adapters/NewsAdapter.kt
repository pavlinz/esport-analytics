package by.vasilevskiy.dota2analytics.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.models.news.NewsItem
import com.bumptech.glide.Glide

class NewsAdapter(private val list: List<NewsItem>, private val onNewsListener: OnNewsListener) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view, onNewsListener)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    class NewsViewHolder(itemView: View, onNewsListener: OnNewsListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var onNewsListener: OnNewsListener = onNewsListener

        private var newsImage: ImageView = itemView.findViewById(R.id.img_news_item)
        private var newsTitle: TextView = itemView.findViewById(R.id.tv_news_title)
        private var newsDate: TextView = itemView.findViewById(R.id.tv_news_date)

        fun bind(newsItem: NewsItem) {
            Glide.with(itemView.context).load(newsItem.imageUrl).into(newsImage)
            newsTitle.text = newsItem.title
            newsDate.text = newsItem.date

            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onNewsListener.onNewsItemClick(adapterPosition)
        }
    }

    interface OnNewsListener {
        fun onNewsItemClick(position: Int)
    }
}