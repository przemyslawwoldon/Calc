import kotlin.math.pow
import kotlin.math.sqrt


class CalcStorePresenter : CalcStoreContract.Presenter {

    private lateinit var view: CalcStoreContract.View

    override fun attach(view: CalcStoreContract.View) {
        this.view = view
    }

    override fun calculateEquation(sb : StringBuilder){
        var argumentsAndOperators : List<String> = sb.toString().trimEnd().trimStart().split(" ")
        val argumentsAndOperatorsResult : MutableList<String> = argumentsAndOperators.filterNot { s -> s == "" }.toMutableList()

        var pair = validateList(argumentsAndOperators)
        view.printInfo(pair.toString())

        if (pair.first) {
            view.updateErrorInfo(" ")
            val iterator = argumentsAndOperatorsResult.iterator()
            /* op _ pow -> op 1 pow */
            var indexPrev = 0;
            for ((index, value) in iterator.withIndex()) {
                if (index == 0)
                    continue
                if (value == "pow" &&
                        (argumentsAndOperatorsResult.get(indexPrev) == "+" || argumentsAndOperatorsResult.get(indexPrev) == "-" ||
                                argumentsAndOperatorsResult.get(indexPrev) == "/" || argumentsAndOperatorsResult.get(indexPrev) == "*")) {

                    argumentsAndOperatorsResult.add(index, "1")
                    view.printInfo("add 1")
                }
                indexPrev = index
            }

            /* n _ sqrt -> n * sqrt */
            val iterator2 = argumentsAndOperatorsResult.iterator()
            indexPrev = 0;
            for ((index, value) in iterator2.withIndex()) {
                if (index == 0)
                    continue
                var valuePrevInArray = argumentsAndOperatorsResult.get(indexPrev).toDoubleOrNull()
                if (value == "sqrt" && valuePrevInArray != null) {
                    argumentsAndOperatorsResult.add(index, "*")
                    view.printInfo("add mul")
                }
                indexPrev = index
            }

            while (!calculatePowAndSqrt(argumentsAndOperatorsResult)){}
            toPostfix(argumentsAndOperatorsResult)
        }else {
            view.updateErrorInfo(pair.second)
        }
    }

    private fun toPostfix(argumentsAndOperatorsResult : MutableList<String>) {
        var outPostfix : MutableList<String> = mutableListOf()
        var stack : MutableList<Operator> = mutableListOf()

        argumentsAndOperatorsResult.forEach {
            var value = it.toDoubleOrNull()
            if(value == null) {
                if (stack.isEmpty()) {
                    stack.add(Operator(it))
                }else {
                    if(Operator(it).priority > stack.last().priority) {
                        stack.add(Operator(it))
                    }else {
                        var lastFromStack = stack.last()
                        while(lastFromStack != null){
                            if(stack.last().priority >= Operator(it).priority){
                                outPostfix.add(stack.last().op)
                                stack.removeAt(stack.lastIndex)

                                if(stack.isEmpty())
                                    break;
                                lastFromStack = stack.last()
                            }
                        }
                        stack.add(Operator(it))
                    }
                }
            }else {
                outPostfix.add(value.toString())
            }
        }
        outPostfix.add(stack.last().op)

        while (!calculateResultP(outPostfix)){}

        view.showResult(outPostfix.first())
    }

    private fun calculateResultP(outPostfix : MutableList<String>) : Boolean {
        var index = outPostfix.indexOfFirst { s -> s == "+" || s == "-" || s == "*" || s == "/"}
        if(index != -1) {
            var arg1 = outPostfix.get(index - 2).toDouble() //-2 for index first operation arg
            var arg2 = outPostfix.get(index - 1).toDouble() //-1 for index second operation arg
            var res = 0.0
            when (outPostfix.get(index)) {
                "+" -> res = arg1 + arg2
                "-" -> res = arg1 - arg2
                "/" -> res = arg1 / arg2
                "*" -> res = arg1 * arg2
//                else -> res = 0.0
            }
            outPostfix.removeAt(index)
            outPostfix.removeAt(index-1) //-1 for index second operation arg
            outPostfix.removeAt(index-2) //-2 for index first operation arg
            outPostfix.add(index -2, res.toString()) //add in first rem index
            return false
        }
        return true
    }

    private fun calculatePowAndSqrt(argumentsAndOperatorsResult : MutableList<String>) : Boolean {
        var index = argumentsAndOperatorsResult.indexOfLast { s -> s == "sqrt" || s == "pow" }
        if(index != -1) {
            var valueOp  = argumentsAndOperatorsResult.get(index)
            if(valueOp == "sqrt"){
                var valueNextInArray  = argumentsAndOperatorsResult.get(index + 1).toDouble()
                argumentsAndOperatorsResult.removeAt(index + 1) //arg sqrt
                argumentsAndOperatorsResult.removeAt(index)
                argumentsAndOperatorsResult.add(index, sqrt(valueNextInArray).toString())
                return false
            }
            /* x ^ n */
            if(valueOp == "pow"){
                var valueN  = argumentsAndOperatorsResult.get(index + 1).toDouble()
                var valueX  = argumentsAndOperatorsResult.get(index - 1).toDouble()
                argumentsAndOperatorsResult.removeAt(index + 1) //remove n
                argumentsAndOperatorsResult.removeAt(index)
                argumentsAndOperatorsResult.removeAt(index - 1) //remove x
                argumentsAndOperatorsResult.add(index - 1, valueX.pow(valueN).toString()) //add result in first rem index
                return false
            }
        }
        return true
    }

    private fun validateList(listArg : List<String>) : Pair<Boolean, String> {
        val sLast = listArg.last()
        if(sLast == "+" || sLast == "-" ||
                sLast == "/" || sLast == "*" ||
                sLast == "sqrt" || sLast == "pow")
            return Pair(false, "Niewlasciwy znak na koncu wyrazenia")

        if(listArg.first() == "pow") {
            return Pair(false, "Niekompletne wyrazenie pow")
        }
        return Pair(true, "ok")
    }

}