package nablarch.core.log;

import nablarch.core.log.basic.LogContext;
import nablarch.core.log.basic.LogWriter;
import nablarch.core.log.basic.ObjectSettings;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MockLogWriter implements LogWriter {

	private static final Queue<LogContext> queue = new LinkedBlockingDeque<LogContext>();

	@Override
	public void initialize(final ObjectSettings settings) {
	}

	@Override
	public void terminate() {
	}

	@Override
	public void write(final LogContext context) {
		queue.add(context);
	}

	public static void init() {
		queue.clear();
	}

	public static void assertLog(final LogContext expected) {
		assertThat(queue.size(), is(1));
		final LogContext lc = queue.remove();
		assertThat(lc.getLoggerName(), is(expected.getLoggerName()));
		assertThat(lc.getLevel(), is(expected.getLevel()));
		assertThat(lc.getMessage(), is(expected.getMessage()));
		assertThat(lc.getError(), is(expected.getError()));
	}

	private final static String LS = System.getProperty("line.separator");

	private static String all() {
		StringBuilder builder = new StringBuilder();
		for (LogContext context : queue) {
			if (builder.length() != 0) {
				builder.append(LS);
			}
			builder.append(stringify(context));
		}
		return builder.toString();
	}

	private static String stringify(final LogContext lc) {
		return String.format("%s %s %s", lc.getLevel(), lc.getMessage(), lc.getError());
	}

	public static Queue<LogContext> getQueue() {
		return queue;
	}
}
