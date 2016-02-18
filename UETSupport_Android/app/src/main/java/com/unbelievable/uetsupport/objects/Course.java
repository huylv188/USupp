package com.unbelievable.uetsupport.objects;

/**
 * Created by huylv on 22/11/2015.
 */
public class Course {
    String name;
    boolean turnedOnRemind;

    public Course(String name, boolean turnedOnRemind) {
        this.name = name;
        this.turnedOnRemind = turnedOnRemind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTurnedOnRemind() {
        return turnedOnRemind;
    }

    public void setTurnedOnRemind(boolean turnedOnRemind) {
        this.turnedOnRemind = turnedOnRemind;
    }
}
