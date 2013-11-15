package com.acmetelecom;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

class DaytimePeakPeriod {

    //Made this function static
    public boolean offPeak(DateTime time) {
        int hour = time.getHourOfDay();
        return hour < 7 || hour >= 19;
    }
}
