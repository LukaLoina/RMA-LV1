import kotlin.math.min

class Board {

    val positions = listOf("ones",
                           "twos",
                           "threes",
                           "fours",
                           "fives",
                           "sixes",
                           "min",
                           "max",
                           "pair",
                           "two pairs",
                           "scale",
                           "full",
                           "poker",
                           "yamb")
    val values : Map<String, Row>
    init{
        val map = mutableMapOf<String, Row>()
        for(position in positions){
            map[position] = Row()
        }
        values = map.toMap()
    }

    override fun toString(): String {
        val padding = positions.maxOf { it.length }
        return positions.map { "${it.padStart(padding)} -- ${values[it]?.toString() ?: ""}\n" }
                        .reduce { acc, s -> acc + s }
    }

    fun calcValue(position : String, dice: List<Int>) : Int{
        val diceCount : Map<Int, Int> = dice.groupBy { it }.mapValues { (_, values) -> values.size }

        return when(position){
            "ones" -> {
                min(diceCount[1] ?: 0, 5)
            }
            "twos" -> {2 * min(diceCount[2] ?: 0, 5)
            }
            "threes" -> {3 * min(diceCount[3] ?: 0, 5)
            }
            "fours" -> {4 * min(diceCount[4] ?: 0, 5)
            }
            "fives"-> {5 * min(diceCount[5] ?: 0, 5)
            }
            "sixes"-> {6 * min(diceCount[6] ?: 0, 5)
            }
            "min"-> {
                var result = 0
                var toSelect = 5
                for(i in 1..6){
                    if(diceCount[i] == null) continue
                    val selected = min(toSelect, diceCount[i]!!)
                    toSelect -= selected
                    result += i * selected
                    if(toSelect == 0) break
                }
                result
            }
            "max"-> {
                var result = 0
                var toSelect = 5
                for(i in 6 downTo 1){
                    if(diceCount[i] == null) continue
                    val selected = min(toSelect, diceCount[i]!!)
                    toSelect -= selected
                    result += i * selected
                    if(toSelect == 0) break
                }
                result
            }
            "pair"-> {
                var result = 0
                for(i in 6 downTo 1){
                    if(diceCount[i] == null || diceCount[i]!! < 2) continue
                    result += 2 * i
                    break
                }
                result
            }
            "two pairs"-> {
                var result = 0
                for(i in 6 downTo 2){
                    if(diceCount[i] == null || diceCount[i]!! < 2) continue
                    for(j in i downTo 1){
                        if(diceCount[j] == null || diceCount[j]!! < 2) continue
                        result += 2 * i + 2 * j
                        break
                    }
                    break
                }
                result
            }
            "scale"-> {
                if(isScale(6, diceCount)) return 50
                if(isScale(5, diceCount)) return 40
                return 0
            }
            "full"-> {
                for(i in 6 downTo 1){
                    if(diceCount[i] == null) continue
                    if(diceCount[i]!! >= 3){
                        for(j in 6 downTo 1){
                            if(i == j || diceCount[j] == null) continue
                            if(diceCount[j]!! >= 2){
                                return 30 + i * diceCount[i]!! + j * diceCount[j]!!
                            }
                        }
                    }
                }
                return 0
            }
            "poker"-> {
                for(i in 6 downTo 1){
                    if(diceCount[i] == null) continue
                    if(diceCount[i]!! >= 4) return 40 + i * diceCount[i]!!
                }
                return 0
            }
            "yamb" -> {
                for(i in 6 downTo 1){
                    if(diceCount[i] == null) continue
                    if(diceCount[i]!! >= 5) return 50 + i * diceCount[i]!!
                }
                return 0
            }
            else ->{println("Unknown position in value calculation!"); 0}
        }
    }

    fun isScale(start : Int, diceCount : Map<Int, Int>) : Boolean{
        if(diceCount.size < 5) return false
        for(i in (start-5)..start){
            if(diceCount[i] == null) return false
        }
        return true
    }

    fun setValue(position : String, column : String, value : Int){
        values[position]?.setValue(column, value)
    }

    fun totalValue() : Int {
        var result = 0
        val numbers = positions.subList(0,6)
        val minMax = positions.subList(6,8)
        val combinations = positions.subList(8, positions.size-1)

        result += values.filterKeys { it in numbers }
                        .values.reduce { acc, row -> acc + row }
                        .values.mapValues { (_, value) -> if(value == null) 0 else if(value > 60) value+30 else value  }
                        .values.sum()

        val min = values[minMax[0]]!!
        val max = values[minMax[1]]!!
        result += ((max - min) * values[numbers[0]]!!).values.mapValues { (_, value) -> value ?: 0 }.values.sum()

        result += values.filterKeys { it in combinations }
                        .values.reduce { acc, row -> acc + row }
                        .values.mapValues { (_, value) -> value ?: 0 }
                        .values.sum()

        return result
    }

    fun mustCall(): Boolean = values.all { (_, value) -> value.isCallOnly() }
    fun anyFree(): Boolean = values.any { (_, value) -> !value.isComplete() }

}