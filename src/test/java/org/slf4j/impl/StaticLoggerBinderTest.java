package org.slf4j.impl;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StaticLoggerBinderTest {

    @Test
    public void loggerFactoryClassStr() {
        assertThat(StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr(),
                is("nablarch.core.log.NablarchLoggerFactory"));
    }
}