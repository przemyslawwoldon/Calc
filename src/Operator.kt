class Operator(val op: String) {
    val operator: String
    var priority: Int

    // initializer block
    init {
        operator = op

        when (operator) {
            "+" -> priority = 1
            "-" -> priority = 1
            "/" -> priority = 2
            "*" -> priority = 2
            else -> priority = 0
        }
    }
}