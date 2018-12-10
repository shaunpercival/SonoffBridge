package sonoff;

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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

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

        String testKey = "device-test1";


        SonoffProperties.setSonofWSproperties( "src/test/resources/sonoffws.properties");
        SonoffProperties.setSonoffDeviceproperties("src/test/resources/devices.properties");

        try {

            Device device = new Device();
            String device1 = new ObjectMapper().writeValueAsString(device);
            SonoffProperties.loadProperties();
            SonoffProperties.persistProperty(testKey, device1);
            log.info("Success");

            SonoffProperties.loadProperties();
            Properties prop = SonoffProperties.getDeviceProperties();
            String testValueRetrieved = prop.getProperty(testKey);

            assertEquals(device1.toString(), testValueRetrieved);

        }catch (Exception e){
            log.error(e);
            fail("Failed" + e.getCause());
        }

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
