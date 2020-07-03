package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nablarch.core.log.LoggerManager;
import nablarch.core.log.basic.LogContext;
import nablarch.core.log.basic.LogLevel;

class NablarchLoggerTest {

	@ParameterizedTest
	@CsvSource(value = {
			"error | true",
			"warn  | true",
			"info  | true",
			"debug | true",
			"trace | true",
	}, delimiter = '|')
	void isErrorEnabled(final String name, final boolean expected) {
		final Logger logger = LoggerFactory.getLogger(name);
		assertEquals(expected, logger.isErrorEnabled());
	}

	@ParameterizedTest
	@CsvSource(value = {
			"error | false",
			"warn  | true",
			"info  | true",
			"debug | true",
			"trace | true",
	}, delimiter = '|')
	void isWarnEnabled(final String name, final boolean expected) {
		final Logger logger = LoggerFactory.getLogger(name);
		assertEquals(expected, logger.isWarnEnabled());
	}

	@ParameterizedTest
	@CsvSource(value = {
			"error | false",
			"warn  | false",
			"info  | true",
			"debug | true",
			"trace | true",
	}, delimiter = '|')
	void isInfoEnabled(final String name, final boolean expected) {
		final Logger logger = LoggerFactory.getLogger(name);
		assertEquals(expected, logger.isInfoEnabled());
	}

	@ParameterizedTest
	@CsvSource(value = {
			"error | false",
			"warn  | false",
			"info  | false",
			"debug | true",
			"trace | true",
	}, delimiter = '|')
	void isDebugEnabled(final String name, final boolean expected) {
		final Logger logger = LoggerFactory.getLogger(name);
		assertEquals(expected, logger.isDebugEnabled());
	}

	@ParameterizedTest
	@CsvSource(value = {
			"error | false",
			"warn  | false",
			"info  | false",
			"debug | false",
			"trace | true",
	}, delimiter = '|')
	void isTraceEnabled(final String name, final boolean expected) {
		final Logger logger = LoggerFactory.getLogger(name);
		assertEquals(expected, logger.isTraceEnabled());
	}

	abstract class Base {

		private final LogLevel logLevel;

		protected Logger logger;

		public Base(final LogLevel logLevel) {
			this.logLevel = Objects.requireNonNull(logLevel);
		}

		@BeforeEach
		void init() {
			LoggerManager.get(""); //ロガー初期化のログを捨てるため、ここで一度Nablachのロガーを取得しておく
			MockLogWriter.init();
			logger = LoggerFactory.getLogger("test");
		}

		protected LogContext expected(final String message, final Throwable error) {
			return new LogContext("TEST", logLevel, message, error);
		}
	}

	@Nested
	class ErrorTest extends Base {

		public ErrorTest() {
			super(LogLevel.ERROR);
		}

		@Test
		void error() {
			logger.error("test");
			MockLogWriter.assertLog(expected("test", null));
		}

		@Test
		void error_with_1_argument() {
			logger.error("test {}", 1);
			MockLogWriter.assertLog(expected("test 1", null));
		}

		@Test
		void error_with_2_arguments() {
			logger.error("test {} {}", 1, 2);
			MockLogWriter.assertLog(expected("test 1 2", null));
		}

		@Test
		void error_with_1_argument_and_error() {
			logger.error("test {}", 1, new MockException());
			MockLogWriter.assertLog(expected("test 1", new MockException()));
		}

		@Test
		void error_with_3_arguments() {
			logger.error("test {} {} {}", 1, 2, 3);
			MockLogWriter.assertLog(expected("test 1 2 3", null));
		}

		@Test
		void error_with_2_arguments_and_error() {
			logger.error("test {} {}", 1, 2, new MockException());
			MockLogWriter.assertLog(expected("test 1 2", new MockException()));
		}

		@Test
		void error_with_error() {
			logger.error("test", new MockException());
			MockLogWriter.assertLog(expected("test", new MockException()));
		}
	}

	@Nested
	class WarnTest extends Base {

		public WarnTest() {
			super(LogLevel.WARN);
		}

		@Test
		void warn() {
			logger.warn("test");
			MockLogWriter.assertLog(expected("test", null));
		}

		@Test
		void warn_with_1_argument() {
			logger.warn("test {}", 1);
			MockLogWriter.assertLog(expected("test 1", null));
		}

		@Test
		void warn_with_2_arguments() {
			logger.warn("test {} {}", 1, 2);
			MockLogWriter.assertLog(expected("test 1 2", null));
		}

