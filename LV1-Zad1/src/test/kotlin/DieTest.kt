import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DieTest{

    @Test
    fun rollTest() {
        val die = Die()
        Assertions.assertTrue { die.number in 1..6 }
        for (i in 1..100){
            die.roll()
            Assertions.assertTrue { die.number in 1..6 }
        }
    }

    @Test
    fun lockTest(){
        val die = Die()
        val initialNumber = die.number
        die.lock()
        for(i in 1..100){
            die.roll()
            Assertions.assertTrue { initialNumber == die.number }
        }
    }

    @Test
    fun unlockTest(){
        val die = Die()
        var tempNumber = die.number
        var isDifferent = false

        die.lock()
        die.unlock()
        for(i in 1..100){
            die.roll()
            if(tempNumber != die.number){isDifferent = true}
            tempNumber = die.number
        }

        Assertions.assertTrue(isDifferent)
    }

}