package by.vasilevskiy.dota2analytics.ui.teams.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import by.vasilevskiy.dota2analytics.Application
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.adapters.PlayerAdapter
import by.vasilevskiy.dota2analytics.ui.teams.viewmodel.TeamsViewModel
import by.vasilevskiy.dota2analytics.ui.teams.viewmodel.TeamsViewModelFactory
import kotlinx.android.synthetic.main.fragment_team_overview.*
import kotlinx.android.synthetic.main.fragment_team_players.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamPlayersFragment : Fragment() {

    private lateinit var viewModel: TeamsViewModel
    private lateinit var viewModelFactory: TeamsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_players, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelFactory = TeamsViewModelFactory((activity?.application as Application).repository)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(TeamsViewModel::class.java)

        rv_list_players.layoutManager = LinearLayoutManager(activity)

        viewModel.getTeamPlayers()

        viewModel.players.observe(viewLifecycleOwner, Observer {
            CoroutineScope(Dispatchers.Main).launch {
                rv_list_players.adapter = PlayerAdapter(it, requireContext())
            }
        })
    }
}