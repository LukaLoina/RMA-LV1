import kotlin.random.Random

class Card {
    private val number = Random.nextInt(1, 14) //random card of 13
    fun value() : Int = when(number){
            in 10..13 -> 10
            else -> number
        }

    fun isAce() = (number == 1)

    override fun toString(): String = when(number){
        13 -> "K"
        12 -> "Q"
        11 -> "J"
        else -> number.toString()
    }
}