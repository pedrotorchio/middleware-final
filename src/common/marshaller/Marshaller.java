package common.marshaller;

import common.requesthandler.Request;

import java.io.*;

public class Marshaller {
    public byte[] marshall(Request req) throws IOException {

        // serializar request

        byte[] head = req.getHeader().toString().replaceAll("(\\{|\\})", "").trim().getBytes();
        byte[] body = req.getBody().getBytes();

        ByteArrayOutputStream  ba = new ByteArrayOutputStream();
        DataOutputStream       ds = new DataOutputStream(ba);

        writeChunk(ds, head);
        writeChunk(ds, body);

        ds.flush();
        ds.close();

        return ba.toByteArray();
    }

    public Request unmarshall(InputStream stream) throws IOException {

        DataInputStream    oi = new DataInputStream(stream);

        byte[] head = readChunk(oi);
        byte[] body = readChunk(oi);


        String sBody = new String(body);
        String sHead = new String(head);

        Request req = new Request();


        for (String single : sHead.split(",")) {
            String[] kv = single.split("=", 2);

            if (kv.length == 2) {
                String key = kv[0], value = kv[1];
                req.addHeader(key.trim(), value.trim());
            }

        }
        req.setBody(sBody);

        return req;
    }

    protected void writeChunk(DataOutputStream stream, byte[] bytes) {
        try {
            stream.writeInt(bytes.length);
            stream.write(bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected byte[] readChunk(DataInputStream stream) throws IOException {
        byte[] chunk = new byte[stream.readInt()];
        stream.read(chunk, 0, chunk.length);

        return chunk;
    }
}
