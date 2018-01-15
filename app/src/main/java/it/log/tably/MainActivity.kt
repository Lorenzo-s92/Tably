package it.log.tably

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import it.log.tably.sections.GamesFragment
import it.log.tably.sections.RankingFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), GamesFragment.OnFragmentInteractionListener, RankingFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    lateinit var sectionFragment: Fragment
        when (item.itemId) {
            R.id.navigation_games -> {
                sectionFragment = GamesFragment.newInstance()
            }
            R.id.navigation_dashboard -> {
                sectionFragment = RankingFragment.newInstance()

//                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                sectionFragment = GamesFragment()

//                return@OnNavigationItemSelectedListener true
            }
        }

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.section, sectionFragment, sectionFragment.tag)
        fragmentTransaction.commit()

        return@OnNavigationItemSelectedListener true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // Set default section
        navigation.selectedItemId = R.id.navigation_games


    }
}
