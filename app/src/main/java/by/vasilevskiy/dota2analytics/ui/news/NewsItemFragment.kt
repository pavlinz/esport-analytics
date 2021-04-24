package by.vasilevskiy.dota2analytics.ui.news

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.ui.news.parsers.HtmlImageGetter
import by.vasilevskiy.dota2analytics.utils.remove
import by.vasilevskiy.dota2analytics.utils.show
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news_item.*


class NewsItemFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        selectNewsItem()

        viewModel.newsItem.observe(viewLifecycleOwner, Observer {
            Glide.with(this).load(it.imageUrl).into(img_news_item_specific)
            tv_news_title_specific.text = it.title
            tv_news_date_specific.text = it.date
            val imageGetter =
                HtmlImageGetter(
                    lifecycleScope,
                    resources,
                    Glide.with(requireActivity()),
                    tv_article_text
                )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_article_text.text = Html.fromHtml(it.text, imageGetter, null)
            } else {
                tv_article_text.text = Html.fromHtml(it.text, imageGetter, null)
            }
            shimmerFrameLayoutNewsItemSpecific.stopShimmerAnimation()
            shimmerFrameLayoutNewsItemSpecific.remove()
            news_item_content.show()
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(requireActivity(), NewsViewModelFactory(requireContext()))
            .get(NewsViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayoutNewsItemSpecific.startShimmerAnimation()
    }

    private fun selectNewsItem() {
        arguments?.getInt("position")?.let { viewModel.selectNewsItem(it) }
    }
}