package by.vasilevskiy.dota2analytics.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.vasilevskiy.dota2analytics.R
import by.vasilevskiy.dota2analytics.data.network.provideApi
import by.vasilevskiy.dota2analytics.models.Player
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerAdapter(
    private val list: List<Player>,
    private val context: Context
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent, false)
        return PlayerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        list[position].is_current_team_member?.let {
            if (it) {
                var url: String? = null

                val job = CoroutineScope(Dispatchers.IO).launch {
                    url = provideApi().getPlayerData(list[position].account_id).profile.avatarmedium
                }

                CoroutineScope(Dispatchers.Main).launch {
                    job.join()
                    Glide.with(context).load(url).into(holder.playerImageView!!)
                }

                val name = list[position].name
                holder.playerNameTextView?.text = name
            }
        }
    }

    override fun getItemCount() = list.size

    class PlayerViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var playerImageView: CircleImageView? = null
        var playerNameTextView: TextView? = null

        init {
            playerImageView = itemView?.findViewById(R.id.civ_player_logo)
            playerNameTextView = itemView?.findViewById(R.id.tv_player_name)
        }
    }
}