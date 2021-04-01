package by.vasilevskiy.dota2analytics.ui.teams.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import by.vasilevskiy.dota2analytics.Application
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.adapters.MatchesAdapter
import by.vasilevskiy.dota2analytics.ui.teams.viewmodel.TeamsViewModel
import by.vasilevskiy.dota2analytics.ui.teams.viewmodel.TeamsViewModelFactory
import kotlinx.android.synthetic.main.fragment_team_matches.*
import kotlinx.android.synthetic.main.fragment_team_players.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamMatchesFragment : Fragment() {

    private lateinit var viewModel: TeamsViewModel
    private lateinit var viewModelFactory: TeamsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_matches, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelFactory = TeamsViewModelFactory((activity?.application as Application).repository)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(TeamsViewModel::class.java)

        rv_team_matches.layoutManager = LinearLayoutManager(activity)

        viewModel.getTeamMatches()

        viewModel.matches.observe(viewLifecycleOwner, Observer {
            CoroutineScope(Dispatchers.Main).launch {
                rv_team_matches.adapter = MatchesAdapter(it, requireContext())
            }
        })
    }
}