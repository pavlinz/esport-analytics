package by.vasilevskiy.dota2analytics.ui.teams.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.adapters.MatchesAdapter
import kotlinx.android.synthetic.main.fragment_team_matches.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamMatchesFragment : BaseFragment(R.layout.fragment_team_matches) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setViewModel()
        setRecyclerView()
        observeMatches()
    }

    private fun setRecyclerView() {
        rv_team_matches.layoutManager = LinearLayoutManager(activity)
    }

    private fun observeMatches() {
        viewModel.getTeamMatches()
        viewModel.matches.observe(viewLifecycleOwner, Observer {
            CoroutineScope(Dispatchers.Main).launch {
                rv_team_matches.adapter = MatchesAdapter(it, requireContext())
            }
        })
    }
}