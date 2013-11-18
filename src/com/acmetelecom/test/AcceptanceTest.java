package com.acmetelecom.test;

import com.acmetelecom.BillGenerator;
import com.acmetelecom.BillingSystemTestDummy;
import com.acmetelecom.CallEnd;
import com.acmetelecom.CallStart;
import com.acmetelecom.customer.*;
import com.acmetelecom.DaytimePeakPeriod;
import junit.framework.AssertionFailedError;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import jdave.unfinalizer.Unfinalizer;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: andreipetric
 * Date: 15/11/2013
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JMock.class)
public class AcceptanceTest {

    private Mockery context  = new JUnit4Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};;
    //Mockery context = new JUnit4Mockery();
    TariffLibrary tariffLibrary;
    CustomerDatabase customerDatabase;

    //Mocked everything but the billGenerator, which is part of the system
    BillingSystemTestDummy billingSystem;

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printer = new PrintStream(outputStream);
    PrintStream stdout = System.out;
    @Before
    public void initOutputStream() {
        System.setOut(printer);

        Unfinalizer.unfinalize();

        tariffLibrary = context.mock(TariffLibrary.class);
        customerDatabase = context.mock(CustomerDatabase.class);
        billingSystem  = new BillingSystemTestDummy(tariffLibrary,
                customerDatabase,
                new BillGenerator(), new DaytimePeakPeriod());
    }

    @After
    public void resetOutputStream() {
        System.setOut(stdout);
    }

    @Test
    public void acceptanceTest() throws IOException {
        //Andrei Calls
        //Call before the peak period
        final DateTime startTime1 = new DateTime(2013,12,12,5,0);
        final CallStart startOffPeakA = new CallStart("0771","0772",startTime1);
        final DateTime endTime1 = new DateTime(2013, 12, 12, 5,10);
        final CallEnd endOffPeakA = new CallEnd("0771","0772",  endTime1);
        //Call that starts before and ends in peak time
        final DateTime startTime2 = new DateTime(2013, 12, 12, 6, 50);
        final CallStart startBothA= new CallStart("0771","0772",  startTime2);
        final DateTime endTime2 = new DateTime(2013, 12, 12, 7, 10);
        final CallEnd endBothA = new CallEnd("0771","0772", endTime2);
        //Call that is all in peak time
        final DateTime startTime3 = new DateTime(2013, 12, 12, 8, 0);
        final CallStart startPeakA = new CallStart("0771","0772", startTime3);
        final DateTime endTime3 = new DateTime(2013, 12, 12, 8, 10);
        final CallEnd endPeakA = new CallEnd("0771","0772",  endTime3);

        //Bianca Calls
        //Call that starts in the peak period and ends after
        final DateTime startTime4 = new DateTime(2013, 12, 12, 18, 50);
        final CallStart startBothB = new CallStart("0772","0771", startTime4);
        final DateTime endTime4 = new DateTime(2013, 12, 12, 19, 10);
        final CallEnd endBothB = new CallEnd("0772","0771", endTime4);
        //Call that is all after peak time
        final DateTime startTime5 = new DateTime(2013, 12, 12, 20, 0);
        final CallStart startOffPeakB = new CallStart("0772","0771",  startTime5);
        final DateTime endTime5 = new DateTime(2013, 12, 12, 20, 10);
        final CallEnd endOffPeakB = new CallEnd("0772","0771", endTime5);
        //Call that starts before peak period and ends after
        final DateTime startTime6 = new DateTime(2013,12,12,6,00);
        final DateTime endTime6 = new DateTime(2013,12,12,20,00);
        final CallStart startLongB = new CallStart("0772","0771",startTime6);
        final CallEnd endLongB = new CallEnd("0772","0771",endTime6);

        final List<Customer> customerList = new ArrayList<Customer>();
        final Customer andrei = new Customer("Andrei", "0771", "Standard");
        final Customer bianca = new Customer("Bianca","0772", "Expensive");
        customerList.add(andrei);
        customerList.add(bianca);

        //Tariffs
        final BigDecimal offPeakTariffAndrei = new BigDecimal("0.03");
        final BigDecimal peakTariffAndrei = new BigDecimal("0.06");
        final BigDecimal offPeakTariffBianca = new BigDecimal("0.04");
        final BigDecimal peakTariffBianca = new BigDecimal("0.08");

        //final TariffMock tariffAndrei = context.mock(TariffMock.class,"AndreiTariffMock");
        //final TariffMock tariffBianca = context.mock(TariffMock.class,"BiancaTariffMock");
        context.setImposteriser(ClassImposteriser.INSTANCE);

        final Tariff tariffAndrei = context.mock(Tariff.class,"tariffAndrei");
        final Tariff tariffBianca = context.mock(Tariff.class,"tariffBianca");

        context.checking( new Expectations() {{
            allowing(customerDatabase).getCustomers(); will(returnValue(customerList));
            allowing(tariffLibrary).tarriffFor(andrei); will(returnValue(tariffAndrei));
            allowing(tariffLibrary).tarriffFor(bianca); will(returnValue(tariffBianca));
            allowing(tariffAndrei).peakRate(); will(returnValue(peakTariffAndrei));
            allowing(tariffAndrei).offPeakRate(); will(returnValue(offPeakTariffAndrei));
            allowing(tariffBianca).peakRate(); will(returnValue(peakTariffBianca));
            allowing(tariffBianca).offPeakRate(); will(returnValue(offPeakTariffBianca));


        }
        });
        //AndreiCalls
        billingSystem.callInitiatedAt("0771","0772",startTime1);
        billingSystem.callCompletedAt("0771","0772",endTime1);
        billingSystem.callInitiatedAt("0771","0772",startTime2);
        billingSystem.callCompletedAt("0771","0772",endTime2);
        billingSystem.callInitiatedAt("0771","0772",startTime3);
        billingSystem.callCompletedAt("0771","0772",endTime3);
        //BiancaCalls
        billingSystem.callInitiatedAt("0772","0771",startTime4);
        billingSystem.callCompletedAt("0772","0771",endTime4);
        billingSystem.callInitiatedAt("0772","0771",startTime5);
        billingSystem.callCompletedAt("0772","0771",endTime5);
        billingSystem.callInitiatedAt("0772","0771",startTime6);
        billingSystem.callCompletedAt("0772","0771",endTime6);

        billingSystem.createCustomerBills();

        System.out.flush();
        String outputStreamString = outputStream.toString();

        String[] outputLinesArray = outputStreamString.split("\n");

        ArrayList<String> outputLinesArrayList = new ArrayList<String>(Arrays.asList(outputLinesArray));
        Iterator<String> outputLineIterator = outputLinesArrayList.iterator();

        BufferedReader br = new BufferedReader(new FileReader("expected"));

        String expected, outputLine, message;
        int lineNumber = 1;

        while ((expected = br.readLine()) != null) {
            outputLine = outputLineIterator.next();
             if (!outputLine.equals(expected)) {
                throw new AssertionFailedError(String.format("On line "+ lineNumber +
                        "%nExpected: "+ expected + "%nActual" + outputLine));            }
            lineNumber++;
            // process the line.
        }
        br.close();


        System.setOut(stdout);

    }


    /**
     * Created with IntelliJ IDEA.
     * User: andreipetric
     * Date: 15/11/2013
     * Time: 18:16
     * To change this template use File | Settings | File Templates.
     */
    public static interface TariffMock {
        public BigDecimal peakRate();
        public BigDecimal offPeakRate();
    }
}

