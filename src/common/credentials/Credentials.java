package common.credentials;

public class Credentials {
    public String uid;
    public String key;

    public Credentials(String uid, String key) {
        this.uid = uid;
        this.key = key;
    }

    public String toString() {
        return uid + " " + key;
    }
}
