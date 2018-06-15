package it.log.tably.viewholders

import android.support.v7.widget.RecyclerView
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import it.log.tably.R
import it.log.tably.models.Game
import kotlinx.android.synthetic.main.game_card.view.*
import com.bumptech.glide.request.RequestOptions
import it.log.tably.models.Player


class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val reporterTV: TextView = itemView.reporter
    private val dateTV: TextView = itemView.date
    private val blueAttackerNicknameTV: TextView = itemView.blue_attacker_nickname
    private val redAttackerNicknameTV: TextView = itemView.red_attacker_nickname
    private val redDefenderNicknameTV: TextView = itemView.red_defender_nickname
    private val blueDefenderNicknameTV: TextView = itemView.blue_defender_nickname
    private val scoreTV: TextView = itemView.score
    private val reporterIV : ImageView = itemView.reporter_avatar
    private val blueAttackerIV : ImageView = itemView.blue_attacker
    private val blueDefenderIV : ImageView = itemView.blue_defender
    private val redAttackerIV : ImageView = itemView.red_attacker
    private val redDefenderIV : ImageView = itemView.red_defender

    private var reporterId : String = ""

    fun bindToGame(game: Game) {
        reporterTV.text = game.reporter.nickname
        dateTV.text = game.date
        blueAttackerNicknameTV.text = game.blueAttacker.nickname
        redAttackerNicknameTV.text = game.redAttacker.nickname
        redDefenderNicknameTV.text = game.redDefender.nickname
        blueDefenderNicknameTV.text = game.blueDefender.nickname

        scoreTV.text = game.score

        reporterId = game.reporter.id

        setPlayerImage(game.reporter.imageUrl, reporterIV )
        setPlayerImage(game.blueAttacker.imageUrl, blueAttackerIV )
        setPlayerImage(game.blueDefender.imageUrl, blueDefenderIV )
        setPlayerImage(game.redAttacker.imageUrl, redAttackerIV )
        setPlayerImage(game.redDefender.imageUrl, redDefenderIV )
    }

    fun getReporterId() : String {
        return reporterId
    }

    private fun getDrawable(id: Int) : Drawable? {
        return ContextCompat.getDrawable(this.itemView.context, id)
    }

    private fun setPlayerImage(imageUrl: String, imageView: ImageView) {
        val cropOptions = RequestOptions().centerCrop().placeholder(R.drawable.empty_player)

        Glide.with(itemView)
                .load(imageUrl)
                .apply(cropOptions)
                .into(imageView)
    }
}