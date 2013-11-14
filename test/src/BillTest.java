import com.acmetelecom.CallEnd;
import com.acmetelecom.CallStart;
import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.natpryce.pearlfish.formats.Formats.MARKDOWN;
import static java.util.Arrays.asList;

public class BillTest {
    @Rule
    public ApprovalRule<Object> approval = new ApprovalRule<Object>("test/docs", MARKDOWN);


    @Test
    public void test() throws IOException {
        CallStart callStart = new CallStart("hey","hi");
        CallEnd callEnd = new CallEnd("hey","hi");
        long start = callStart.time();
        long end = callEnd.time();

        Map<String,Object> results = new HashMap<String, Object>();

        results.put("start", start);
        results.put("end", end);
        approval.check(results);
    }
}

