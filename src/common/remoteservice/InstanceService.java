package common.remoteservice;

import common.requesthandler.Request;

public abstract class InstanceService extends Service {
    public abstract Request execute(Request req, String methodname, String... parameters);

    public abstract boolean isProtected();
}
