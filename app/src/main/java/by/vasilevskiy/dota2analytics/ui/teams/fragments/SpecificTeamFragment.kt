package by.vasilevskiy.dota2analytics.ui.teams.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import by.vasilevskiy.dota2analytics.Application
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.adapters.TeamViewPagerAdapter
import by.vasilevskiy.dota2analytics.ui.teams.viewmodel.TeamsViewModel
import by.vasilevskiy.dota2analytics.ui.teams.viewmodel.TeamsViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_specific_team.*

class SpecificTeamFragment : BaseFragment(R.layout.fragment_specific_team) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabs()
        setViewModel()
        observeSelected()
        selectSpecificTeam()
    }

    private fun selectSpecificTeam() {
        arguments?.getInt("position")?.let { viewModel.select(it) }
    }

    private fun observeSelected() {
        viewModel.selected.observe(viewLifecycleOwner, Observer {
            Glide.with(this).load(it.logo_url).into(team_logo_specific_team)
            tv_team_name_specific_team.text = it.name
        })
    }

    private fun setUpTabs() {
        val adapter = TeamViewPagerAdapter(childFragmentManager)
        adapter.addFragment(TeamPlayersFragment(), getString(R.string.players_tab))
        adapter.addFragment(TeamOverviewFragment(), getString(R.string.overview_tab))
        adapter.addFragment(TeamMatchesFragment(), getString(R.string.matches_tab))

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
}