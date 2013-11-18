package com.acmetelecom;

import org.joda.time.*;
import org.joda.time.MutableDateTime;

class DaytimePeakPeriod {


    static public Interval initializePeak(DateTime day){
        MutableDateTime peak1=day.toMutableDateTime();
        MutableDateTime peak2= day.toMutableDateTime();
        peak1.setTime(7,0,0,0);
        peak2.setTime(19,0,0,0);
        return new Interval(peak1,peak2);
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

    static public long offPeakDuration(Call call) {

        DateTime startTime=call.startTime();
        DateTime endTime=call.endTime();
        Duration callInterval= new Duration(startTime,endTime);
        if (callInterval.minus(peakDuration(call)*1000)==null) return 0;
        else
        return callInterval.minus(peakDuration(call)*1000).getStandardSeconds();
    }
    static public long peakDuration(Call call) {

        DateTime startTime=call.startTime();
        Interval peak= initializePeak(startTime);
        DateTime endTime=call.endTime();
        Interval callInterval= new Interval(startTime,endTime);
        Interval peakInterval = callInterval.overlap(peak);
        if (peakInterval==null) return 0;
        else return peakInterval.toDuration().getStandardSeconds();
    }

}
