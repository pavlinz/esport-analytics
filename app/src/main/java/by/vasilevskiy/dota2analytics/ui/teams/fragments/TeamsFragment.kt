package by.vasilevskiy.dota2analytics.ui.teams.fragments

import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.vasilevskiy.dota2analytics.helpers.NetworkManager
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.adapters.TeamAdapter
import by.vasilevskiy.dota2analytics.models.Team
import by.vasilevskiy.dota2analytics.utils.hide
import by.vasilevskiy.dota2analytics.utils.showNoNetworkConnectionToast
import kotlinx.android.synthetic.main.fragment_teams.*
import java.lang.Exception

class TeamsFragment : BaseFragment(R.layout.fragment_teams), TeamAdapter.OnTeamListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var response: List<Team>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setViewModel()
        setRecyclerView()
        observeTeams()
        setOnQueryTextListener()
    }

    private fun setRecyclerView() {
        recyclerView = activity?.findViewById(R.id.recycler_view_teams)!!
        recyclerView?.layoutManager = LinearLayoutManager(activity)
    }

    private fun observeTeams() {
        viewModel.result.observe(viewLifecycleOwner, Observer { listOfTeams ->
            listOfTeams?.let {
                viewModel.searchList.clear()
                viewModel.searchList.addAll(listOfTeams)
                recyclerView?.adapter = TeamAdapter(listOfTeams, requireActivity(), this)
                progressBar.hide()
            }
        })
    }

    private fun setOnQueryTextListener() {
        search_view_teams.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                try {
                    response = viewModel.result.value!!
                    response = (response as MutableList).filter { it.name.contains(p0 as CharSequence, true) }

                    recyclerView?.adapter = TeamAdapter(response, requireActivity(), this@TeamsFragment)
                    viewModel.searchList.clear()
                    viewModel.searchList.addAll(response)
                } catch (e: Exception) { }

                return true
            }
        })
    }

    override fun onTeamClick(position: Int) {
        if (NetworkManager.isNetworkAvailable(requireContext())) {
            var bundle = Bundle()
            bundle.putInt("position", position)

            findNavController().navigate(R.id.action_teamsFragment_to_specificTeamFragment, bundle)
        } else {
            activity?.showNoNetworkConnectionToast()
        }
    }
}