package sonoff;

/**
 * Created by bearingpoint on 28/11/18.
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sonoff.model.Device;

import javax.json.JsonObject;

//import org.codehaus.jackson.map.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sonoff.model.DeviceParams;

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


//    @Test
//    void shouldThrowException() {
//        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
//            throw new UnsupportedOperationException("Not supported");
//        });
//        assertEquals(exception.getMessage(), "Not supported");
//    }


    @DisplayName("Test JSON encoding")
    @Test
    void testSingleSuccessTest() {


        try {

            String P_PARAM1 = "P_PARAM1";
            String P_PARAM2 = "P_PARAM2";
            String P_VALUE1 = "P_VALUE1";
            String P_VALUE2 = "P_VALUE2";

            Device device = new Device();


            DeviceParams params1 = new DeviceParams();
            params1.setKey(P_PARAM1);
            params1.setValue(P_VALUE1);
            DeviceParams params2 = new DeviceParams();
            params2.setKey(P_PARAM2);
            params2.setValue(P_VALUE2);

            device.putDeviceParam(params1);
            device.putDeviceParam(params2);

            String device1 = new ObjectMapper().writeValueAsString(device);
            Device deviceMirror = new ObjectMapper().readValue(device1, Device.class);

            log.info("device1-"+ device1);
            log.info("deviceM-"+ new ObjectMapper().writeValueAsString(deviceMirror));

        }catch(Exception e){
            e.getStackTrace();
            log.error(e.fillInStackTrace());
            fail();
        }

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
