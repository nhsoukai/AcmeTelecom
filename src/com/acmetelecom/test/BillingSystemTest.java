package com.acmetelecom.test;

import com.acmetelecom.BillGeneratorInterface;
import com.acmetelecom.BillingSystem;
import com.acmetelecom.Call;
import com.acmetelecom.DaytimePeakPeriod;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.customer.TariffLibrary;
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
import jdave.unfinalizer.Unfinalizer;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: andreipetric
 * Date: 18/11/2013
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JMock.class)
public class BillingSystemTest {
    private Mockery context = new JUnit4Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);

    }};;;
    private BillingSystem billingSystem;
    private CustomerDatabase customerDatabase;
    private BillGeneratorInterface billGenerator;
    private DaytimePeakPeriod daytimePeakPeriod;
    private Customer customer;
    private TariffLibrary tariffLibrary;
    private Tariff tariff;


    @Before
    public void prepareTest() {
        Unfinalizer.unfinalize();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        tariff = context.mock(Tariff.class);
        customerDatabase = context.mock(CustomerDatabase.class);
        customer = context.mock(Customer.class);

        billGenerator = context.mock(BillGeneratorInterface.class);
        daytimePeakPeriod = context.mock(DaytimePeakPeriod.class);
        billingSystem = new BillingSystem(tariffLibrary,customerDatabase,
                                          billGenerator,daytimePeakPeriod);
    }

    //Test that BillingSystem
    @Test
    public void testCalculateCost() throws Exception {
        final Call call = context.mock(Call.class);
        context.checking( new Expectations() {{
            allowing(daytimePeakPeriod).offPeakDuration(call);
               will(returnValue(100));
            allowing(tariff.offPeakRate());
               will(returnValue(new BigDecimal("0.03")));
            allowing(daytimePeakPeriod).peakDuration(call);
               will(returnValue(100));
            allowing(tariff.peakRate());
               will(returnValue(new BigDecimal("0.08")));
        }
        });

        BigDecimal totalCost = billingSystem.calculateTotalCost(call,tariff);
        assertEquals(totalCost,new BigDecimal("0.54"));
    }
}
