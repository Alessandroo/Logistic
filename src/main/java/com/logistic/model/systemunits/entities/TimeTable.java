package com.logistic.model.systemunits.entities;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Vojts on 10.12.2016.
 */
public class TimeTable extends Entity {

    private Timestamp timeBegin;
    private Timestamp timeEnd;

    public void setTimeBegin(Timestamp timeBegin) {
        this.timeBegin = timeBegin;
    }

    public void setTimeEnd(Timestamp timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Timestamp getTimeBegin() {
        return timeBegin;
    }

    public Timestamp getTimeEnd() {
        return timeEnd;
    }
}
