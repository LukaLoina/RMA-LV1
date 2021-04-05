class HumanPlayer : Player() {
    override fun play() {
        hand = mutableListOf<Card>(Card(), Card())
        var playing = true;
        this.showHand("Your")
        while (playing) {
            print("> ")
            val input = readLine()?.trim() ?: ""
            when (input) {
                "hit" -> {
                    hand.add(Card());
                    if(isBusted()) {
                        playing = false
                    }
                }
                "stand" -> playing = false;
                else -> println("Expected hit or stand");
            }
            this.showHand("Your")
        }
    }
}

