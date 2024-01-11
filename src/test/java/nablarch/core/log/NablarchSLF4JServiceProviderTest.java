package nablarch.core.log;

import org.junit.Test;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.NOPMDCAdapter;

import static org.junit.Assert.assertTrue;

public class NablarchSLF4JServiceProviderTest {

    @Test
    public void initialize() {
        NablarchSLF4JServiceProvider provider = new NablarchSLF4JServiceProvider();
        provider.initialize();
        assertTrue(provider.getLoggerFactory() instanceof NablarchLoggerFactory);
        assertTrue(provider.getMarkerFactory() instanceof BasicMarkerFactory);
        assertTrue(provider.getMDCAdapter() instanceof NOPMDCAdapter);
    }
}