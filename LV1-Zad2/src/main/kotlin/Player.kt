abstract class Player {
    var hand : MutableList<Card> = mutableListOf();
    abstract fun play()

    fun handValue() : Int
    {
        var aces = hand.count { it.isAce() }
        var points = hand.map { it.value() }.sum()
        while((aces > 0) && (points+10 <= 21)) {
            aces-=1;
            points+=10
        }
        return points
    }

    fun isBusted() : Boolean = this.handValue() > 21

    fun showHand(name:String) {
        print("$name hand is [${this.handValue()}]:")
        for(card in hand) print(" ${card.toString()}")
        println("")
    }
}