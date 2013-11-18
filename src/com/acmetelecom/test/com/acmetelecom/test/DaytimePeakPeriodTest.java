package com.acmetelecom.test;

/*
 * Created with IntelliJ IDEA.
 * User: nhsoukaina
 * Date: 15/11/13
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */

import com.acmetelecom.Call;
import com.acmetelecom.CallEnd;
import com.acmetelecom.CallStart;
import com.acmetelecom.DaytimePeakPeriod;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.junit.*;
import static org.junit.Assert.assertEquals;

public class DaytimePeakPeriodTest {


    MutableDateTime start=new MutableDateTime();
    MutableDateTime end=new MutableDateTime();
    DaytimePeakPeriod daytimePeakPeriod = new DaytimePeakPeriod();

    @Test
    public void startOverlapPeakTime() {
        start.setTime(6,50,0,0);
        end.setTime(7,10,0,0);

        CallStart callStart= new CallStart("447722113434", "447766511332", new DateTime(2013,12,12,6,50));
        CallEnd callEnd= new CallEnd("447722113434", "447766511332", new DateTime(2013,12,12,7,10));
                //end.toDateTime());
        Call call=new Call(callStart,callEnd);

        assertEquals(600, daytimePeakPeriod.offPeakDuration(call));
        assertEquals(600, daytimePeakPeriod.peakDuration(call));

    }

    @Test
    public void endOverlapPeakTime(){

        start.setTime(18,0,0,0);
        end.setTime(20,0,0,0);
        CallStart callStart= new CallStart("447722113434", "447766511332", new DateTime(2012,12,12,18,0));
        CallEnd callEnd= new CallEnd("447722113434", "447766511332", new DateTime(2012,12,12,20,0));
        Call call=new Call(callStart,callEnd);

        assert(daytimePeakPeriod.offPeakDuration(call)==3600);
        assert(daytimePeakPeriod.peakDuration(call)==3600);

    }

    @Test
    public void onlyPeakTime(){
        start.setTime(12,0,0,0);
        end.setTime(13,0,0,0);
        CallStart callStart= new CallStart("447722113434", "447766511332", start.toDateTime());
        CallEnd callEnd= new CallEnd("447722113434", "447766511332", end.toDateTime());
        Call call=new Call(callStart,callEnd);

        assert(daytimePeakPeriod.offPeakDuration(call)==0);
        assert(daytimePeakPeriod.peakDuration(call)==3600);
    }


    @Test
    public void onlyOffPeakTime(){
        start.setTime(6,30,0,0);
        end.setTime(7,0,0,0);
        CallStart callStart= new CallStart("447722113434", "447766511332", start.toDateTime());
        CallEnd callEnd= new CallEnd("447722113434", "447766511332", end.toDateTime());
        Call call=new Call(callStart,callEnd);

        assert(daytimePeakPeriod.offPeakDuration(call)==1800);
        assert(daytimePeakPeriod.peakDuration(call)==0);
    }


}