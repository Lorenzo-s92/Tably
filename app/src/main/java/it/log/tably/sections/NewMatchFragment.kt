package it.log.tably.sections

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import it.log.tably.R
import it.log.tably.models.FirebaseGame
import it.log.tably.models.Game
import kotlinx.android.synthetic.main.fragment_games.view.*
import kotlinx.android.synthetic.main.fragment_new_match.*

private lateinit var scoreBlueTeam: EditText
private var scoreRedTeam: EditText? = null

class NewMatchFragment : Fragment() {

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

        val scoreBlueTeam = score_blue_team.text
        val scoreRedTeam = score_red_team.text

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