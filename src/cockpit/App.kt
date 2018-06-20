package cockpit

import middleAir.common.logger.Logger

import java.util.Date
import java.util.Scanner

object App {

    private lateinit var pit: Cockpit
    private var `in` = Scanner(System.`in`)
    private val command: IntArray
        get() {

            val line = `in`.nextLine()

            val comm = intArrayOf('\n'.toInt(), -1, -1)

            if (line.isEmpty())
                return comm

            val command = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val commC = command[0][0].toInt()

            var value = -1

            if (command.size == 2) {
                value = Integer.parseInt(command[1])

            } else if (command.size > 2) {
                value = command[1][0].toInt()
                val repeat = Integer.parseInt(command[2])
                comm[2] = repeat
            }

            comm[0] = commC
            comm[1] = value

            return comm
        }

    @Throws(InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {

        pit = Cockpit()
        start()

    }

    @Throws(InterruptedException::class)
    private fun start() {
        do {
            Logger.getSingleton().println("<enter> para iniciar teclado de autenticação")
            `in`.nextLine()
            Logger.getSingleton().println("Verifique o teclado...")

        } while (!pit.setup())

        do {

            val command = commandMenu()
            val c = command[0]
            var v = command[1]
            val repeat = command[2]

            when (c.toChar()) {
                'q' -> if (v == -1)
                    v = 500
                'e' -> if (v == -1)
                    v = 1000
                'a', 'd', 's', 'w' -> if (v == -1)
                    v = 10
            }
            when (c.toChar()) {
                'n' -> pit.on()
                'm' -> pit.off()
                'q' -> pit.powerDown(v)
                'e' -> pit.powerUp(v)
                'w' -> pit.rise(-v)
                's' -> pit.rise(v)
                'a' -> pit.steer(-v)
                'd' -> pit.steer(v)
                'p' -> pit.power
                't' -> {
                    val startTimestamp = Date().time
                    pit.repeat(startTimestamp, repeat, v)
                }
            }

        } while (true)
    }

    private fun commandMenu(): IntArray {
        Logger.getSingleton().println("Comandos ( em minúsculo )",
                " N - Ligar\n" +
                        " M - Desligar\n\n" +
                        " P  - Visualizar Potência\n" +
                        " Q  <num (500pwr)>  - Reduzir Potência\n" +
                        " E  <num (1000pwr)> - Aumentar Potência\n" +
                        " W  <num (10deg)>   - Navegação\n" +
                        "ASD\n" +
                        " T  <comando> <repetições> - Avaliação"

        )
        return command
    }

}
