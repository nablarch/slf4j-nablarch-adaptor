package nablarch.core.log;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NablarchLoggerFactoryTest {

	private static final int SIZE = 10;
	private static ExecutorService executor;

	@BeforeClass
	public static void init() {
		executor = Executors.newFixedThreadPool(SIZE);
		TestLoggerFactory.setWithSleep(true);
	}

	@AfterClass
	public static void destroy() {
		if (executor != null) {
			executor.shutdown();
		}
		TestLoggerFactory.setWithSleep(false);
	}

	@Test
	public void multiThread() throws Exception {
		final NablarchLoggerFactory factory = new NablarchLoggerFactory();
		final CountDownLatch ready = new CountDownLatch(SIZE);
		final CountDownLatch go = new CountDownLatch(1);
		final List<Future<Logger>> futures = new ArrayList<Future<Logger>>(SIZE);
		for (int i = 0; i < SIZE; i++) {
			final Future<Logger> future = executor.submit(new Callable<Logger>() {
				@Override
				public Logger call() throws Exception {
					ready.countDown();
					go.await();
					return factory.getLogger("multiThread");
				}
			});
			futures.add(future);
		}
		ready.await();
		go.countDown();
		final Logger logger = factory.getLogger("multiThread");
		for (final Future<Logger> future : futures) {
			assertThat(future.get(), is(logger));
		}
	}
}
