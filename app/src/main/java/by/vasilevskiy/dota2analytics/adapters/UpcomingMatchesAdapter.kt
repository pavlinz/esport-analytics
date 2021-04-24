package by.vasilevskiy.dota2analytics.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.models.UpcomingMatch
import by.vasilevskiy.dota2analytics.utils.Constants
import com.bumptech.glide.Glide

class UpcomingMatchesAdapter(
    private val list: List<UpcomingMatch>,
    private val onUpcomingTeamListener: OnUpcomingTeamListener
) :
    RecyclerView.Adapter<UpcomingMatchesAdapter.UpcomingMatchesViewHolder>() {

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMatchesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.upcoming_match_item, parent, false)
        return UpcomingMatchesViewHolder(view, onUpcomingTeamListener)
    }

    override fun onBindViewHolder(holder: UpcomingMatchesViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class UpcomingMatchesViewHolder(
        itemView: View,
        upcomingTeamListener: OnUpcomingTeamListener
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var onUpcomingTeamListener: OnUpcomingTeamListener = upcomingTeamListener

        private var leagueNameTextView: TextView =
            itemView.findViewById(R.id.tv_league_name_upcoming_matches)
        private var leagueLogo: ImageView = itemView.findViewById(R.id.img_league_logo)
        private var firstTeamLogo: ImageView = itemView.findViewById(R.id.img_first_team_logo)
        private var firstTeamName: TextView = itemView.findViewById(R.id.tv_first_team_name)
        private var secondTeamLogo: ImageView = itemView.findViewById(R.id.img_second_team_logo)
        private var secondTeamName: TextView = itemView.findViewById(R.id.tv_second_team_name)
        private var matchTime: TextView = itemView.findViewById(R.id.tv_match_time)
        private var versusTextView: TextView = itemView.findViewById(R.id.tv_versus)

        fun bind(match: UpcomingMatch) {
            leagueNameTextView.text = match.leagueName
            Glide.with(itemView.context).load(match.leagueLogo).into(leagueLogo)

            if (match.firstTeamLogo != Constants.FORBIDDEN_URL) {
                Glide.with(itemView.context).load(match.firstTeamLogo).into(firstTeamLogo)
            } else {
                Glide.with(itemView.context).load(UpcomingMatch.URL).into(firstTeamLogo)
            }

            if (match.secondTeamLogo != Constants.FORBIDDEN_URL) {
                Glide.with(itemView.context).load(match.secondTeamLogo).into(secondTeamLogo)
            } else {
                Glide.with(itemView.context).load(UpcomingMatch.URL).into(secondTeamLogo)
            }

            firstTeamName.text = match.firstTeamName
            secondTeamName.text = match.secondTeamName
            versusTextView.text = match.versus
            matchTime.text = match.matchTime

            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onUpcomingTeamListener.onUpcomingTeamClick(adapterPosition)
        }
    }

    interface OnUpcomingTeamListener {
        fun onUpcomingTeamClick(position: Int)
    }
}