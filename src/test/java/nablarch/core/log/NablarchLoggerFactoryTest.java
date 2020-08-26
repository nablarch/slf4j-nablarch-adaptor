package nablarch.core.log;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

class NablarchLoggerFactoryTest {

	private static final int SIZE = 10;
	private static ExecutorService executor;

	@BeforeAll
	static void init() {
		executor = Executors.newFixedThreadPool(SIZE);
		TestLoggerFactory.setWithSleep(true);
	}

	@AfterAll
	static void destroy() {
		if (executor != null) {
			executor.shutdown();
		}
		TestLoggerFactory.setWithSleep(false);
	}

	@Test
	void multiThread() throws Exception {
		final NablarchLoggerFactory factory = new NablarchLoggerFactory();
		final CountDownLatch ready = new CountDownLatch(SIZE);
		final CountDownLatch go = new CountDownLatch(1);
		final List<Future<Logger>> futures = new ArrayList<>(SIZE);
		for (int i = 0; i < SIZE; i++) {
			final Future<Logger> future = executor.submit(() -> {
				ready.countDown();
				go.await();
				return factory.getLogger("multiThread");
			});
			futures.add(future);
		}
		ready.await();
		go.countDown();
		final Logger logger = factory.getLogger("multiThread");
		for (final Future<Logger> future : futures) {
			assertSame(logger, future.get());
		}
	}
}
