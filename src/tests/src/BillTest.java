package tests.src;

import com.acmetelecom.CallEnd;
import com.acmetelecom.CallStart;
import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import org.junit.Rule;
import org.junit.Test;
import org.joda.time.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.natpryce.pearlfish.formats.Formats.MARKDOWN;

public class BillTest {
    @Rule
    public ApprovalRule<Object> approval = new ApprovalRule<Object>("test/docs", MARKDOWN);


    @Test
    public void test() throws IOException, InterruptedException {
        CallStart callStart = new CallStart("hey","hi");
        Thread.sleep(2000);
        CallEnd callEnd = new CallEnd("hey","hi");

        Duration duration= new Duration(callStart.time(),callEnd.time());

        Map<String,Object> results = new HashMap<String, Object>();

        results.put("duration", duration.getStandardSeconds());

        approval.check(results);
    }
}
