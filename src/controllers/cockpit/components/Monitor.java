package controllers.cockpit.components;

import middleAir.common.exceptions.MiddleAirException;
import middleAir.common.logger.Logger;
import middleAir.common.remoteservice.InstanceService;
import middleAir.common.requesthandler.Request;

public class Monitor extends InstanceService implements IOutputChannel{

    public Monitor(){
        uid = "output-monitor";
    }

    public void write(String message) {
        message = consoleString(message, "-");

        Logger.getSingleton().println("DISPLAY", message);
    }

    public void writeError(String message) {
        write("ERRO: " + message != null ? message : "~");
    }

    protected String consoleString(String message, String PS1) {
        String ls = System.getProperty("line.separator");
        String[] lines = message.split(ls);
        message = "";

        for (String line : lines)
            message += PS1 + "\t\t" + line + ls;

        return message;
    }
    public String call(Request req, String methodname, String[] parameters) throws MiddleAirException {


        String angle = parameters[0];

        switch (methodname) {
            case "write":
                write(parameters[0]);
                return "ok.";

            case "writeError":
                writeError(parameters[0]);
                return "ok";

            default:
                return "DEFAULT";
        }

    }


    public boolean isProtected() {
        return false;
    }
}
