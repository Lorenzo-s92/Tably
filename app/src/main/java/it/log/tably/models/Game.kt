package it.log.tably.models

import it.log.tably.database.Database

class Game (var reporter: Player, var date: String, var blueAttacker: Player,  var redAttacker: Player,
            var redDefender: Player, var blueDefender: Player, var blueScore: Int = -1, var redScore: Int = -1) {

    constructor (firebaseGame: FirebaseGame) : this(
            Database.players.getValue(firebaseGame.posterId),
            firebaseGame.date,
            Database.players.getValue(firebaseGame.bluAttack),
            Database.players.getValue(firebaseGame.redAttack),
            Database.players.getValue(firebaseGame.redDefense),
            Database.players.getValue(firebaseGame.bluDefense),
            firebaseGame.bluScore,
            firebaseGame.redScore
    )

    val score = """$blueScore - $redScore"""

}






