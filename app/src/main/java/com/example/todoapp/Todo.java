package com.example.todoapp;

import java.util.Date;
import java.util.UUID;

public class Todo {

    private UUID mID;
    private String mHeading;
    private String mInfo;
    private Date mDate;
    private int mIsComplete;

    public Todo() {
        this(UUID.randomUUID());
    }

    public Todo(UUID id){
        mID = id;
        mDate = new Date();
    }

    public UUID getID() {
        return mID;
    }

    public void setID(UUID id) {
        mID = id;
    }

    public String getHeading() {
        return mHeading;
    }

    public void setHeading(String heading) {
        mHeading = heading;
    }

    public String getInfo() {
        return mInfo;
    }

    public void setInfo(String info) {
        mInfo = info;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int isComplete() {
        return mIsComplete;
    }

    public void setComplete(int complete) {
        mIsComplete = complete;
    }

}