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

class SpecificTeamFragment : Fragment() {

    private lateinit var viewModel: TeamsViewModel
    private lateinit var viewModelFactory: TeamsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_specific_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpTabs()

        viewModelFactory = TeamsViewModelFactory((activity?.application as Application).repository)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(TeamsViewModel::class.java)

        viewModel.selected.observe(viewLifecycleOwner, Observer {
            Glide.with(this).load(it.logo_url).into(team_logo_specific_team)
            tv_team_name_specific_team.text = it.name
        })

        arguments?.getInt("arg1")?.let { viewModel.select(it) }
    }

    private fun setUpTabs() {
        val adapter = TeamViewPagerAdapter(childFragmentManager)
        Log.d("Test", adapter.toString())
        adapter.addFragment(TeamPlayersFragment(), "Players")
        adapter.addFragment(TeamOverviewFragment(), "Overview")
        adapter.addFragment(TeamMatchesFragment(), "Matches")

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
}