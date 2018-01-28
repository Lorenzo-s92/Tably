package it.log.tably.models

import it.log.tably.database.Database

class Game (val reporter: Player, val date: String, val blueAttacker: Player,  val redAttacker: Player,
            val redDefender: Player, val blueDefender: Player, val blueScore: Long = -1,
            val redScore: Long = -1, val key: String) {

    /* From FirebaseGame to Game model */
    constructor (firebaseGame: FirebaseGame, key: String) : this(
            Database.players.getValue(firebaseGame.posterId),
            firebaseGame.date,
            Database.players.getValue(firebaseGame.bluAttack),
            Database.players.getValue(firebaseGame.redAttack),
            Database.players.getValue(firebaseGame.redDefense),
            Database.players.getValue(firebaseGame.bluDefense),
            firebaseGame.bluScore,
            firebaseGame.redScore,
            key
    )


    val score = """$blueScore - $redScore"""

    /* From Firebase map to Game model */
    constructor (map: MutableMap.MutableEntry<out Any, HashMap<out Any, out Any>>) : this (
            Database.players.getValue(map.value["posterId"] as String),
            map.value["date"] as String,
            Database.players.getValue(map.value["bluAttack"] as String),
            Database.players.getValue(map.value["redAttack"] as String),
            Database.players.getValue(map.value["redDefense"] as String),
            Database.players.getValue(map.value["bluDefense"] as String),
            map.value["bluScore"] as Long,
            map.value["redScore"] as Long,
            map.key as String
    )

}






