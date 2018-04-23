package common.remoteservice;

import java.net.InetAddress;
import java.util.ArrayList;

public interface IRemoteService {
    InetAddress getHost();
    int getPort();
    String getUid();

    void setHost(InetAddress host);
    void setPort(int port);
    void setUid(String uid);
}
