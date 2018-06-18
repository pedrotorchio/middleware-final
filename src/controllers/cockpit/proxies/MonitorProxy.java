package controllers.cockpit.proxies;

import controllers.cockpit.components.IOutputChannel;
import middleAir.common.logger.Logger;

public class MonitorProxy implements IOutputChannel {

    public void write(String message){
        Logger.getSingleton().println("Display", message);
    }

    public void writeError(String message){
        Logger.getSingleton().println("Display", "!!! " + message);
    }
}
