package by.vasilevskiy.dota2analytics.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.adapters.NewsAdapter
import by.vasilevskiy.dota2analytics.ui.news.parsers.RuNewsParser
import by.vasilevskiy.dota2analytics.utils.remove
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsFragment : Fragment(), NewsAdapter.OnNewsListener {

    private lateinit var viewModel: NewsViewModel
    lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        checkLanguage()

        viewModel.news.observe(viewLifecycleOwner, Observer {
            adapter = NewsAdapter(it, this@NewsFragment)
            rv_news.layoutManager = LinearLayoutManager(context)
            rv_news.adapter = adapter
            shimmerFrameLayoutNews.stopShimmerAnimation()
            shimmerFrameLayoutNews.remove()
        })

        viewModel.newsParser.observe(viewLifecycleOwner, Observer {
            viewModel.getNews()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkNewsParserType()
        shimmerFrameLayoutNews.startShimmerAnimation()
    }

    private fun checkLanguage() {
        val languageValues = resources.getStringArray(R.array.language_values)

        when (PreferenceManager.getDefaultSharedPreferences(requireContext())
            .getString(getString(R.string.language_key), languageValues[0])) {
            languageValues[0] -> {
                viewModel = ViewModelProviders.of(requireActivity(), NewsViewModelFactory(requireContext()))
                    .get(NewsViewModel::class.java)
            }
            languageValues[1] -> {
                viewModel = ViewModelProviders.of(requireActivity(), NewsViewModelFactory(requireContext()))
                    .get(NewsViewModel::class.java)
            }
        }
    }

    override fun onNewsItemClick(position: Int) {
        var bundle = Bundle()
        bundle.putInt("position", position)

        findNavController().navigate(
            R.id.action_newsFragment_to_newsItemFragment,
            bundle
        )
    }
}