package com.unbelievable.uetsupport.objects;

/**
 * Created by DucAnhZ on 20/11/2015.
 */
public class Reminder {

    public Long reminderId;
    public Long timeReminder;
    public Long beforeReminder;
    public Integer numberOfReminder;
    public String title;
    public String note;
    public boolean isActivated;

    public Reminder(Long reminderId, Long timeReminder, Long beforeReminder, Integer numberOfReminder, String title, String note) {
        this.reminderId = reminderId;
        this.timeReminder = timeReminder;
        this.beforeReminder = beforeReminder;
        this.numberOfReminder = numberOfReminder;
        this.title = title;
        this.note = note;
        isActivated = true;
    }

    public Long getReminderId() {
        return reminderId;
    }

    public void setReminderId(Long reminderId) {
        this.reminderId = reminderId;
    }

    public Long getTimeReminder() {
        return timeReminder;
    }

    public void setTimeReminder(Long timeReminder) {
        this.timeReminder = timeReminder;
    }

    public Long getBeforeReminder() {
        return beforeReminder;
    }

    public void setBeforeReminder(Long beforeReminder) {
        this.beforeReminder = beforeReminder;
    }

    public Integer getNumberOfReminder() {
        return numberOfReminder;
    }

    public void setNumberOfReminder(Integer numberOfReminder) {
        this.numberOfReminder = numberOfReminder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
