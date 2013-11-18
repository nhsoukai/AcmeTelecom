package com.acmetelecom;

import org.joda.time.*;
import org.joda.time.MutableDateTime;
import java.util.*;

class DaytimePeakPeriod {


    static public Set<Interval> initializePeak(DateTime day1, DateTime day2){
        Set<Interval> set= new HashSet<Interval>();
        DateTime day=day1;
        while (day.isBefore(day2)){
        MutableDateTime peak1=day.toMutableDateTime();
        MutableDateTime peak2= day.toMutableDateTime();
        peak1.setTime(7,0,0,0);
        peak2.setTime(19,0,0,0);
        set.add(new Interval(peak1,peak2));
        day=day.plusDays(1);
        }
        return set;
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
        DateTime endTime=call.endTime();
        Set <Interval> peaks= initializePeak(startTime,endTime);
        Interval callInterval= new Interval(startTime,endTime);
        long peakDuration=0;
        for( Interval peak :peaks){
            Interval peakInterval = callInterval.overlap(peak);
        if (peakInterval!=null)  {
            peakDuration+=peakInterval.toDuration().getStandardSeconds();
                }
        }
                    return peakDuration;
    }

}
