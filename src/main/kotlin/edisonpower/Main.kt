package edisonpower

import org.ligi.plughub.EdiMaxCommands
import org.ligi.plughub.EdiMaxCommunicator
import org.ligi.plughub.EdiMaxConfig

val cfg = EdiMaxConfig()
val comm = EdiMaxCommunicator(cfg)

fun main(args: Array<String>) {
    println("Meter Edison connecting to" + cfg.host)
    poll()
}


fun poll() {
    comm.executeCommand(EdiMaxCommands.CMD_GET_POWER, { response ->
        val nowPower = EdiMaxCommands.unwrapNowPower(response.toString())
        println("response:" + nowPower)

        val runtime = Runtime.getRuntime()
        runtime.exec("/home/root/bin/rgb-lcd.py 0 0 255 1 " + nowPower)

        poll() // loop
    })
}