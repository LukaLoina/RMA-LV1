class Game {
    val player = HumanPlayer()
    val dealer = DealerPlayer()

    var playerWins = 0
    var dealerWins = 0

    fun turn(){
        player.play()

        if(player.isBusted()){
            println("You busted, dealer wins!")
            dealerWins += 1;

        } else {
            dealer.play()
            if(dealer.isBusted())
            {
                playerWins += 1;
                println("Dealer busted, you win!")
            }
            else if (player.handValue() > dealer.handValue()){
                playerWins += 1;
                println("You win!")

            } else {
                dealerWins += 1;
                println("Dealer wins!")
            }
        }
        println("you: $playerWins, dealer: $dealerWins")
    }
}