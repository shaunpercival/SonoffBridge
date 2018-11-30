package sonoff;

/**
 * Created by bearingpoint on 28/11/18.
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SonoffClientTest {

    static Logger log = LogManager.getRootLogger();


    @AfterEach
    void tearDown() {
        log.info("@AfterEach - executed after each test method.");
    }

    @AfterAll
    static void done() {
        log.info("@AfterAll - executed after all test methods.");
    }


    @Test
    void shouldThrowException() {
        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Not supported");
        });
        assertEquals(exception.getMessage(), "Not supported");
    }


    @DisplayName("Single test successful")
    @Test
    void testSingleSuccessTest() {
        log.info("Success");
    }

    @Test
    @Disabled("Not implemented yet")
    void testShowSomething() {
    }


//    @TestFactory
//    public Stream<DynamicTest> translateDynamicTestsFromStream() {
//        return in.stream()
//                .map(word ->
//                                DynamicTest.dynamicTest("Test translate " + word, () -> {
//                                    int id = in.indexOf(word);
//                                    assertEquals(out.get(id), translate(word));
//                                })
//                );
//    }


}
