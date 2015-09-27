package edisonpower

class EdisonUserInterfaceViaPython : EdisonUserInterface() {

    override
    fun printLCD(line1: String?, line2: String?) {
        val runtime = Runtime.getRuntime()
        runtime.exec("/home/root/bin/rgb-lcd.py 0 0 255 \"${line1}\" \"${line2}\" ")
    }

    override
    fun beep() {
        val runtime = Runtime.getRuntime()
        runtime.exec("/home/root/bin/buzzer.py")
    }
}