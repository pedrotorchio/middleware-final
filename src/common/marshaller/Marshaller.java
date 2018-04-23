package common.marshaller;

import common.requesthandler.Request;

import java.io.*;

public class Marshaller {
    public byte[] marshall(Request req) throws IOException {

        byte[] body   = req.getBody().getBytes();

        ByteArrayOutputStream  ba = new ByteArrayOutputStream();
        DataOutputStream       ds = new DataOutputStream(ba);
                               ds.writeInt(body.length);
                               ds.write(body, 0, body.length);

                               ds.flush();
                               ds.close();

        return ba.toByteArray();

    }

    public Request unmarshall(InputStream stream) throws IOException {

        DataInputStream    oi = new DataInputStream(stream);

        int bodySize = oi.readInt();
        byte[] body  = new byte[bodySize];
            oi.read(body, 0, bodySize);

        Request req = new Request();
                req.setBody(new String(body));

        return req;
    }
}
