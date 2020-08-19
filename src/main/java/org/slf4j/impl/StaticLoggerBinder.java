package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

import nablarch.core.log.NablarchLoggerFactory;

/**
 * {@link LoggerFactoryBinder}の実装クラス。
 *
 * @author Kiyohito Itoh
 */
public class StaticLoggerBinder implements LoggerFactoryBinder {

	private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();
	private final NablarchLoggerFactory loggerFactory = new NablarchLoggerFactory();

	/** プライベートコンストラクタ */
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
