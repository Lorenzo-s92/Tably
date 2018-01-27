package it.log.tably.models

import it.log.tably.utils.Avatar
import java.util.HashMap


class Player (val id: String, val nickname: String, val emailAddress: String,
              val likes: HashMap<String, Boolean>) {

    companion object {
        fun defaultPlayer(): Player {
            return Player(
                    "0",
                    "defaultPlayer",
                    "default@player",
                    HashMap()
            )
        }
    }

    // constructor from Firebase JSON
    constructor (map: MutableMap.MutableEntry<out Any, HashMap<out Any, out Any>>) : this (
           map.key as String,
           map.value["nickname"] as String,
           map.value["email"] as String,
           if (map.value["likes"] is HashMap<*, *>) map.value["likes"] as HashMap<String, Boolean> else HashMap<String, Boolean>()
    )

    val avatarAsId = Avatar.map.getValue(nickname)

}