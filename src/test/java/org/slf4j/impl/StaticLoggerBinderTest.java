package org.slf4j.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaticLoggerBinderTest {

    @Test
    void loggerFactoryClassStr() {
        assertEquals("nablarch.core.log.NablarchLoggerFactory",
                StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr());
    }
}