package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

import com.example.NablarchLoggerFactory;

public class StaticLoggerBinder implements LoggerFactoryBinder {

	private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();
	private final NablarchLoggerFactory loggerFactory = new NablarchLoggerFactory();

	private StaticLoggerBinder() {
	}

	public static StaticLoggerBinder getSingleton() {
		return SINGLETON;
	}

	@Override
	public ILoggerFactory getLoggerFactory() {
		return loggerFactory;
	}

	@Override
	public String getLoggerFactoryClassStr() {
		return NablarchLoggerFactory.class.getName();
	}
}
