package com.example;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import nablarch.core.log.LoggerManager;

public class NablarchLoggerFactory implements ILoggerFactory {

	private final ConcurrentMap<String, NablarchLogger> loggers = new ConcurrentHashMap<>();

	@Override
	public Logger getLogger(final String name) {
		NablarchLogger logger = loggers.get(name);
		if (logger != null) {
			return logger;
		}
		logger = new NablarchLogger(LoggerManager.get(name));
		final NablarchLogger already = loggers.putIfAbsent(name, logger);
		if (already != null) {
			return already;
		}
		return logger;
	}
}
