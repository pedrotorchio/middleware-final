package StringExtractorService.requesthandler;

import StringExtractorService.marshaller.Marshaller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler {
    protected Socket sock;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;

    static final String EOF = "#";

    public RequestHandler send(Request req)
                                            throws IOException {
        System.out.println(req.toString());
        System.out.println();

        req.setBody(req.getBody() + EOF); // # indica fim do conteúdo

        int    port = Integer.parseInt(req.getHeader().get("port"));
        String host = req.getHeader().get("host");

        sock = new Socket(host, port);
        out  = new ObjectOutputStream(sock.getOutputStream());
        in   = new ObjectInputStream(sock.getInputStream());

        out.write(
                new Marshaller()
                        .marshall(req));

        return this;
    }
    public Request receive() throws IOException {
        String chunk = "";
        String buffr = "";

        boolean eof = false;
        while(!eof){
            byte[] bytes = {in.readByte()};
            chunk = new String(bytes);

            if(chunk == EOF)
                eof = true;
            else
                buffr += chunk;
        }

        Request req = new Request();
                req.setBody(buffr);

        return req;
    }


}
