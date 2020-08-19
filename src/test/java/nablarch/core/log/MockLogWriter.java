package nablarch.core.log;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import nablarch.core.log.basic.LogContext;
import nablarch.core.log.basic.LogWriter;
import nablarch.core.log.basic.ObjectSettings;

public class MockLogWriter implements LogWriter {

	private static final Queue<LogContext> queue = new LinkedBlockingDeque<>();

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
		assertEquals(1, queue.size(), () -> queue.stream().map(MockLogWriter::stringify)
				.collect(Collectors.joining(System.lineSeparator())));

		final LogContext lc = queue.remove();
		final Supplier<String> messageSupplier = () -> stringify(lc);
		assertEquals(Objects.requireNonNull(expected.getLoggerName()), lc.getLoggerName(),
				messageSupplier);
		assertEquals(Objects.requireNonNull(expected.getLevel()), lc.getLevel(), messageSupplier);
		assertEquals(Objects.requireNonNull(expected.getMessage()), lc.getMessage(),
				messageSupplier);
		assertEquals(expected.getError(), lc.getError(), messageSupplier);
	}

	private static String stringify(final LogContext lc) {
		return String.format("%s %s %s", lc.getLevel(), lc.getMessage(), lc.getError());
	}

	public static Queue<LogContext> getQueue() {
		return queue;
	}
}
