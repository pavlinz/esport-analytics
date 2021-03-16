package by.vasilevskiy.dota2analytics.ui.teams.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.ui.teams.viewmodel.TeamsViewModel

class SpecificTeamFragment : Fragment() {

    private val TAG = "SpecificTeamFragment"

    private lateinit var viewModel: TeamsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_specific_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(TeamsViewModel::class.java)

        viewModel.selected.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "Tuta")
        })

        arguments?.getInt("arg1")?.let { viewModel.select(it) }
    }
}