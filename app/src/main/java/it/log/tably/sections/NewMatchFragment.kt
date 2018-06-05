package it.log.tably.sections

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import it.log.tably.R
import it.log.tably.TablyApplication
import it.log.tably.TablyApplication.Companion.playersMap
import it.log.tably.models.FirebaseGame
import it.log.tably.models.Game
import kotlinx.android.synthetic.main.fragment_games.view.*
import kotlinx.android.synthetic.main.fragment_new_match.*
import kotlinx.android.synthetic.main.rankbar.*
import java.text.SimpleDateFormat
import java.util.*

private lateinit var scoreBlueTeam: EditText
private var scoreRedTeam: EditText? = null

class NewMatchFragment : Fragment() {

    val gamesReference = FirebaseDatabase.getInstance().getReference("matches")



    private var isPlayerSelected = false
    private var selectedPlayerNickname : String = ""
    private var selectedPlayerImageUrl : String = ""

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

        val currentUserEmail : String = FirebaseAuth.getInstance().currentUser!!.email!!
        var currentUserId : String = ""
        var currentUserImageUrl : String = ""

        for (player in TablyApplication.playersMap) {
            playerKeyMap.put(player.value.emailAddress, player.key)
            var playerLinearLayout = LinearLayout(activity)
            playerLinearLayout.orientation = LinearLayout.VERTICAL
            var imageView = ImageView(activity)
        //    imageView.layoutParams.height = 20
         //   setPlayerImage(player.value.imageUrl, imageView)
            val cropOptions = RequestOptions().centerCrop().placeholder(R.drawable.empty_player)
                    .circleCrop().sizeMultiplier(0.15f)
            Glide.with(context!!)
                    .load(player.value.imageUrl)
                    .apply(cropOptions)
                    .into(imageView)

            var text = TextView(activity)
            text.gravity = Gravity.CENTER
            text.setText(player.value.nickname)

            playerLinearLayout.addView(imageView)
            playerLinearLayout.addView(text)
            playerLinearLayout.setPadding(10, 10, 10, 10)
            playerLinearLayout.setOnClickListener{
                selectedPlayerNickname = text.text.toString()
                selectedPlayerImageUrl = player.value.imageUrl
                isPlayerSelected = true
                Toast.makeText(activity, "Selected: " + selectedPlayerNickname, Toast.LENGTH_SHORT).show()
            }
            this.players_linear.addView(playerLinearLayout)

            if (player.value.emailAddress == currentUserEmail) {
                currentUserId = player.value.nickname
                currentUserImageUrl = player.value.imageUrl
            }
        }

        reporter.text = currentUserId
        setPlayerImage(currentUserImageUrl, reporter_avatar)

        blue_attacker.setOnClickListener{
            if (isPlayerSelected) {
                blue_attacker_nickname.text = selectedPlayerNickname
                setPlayerImage(selectedPlayerImageUrl, blue_attacker)
                isPlayerSelected = false
            }
        }
        blue_defender.setOnClickListener{
            if (isPlayerSelected) {
                blue_defender_nickname.text = selectedPlayerNickname
                setPlayerImage(selectedPlayerImageUrl, blue_defender)
                isPlayerSelected = false
            }
        }
        red_attacker.setOnClickListener{
            if (isPlayerSelected) {
                red_attacker_nickname.text = selectedPlayerNickname
                setPlayerImage(selectedPlayerImageUrl, red_attacker)
                isPlayerSelected = false
            }
        }
        red_defender.setOnClickListener{
            if (isPlayerSelected) {
                red_defender_nickname.text = selectedPlayerNickname
                setPlayerImage(selectedPlayerImageUrl, red_defender)
                isPlayerSelected = false
            }
        }

        confirm_add_match.setOnClickListener{
            val now = Date()
            val df = SimpleDateFormat("dd.MM.yyyy")
            val date = df.format(now)
            val hf = SimpleDateFormat("HH:mm")
            val hour = hf.format(now)
            val dateAndTime = date + " - " + hour

            var newMatch = FirebaseGame(
                    posterId = playerKeyMap[reporter.text!!].toString(),
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

        var playerKeyMap = HashMap<String, String>()
    }

    private fun setPlayerImage(imageUrl: String, imageView: ImageView) {
        val cropOptions = RequestOptions().centerCrop()
                .circleCrop().placeholder(R.drawable.empty_player)

        Glide.with(context!!)
                .load(imageUrl)
                .apply(cropOptions)
                .into(imageView)
    }
}