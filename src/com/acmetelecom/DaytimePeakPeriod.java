package com.acmetelecom;

import org.joda.time.*;
import org.joda.time.MutableDateTime;

public class DaytimePeakPeriod {
    Interval interval;

    public DaytimePeakPeriod (){
        MutableDateTime peak1= new MutableDateTime();
        peak1.setTime(7,0,0,0);
        MutableDateTime peak2= new MutableDateTime();
        peak2.setTime(19,0,0,0);
        interval = new Interval(peak1,peak2);
    }
    //Made this function static
   /* public boolean offPeak(DateTime time) {
        int hour = time.getHourOfDay();
        return hour < 7 || hour >= 19;
    }  */
      /*
    public boolean offPeak(Call call){
        Interval peak= initializePeak();
        DateTime startTime=call.startTime();
        DateTime endTime=call.endTime();
        Interval callInterval= new Interval(startTime,endTime);
        return !callInterval.overlaps(peak);
    }        */

    public long offPeakDuration(CallInterface call) {

        DateTime startTime=call.startTime();
        DateTime endTime=call.endTime();
        Duration callInterval= new Duration(startTime,endTime);
        if (callInterval.minus(peakDuration(call))==null) return 0;
        else
            return callInterval.minus(peakDuration(call)*1000).getStandardSeconds();
    }
    public long peakDuration(CallInterface call) {
        DateTime startTime=call.startTime();
        DateTime endTime=call.endTime();
        Interval callInterval= new Interval(startTime,endTime);
        Interval peakInterval = callInterval.overlap(interval);
        if (peakInterval==null) return 0;
        else return peakInterval.toDuration().getStandardSeconds();
    }

}