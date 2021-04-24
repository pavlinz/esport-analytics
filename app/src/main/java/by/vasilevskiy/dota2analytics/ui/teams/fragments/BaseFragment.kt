package by.vasilevskiy.dota2analytics.ui.teams.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import by.vasilevskiy.dota2analytics.Application
import by.vasilevskiy.dota2analytics.ui.teams.viewmodel.TeamsViewModel
import by.vasilevskiy.dota2analytics.ui.teams.viewmodel.TeamsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes val layout: Int) : Fragment() {

    lateinit var viewModel: TeamsViewModel
    private lateinit var viewModelFactory: TeamsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    open fun setViewModel() {
        viewModelFactory = TeamsViewModelFactory(
            (activity?.application as Application).repository,
            requireActivity()
        )

        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(TeamsViewModel::class.java)
    }

}