		@Test
		void warn_with_1_argument_and_warn() {
			logger.warn("test {}", 1, new MockException());
			MockLogWriter.assertLog(expected("test 1", new MockException()));
		}

		@Test
		void warn_with_3_arguments() {
			logger.warn("test {} {} {}", 1, 2, 3);
			MockLogWriter.assertLog(expected("test 1 2 3", null));
		}

		@Test
		void warn_with_2_arguments_and_warn() {
			logger.warn("test {} {}", 1, 2, new MockException());
			MockLogWriter.assertLog(expected("test 1 2", new MockException()));
		}

		@Test
		void warn_with_warn() {
			logger.warn("test", new MockException());
			MockLogWriter.assertLog(expected("test", new MockException()));
		}
	}

	@Nested
	class InfoTest extends Base {

		public InfoTest() {
			super(LogLevel.INFO);
		}

		@Test
		void info() {
			logger.info("test");
			MockLogWriter.assertLog(expected("test", null));
		}

		@Test
		void info_with_1_argument() {
			logger.info("test {}", 1);
			MockLogWriter.assertLog(expected("test 1", null));
		}

		@Test
		void info_with_2_arguments() {
			logger.info("test {} {}", 1, 2);
			MockLogWriter.assertLog(expected("test 1 2", null));
		}

		@Test
		void info_with_1_argument_and_error() {
			logger.info("test {}", 1, new MockException());
			MockLogWriter.assertLog(expected("test 1", new MockException()));
		}

		@Test
		void info_with_3_arguments() {
			logger.info("test {} {} {}", 1, 2, 3);
			MockLogWriter.assertLog(expected("test 1 2 3", null));
		}

		@Test
		void info_with_2_arguments_and_error() {
			logger.info("test {} {}", 1, 2, new MockException());
			MockLogWriter.assertLog(expected("test 1 2", new MockException()));
		}

		@Test
		void info_with_info() {
			logger.info("test", new MockException());
			MockLogWriter.assertLog(expected("test", new MockException()));
		}
	}

	@Nested
	class DebugTest extends Base {

		public DebugTest() {
			super(LogLevel.DEBUG);
		}

		@Test
		void debug() {
			logger.debug("test");
			MockLogWriter.assertLog(expected("test", null));
		}

		@Test
		void debug_with_1_argument() {
			logger.debug("test {}", 1);
			MockLogWriter.assertLog(expected("test 1", null));
		}

		@Test
		void debug_with_2_arguments() {
			logger.debug("test {} {}", 1, 2);
			MockLogWriter.assertLog(expected("test 1 2", null));
		}

		@Test
		void debug_with_1_argument_and_error() {
			logger.debug("test {}", 1, new MockException());
			MockLogWriter.assertLog(expected("test 1", new MockException()));
		}

		@Test
		void debug_with_3_arguments() {
			logger.debug("test {} {} {}", 1, 2, 3);
			MockLogWriter.assertLog(expected("test 1 2 3", null));
		}

		@Test
		void debug_with_2_arguments_and_error() {
			logger.debug("test {} {}", 1, 2, new MockException());
			MockLogWriter.assertLog(expected("test 1 2", new MockException()));
		}

		@Test
		void debug_with_debug() {
			logger.debug("test", new MockException());
			MockLogWriter.assertLog(expected("test", new MockException()));
		}
	}

	@Nested
	class TraceTest extends Base {

		public TraceTest() {
			super(LogLevel.TRACE);
		}

		@Test
		void trace() {
			logger.trace("test");
			MockLogWriter.assertLog(expected("test", null));
		}

		@Test
		void trace_with_1_argument() {
			logger.trace("test {}", 1);
			MockLogWriter.assertLog(expected("test 1", null));
		}

		@Test
		void trace_with_2_arguments() {
			logger.trace("test {} {}", 1, 2);
			MockLogWriter.assertLog(expected("test 1 2", null));
		}

		@Test
		void trace_with_1_argument_and_error() {
			logger.trace("test {}", 1, new MockException());
			MockLogWriter.assertLog(expected("test 1", new MockException()));
		}

		@Test
		void trace_with_3_arguments() {
			logger.trace("test {} {} {}", 1, 2, 3);
			MockLogWriter.assertLog(expected("test 1 2 3", null));
		}

		@Test
		void trace_with_2_arguments_and_error() {
			logger.trace("test {} {}", 1, 2, new MockException());
			MockLogWriter.assertLog(expected("test 1 2", new MockException()));
		}

		@Test
		void trace_with_trace() {
			logger.trace("test", new MockException());
			MockLogWriter.assertLog(expected("test", new MockException()));
		}
	}
}
