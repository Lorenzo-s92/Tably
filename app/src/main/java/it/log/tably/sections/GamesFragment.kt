package it.log.tably.sections

import android.content.Context
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import it.log.tably.R
import it.log.tably.models.FirebaseGame
import it.log.tably.models.Game
import it.log.tably.viewholders.GameViewHolder
import kotlinx.android.synthetic.main.fragment_games.*
import kotlinx.android.synthetic.main.fragment_games.view.*
import android.support.annotation.NonNull
import android.support.v7.app.AlertDialog
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.nio.file.Files.delete
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import it.log.tably.TablyApplication
import it.log.tably.TablyApplication.Companion.playersMap
import it.log.tably.utils.StatsFactory
import java.util.HashMap


const val TAG  = "GamesFragment"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GamesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GamesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GamesFragment : Fragment() {

    // TODO: Rename and change types of parameters

    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mFirebaseRecyclerAdapter: FirebaseRecyclerAdapter<*,*>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_games, container, false)

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        view.cards_containers.setHasFixedSize(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // use a linear layout manager
        mLayoutManager = LinearLayoutManager(context)
        (mLayoutManager as LinearLayoutManager).reverseLayout = true
        (mLayoutManager as LinearLayoutManager).stackFromEnd = true


        cards_containers.cards_containers.layoutManager = mLayoutManager

        // Set up FirebaseRecyclerAdapter with the Query
        val gamesQuery = FirebaseDatabase.getInstance().getReference("matches").orderByKey()

        val options = FirebaseRecyclerOptions.Builder<FirebaseGame>()
                .setQuery(gamesQuery, FirebaseGame::class.java)
                .build()

        // specify an adapter
        mFirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<FirebaseGame, GameViewHolder>(options) {

            override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GameViewHolder {
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                return GameViewHolder(layoutInflater.inflate(R.layout.game_card, viewGroup, false))
            }

            override fun onBindViewHolder(holder: GameViewHolder?, position: Int, model: FirebaseGame?) {
                if (holder != null && model != null) {
                    val game = Game(model, mFirebaseRecyclerAdapter.getRef(position).key)
                    holder.bindToGame(game)
                } else {
                    Log.d(TAG, "Error during onBindViewHolder")
                }

            }

        }

        cards_containers.adapter = mFirebaseRecyclerAdapter


        ItemTouchHelper(object : ItemTouchHelper.Callback() {

            override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
                val selectedMatch = FirebaseDatabase.getInstance().getReference("matches")
                        .child(mFirebaseRecyclerAdapter.getRef(viewHolder!!.layoutPosition).key)

                val reporterId = (viewHolder as GameViewHolder).getReporterId()
                if (playersMap.getValue(reporterId).emailAddress == FirebaseAuth.getInstance().currentUser!!.email!!) {
                    return makeMovementFlags(0, ItemTouchHelper.LEFT) // ItemTouchHelper.LEFT
                } else return 0
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val selectedMatch = FirebaseDatabase.getInstance().getReference("matches")
                        .child(mFirebaseRecyclerAdapter.getRef(viewHolder.layoutPosition).key)


                AlertDialog.Builder(context!!)
                        .setTitle("Cancel match?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener { dialog, whichButton ->
                                selectedMatch.setValue(null) })
                        .show()


                mFirebaseRecyclerAdapter.notifyDataSetChanged()
            }
        }).attachToRecyclerView(cards_containers)

    }

    override fun onStart() {
        super.onStart()
        mFirebaseRecyclerAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mFirebaseRecyclerAdapter.stopListening()

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
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
        fun newInstance(): GamesFragment {
            val fragment = GamesFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
