package by.vasilevskiy.dota2analytics.ui.teams.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.vasilevskiy.dota2analytics.R
import kotlinx.android.synthetic.main.fragment_team_overview.*

class TeamOverviewFragment : BaseFragment(R.layout.fragment_team_overview) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        fillTheFields()
    }

    private fun fillTheFields() {
        tv_wins_count.text = viewModel.selected.value?.wins.toString()
        tv_losses_count.text = viewModel.selected.value?.losses.toString()
        tv_rating_count.text = viewModel.selected.value?.rating.toString()
    }
}