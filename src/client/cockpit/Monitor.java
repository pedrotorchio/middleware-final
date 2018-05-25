package client.cockpit;

import common.clientproxy.ClientProxy;

public class Monitor implements ClientProxy.Output {
    public void write(String message) {
        message = consoleString(message, "-");

        System.out.println("- DISPLAY:");
        System.out.println(message);
    }

    public void writeError(String message) {
        write("ERRO: " + message != null ? message : "~");
    }

    public void writeError(Exception e) {
        writeError(e.getMessage());
    }

    protected String consoleString(String message, String PS1) {
        String ls = System.getProperty("line.separator");
        String[] lines = message.split(ls);
        message = "";

        for (String line : lines)
            message += PS1 + "\t\t" + line + ls;

        return message;
    }
}
