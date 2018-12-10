package org.sonoff.websocket;

/**
 * Created by bearingpoint on 28/11/18.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.sonoff.model.Device;
import org.sonoff.utils.SonoffProperties;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DeviceSessionHandlerTest {

    static Logger log = LogManager.getRootLogger();


    @AfterEach
    void tearDown() {
        log.info("@AfterEach - executed after each test method.");
    }

    @AfterAll
    static void done() {
        log.info("@AfterAll - executed after all test methods.");
    }


//    @Test
//    void shouldThrowException() {
//        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
//            throw new UnsupportedOperationException("Not supported");
//        });
//        assertEquals(exception.getMessage(), "Not supported");
//    }


    @DisplayName("handleSonoffRegisterRequest")
    @Test
    void handleSonoffRegisterRequest() {


    }

    @Test
    @Disabled("Not implemented yet")
    void testShowSomething() {
    }




}
