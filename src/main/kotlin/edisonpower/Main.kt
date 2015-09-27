package edisonpower

import org.ligi.plughub.EdiMaxCommands
import org.ligi.plughub.EdiMaxCommunicator
import org.ligi.plughub.EdiMaxConfig
import java.io.File
import java.lang.Double

val cfg = EdiMaxConfig()
val comm = EdiMaxCommunicator(cfg)
val version = "0.1"
val logDir = File("logs")
var i = 0
var switchedOn = false

fun main(args: Array<String>) {
    printLCD("Meter Edison ${version}", "to " + cfg.host)
    println("Meter Edison connecting to " + cfg.host)
    poll()
}


fun poll() {
    comm.executeCommand(EdiMaxCommands.CMD_GET_POWER, { response ->
        val nowPower = EdiMaxCommands.unwrapNowPower(response.toString())
        println("response:" + nowPower)

        val switchedOnNew = Double.parseDouble(nowPower) < 0.1

        if (switchedOn != switchedOnNew) {
            beep()
            switchedOn = switchedOnNew
        }
        printLCD(nowPower + "W", "" + i)
        i++
        poll() // loop

    })
}

private fun printLCD(line1: String?, line2: String?) {
    val runtime = Runtime.getRuntime()
    runtime.exec("/home/root/bin/rgb-lcd.py 0 0 255 \"${line1}\" \"${line2}\" ")
}

private fun beep() {
    val runtime = Runtime.getRuntime()
    runtime.exec("/home/root/bin/buzzer.py")
}