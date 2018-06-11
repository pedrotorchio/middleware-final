package middleAir.security.common;

public class BasicTextEncryptor {
    private String password;
    private final String START = "+";
    private final String END   = "==";
    private final int LENGTH = 16;
    public void setPassword(String pass){

        password = pass;
    }

    public String encrypt(String s) {
        String enc = START;
        for(int i = 0 ; i < LENGTH - (START.length() + END.length()); i++){

            int  charInt = (int)(Math.random() * 45 + 45);
            char randChar = (char)charInt;

            enc += randChar;
        }
        enc += END;
        return enc;
    }
    public static void main(String[] args){
        BasicTextEncryptor enc = new BasicTextEncryptor();

        for(int i = 1 ; i < 16 ; i++){

            String pass = "";
            for(int j = i ; j > 0 ; j--)
                pass += j;

            enc.setPassword(pass);
            pass = enc.encrypt("testando isso daqui");

            System.out.println(pass);
        }



    }
}
