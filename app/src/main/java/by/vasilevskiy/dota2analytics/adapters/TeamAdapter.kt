package by.vasilevskiy.dota2analytics.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.data.Team
import by.vasilevskiy.dota2analytics.helpers.TeamsLogoHelper
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class TeamAdapter(
    private val list: List<Team>,
    private val context: Context,
    private val onTeamListener: OnTeamListener
) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    private val TAG = "TeamAdapter"

    private val teamsLogoHelper: TeamsLogoHelper = TeamsLogoHelper()

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false)
        return TeamViewHolder(itemView, onTeamListener)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val item = list[position]

        if (teamsLogoHelper.checkProblemTeam(item.name)) {
            val url = teamsLogoHelper.getImageUrl(item.name)

            Glide.with(context).load(url).into(holder.teamImageView!!)
            holder.teamNameTextView?.text = item.name
            return
        }
        Glide.with(context).load(item.logo_url).into(holder.teamImageView!!)
        holder.teamNameTextView?.text = item.name
    }

    class TeamViewHolder(itemView: View, onTeamListener: OnTeamListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var teamImageView: CircleImageView? = null
        var teamNameTextView: TextView? = null

        private val onTeamListener: OnTeamListener

        init {
            teamImageView = itemView?.findViewById(R.id.civ_team_logo)
            teamNameTextView = itemView?.findViewById(R.id.tv_team_name)

            this.onTeamListener = onTeamListener

            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onTeamListener.onTeamClick(adapterPosition)
        }
    }

    interface OnTeamListener {
        fun onTeamClick(position: Int)
    }
}