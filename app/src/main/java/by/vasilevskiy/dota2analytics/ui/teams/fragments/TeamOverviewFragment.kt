package by.vasilevskiy.dota2analytics.ui.teams.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import by.vasilevskiy.dota2analytics.Application
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.ui.teams.viewmodel.TeamsViewModel
import by.vasilevskiy.dota2analytics.ui.teams.viewmodel.TeamsViewModelFactory
import kotlinx.android.synthetic.main.fragment_team_overview.*

class TeamOverviewFragment : Fragment() {

    private lateinit var viewModel: TeamsViewModel
    private lateinit var viewModelFactory: TeamsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = TeamsViewModelFactory((activity?.application as Application).repository)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(TeamsViewModel::class.java)

        tv_wins_count.text = viewModel.selected.value?.wins.toString()
        tv_losses_count.text = viewModel.selected.value?.losses.toString()
        tv_rating_count.text = viewModel.selected.value?.rating.toString()
    }
}