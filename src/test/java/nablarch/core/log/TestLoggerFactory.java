package nablarch.core.log;

import java.util.concurrent.TimeUnit;

import nablarch.core.log.basic.BasicLoggerFactory;

public class TestLoggerFactory extends BasicLoggerFactory {

	private static boolean withSleep = false;

	@Override
	public Logger get(final String name) {
		if (withSleep == false) {
			return super.get(name);
		}
		try {
			TimeUnit.MILLISECONDS.sleep(50);
			final Logger logger = super.get(name);
			TimeUnit.MILLISECONDS.sleep(50);
			return logger;
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void setWithSleep(final boolean withSleep) {
		TestLoggerFactory.withSleep = withSleep;
	}
}
