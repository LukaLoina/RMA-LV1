class Player(val name : String) {
    val board : Board = Board()
    var currentDown = 0
    var currentUp = board.values.size-1

    fun turn(dice : List<Die>)
    {
        dice.forEach { it.unlock() }
        var roll = 0
        var called : String? = null
        println("$name board")
        println(board.toString())
        println("total: ${board.totalValue()}")
        println("current down: ${ if(currentDown < board.values.size) board.positions[currentDown] else "finished"}")
        println("current up: ${if(currentUp >= 0) board.positions[currentUp] else "finished"}")
        while(true){
            print("${this.name} > ")
            val input = readLine()?.trim()?.split(" ") ?: listOf()
            val args = input.subList(1, input.size)
            when (input[0]) {
                "lock" -> {
                    if (roll < 1){
                        println("You cannot lock dice before throwing them.")
                        continue
                    }

                    for (arg in args) {
                        applyDice(dice, arg) { d: Die -> d.lock() }
                    }
                    for (die in dice) {
                        die.print()
                    }
                    println("")
                }
                "unlock" -> {
                    if (roll < 1){
                        println("You cannot unlock dice before throwing them.")
                        continue
                    }
                    for (arg in args) {
                        applyDice(dice, arg) { d: Die -> d.unlock() }
                    }
                    for (die in dice) {
                        die.print()
                    }
                    println("")
                }
                "roll" -> {
                    if (roll >= 3) {
                        println("You cannot roll more than 3 times.")
                        continue
                    }

                    if(roll == 1 && board.mustCall()){
                        println("You must call first!")
                        continue
                    }

                    roll += 1

                    print("Roll: $roll, Dice:")
                    for (die in dice) {
                        die.roll()
                        die.print()
                    }
                    println("")
                }
                "call" -> {
                    //could call previously called number
                    if(roll != 1){
                        println("You can only call after first roll!")
                        continue
                    }

                    val position = args.joinToString(" ")
                    if(position in board.positions){
                        if(board.values[position]!!.values["called"] != null){
                            println("Cannot call same position twice!")
                            continue
                        }
                        called = position
                        println("Called $position!")
                    } else {
                        println("$position not recognized as board position")
                    }
                }
                "write" -> {
                    if(roll < 1){
                        println("You cannot write down result before rolling.")
                        continue
                    }

                    var position = args.joinToString(" ")
                    var column = "free"

                    if(called != null && (position == "" || position == called)) {
                        position = called
                        column = "called"
                    }

                    if(position == "up"){
                        if(currentUp >= 0){
                            position = board.positions[currentUp]
                            column = "up"
                            currentUp -= 1
                        } else {
                            println("Up column is already completed!")
                            continue
                        }
                    }

                    if(position == "down"){
                        if(currentDown < board.positions.size) {
                            position = board.positions[currentDown]
                            column = "down"
                            currentDown += 1
                        } else {
                            println("Down column is completed!")
                            continue
                        }
                    }

                    if(position in board.positions){
                        if(board.values[position]!!.values[column] != null){
                            println("Cannot write same position twice!")
                        }
                        val result = board.calcValue(position, dice.map { it.number })
                        board.setValue(position, column, result)
                        println("Wrote down $result in $position $column!")
                        break
                    } else {
                        println("$position not recognized as valid board position")
                    }
                }
                "help" -> {this.help()}
                else -> {
                    println("Unknown command!")
                }
            }
        }
    }

    fun applyDice(dice: List<Die>, arg: String, f: (Die) -> Unit) {
        if (arg.endsWith("s")) {
            val num = arg.substring(0, arg.length - 1).toIntOrNull()
            if (num == null) {
                println("$arg not recognized as number.")
                return
            }
            for (die in dice) {
                if (die.number == num) f(die)
            }
        } else {
            val num = arg.toIntOrNull()
            if (num == null) {
                println("$arg not recognized as number.")
                return
            }
            if (num !in 1..dice.size) {
                println("$arg is not valid die number")
                return
            }
            f(dice[num+1])
        }
    }

    fun canPlay() : Boolean = board.anyFree()
    fun score() = board.totalValue()

    fun help() {
        println("Commands :")
        println("roll - rolls dice")
        println("lock [arg] - locks dice specified by arg")
        println("             'lock 1' locks first die, 'lock 1s' locks dice with current value of 1")
        println("unlock [arg] - unlock dice specified by arg")
        println("               'unlock 1' unlocks first die, 'unlock 1s' unlocks dice with current value one")
        println("call [position] - calls position, could only be used after first throw")
        println("write [field] - writes current dice result in specified position")
        println("                field could be 'up', 'down' for writing into up and down columns")
        println("                field could be row name for writing in free column")
        println("                you do not need to specify field if you called it")
        println("help - displays this text")
    }
}

