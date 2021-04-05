class DealerPlayer : Player() {
    override fun play() {
        hand = mutableListOf(Card(), Card());
        while(this.handValue() < 17){
            hand.add(Card())
        }
        this.showHand("Dealers")
    }

}
