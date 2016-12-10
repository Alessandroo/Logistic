package com.logistic.model.systemunits.entities;

/**
 * Created by Vojts on 10.12.2016.
 */
public class History extends Entity {
    private User user;

    private String table;

    private String action;

    private String info;


    public void setAction(String action) {
        this.action = action;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public User getUser() {
        return user;
    }

    public String getAction() {
        return action;
    }

    public String getInfo() {
        return info;
    }

    public String getTable() {
        return table;
    }
}
