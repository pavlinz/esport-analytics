package by.vasilevskiy.dota2analytics.ui.teams.fragments

import android.os.Bundle
import android.view.View
import by.vasilevskiy.dota2analytics.R
import kotlinx.android.synthetic.main.fragment_team_overview.*

class TeamOverviewFragment : BaseFragment(R.layout.fragment_team_overview) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        fillTheFields()
    }

    private fun fillTheFields() {
        tv_wins_count.text = viewModel.getSelectedTeam()[0]
        tv_losses_count.text = viewModel.getSelectedTeam()[1]
        tv_rating_count.text = viewModel.getSelectedTeam()[2]
    }
}