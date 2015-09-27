package edisonpower

import org.ligi.plughub.EdiMaxCommands
import org.ligi.plughub.EdiMaxCommunicator
import org.ligi.plughub.EdiMaxConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

val cfg = EdiMaxConfig()
val comm = EdiMaxCommunicator(cfg)
val version = "0.1"
val logDir = File("logs")
val ui = EdisonUserInterface()

var i = 0
var switchedOn = false

fun main(args: Array<String>) {
    logDir.mkdirs()
    ui.printLCD("MeterEdison${version}", "to " + cfg.host)
    println("Meter Edison connecting to " + cfg.host)
    poll()
}


fun poll() {
    comm.executeCommand(EdiMaxCommands.CMD_GET_POWER, { response ->
        val nowPower = EdiMaxCommands.unwrapNowPower(response.toString())
        println("response:" + nowPower)
        val simpleDateFormat = SimpleDateFormat("yyyy_MM_dd__HH_mm_ss")
        val date = Date()
        println("Date:" + simpleDateFormat.format(date))

        val switchedOnNew = nowPower!!.toDouble() < 0.1

        if (switchedOn != switchedOnNew) {
            ui.beep()
            switchedOn = switchedOnNew
        }
        ui.printLCD(nowPower + "W", "" + i)
        i++
        poll() // loop

    })
}

