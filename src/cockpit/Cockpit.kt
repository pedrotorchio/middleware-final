package cockpit

import controllers.cockpit.proxies.AuthenticationKeyboardProxy
import controllers.cockpit.proxies.MonitorProxy
import controllers.cockpit.proxies.ThrottleProxy
import controllers.cockpit.proxies.YokeProxy
import middleAir.MiddleAir
import middleAir.common.exceptions.NotFoundException
import middleAir.common.logger.Logger

import java.util.Date

class Cockpit {
    internal var mAir: MiddleAir? = null
    internal lateinit var yoke: YokeProxy
    internal lateinit var throttle: ThrottleProxy
    internal var monitor: MonitorProxy? = null
    val power: Int
        get() {
            if (mAir == null) return 0
            val power = throttle.power
            printTitle("Potência " + power + "pwr")

            return power
        }

    val isOn: Boolean
        get() {
            if (mAir == null) return false
            val ison = throttle.isOn

            printTitle("Motores " + if (ison) "ligados" else "desligados")

            return ison
        }

    @Throws(InterruptedException::class)
    fun emulate() {
        while (!setup());


        printTitle("INICIANDO SIMULAÇÃO")

        on()

        powerUp(3000)
        rise(45)
        rise(-45)
        off()

        // viaja um pouco
        Thread.sleep(3000)

        on()
        powerUp(3000)
        steer(20)
        steer(-20)
        powerDown(2500)
        rise(-30)

    }

    fun rise(angle: Int): Int {
        var angle = angle
        if (mAir == null) return 0
        printTitle("Subindo/Descendo $angle graus")
        angle = yoke.rise(angle)
        printResult("Ângulo Final:" + angle)

        return angle
    }

    fun steer(angle: Int): Int {
        var angle = angle
        if (mAir == null) return 0
        printTitle("Virando $angle graus")
        angle = yoke.steer(angle)
        printResult("Ângulo Final:" + angle)

        return angle
    }

    fun off(): Boolean {
        if (mAir == null) return false
        printTitle("Desligando motor")
        val off = throttle.off()
        if (off)
            printResult("Motor Desligado")
        else
            printResult("Não foi possível desligar todos os motores")

        return off
    }

    fun on(): Boolean {
        if (mAir == null) return false
        printTitle("Ligando motor")
        val on = throttle.on()
        if (on)
            printResult("Motor Ligado")
        else
            printResult("Não foi possível ligar todos os motores")
        return on
    }

    fun powerUp(power: Int): Int {
        var power = power
        if (mAir == null) return 0
        printTitle("Acelerando " + power + "pwr")
        power = throttle.powerUp(power)
        printResult("Potência Final:" + power)
        return power
    }

    fun powerDown(power: Int): Int {
        var power = power
        if (mAir == null) return 0
        printTitle("Desacelerando " + power + "pwr")
        power = throttle.powerDown(power)
        printResult("Potência Final:" + power)

        return power
    }

    @Throws(InterruptedException::class)
    fun repeat(starTimestamp: Long, repeat: Int, value: Int) {
        for (i in 0 until repeat) {
            runCommandForValue(value)
            Thread.sleep(2)
        }

        val endTimestamp = Date().time
        val diff = endTimestamp - starTimestamp

        Logger.getSingleton().println("Durou $diff milisegundos")
    }

    private fun runCommandForValue(c: Int) {
        when (c.toChar()) {
            'n' -> {
                on()
                off()
            }
            'm' -> off()
            'q' -> powerDown(10)
            'e' -> powerUp(10)
            'w' -> rise(-10)
            's' -> rise(10)
            'a' -> steer(-10)
            'd' -> steer(10)
            'p' -> power
        }
    }

    fun printTitle(title: String) {
        println("\n--------[ " + title)
    }

    fun printResult(message: String?) {
        var message = message
        message = if (message == null) "" else "\t\t" + message + "\n"
        println(message)
    }

    fun setup(): Boolean {
        // autentica usuário com nome e senha
        // define o canal de saída para mensagens dos proxies (monitor do cockpit)

        mAir = MiddleAir()
        val authKeyboard: AuthenticationKeyboardProxy

        try {

            authKeyboard = AuthenticationKeyboardProxy(mAir!!.lookup("auth-keyboard"))
            if (!mAir!!.authenticate(authKeyboard))
                return false

            yoke = YokeProxy(mAir!!.lookup("air-yoke"))
            throttle = ThrottleProxy(mAir!!.lookup("air-throttle"))


        } catch (e: NotFoundException) {
            monitor!!.writeError("Problemas encontrados.")
            return false
        }

        return mAir!!.checkComponents()
    }

}
