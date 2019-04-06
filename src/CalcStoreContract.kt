interface CalcStoreContract {
    interface View {
        fun show()
        fun createView()
        fun showResult(s : String)
        fun printInfo(s: String)
        fun updateErrorInfo(s : String)
    }

    interface Presenter {
        fun attach(view: View)
        fun calculateEquation(sb : StringBuilder)
    }
}