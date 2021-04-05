class Row{
    val positions = listOf<String>("up", "down", "free", "called")
    val values : MutableMap<String, Int?>

    constructor(){
        this.values = positions.map { it to null }.toMap() as MutableMap<String, Int?>
    }
    constructor(values : MutableMap<String, Int?>){
        this.values = values
    }

    override fun toString(): String {
        return positions.map { "$it: ${values[it]?.toString()?.padStart(3) ?: "   " }" }
                        .reduce { acc, s -> acc + s }
    }

    operator fun plus(row: Row) : Row {
        val result  = Row()
        for(key in result.values.keys){
            if(this.values[key] == null && row.values[key] == null){
                result.values[key] = null

            } else if(this.values[key] == null) {
                result.values[key] = row.values[key]
            } else if(row.values[key] == null) {
                result.values[key] = this.values[key]
            } else {
                result.values[key] = (this.values[key]!! + row.values[key]!!)
            }
        }
        result.values.plus(this.values).plus(row.values)
        return result
    }

    operator fun minus(row: Row): Row {
        val result  = Row()
        result.values.plus(this.values).minus(row.values)
        return result
    }

    operator fun times(row: Row) : Row {
        val result  = Row()
        for(key in result.values.keys){
            if(this.values[key] == null){
                result.values[key] = 0
                continue
            }
            if(row.values[key] == null){
                result.values[key] = this.values[key]
                continue
            }
            result.values[key] = this.values[key]!! * row.values[key]!!
        }

        return result
    }

    fun setValue(column: String, value: Int) {
        this.values[column] = value
    }

    fun isCallOnly(): Boolean = this.values.filterKeys { it in listOf("up", "down", "free") }.all { it.value != null }
    fun isComplete(): Boolean = this.values.all{ it.value != null}

}