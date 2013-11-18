package com.acmetelecom;

/*
 * Created with IntelliJ IDEA.
 * User: nhsoukaina
 * Date: 15/11/13
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
import org.joda.time.MutableDateTime;
import org.joda.time.*;
import org.junit.*;
import static org.junit.Assert.assertEquals;


public class DayTimePeakPeriodTest {


    MutableDateTime start=new MutableDateTime();
    MutableDateTime end=new MutableDateTime();

    @Test
    public void startOverlapPeakTime() {
        start.setTime(6,50,0,0);
        end.setTime(7,10,0,0);

        CallStart callStart= new CallStart("447722113434", "447766511332", new DateTime(2013,12,12,6,50));
        CallEnd callEnd= new CallEnd("447722113434", "447766511332", new DateTime(2013,12,12,7,10));
        //end.toDateTime());
        Call call=new Call(callStart,callEnd);

        assertEquals(600, DaytimePeakPeriod.offPeakDuration(call));
        assertEquals(600, DaytimePeakPeriod.peakDuration(call));

    }

    @Test
    public void overlapPeakTime(){

        start.setTime(18,0,0,0);
        end.setTime(20,0,0,0);
        CallStart callStart= new CallStart("447722113434", "447766511332", start.toDateTime());
        CallEnd callEnd= new CallEnd("447722113434", "447766511332", end.toDateTime());
        Call call=new Call(callStart,callEnd);

        assert(DaytimePeakPeriod.offPeakDuration(call)==3600);
        assert(DaytimePeakPeriod.peakDuration(call)==3600);

    }

    @Test
    public void onlyPeakTime(){
        start.setTime(12,0,0,0);
        end.setTime(13,0,0,0);
        CallStart callStart= new CallStart("447722113434", "447766511332", start.toDateTime());
        CallEnd callEnd= new CallEnd("447722113434", "447766511332", end.toDateTime());
        Call call=new Call(callStart,callEnd);

        assert(DaytimePeakPeriod.offPeakDuration(call)==0);
        assert(DaytimePeakPeriod.peakDuration(call)==3600);
    }


    @Test
    public void onlyOffPeakTime(){
        start.setTime(6,30,0,0);
        end.setTime(7,0,0,0);
        CallStart callStart= new CallStart("447722113434", "447766511332", start.toDateTime());
        CallEnd callEnd= new CallEnd("447722113434", "447766511332", end.toDateTime());
        Call call=new Call(callStart,callEnd);

        assert(DaytimePeakPeriod.offPeakDuration(call)==1800);
        assert(DaytimePeakPeriod.peakDuration(call)==0);
    }


}
