package by.vasilevskiy.dota2analytics.ui.teams.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.adapters.PlayerAdapter
import kotlinx.android.synthetic.main.fragment_team_players.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TeamPlayersFragment : BaseFragment(R.layout.fragment_team_players) {

    private lateinit var job: Job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        setRecyclerView()
        observePlayers()
    }

    private fun setRecyclerView() {
        rv_list_players.layoutManager = LinearLayoutManager(activity)
    }

    private fun observePlayers() {
        viewModel.getTeamPlayers()

        viewModel.players.observe(viewLifecycleOwner, Observer {
            job = CoroutineScope(Dispatchers.Main).launch {
                rv_list_players.adapter = PlayerAdapter(it, requireContext())
            }
        })
    }

    override fun onStop() {
        super.onStop()
        job.cancel()
    }
}