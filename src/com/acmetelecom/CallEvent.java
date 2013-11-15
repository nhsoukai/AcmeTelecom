package com.acmetelecom;

import org.joda.time.DateTime;

public abstract class CallEvent {
    private String caller;
    private String callee;
    private DateTime time;

    public CallEvent(String caller, String callee, DateTime time) {
        this.caller = caller;
        this.callee = callee;
        this.time = time;
    }

    public String getCaller() {
        return caller;
    }

    public String getCallee() {
        return callee;
    }

    public DateTime time() {
        return time;
    }

    public long getMillis() {
        return time.getMillis();
    }
}
