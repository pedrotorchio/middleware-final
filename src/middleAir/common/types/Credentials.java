package middleAir.common.types;

import java.lang.reflect.MalformedParametersException;

public class Credentials{
    public String uid;
    public String key;
    public String authKey;

    public Credentials(String uid, String key) {
        this.uid = uid;
        this.key = key;
    }

    public String toString() {

        return uid + " " + authKey;
    }

    public Credentials setAuthKey(String key){
        authKey = key;
        return this;
    }
    public boolean isAuthenticated(){
        return authKey != null && !authKey.equals("null");
    }
    static public String serialize(Credentials object) {

        return String.format("%s:%s:%s", object.uid, object.key, object.authKey);
    }

    static public Credentials deserialize(String object) throws MalformedParametersException{
        String [] parts = object.split(":");

        for(String part : parts)
            part = part.equals("null")? null : part;

        if(parts.length != 3)
            throw new MalformedParametersException();

        Credentials cred = new Credentials(parts[0], parts[1]);
                    cred.setAuthKey(parts[2]);

        return cred;
    }
    static public Credentials fromString(String creds) throws MalformedParametersException{
        String[] parts = creds.split(" ");

        if(parts.length != 2)
            throw new MalformedParametersException();

        Credentials cred = new Credentials(parts[0], null);
                    cred.setAuthKey(parts[1]);

        return cred;
    }
}
