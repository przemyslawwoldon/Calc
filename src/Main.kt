fun main(args: Array<String>) {

    val bookStorePresenter = CalcStorePresenter()
    val bookStorePage = CalcStorePage(bookStorePresenter)

    bookStorePresenter.attach(bookStorePage)

    bookStorePage.show()
    bookStorePage.createView()

}