package it.log.tably.database

import it.log.tably.TablyApplication
import it.log.tably.models.Player


class Database {

    companion object {
        var players = TablyApplication.playersMap.withDefault { Player.defaultPlayer() }
    }

}