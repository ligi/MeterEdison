package edisonpower

open class EdisonUserInterface {

    open fun printLCD(line1: String?, line2: String?) {
        println(line1)
        println(line2)
    }

    open fun beep() {
        println("beep")
    }
}