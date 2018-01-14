package it.log.tably.sections.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import it.log.tably.R

/**
 * Created by log on 13/01/18.
 */
// Provide a suitable constructor (depends on the kind of dataset)

class GamesAdapter (private val mDataset: Array<String>?) : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    // each data item is just a string in this case
    data class ViewHolder(var mLL: LinearLayout) : RecyclerView.ViewHolder(mLL)


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
        //holder.mTextView.text = mDataset[position]

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return 3
    }
}

