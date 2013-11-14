package tests.src;

import com.acmetelecom.CallEnd;
import com.acmetelecom.CallStart;
import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import org.junit.Rule;
import org.junit.Test;

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
        long start = callStart.time()/1000;
        long end = callEnd.time()/1000;

        Map<String,Object> results = new HashMap<String, Object>();

        results.put("duration", end-start);

        approval.check(results);
    }
}
