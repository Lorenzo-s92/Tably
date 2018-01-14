package it.log.tably.sections

import android.arch.lifecycle.GeneratedAdapter
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import it.log.tably.R
import it.log.tably.sections.adapters.GamesAdapter
import kotlinx.android.synthetic.main.fragment_games.*
import kotlinx.android.synthetic.main.fragment_games.view.*

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
    private lateinit var mAdapter: Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_games, container, false)
        // Inflate the layout for this fragment

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        view.cards_containers.setHasFixedSize(true)

        // use a linear layout manager
        mLayoutManager = LinearLayoutManager(context)
        view.cards_containers.cards_containers.layoutManager = mLayoutManager

        // specify an adapter (see also next example)
        mAdapter = GamesAdapter(null)
        view.cards_containers.adapter = mAdapter
        return view
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
}// Required empty public constructor
