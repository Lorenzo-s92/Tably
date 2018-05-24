package it.log.tably.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import it.log.tably.R
import it.log.tably.models.PlayerStats
import it.log.tably.enums.RankingType
import it.log.tably.utils.Avatar
import kotlinx.android.synthetic.main.rank_card.view.*


class RankAdapter(var orderedStats: List<PlayerStats>, var rankingType: RankingType) : RecyclerView.Adapter<RankAdapter.ViewHolder>() {

//    var idsOfPlayers = Database.playerStats.keys.toTypedArray()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.rank_card, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return orderedStats.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val stat = orderedStats[position]

        holder.playerTV.text = stat.player.nickname
        holder.playerAvatarIV.setImageResource(Avatar.map.getValue(stat.player.emailAddress))

        holder.score1TV.text = getScore1(stat)
        holder.score2TV.text = getScore2(stat)
        holder.score3TV.text = getScore3(stat)
        holder.score4TV.text = getScore4(stat)

    }

    fun replaceDataSetWith(dataSet : List<PlayerStats>) {
        orderedStats = dataSet
        notifyDataSetChanged()
    }

    fun reverseDataset(): Unit {
        orderedStats = orderedStats.reversed()
        notifyDataSetChanged()
    }

    private fun toPercentage(aValue: String) : String {
        return "~$aValue%"
    }

    private fun getScore1(playerStats: PlayerStats): String? {

        var score: String? = null
        when (rankingType) {
            RankingType.SHAMES -> {
                score = playerStats.shames.toString()
            }
        }

        return score
    }

    private fun getScore2(playerStats: PlayerStats): String? {

        var score: String? = null
        when (rankingType) {
            RankingType.SHAMES -> {
                score = playerStats.getShamesAsPercentage().toString()
            }
        }
        return toPercentage(score)
    }

    private fun getScore3(playerStats: PlayerStats): String? {

        var score: String? = null
        when (rankingType) {
            RankingType.SHAMES -> {
                score = playerStats.attackShames.toString()
            }
        }
        return score
    }

    private fun getScore4(playerStats: PlayerStats): String? {

        var score: String? = null
        when (rankingType) {
            RankingType.SHAMES -> {
                score = playerStats.defenseShames.toString()
            }
        }
        return score
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val playerTV = itemView.player as TextView
        val playerAvatarIV = itemView.player_avatar as ImageView

        val score1TV = itemView.score1 as TextView
        val score2TV = itemView.score2 as TextView
        val score3TV = itemView.score3 as TextView
        val score4TV = itemView.score4 as TextView



    }
}