package it.log.tably.models

import it.log.tably.enums.Roles
import kotlin.math.round
import kotlin.math.roundToInt

/**
 * Created by log on 28/01/18.
 */
class PlayerStats (val player: Player) {

    var victories = 0
    var defeats = 0
    var matches = 0
    var madeShames = 0


    // Number of shames when *player* was a defender
    var defenseShames = 0

    // Number of shames when *player* was a attacker
    var attackShames = 0

    var shames = 0
        get() = attackShames + defenseShames
        private set


    fun addVictory() {
        victories += 1
        matches += 1
    }

    fun addDefeat() {
        defeats += 1
        matches += 1
    }

    fun addShame(roles: Roles) {
        if (roles == Roles.ATTACK)
            attackShames += 1
        else
            defenseShames += 1

    }

    fun addMadeShame() {
        madeShames += 1
    }

    fun getShamesAsPercentage(): Int {
        return (shames.toFloat() * 100.toFloat() / matches.toFloat()).roundToInt()
    }





}