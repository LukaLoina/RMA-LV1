import kotlin.random.Random

class Die {
    var number : Int = Random.nextInt(1, 7); // random 1 to 6
    var isLocked : Boolean = false;

    fun roll()
    {
        if(isLocked){
            return
        }

        number = Random.nextInt(1, 7);// random 1 to 6
    }

    fun lock(){
        isLocked = true;
    }

    fun unlock() {
        isLocked = false;
    }

    fun print(){
        if(isLocked) print(" [${number}]")
        else print(" ${number}")
    }

}