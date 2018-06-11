package middleAir.common.requesthandler;

import middleAir.common.marshaller.Marshaller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler {
    protected Socket sock;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;
    protected Request request;

    static final char EOF = '!';

    public RequestHandler(Socket soq, Request req) throws IOException {
        this.sock = soq;
        this.request = req;

        mkStreams();
    }
    public Request getRequest(){
        return request;
    }
    public RequestHandler setRequest(Request req){
        request = req;
        return this;
    }
    public RequestHandler send() throws IOException{
        System.out.print("Sending ");
        System.out.println(request.toString() + "\n");

        out.write(
                new Marshaller()
                        .marshall(request));

        out.flush();

        return this;
    }
    public RequestHandler receive() throws IOException {

        request = new Marshaller()
                            .unmarshall(in);
//        request.addHeader("client", sock.getRemoteSocketAddress().toString());

        System.out.print("Receiving ");
        System.out.println(request.toString() + "\n");

        return this;
    }

    public void close() {

        try {
            in.close();
            out.close();
            sock.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isClosed(){
        return sock.isClosed();
    }
    protected void mkStreams() throws IOException {
        out  = new ObjectOutputStream(sock.getOutputStream());
        in   = new ObjectInputStream(sock.getInputStream());
    }
}
