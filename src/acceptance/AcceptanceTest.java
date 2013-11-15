package acceptance;

import com.acmetelecom.BillGenerator;
import com.acmetelecom.BillingSystemTestDummy;
import com.acmetelecom.CallEnd;
import com.acmetelecom.CallStart;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.TariffLibrary;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: andreipetric
 * Date: 15/11/2013
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JMock.class)
public class AcceptanceTest {
    Mockery context = new JUnit4Mockery();
    TariffLibrary tariffLibrary = context.mock(TariffLibrary.class);
    CustomerDatabase customerDatabase = context.mock(CustomerDatabase.class);

    //Mocked everything but the billGenerator, which is part of the system
    BillingSystemTestDummy billingSystem =
            new BillingSystemTestDummy(tariffLibrary,
                                       customerDatabase,
                                       new BillGenerator());

    PrintStream outputStream = new PrintStream(new ByteArrayOutputStream());
    PrintStream stdout = System.out;
    @Before
    public void initOutputStream() {
        System.setOut(outputStream);
    }

    @After
    public void resetOutputStream() {
        System.setOut(stdout);
    }

    @Test
    public void acceptanceTest() {
        final CallStart start = new CallStart("07920445573","2",new DateTime(2013,12,12,21,30,10));
        final CallEnd end = new CallEnd("07920445573","2",new DateTime(2013,12,12,21,31,12));
        final List<Customer> customerList = new ArrayList<Customer>();
        final Customer andrei = context.mock(Customer.class);
        //final Customer andrei = new Customer("07920445573","Andrei","Standard");
        customerList.add(andrei);
        final BigDecimal offpeakTariff = new BigDecimal(1);
        final TariffMock tariff = context.mock(TariffMock.class);

        context.checking( new Expectations() {{
            allowing(customerDatabase).getCustomers(); will(returnValue(customerList));
            allowing(tariffLibrary).tarriffFor(andrei); will(returnValue(tariff));
            allowing(tariff).offPeakRate(); will(returnValue(offpeakTariff));





       }
        });

        String st = outputStream.toString();
        System.setOut(stdout);

        System.out.println(st);

    }




}
