package it.log.tably.sections.adapters

import android.app.PendingIntent.getActivity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.database.*

import it.log.tably.R
import it.log.tably.TablyApplication
import it.log.tably.models.Game
import it.log.tably.utils.Avatar
import kotlinx.android.synthetic.main.game_card.view.*

/**
 * Created by log on 13/01/18.
 */
// Provide a suitable constructor (depends on the kind of dataset)

class GamesAdapter () : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    private val TAG = "GamesAdapter"

    private val gamesReference = FirebaseDatabase.getInstance().getReference("matches")
    private val playersReference = FirebaseDatabase.getInstance().getReference("players")

    private var gamesMap = HashMap<String, HashMap<*,*>>()
    private var playersMap = HashMap<String, HashMap<*, *>>()

    private lateinit var idsOfGames : Array<String>

    init {
        gamesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                gamesMap = dataSnapshot.value as HashMap<String, HashMap<*, *>>

                idsOfGames = gamesMap.keys.toTypedArray()

                // Games are sorted by Firebase id (that seems to be incremental...)
                idsOfGames.sortDescending()

                Log.d(TAG, idsOfGames.size.toString())
                notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        playersReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                playersMap = dataSnapshot.value as HashMap<String, HashMap<*, *>>
                notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

    }



    // each data item is just a string in this case
    class ViewHolder(var mLL: LinearLayout) : RecyclerView.ViewHolder(mLL) {

        val reporterTV: TextView = mLL.reporter
        val dateTV: TextView = mLL.date
        val blueAttackerNicknameTV: TextView = mLL.blue_attacker_nickname
        val redAttackerNicknameTV: TextView = mLL.red_attacker_nickname
        val redDefenderNicknameTV: TextView = mLL.red_defender_nickname
        val blueDefenderNicknameTV: TextView = mLL.blue_defender_nickname
        val scoreTV: TextView = mLL.score
        val reporterIV : ImageView = mLL.reporter_avatar
        val blueAttackerIV : ImageView = mLL.blue_attacker
        val blueDefenderIV : ImageView = mLL.blue_defender
        val redAttackerIV : ImageView = mLL.red_attacker
        val redDefendeIV : ImageView = mLL.red_defender
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): GamesAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.game_card, parent, false) as LinearLayout
        // set the view's size, margins, paddings and layout parameters

        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        val game = gamesMap[idsOfGames[position]]

        val reporter = playersMap[game?.get("posterId")]
        Log.d(TAG, reporter.toString())

        val blueAttacker = playersMap[game?.get("bluAttack")]
        val redAttacker = playersMap[game?.get("redAttack")]
        val redDefense = playersMap[game?.get("redDefense")]
        val bluDefense = playersMap[game?.get("bluDefense")]

        Log.d(TAG, game.toString())

        holder.reporterTV.text = reporter?.get("nickname").toString()
        holder.dateTV.text = game?.get("date").toString()
        holder.blueAttackerNicknameTV.text = blueAttacker?.get("nickname").toString()
        holder.redAttackerNicknameTV.text = redAttacker?.get("nickname").toString()
        holder.redDefenderNicknameTV.text = redDefense?.get("nickname").toString()
        holder.blueDefenderNicknameTV.text = bluDefense?.get("nickname").toString()
        holder.scoreTV.text = """${game?.get("bluScore").toString()} - ${game?.get("redScore").toString()}"""

        var avatarId: Int

        avatarId = Avatar.map.getValue(reporter?.get("nickname").toString())
        holder.reporterIV.setImageDrawable(ContextCompat.getDrawable(holder.mLL.context, avatarId))

        avatarId = Avatar.map.getValue(blueAttacker?.get("nickname").toString())
        holder.blueAttackerIV.setImageDrawable(ContextCompat.getDrawable(holder.mLL.context, avatarId))

        avatarId = Avatar.map.getValue(bluDefense?.get("nickname").toString())
        holder.blueDefenderIV.setImageDrawable(ContextCompat.getDrawable(holder.mLL.context, avatarId))

        avatarId = Avatar.map.getValue(redAttacker?.get("nickname").toString())
        holder.redAttackerIV.setImageDrawable(ContextCompat.getDrawable(holder.mLL.context, avatarId))

        avatarId = Avatar.map.getValue(redDefense?.get("nickname").toString())
        holder.redDefendeIV.setImageDrawable(ContextCompat.getDrawable(holder.mLL.context, avatarId))

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return gamesMap.size
    }
}
