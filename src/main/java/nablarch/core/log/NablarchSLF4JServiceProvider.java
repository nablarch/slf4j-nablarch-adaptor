package nablarch.core.log;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

/**
 * {@link SLF4JServiceProvider }の実装クラス。
 *
 * @author Kiyohito Itoh
 */
public class NablarchSLF4JServiceProvider implements SLF4JServiceProvider {

	public static String REQUESTED_API_VERSION = "2.0.8";

	private ILoggerFactory loggerFactory;
	private IMarkerFactory markerFactory;
	private MDCAdapter mdcAdapter;

	public ILoggerFactory getLoggerFactory() {
		return loggerFactory;
	}

	@Override
	public IMarkerFactory getMarkerFactory() {
		return markerFactory;
	}

	@Override
	public MDCAdapter getMDCAdapter() {
		return mdcAdapter;
	}

	@Override
	public String getRequestedApiVersion() {
		return REQUESTED_API_VERSION;
	}

	@Override
	public void initialize() {
		loggerFactory = new NablarchLoggerFactory();
		// markerは対応対象外
		markerFactory = new BasicMarkerFactory();
		// MDCは対応対象外
		mdcAdapter = new NOPMDCAdapter();
	}
}
