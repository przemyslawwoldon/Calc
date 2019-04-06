import org.w3c.dom.*
import kotlin.browser.document

class CalcStorePage(private val presenter: CalcStoreContract.Presenter) : CalcStoreContract.View {

    private val content = document.getElementById("content") as HTMLDivElement
//    private val loader = document.getElementById("loader") as HTMLDivElement

    var output = document.createElement("textarea") as HTMLTextAreaElement
    val infoError = document.createElement("label") as HTMLLabelElement

    private var input : StringBuilder = StringBuilder()
    private var blockOp : Boolean = true

    override fun show() {
        presenter.attach(this)
    }

    override fun showResult(s : String) {
        input.clear()
        input.append(s)
        output.value = s
    }

    override fun printInfo(s: String) {
        println(s)
    }

    override fun createView() {
        makeOutputArea()
        makeClearButton()
        make1CalcRow()
        make2CalcRow()
        make3CalcRow()
        make4CalcRow()
        makeErrorAndInfoArea()
    }

    private fun makeErrorAndInfoArea() {
        val row0 = document.createElement("div") as HTMLDivElement
        val row1 = document.createElement("div") as HTMLDivElement

        val info = document.createElement("label") as HTMLLabelElement
        info.innerText = " Wyrazenie nie moze zakonczyc sie znakiem operacji (zop)\n" +
                " n sqrt n -> auto -> n * sqrt n \n" +
                " zop pow n -> auto -> zop 1 pow n \n" +
                " sqrt n" +
                " x^n -> x pow n"

        info.style.color = "white"
        row0.appendChild(info)

        row1.style.height = "25px"
        row1.style.width = "100%"

        content.appendChild(row1)
        content.appendChild(row0)

        val row2 = document.createElement("div") as HTMLDivElement
        val row3 = document.createElement("div") as HTMLDivElement

        infoError.innerText = " "
        infoError.style.color = "white"
        row3.appendChild(infoError)

        row2.style.height = "25px"
        row2.style.width = "100%"

        content.appendChild(row2)
        content.appendChild(row3)
    }

    override fun updateErrorInfo(s : String) {
        infoError.innerText = s
    }

    private fun updateResult() {
        output.value = input.toString()
    }

    private fun makeOutputArea() {
        val row0 = document.createElement("div") as HTMLDivElement
        val row1 = document.createElement("div") as HTMLDivElement

        val buttonFake = document.createElement("button") as HTMLButtonElement
        buttonFake.innerHTML = "XD" //random
        changeStyle(buttonFake)
        buttonFake.style.visibility = "hidden"

        changeStyle(output)
        output.style.resize = "none"
        output.style.backgroundColor = "white"
        output.style.textAlign = "right"
        output.readOnly = true
        output.value = "0" //initial value
        output.style.width = "277"

        row0.appendChild(buttonFake)
        row0.appendChild(output)
        row0.style.width = "100%"

        row1.style.height = "25px"
        row1.style.width = "100%"

        content.appendChild(row0)
        content.appendChild(row1)
    }

    private fun makeClearButton() {
        val row = document.createElement("div") as HTMLDivElement

        val buttonFake = document.createElement("button") as HTMLButtonElement
        buttonFake.innerHTML = "XD" //random
        changeStyle(buttonFake)
        buttonFake.style.visibility = "hidden"

        val buttonC = document.createElement("button") as HTMLButtonElement
        buttonC.innerHTML = "C"
        changeStyle(buttonC)
        buttonC.style.width = "280"

        row.appendChild(buttonFake)
        row.appendChild(buttonC)
        row.style.width = "100%"

        content.appendChild(row)

        buttonC.addEventListener("click", {
            input.clear()
            updateResult()
        })
    }

    private fun make1CalcRow() {
        val row = document.createElement("div") as HTMLDivElement

        val buttonFake = document.createElement("button") as HTMLButtonElement
        buttonFake.innerHTML = "XD" //random
        changeStyle(buttonFake)
        buttonFake.style.visibility = "hidden"

        val button7 = document.createElement("button") as HTMLButtonElement
        button7.innerHTML = "7"
        changeStyle(button7)

        val button8 = document.createElement("button") as HTMLButtonElement
        button8.innerHTML = "8"
        changeStyle(button8)

        val button9 = document.createElement("button") as HTMLButtonElement
        button9.innerHTML = "9"
        changeStyle(button9)

        val buttonDiv = document.createElement("button") as HTMLButtonElement
        buttonDiv.innerHTML = "/"
        changeStyle(buttonDiv)

        row.appendChild(buttonFake)
        row.appendChild(button7)
        row.appendChild(button8)
        row.appendChild(button9)
        row.appendChild(buttonDiv)
        row.style.width = "100%"
        content.appendChild(row)


        button7.addEventListener("click", {
            input.append("7")
            blockOp = false
            updateResult()
        })

        button8.addEventListener("click", {
            input.append("8")
            blockOp = false
            updateResult()
        })

        button9.addEventListener("click", {
            input.append("9")
            blockOp = false
            updateResult()
        })

        buttonDiv.addEventListener("click", {
            if(!blockOp) {
                blockOp = true
                input.append(" / ")
                updateResult()
            }
        })
    }

