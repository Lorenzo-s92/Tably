package it.log.tably.sections

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast

import it.log.tably.R
import it.log.tably.adapter.RankAdapter
import it.log.tably.database.Database
import it.log.tably.enums.RankingType
import it.log.tably.models.PlayerStats
import kotlinx.android.synthetic.main.fragment_ranking.view.*
import kotlinx.android.synthetic.main.rankbar.view.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RankingFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RankingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RankingFragment : Fragment() {

    // TODO: Rename and change types of parameters

    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: RankAdapter
    var mRankingType = RankingType.SHAMES

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_ranking, container, false)

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        view.rank_container.setHasFixedSize(true)




        // use a linear layout manager
        mLayoutManager = LinearLayoutManager(context);
        view.rank_container.layoutManager = mLayoutManager

        var stats = Database.playerStats.values.toTypedArray()

        var sorted : List<PlayerStats>



//        mAdapter = RankAdapter(sorted, RankingType.SHAMES)
//        view.rank_container.adapter = mAdapter
//
//        val rankBarManager = RankBarManager(view, sorted, mAdapter)
//        rankBarManager.setRankingType(mRankingType)

        var rankingTypes = arrayOf("Standings", "Streaks", "Shames")

        sorted = stats.sortedByDescending { it.getVictoriesAsPercentage() }
        view.ranking_bar.rankbar_name.text = "Standings"
        mAdapter = RankAdapter(sorted, RankingType.STANDINGS)
        view.rank_container.adapter = mAdapter

        val rankBarManager = RankBarManager(view, sorted, mAdapter)
        rankBarManager.setRankingType(mRankingType)

        var i : Int = 0

        view.ranking_bar.rankbar_name.setOnClickListener {
        //    mAdapter.reverseDataset()

            i = (i + 1) % 3

            if (i == 0) { sorted = stats.sortedByDescending { it.getVictoriesAsPercentage() } }
            else if (i == 1) { sorted = stats.sortedByDescending { it.getDefeatsAsPercentage() } }
            else if (i == 2) { sorted = stats.sortedByDescending { it.shames } }

            view.ranking_bar.rankbar_name.text = rankingTypes[i]
            mAdapter = RankAdapter(sorted, RankingType.values()[i])
            view.rank_container.adapter = mAdapter

            val rankBarManager = RankBarManager(view, sorted, mAdapter)
            rankBarManager.setRankingType(mRankingType)
        }

//        val adapter = ArrayAdapter.createFromResource(activity,
//                R.array.score_array, android.R.layout.simple_spinner_item)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        rankbar_spinner!!.setAdapter(adapter)
//
//        rankbar_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//
//                Toast.makeText(activity, "You have Selected " + rankbar_spinner.selectedItem.toString(), Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//            }
//        }

