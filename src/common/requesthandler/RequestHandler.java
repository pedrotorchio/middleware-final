package common.requesthandler;

import common.marshaller.Marshaller;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

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
        System.out.println("Sending");
        System.out.println(request.toString() + "\n");

        out.write(
                new Marshaller()
                        .marshall(request));

        out.flush();

        return this;
    }
    public RequestHandler receive() throws IOException {

        Request req = new Marshaller()
                            .unmarshall(in);
        String body = req.getBody();
                req.setBody(body);
                req.addHeader("client", sock.getRemoteSocketAddress().toString());
                req.addHeader("port", ""+sock.getPort());
        request = req;

        System.out.println("Receiving");
        System.out.println(request.toString() + "\n");

        return this;
    }
    public void close() throws IOException {
        in.close();
        out.close();
        sock.close();
    }

    protected void mkStreams() throws IOException {
        out  = new ObjectOutputStream(sock.getOutputStream());
        in   = new ObjectInputStream(sock.getInputStream());
    }
}