    private fun make2CalcRow() {
        val row = document.createElement("div") as HTMLDivElement

        val buttonFake = document.createElement("button") as HTMLButtonElement
        buttonFake.innerHTML = "XD"
        changeStyle(buttonFake)
        buttonFake.style.visibility = "hidden"

        val button4 = document.createElement("button") as HTMLButtonElement
        button4.innerHTML = "4"
        changeStyle(button4)

        val button5 = document.createElement("button") as HTMLButtonElement
        button5.innerHTML = "5"
        changeStyle(button5)

        val button6 = document.createElement("button") as HTMLButtonElement
        button6.innerHTML = "6"
        changeStyle(button6)

        val buttonMul = document.createElement("button") as HTMLButtonElement
        buttonMul.innerHTML = "*"
        changeStyle(buttonMul)

        row.appendChild(buttonFake)
        row.appendChild(button4)
        row.appendChild(button5)
        row.appendChild(button6)
        row.appendChild(buttonMul)
        row.style.width = "100%"

        content.appendChild(row)

        button4.addEventListener("click", {
            input.append("4")
            blockOp = false
            updateResult()
        })

        button5.addEventListener("click", {
            input.append("5")
            blockOp = false
            updateResult()
        })

        button6.addEventListener("click", {
            input.append("6")
            blockOp = false
            updateResult()
        })

        buttonMul.addEventListener("click", {
            if(!blockOp) {
                blockOp = true
                input.append(" * ")
                updateResult()
            }
        })
    }

    private fun make3CalcRow() {
        val row = document.createElement("div") as HTMLDivElement

        val buttonSqrt = document.createElement("button") as HTMLButtonElement
        buttonSqrt.innerHTML = "sqrt "
        changeStyle(buttonSqrt)

        val button1 = document.createElement("button") as HTMLButtonElement
        button1.innerHTML = "1"
        changeStyle(button1)

        val button2 = document.createElement("button") as HTMLButtonElement
        button2.innerHTML = "2"
        changeStyle(button2)

        val button3 = document.createElement("button") as HTMLButtonElement
        button3.innerHTML = "3"
        changeStyle(button3)

        val buttonSub = document.createElement("button") as HTMLButtonElement
        buttonSub.innerHTML = "-"
        changeStyle(buttonSub)

        row.appendChild(buttonSqrt)
        row.appendChild(button1)
        row.appendChild(button2)
        row.appendChild(button3)
        row.appendChild(buttonSub)
        row.style.width = "100%"

        content.appendChild(row);

        buttonSqrt.addEventListener("click", {
            input.append(" sqrt ")
            blockOp = true
            updateResult()
        })

        button1.addEventListener("click", {
            input.append("1")
            blockOp = false
            updateResult()
        })

        button2.addEventListener("click", {
            input.append("2")
            blockOp = false
            updateResult()
        })

        button3.addEventListener("click", {
            input.append("3")
            blockOp = false
            updateResult()
        })

        buttonSub.addEventListener("click", {
            if(!blockOp) {
                blockOp = true
                input.append(" - ")
                updateResult()
            }
        })
    }

    private fun make4CalcRow() {
        val row = document.createElement("div") as HTMLDivElement

        val buttonPow = document.createElement("button") as HTMLButtonElement
        buttonPow.innerHTML = "pow "
        changeStyle(buttonPow)

        val button0 = document.createElement("button") as HTMLButtonElement
        button0.innerHTML = "0"
        changeStyle(button0)

        val buttonDot = document.createElement("button") as HTMLButtonElement
        buttonDot.innerHTML = "."
        changeStyle(buttonDot)

        val buttonEq = document.createElement("button") as HTMLButtonElement
        buttonEq.innerHTML = "="
        changeStyle(buttonEq)

        val buttonAdd = document.createElement("button") as HTMLButtonElement
        buttonAdd.innerHTML = "+"
        changeStyle(buttonAdd)

        row.appendChild(buttonPow)
        row.appendChild(button0)
        row.appendChild(buttonDot)
        row.appendChild(buttonEq)
        row.appendChild(buttonAdd)
        row.style.width = "100%"

        content.appendChild(row);

        buttonPow.addEventListener("click", {
            input.append(" pow ")
            blockOp = true
            updateResult()
        })

        button0.addEventListener("click", {
            input.append("0")
            blockOp = false
            updateResult()
        })

        buttonDot.addEventListener("click", {
            input.append(".")
            blockOp = false
            updateResult()
        })

        buttonEq.addEventListener("click", {
            blockOp = false
            calculateResult()
        })

        buttonAdd.addEventListener("click", {
            if(!blockOp) {
                blockOp = true
                input.append(" + ")
                updateResult()
            }
        })
    }

    private fun calculateResult(){
        presenter.calculateEquation(input)
    }

    private fun changeStyle(el : HTMLElement){
        el.style.width = "70"
        el.style.height = "40"
        el.style.fontFamily = "Arial,sans-serif"
        el.style.fontSize = "large"
    }
}