package client.marshaller;

import client.requesthandler.Request;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Hashtable;

public class Marshaller {
    public byte[] marshall(Request req) throws IOException {

        byte[] body   = req.getBody().getBytes();

        ByteArrayOutputStream  ba = new ByteArrayOutputStream();
        ObjectOutputStream     ds = new ObjectOutputStream(ba);
                               ds.writeInt(body.length);
                               ds.write(body);

        return ba.toByteArray();

    }
    public Request unmarshall(byte[] serialized){

        Request req = new Request();
                req.setBody(new String(serialized));
        return req;
    }
}
