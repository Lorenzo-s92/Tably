package it.log.tably.sections

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.FirebaseDatabase
import it.log.tably.R
import it.log.tably.TablyApplication
import it.log.tably.models.FirebaseGame
import it.log.tably.models.Game
import kotlinx.android.synthetic.main.fragment_games.view.*
import kotlinx.android.synthetic.main.fragment_new_match.*
import java.text.SimpleDateFormat
import java.util.*

private lateinit var scoreBlueTeam: EditText
private var scoreRedTeam: EditText? = null

class NewMatchFragment : Fragment() {

    val gamesReference = FirebaseDatabase.getInstance().getReference("matches")

    private var playerKeyMap = HashMap<String, String>()

    private var isPlayerSelected = false
    private var selectedPlayer = String()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_match, container, false)

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
    //    view.cards_containers.setHasFixedSize(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        val scoreBlueTeam = score_blue_team.text
//        val scoreRedTeam = score_red_team.text

        for (player in TablyApplication.playersMap) {
            playerKeyMap.put(player.value.nickname, player.key)
            var text = TextView(activity)
            text.setText(player.value.nickname)
            text.setPadding(10, 10, 10, 10)
            text.setOnClickListener {
                selectedPlayer = text.text.toString()
                isPlayerSelected = true
                Toast.makeText(activity, "Selected: " + selectedPlayer, Toast.LENGTH_SHORT).show()
            }
            this.players_linear.addView(text)
        }

        reporter_avatar.setOnClickListener{
            if (isPlayerSelected)
            reporter.text = selectedPlayer
            isPlayerSelected = false
        }
        blue_attacker.setOnClickListener{
            if (isPlayerSelected)
                blue_attacker_nickname.text = selectedPlayer
            isPlayerSelected = false
        }
        blue_defender.setOnClickListener{
            if (isPlayerSelected)
                blue_defender_nickname.text = selectedPlayer
            isPlayerSelected = false
        }
        red_attacker.setOnClickListener{
            if (isPlayerSelected)
                red_attacker_nickname.text = selectedPlayer
            isPlayerSelected = false
        }
        red_defender.setOnClickListener{
            if (isPlayerSelected)
                red_defender_nickname.text = selectedPlayer
            isPlayerSelected = false
        }

        confirm_add_match.setOnClickListener{
            val now = Date()
            val df = SimpleDateFormat("dd.MM.yyyy")
            val date = df.format(now)
            val hf = SimpleDateFormat("HH:mm")
            val hour = hf.format(now)
            val dateAndTime = date + " - " + hour

            var newMatch = FirebaseGame(
                    posterId = playerKeyMap[reporter.text].toString(),
                    date = dateAndTime,
                    bluAttack =  playerKeyMap[blue_attacker_nickname.text].toString(),
                    redAttack =  playerKeyMap[red_attacker_nickname.text].toString(),
                    redDefense = playerKeyMap[red_defender_nickname.text].toString(),
                    bluDefense = playerKeyMap[blue_defender_nickname.text].toString(),
                    bluScore = score_blue_team.text.toString().toLong(),
                    redScore = score_red_team.text.toString().toLong(),
                    likes = 0)

            var newPost = gamesReference.push()
            newPost.setValue(newMatch)
        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment GamesFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): NewMatchFragment {
            val fragment = NewMatchFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}