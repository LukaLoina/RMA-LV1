fun main(args: Array<String>) {
    println("Welcome to BlackJack!")
    val game = Game();
    while(true) game.turn();
}