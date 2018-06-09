package middleAir.common.invoker;

public class Time{
    public long timestamp = -1;

    public boolean isSet(){
        return timestamp != -1;
    }

    public String toString(){
        return String.format("%d", timestamp);
    }

    public static Time parse(String stringtime){
        Time t = new Time();
             t.timestamp = Long.parseLong(stringtime);

        return t;
    }
    public static Time now(){
        Time t = new Time();
             t.timestamp = System.currentTimeMillis();

        return t;
    }
    public static Time secondsLater(int seconds){

        Time now = now();
             now.timestamp += seconds*1000;

             return now;
    }

    public boolean passed(Time other){
        return timestamp > other.timestamp;

    }

}
