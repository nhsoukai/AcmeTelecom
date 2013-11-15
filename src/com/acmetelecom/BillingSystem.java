package com.acmetelecom;

import com.acmetelecom.customer.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class BillingSystem {
    private TariffLibrary tariffLibrary;
    private CustomerDatabase customerDatabase;
    private BillGeneratorInterface billGenerator;

    //Andrei - made this attribute protected
    protected List<CallEvent> callLog = new ArrayList<CallEvent>();

    //TESTING CONSTRUCTOR inserts a seam into the object
    public BillingSystem(TariffLibrary tarifflib, CustomerDatabase custDB,
                              BillGeneratorInterface bgi) {
        tariffLibrary = tarifflib;
        customerDatabase = custDB;
        billGenerator = bgi;
    }

    //Default constructor called normally
    public BillingSystem() {
        this(CentralTariffDatabase.getInstance(),
                      CentralCustomerDatabase.getInstance(),
                      new BillGenerator());
    }

    public void callInitiated(String caller, String callee) {
        callLog.add(new CallStart(caller, callee));
    }

    public void callCompleted(String caller, String callee) {
        callLog.add(new CallEnd(caller, callee));
    }

    public void createCustomerBills() {
        //Removed the instantiation of the customerDatabase here - injected in constructor
        List<Customer> customers = customerDatabase.getCustomers();
        for (Customer customer : customers) {
            createBillFor(customer);
        }
        callLog.clear();
    }

    private void createBillFor(Customer customer) {
        //Andrei - Moved constructor out of customer loop
        DaytimePeakPeriod peakPeriod = new DaytimePeakPeriod();



        List<CallEvent> customerEvents = new ArrayList<CallEvent>();
        for (CallEvent callEvent : callLog) {
            if (callEvent.getCaller().equals(customer.getPhoneNumber())) {
                customerEvents.add(callEvent);
            }
        }

        List<Call> calls = new ArrayList<Call>();

        CallEvent start = null;
        for (CallEvent event : customerEvents) {
            if (event instanceof CallStart) {
                start = event;
            }
            if (event instanceof CallEnd && start != null) {
                calls.add(new Call(start, event));
                start = null;
            }
        }

        BigDecimal totalBill = new BigDecimal(0);
        List<LineItem> items = new ArrayList<LineItem>();



        for (Call call : calls) {
            //Removed the instantiation of the tarrifLibrary here - injected in constructor
            Tariff tariff = tariffLibrary.tarriffFor(customer);

            BigDecimal cost;

            if (peakPeriod.offPeak(call.startTime()) && peakPeriod.offPeak(call.endTime()) && call.durationSeconds() < 12 * 60 * 60) {
                cost = new BigDecimal(call.durationSeconds()).multiply(tariff.offPeakRate());
            } else {
                cost = new BigDecimal(call.durationSeconds()).multiply(tariff.peakRate());
            }

            cost = cost.setScale(0, RoundingMode.HALF_UP);
            BigDecimal callCost = cost;
            totalBill = totalBill.add(callCost);
            items.add(new LineItem(call, callCost));
        }
        //Removed the instantiation of the billGenerator here - injected in constructor
        billGenerator.send(customer, items, MoneyFormatter.penceToPounds(totalBill));
    }

    static class LineItem {
        private Call call;
        private BigDecimal callCost;

        public LineItem(Call call, BigDecimal callCost) {
            this.call = call;
            this.callCost = callCost;
        }

        public String date() {
            return call.date();
        }

        public String callee() {
            return call.callee();
        }

        public String durationMinutes() {
            return "" + call.durationSeconds() / 60 + ":" + String.format("%02d", call.durationSeconds() % 60);
        }

        public BigDecimal cost() {
            return callCost;
        }
    }
}
