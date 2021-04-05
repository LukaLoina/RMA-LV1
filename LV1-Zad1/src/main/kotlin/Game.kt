class Game{
    private var players: List<Player>
    private var numberOfPlayers: Int = 0
    private var dice: List<Die>
    init {
        val mutable = mutableListOf<Die>()
        for (i in 1..6) {
            mutable.add(Die())
        }
        dice = mutable.toList()
    }

    constructor(players: List<Player>) {
        this.numberOfPlayers = players.size
        this.players = players
    }

    fun turn(){
        players.forEach { it.turn(dice) }
    }

    fun isPlayed(): Boolean {
        return players.any { it.canPlay() }
    }

    fun help() = players.first().help();
}