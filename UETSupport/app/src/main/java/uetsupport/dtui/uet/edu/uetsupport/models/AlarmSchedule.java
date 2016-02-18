package uetsupport.dtui.uet.edu.uetsupport.models;

/**
 * Created by huylv on 27-Dec-15.
 */
public class AlarmSchedule {
    String time;
    boolean turnOn;

    public AlarmSchedule(String time, boolean turnOn) {
        this.time = time;
        this.turnOn = turnOn;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isTurnOn() {
        return turnOn;
    }

    public void setTurnOn(boolean turnOn) {
        this.turnOn = turnOn;
    }
}
