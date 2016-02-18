package uetsupport.dtui.uet.edu.uetsupport.models;

/**
 * Created by huylv on 30-Dec-15.
 */
public class Reminder {
    private String time;
    private String title;
    private boolean ON;

    public Reminder(String time, String title, boolean ON) {
        this.time = time;
        this.title = title;
        this.ON = ON;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isON() {
        return ON;
    }

    public void setON(boolean ON) {
        this.ON = ON;
    }
}
