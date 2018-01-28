package it.log.tably.models

/**
 * Firebase Game class
 */
class FirebaseGame {

    lateinit var posterId: String
    lateinit var date: String
    lateinit var bluAttack: String
    lateinit var redAttack: String
    lateinit var redDefense: String
    lateinit var bluDefense: String
    var bluScore: Long = -1
    var redScore: Long = -1
    var likes: Int = -1

    constructor(posterId: String, date: String, bluAttack: String, redAttack: String,
                redDefense: String, bluDefense: String, bluScore: Long, redScore: Long, likes: Int) {

        this.posterId = posterId
        this.date = date
        this.bluAttack = bluAttack
        this.redAttack = redAttack
        this.redDefense = redDefense
        this.bluDefense = bluDefense
        this.bluScore = bluScore
        this.redScore = redScore
        this.bluScore = bluScore
        this.likes = likes
    }

    // empty constructor for Firebase
    constructor()

}

