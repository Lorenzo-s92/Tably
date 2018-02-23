package it.log.tably

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import it.log.tably.models.Game
import it.log.tably.models.Player
import it.log.tably.models.PlayerStats
import it.log.tably.utils.StatsFactory

const val TAG = "TablyApplication"

class TablyApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: TablyApplication? = null
        val playersMap: HashMap<String, Player> = HashMap()
        val gamesMap: HashMap<String, Game> = HashMap()
        val playerStatsMap: HashMap<String, PlayerStats> = HashMap()


        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

        fun loadPlayersDB () {
            // FirebaseApp.initializeApp(this)

            /* Players listeners */
            val playersReference = FirebaseDatabase.getInstance().getReference("players")
            playersReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val firebasePlayersMap = dataSnapshot.value as HashMap<*, HashMap<*, *>>

                    Toast.makeText(applicationContext(), "Player added!", Toast.LENGTH_LONG).show()


                    playersMap.clear()
                    for (map in firebasePlayersMap) {
                        val player = Player(map)
                        playersMap[player.id] = player
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.d(TAG, "Failed to read value.", error.toException())
                }
            })

            /* Games listeners */
            val gamesReference = FirebaseDatabase.getInstance().getReference("matches")
            gamesReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val firebaseGameMap = dataSnapshot.value as HashMap<*, HashMap<*, *>>
                    val statsFactory = StatsFactory(playerStatsMap)

                    Toast.makeText(applicationContext(), "Games added!", Toast.LENGTH_LONG).show()

                    gamesMap.clear()
                    playerStatsMap.clear()
                    for (map in firebaseGameMap) {

                        val game = Game(map)
                        gamesMap[game.key] = game
                        statsFactory.addGame(game)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.d(TAG, "Failed to read value.", error.toException())
                }
            })
        }

    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }
}


