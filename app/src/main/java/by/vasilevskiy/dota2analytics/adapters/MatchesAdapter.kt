package by.vasilevskiy.dota2analytics.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.models.Match
import com.bumptech.glide.Glide

class MatchesAdapter(
    private val list: List<Match>,
    private val context: Context
) : RecyclerView.Adapter<MatchesAdapter.MatchViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MatchViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.match_item, parent, false)
        return MatchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val item = list[position]

        holder.leagueNameTextView?.text = item.league_name
        Glide.with(context).load(item.opposing_team_logo).into(holder.opposingTeamImageView!!)
        holder.opposingTeamTextView?.text = item.opposing_team_name
        if ((item.radiant && item.radiant_win) || (!item.radiant && !item.radiant_win)) {
            holder.resultTextView?.text = context.getString(R.string.won_match)
            holder.resultTextView?.setTextColor(ContextCompat.getColor(context, R.color.green))
        } else{
            holder.resultTextView?.text = context.getString(R.string.lost_match)
            holder.resultTextView?.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
    }

    override fun getItemCount() = list.size

    class MatchViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var leagueNameTextView: TextView? = null
        var opposingTeamImageView: ImageView? = null
        var opposingTeamTextView: TextView? = null
        var resultTextView: TextView? = null

        init {
            leagueNameTextView = itemView?.findViewById(R.id.tv_league_name)
            opposingTeamImageView = itemView?.findViewById(R.id.civ_opposing_team_logo)
            opposingTeamTextView = itemView?.findViewById(R.id.tv_opposing_team_name)
            resultTextView = itemView?.findViewById(R.id.tv_result)
        }
    }

}