package com.logistic.model.systemunits.entities;

import java.sql.Date;

/**
 * Created by Vojts on 10.12.2016.
 */
public class TimeTable extends Entity {

    private Date timeBegin;
    private Date timeEnd;

    public void setTimeBegin(Date timeBegin) {
        this.timeBegin = timeBegin;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Date getTimeBegin() {
        return timeBegin;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }
}
