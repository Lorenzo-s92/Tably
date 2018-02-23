package it.log.tably.utils

import it.log.tably.enums.Roles
import it.log.tably.models.Game
import it.log.tably.models.Player
import it.log.tably.models.PlayerStats



class StatsFactory(private var playerStatsMap: HashMap<String, PlayerStats>) {




    fun addGame(game: Game) {

        val playerStats1 = addOrCreatePlayerStats(game.blueAttacker)
        val playerStats2 = addOrCreatePlayerStats(game.blueDefender)
        val playerStats3 = addOrCreatePlayerStats(game.redAttacker)
        val playerStats4 = addOrCreatePlayerStats(game.redDefender)


        if (game.blueScore >= 6) {
            // Blue team wins
            playerStats1.addVictory()
            playerStats2.addVictory()
            playerStats3.addDefeat()
            playerStats4.addDefeat()

            if (game.blueScore == 6L && game.redScore == 0L) {
                // Red shame!
                playerStats1.addMadeShame()
                playerStats2.addMadeShame()
                playerStats3.addShame(Roles.ATTACK)
                playerStats4.addShame(Roles.DEFENSE)
            }

        } else {
            // Red team wins
            playerStats1.addDefeat()
            playerStats2.addDefeat()
            playerStats3.addVictory()
            playerStats4.addVictory()
        }

        if (game.blueScore == 0L && game.redScore == 6L) {
            // Blue shame!
            playerStats1.addShame(Roles.ATTACK)
            playerStats2.addShame(Roles.DEFENSE)
            playerStats3.addMadeShame()
            playerStats4.addMadeShame()
        }


    }

    private fun addOrCreatePlayerStats(player: Player): PlayerStats {

        if (playerStatsMap.containsKey(player.id)) {
            return playerStatsMap.getValue(player.id)
        }

        val playerStats = PlayerStats(player)
        playerStatsMap[player.id] = playerStats

        return playerStats
    }
}