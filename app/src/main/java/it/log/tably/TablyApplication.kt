package it.log.tably

import android.app.Application
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import it.log.tably.models.Game
import it.log.tably.models.Player

const val TAG = "TablyApplication"

class TablyApplication : Application() {

    companion object {
        val playersMap: HashMap<String, Player> = HashMap()
        val gamesMap: HashMap<String, Game> = HashMap()

        fun loadPlayersDB () {
            // FirebaseApp.initializeApp(this)

            /* Players listeners */
            val playersReference = FirebaseDatabase.getInstance().getReference("players")
            playersReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val firebasePlayersMap = dataSnapshot.value as HashMap<*, HashMap<*, *>>

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

                    gamesMap.clear()
                    for (map in firebaseGameMap) {
                        Log.d(TAG, map.toString())

                        val game = Game(map)
                        gamesMap[game.key] = game
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


