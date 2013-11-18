package com.acmetelecom;

import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.TariffLibrary;
import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: andreipetric
 * Date: 15/11/2013
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */

//Dummy class used to control the time at which calls start
//NOT PUBLIC so that other classes can't access it
public class BillingSystemTestDummy extends BillingSystem {
    public BillingSystemTestDummy(TariffLibrary tariffLibrary,
                                  CustomerDatabase customerDatabase,
                                  BillGeneratorInterface billGenerator,
                                  DaytimePeakPeriod daytimePeakPeriod) {
        super(tariffLibrary,customerDatabase,billGenerator,daytimePeakPeriod);
    }
    public void callInitiatedAt(String caller, String callee, DateTime time) {
        callLog.add(new CallStart(caller,callee,time));
    }

    public void callCompletedAt(String caller, String callee, DateTime time) {
        callLog.add(new CallEnd(caller,callee,time));
    }
}
