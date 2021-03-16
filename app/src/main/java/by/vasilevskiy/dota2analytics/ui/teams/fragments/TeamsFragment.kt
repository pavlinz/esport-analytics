package by.vasilevskiy.dota2analytics.ui.teams.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.adapters.TeamAdapter
import by.vasilevskiy.dota2analytics.ui.teams.viewmodel.TeamsViewModel
import kotlinx.android.synthetic.main.fragment_teams.*

class TeamsFragment : Fragment(), TeamAdapter.OnTeamListener {

    private val TAG = "TeamsFragment"

    private lateinit var viewModel: TeamsViewModel

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamsViewModel::class.java)

        progressBar.visibility = View.VISIBLE

        recyclerView = activity?.findViewById(R.id.recycler_view_teams)!!

        recyclerView?.layoutManager = LinearLayoutManager(activity)

        viewModel.result.observe(viewLifecycleOwner, Observer {
            recyclerView?.adapter = TeamAdapter(it, requireActivity(), this)

            progressBar.visibility = View.INVISIBLE
        })
    }

    override fun onTeamClick(position: Int) {
        var bundle = Bundle()
        bundle.putInt("arg1", position)

        findNavController().navigate(R.id.action_teamsFragment_to_specificTeamFragment, bundle)
    }
}