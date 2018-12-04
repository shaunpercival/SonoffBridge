package sonoff;

/**
 * Created by bearingpoint on 28/11/18.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.sonoff.utils.SonoffProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SonoffClientPropertiesTest {

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


    @DisplayName("Test Properties Load")
    @Test
    void testPropertiesLoadTest() {

        SonoffProperties.loadProperties();
        SonoffProperties.persistProperty("test1","value1");
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
