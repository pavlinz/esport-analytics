package by.vasilevskiy.dota2analytics.ui.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.data.local.AppDatabase
import by.vasilevskiy.dota2analytics.data.local.Vote
import by.vasilevskiy.dota2analytics.data.local.VoteDao
import by.vasilevskiy.dota2analytics.data.local.VotingOptions
import by.vasilevskiy.dota2analytics.ui.main.models.FirebaseUpcomingMatch
import by.vasilevskiy.dota2analytics.ui.main.parsers.UpcomingMatchParser
import by.vasilevskiy.dota2analytics.ui.main.repo.GamesRepoImpl
import by.vasilevskiy.dota2analytics.ui.main.viewmodel.MainViewModel
import by.vasilevskiy.dota2analytics.ui.main.viewmodel.SpecificMatchViewModelFactory
import by.vasilevskiy.dota2analytics.utils.remove
import by.vasilevskiy.dota2analytics.utils.show
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_specific_upcoming_match.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


// TODO: refactor this code
class SpecificUpcomingMatchFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var database: FirebaseDatabase
    private lateinit var match: FirebaseUpcomingMatch

    private var db: AppDatabase? = null
    private var votesDao: VoteDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_specific_upcoming_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(
            requireActivity(),
            SpecificMatchViewModelFactory(
                GamesRepoImpl(UpcomingMatchParser())
            )
        ).get(MainViewModel::class.java)

        observeSelected()
        selectSpecificTeam()
        setupDatabase()
        checkVotes()
        setupListeners()
    }

    private fun observeSelected() {
        viewModel.selectedMatch.observe(viewLifecycleOwner, Observer { upcomingMatch ->
            Glide.with(this).load(upcomingMatch.firstTeamLogo).into(img_first_team_upcoming_match)
            Glide.with(this).load(upcomingMatch.secondTeamLogo).into(img_second_team_upcoming_match)
            tv_first_team_name_upcoming_match.text = upcomingMatch.firstTeamName
            tv_second_team_name_upcoming_match.text = upcomingMatch.secondTeamName
            tv_date_or_score.text = upcomingMatch.versus
        })
    }

    private fun selectSpecificTeam() {
        arguments?.getInt("position")?.let { viewModel.selectUpcomingMatch(it) }
    }

    private fun setupDatabase() {
        database = Firebase.database
        db = AppDatabase.getAppDataBase(context = requireContext())
        votesDao = db?.voteDao()

        val matchName = viewModel.getRefactoredName()

        val allMatchesRef = database.getReference("upcoming_matches")
        match =
            FirebaseUpcomingMatch(
                name = matchName
            )

        allMatchesRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var exists = false
                for (firebaseMatch in snapshot.children) {
                    if (firebaseMatch.key.equals(matchName)) {
                        exists = true
                    }
                }
                if (!exists) {
                    val ref = database.getReference("upcoming_matches").child(matchName)
                    ref.setValue(match)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun checkVotes() {
        database.getReference("upcoming_matches").child(match.name).get()
            .addOnSuccessListener { snapshot ->

                try {
                    match = snapshot.getValue<FirebaseUpcomingMatch>()!!

                    CoroutineScope(Dispatchers.IO).launch {

                        val vote = votesDao?.getVoteById(match.name)
                        if (vote != null) {
                            CoroutineScope(Dispatchers.Main).launch {
                                calculatePercentages()
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Firebase", e.message.toString())
                }
            }
    }

    private fun setupListeners() {
        btn_first_team_vote.setOnClickListener {
            database.getReference("upcoming_matches").child(match.name).get()
                .addOnSuccessListener { snapshot ->

                    match = snapshot.getValue<FirebaseUpcomingMatch>()!!

                    val vote = Vote(name = match.name, team = VotingOptions.FIRST.ordinal)
                    CoroutineScope(Dispatchers.IO).launch {
                        votesDao?.insert(vote)
                    }

                    match.countFirstTeamVotes += 1
                    val map = mapOf<String, Any>("countFirstTeamVotes" to match.countFirstTeamVotes)
                    database.getReference("upcoming_matches").child(match.name).updateChildren(map)

                    calculatePercentages()
                }
        }

        btn_second_team_vote.setOnClickListener {
            database.getReference("upcoming_matches").child(match.name).get()
                .addOnSuccessListener { snapshot ->

                    match = snapshot.getValue<FirebaseUpcomingMatch>()!!

                    val vote = Vote(name = match.name, team = VotingOptions.SECOND.ordinal)
                    CoroutineScope(Dispatchers.IO).launch {
                        votesDao?.insert(vote)
                    }

                    match.countSecondTeamVotes += 1
                    val map =
                        mapOf<String, Any>("countSecondTeamVotes" to match.countSecondTeamVotes)
                    database.getReference("upcoming_matches").child(match.name).updateChildren(map)

                    calculatePercentages()
                }
        }
    }

    private fun calculatePercentages() {
        btn_first_team_vote?.remove()
        btn_second_team_vote?.remove()

        val allVotes = match.countFirstTeamVotes + match.countSecondTeamVotes

        tv_first_team_percentages?.show()

        if (match.countFirstTeamVotes == 0) {
            tv_first_team_percentages?.text = "0%"
        } else {
            tv_first_team_percentages?.text =
                (match.countFirstTeamVotes.toFloat() / allVotes.toFloat() * 100).toString()
                    .substring(0, 4) + "%"
        }

        tv_second_team_percentages?.show()

        if (match.countSecondTeamVotes == 0) {
            tv_second_team_percentages?.text = "0%"
        } else {
            tv_second_team_percentages?.text =
                (match.countSecondTeamVotes.toFloat() / allVotes.toFloat() * 100).toString()
                    .substring(0, 4) + "%"
        }
    }
}