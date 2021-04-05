fun main(args: Array<String>) {
    println("Welcome to Jamb!")
    val numberOfPlayers : Int = getNumberOfPlayers()
    val players = createPlayers(numberOfPlayers)
    val game = Game(players)

    game.help()
    print("\n\n")
    while(game.isPlayed())
        game.turn()

    println("Game score:")
    for(player in players){
        println("${player.name}: ${player.score()}")
    }
}

private fun getNumberOfPlayers() : Int
{
    var numberOfPlayers : Int = 0;
    while(true){
        print("Please enter the number of players:")
        val input = readLine()
        numberOfPlayers = input?.toIntOrNull() ?: 0;

        if (numberOfPlayers == 0) {
            println("Please enter number.")
            continue
        }
        if (numberOfPlayers < 2)
        {
            println("Minimum number of players is 2.")
            continue
        }
        break
    }
    return numberOfPlayers
}

private fun createPlayers(numberOfPlayers : Int) : List<Player>
{
    val players = mutableListOf<Player>()
    var i = 0
    while(i < numberOfPlayers)
    {
        print("Please enter name for player ${i+1}:");
        val name = readLine() ?: continue;

        players.add(Player(name))
        i += 1
    }
    println("")
    return players.toList();
}