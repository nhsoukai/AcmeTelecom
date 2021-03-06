package com.acmetelecom;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.swing.text.DateFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Call {
    private CallEvent start;
    private CallEvent end;
    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yy K:mm a");

    public Call(CallEvent start, CallEvent end) {
        this.start = start;
        this.end = end;
    }


    public String callee() {
        return start.getCallee();
    }


    public int durationSeconds() {
        return (int) (((end.getMillis() - start.getMillis()) / 1000));
    }

    // Needs to return following format: 11/15/13 4:21 PM
    public String date() {
        return formatter.print(start.time());

    }

    public DateTime startTime() {
        return start.time();
    }

    public DateTime endTime() {
        return end.time();
    }
}
