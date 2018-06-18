package middleAir.common.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger{

    File file;
    BufferedWriter writer;
    String folder = "";
    String lastMsg = "";
    boolean doPrint = false;
    static Logger singleton;


    public boolean saves2File(){

        return writer != null;
    }

    public Logger shouldPrint(boolean should){
        doPrint = should;
        return this;
    }
    public boolean log(){
        return log(lastMsg);
    }
    public boolean log(String message) {

        if(!saves2File())
            return false;

        try {
            writer.append(message);
        } catch (IOException e) {
            return false;
        }
        try {
            writer.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean save2File(String filename){
        this.file = new File(folder + filename + ".txt");

        try {

            this.writer = new BufferedWriter(new FileWriter(file));

        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public Logger maybePrintln(String message){
        if(doPrint)
            println(message);

        return this;
    }
    public Logger maybePrintln(String title, String message){
        if(doPrint)
            println(title, message);

        return this;
    }

    public Logger println(String message) {
        message = message + "\n---\n";

        print(message);

        return this;
    }

    public Logger println(String title, String message) {
        return println(String.format(
                "--------[ %s" +
                        "\n%s"
                , title, message));
    }
    public Logger maybePrint(String message){
        if(doPrint)
            print(message);

        return this;
    }
    public Logger maybePrint(String title, String message){
        if(doPrint)
            print(title, message);

        return this;
    }

    public Logger print(String message) {
        message = "\n" + message;
        lastMsg = message;

        System.out.print(message);

        return this;
    }

    public Logger print(String title, String message) {
        return print(String.format(
                "--------[ %s" +
                        "\n %s"
                , title, message));
    }

    public static void main(String[] args){
        Logger log = new Logger();
               log.save2File("logger");
               log.maybePrintln(""+log.saves2File());
        log.maybePrintln("Titulo Irado", "Mensagem muito louca \n Com outras linhas");
        log.maybePrintln("Titulo Irado", "Mensagem muito louca").log();
        log.shouldPrint(false);

        log.maybePrintln("Mensagem muito louca");

        log.maybePrintln("Mensagem muito louca").log();
        log.maybePrintln("Titulo Irado", "Mensagem muito louca \n Com outras linhas");

        log.maybePrintln("Titulo Irado", "Mensagem muito louca").log();
        log.maybePrintln("Mensagem muito louca");
        log.shouldPrint(true);
        log.maybePrintln("Titulo Irado", "Mensagem muito louca");
    }
    public static Logger getSingleton(){
        if(singleton == null)
            singleton = new Logger();
        return singleton;
    }
}
