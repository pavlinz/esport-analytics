package by.vasilevskiy.dota2analytics.ui.main.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.vasilevskiy.dota2analytics.helpers.NetworkManager
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.adapters.UpcomingMatchesAdapter
import by.vasilevskiy.dota2analytics.ui.main.parsers.UpcomingMatchParser
import by.vasilevskiy.dota2analytics.ui.main.repo.GamesRepoImpl
import by.vasilevskiy.dota2analytics.ui.main.viewmodel.MainViewModel
import by.vasilevskiy.dota2analytics.ui.main.viewmodel.SpecificMatchViewModelFactory
import by.vasilevskiy.dota2analytics.utils.remove
import by.vasilevskiy.dota2analytics.utils.show
import by.vasilevskiy.dota2analytics.utils.showNoNetworkConnectionToast
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.*

class MainFragment : Fragment(), UpcomingMatchesAdapter.OnUpcomingTeamListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UpcomingMatchesAdapter
    private lateinit var job: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        showActivityComponent()

        rv_upcoming_matches.layoutManager = LinearLayoutManager(this.context)
        job = CoroutineScope(Dispatchers.IO).launch {
            if (NetworkManager.isNetworkAvailable(requireContext())) {
                viewModel.getData()
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    activity?.showNoNetworkConnectionToast()
                }
            }

            if (!job.isCancelled) {
                CoroutineScope(Dispatchers.Main).launch {
                    adapter =
                        UpcomingMatchesAdapter(viewModel.listUpcomingMatches, this@MainFragment)
                    rv_upcoming_matches.adapter = adapter
                    if (NetworkManager.isNetworkAvailable(requireContext())) {
                        shimmerFrameLayout.stopShimmerAnimation()
                        shimmerFrameLayout.remove()
                    }
                }
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            requireActivity(),
            SpecificMatchViewModelFactory(
                GamesRepoImpl(UpcomingMatchParser())
            )
        )
            .get(MainViewModel::class.java)
    }

    private fun showActivityComponent() {
        activity?.toolbar?.show()
        activity?.bottom_nav_menu?.show()
    }

    override fun onUpcomingTeamClick(position: Int) {
        var bundle = Bundle()
        bundle.putInt("position", position)

        findNavController().navigate(
            R.id.action_gamesFragment_to_specificUpcomingMatchFragment,
            bundle
        )
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onStop() {
        super.onStop()
        job.cancel()
    }
}