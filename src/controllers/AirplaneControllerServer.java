package controllers;

import controllers.cockpit.components.AuthenticationKeyboard;
import controllers.cockpit.components.Monitor;
import controllers.cockpit.components.Throttle;
import controllers.cockpit.components.Yoke;
import controllers.simulators.Airplane;
import middleAir.common.invoker.InvokerExecution;
import middleAir.naming.NamingProxy;

public class AirplaneControllerServer{
    static final int PORT = 4325;
    static final String HOST = "localhost";

    public static void main(String [] args) throws Exception{
        Airplane plane = new Airplane();

        Yoke yoke = new Yoke(plane);
             yoke.setHost(HOST);
             yoke.setPort(PORT);
        Throttle throttle = new Throttle(plane);
                 throttle.setHost(HOST);
                 throttle.setPort(PORT);

        AuthenticationKeyboard key = new AuthenticationKeyboard();
                               key.setHost(HOST);
                               key.setPort(PORT);
        Monitor monitor = new Monitor();
                monitor.setHost(HOST);
                monitor.setPort(PORT);


        new NamingProxy("localhost")
                .bind(yoke)
                .bind(throttle)
                .bind(key)
                .bind(monitor);

        new InvokerExecution()
                .registerService(yoke)
                .registerService(throttle)
                .registerService(key)
                .registerService(monitor)
                .listen(PORT);

    }
}