//        mRecyclerView.setAdapter(mAdapter);
//

        return view
    }

     private class RankBarManager (val parentView: View, var dataSet: List<PlayerStats>, val adapter: RankAdapter) {

        var sortViewsList: List<View> = listOf(
                parentView.ranking_bar.ascending_order_score1,
                parentView.ranking_bar.ascending_order_score2,
                parentView.ranking_bar.ascending_order_score3,
                parentView.ranking_bar.ascending_order_score4,
                parentView.ranking_bar.descending_order_score1,
                parentView.ranking_bar.descending_order_score2,
                parentView.ranking_bar.descending_order_score3,
                parentView.ranking_bar.descending_order_score4
        )

         var labelViewsList : List<View> = listOf(
                 parentView.ranking_bar.score1,
                 parentView.ranking_bar.score2,
                 parentView.ranking_bar.score3,
                 parentView.ranking_bar.score4
         )

         lateinit var currentRankingType: RankingType
         var tapViewMap: HashMap<Int, Boolean> = HashMap()

         /**
          * Init
          */
        init {
            hideAllSortViews()

            for (v in labelViewsList)
                tapViewMap[v.id] = false
        }

        fun setRankingType(aRankingType: RankingType) {
            currentRankingType = aRankingType
            hideAllSortViews()
            initScoreLabelsForRankingType()
            initClickListenersForRankingType()
            initSortViewsForRankingType()

        }


        private fun hideAllSortViews() {
            for (v in sortViewsList)
                 v.visibility = View.INVISIBLE
        }

        private fun changeSortingView(vDescending: View, vAscending: View) {
            if (vDescending.visibility == View.VISIBLE ) {
                hideAllSortViews()
                vDescending.visibility = View.INVISIBLE
                vAscending.visibility = View.VISIBLE
            } else {
                hideAllSortViews()
                vDescending.visibility = View.VISIBLE
                vAscending.visibility = View.INVISIBLE
            }

        }


         private fun initClickListenersForRankingType() {

             when (currentRankingType) {
                 RankingType.SHAMES -> {

                     parentView.ranking_bar.score1.setOnClickListener {
                         val vDescending = parentView.ranking_bar.descending_order_score1
                         val vAscending= parentView.ranking_bar.ascending_order_score1
                         val shouldSortAscending = vDescending.visibility == View.VISIBLE && vAscending.visibility == View.INVISIBLE

                         dataSet = if (shouldSortAscending) dataSet.sortedBy { it.shames } else dataSet.sortedByDescending { it.shames }
                         adapter.replaceDataSetWith(dataSet)

                         changeSortingView(vDescending, vAscending)

                     }

                     parentView.ranking_bar.score2.setOnClickListener {
                         val vDescending = parentView.ranking_bar.descending_order_score2
                         val vAscending= parentView.ranking_bar.ascending_order_score2
                         val shouldSortAscending = vDescending.visibility == View.VISIBLE && vAscending.visibility == View.INVISIBLE

                         dataSet = if (shouldSortAscending) dataSet.sortedBy { it.getShamesAsPercentage() } else dataSet.sortedByDescending { it.getShamesAsPercentage() }
                         adapter.replaceDataSetWith(dataSet)


                         changeSortingView(vDescending, vAscending)

                     }
                     parentView.ranking_bar.score3.setOnClickListener {
                         val vDescending = parentView.ranking_bar.descending_order_score3
                         val vAscending= parentView.ranking_bar.ascending_order_score3
                         val shouldSortAscending = vDescending.visibility == View.VISIBLE && vAscending.visibility == View.INVISIBLE

                         dataSet = if (shouldSortAscending) dataSet.sortedBy { it.attackShames } else dataSet.sortedByDescending { it.attackShames }
                         adapter.replaceDataSetWith(dataSet)

                         changeSortingView(vDescending, vAscending)
                     }

                     parentView.ranking_bar.score4.setOnClickListener {
                         val vDescending = parentView.ranking_bar.descending_order_score4
                         val vAscending= parentView.ranking_bar.ascending_order_score4
                         val shouldSortAscending = vDescending.visibility == View.VISIBLE && vAscending.visibility == View.INVISIBLE

                         dataSet = if (shouldSortAscending) dataSet.sortedBy { it.defenseShames } else dataSet.sortedByDescending { it.defenseShames }
                         adapter.replaceDataSetWith(dataSet)

                         changeSortingView(vDescending, vAscending)
                     }



                 }

             }

         }

        private fun initSortViewsForRankingType() {

            when (currentRankingType) {

                RankingType.SHAMES -> {
                    parentView.ranking_bar.descending_order_score1.performClick()
                }
            }

        }

        private fun initScoreLabelsForRankingType() {
            when (currentRankingType) {
                RankingType.SHAMES -> {
                    parentView.ranking_bar.score1.text = "Total"
                    parentView.ranking_bar.score2.text = "%"
                    parentView.ranking_bar.score3.text = "#A"
                    parentView.ranking_bar.score4.text = "#D"
                }
            }
         }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
         *
         * @return A new instance of fragment RankingFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): RankingFragment {
            val fragment = RankingFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
