package acceptance;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: andreipetric
 * Date: 15/11/2013
 * Time: 18:16
 * To change this template use File | Settings | File Templates.
 */
public interface TariffMock {
    public BigDecimal peakRate();
    public BigDecimal offPeakRate();
}
