package at.geyerritter.dezsys07.test;

import at.geyerritter.dezsys07.client.CalculatorClient;
import at.geyerritter.dezsys07.client.CalculatorController;
import at.geyerritter.dezsys07.client.ConsoleIO;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorClientTest {

    private TestAppender appender;

    @Before
    public void setUp() throws Exception {
        appender = new TestAppender();
        Logger.getRootLogger().addAppender(this.appender);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRun() throws Exception {
        CalculatorClient c = new CalculatorClient(new CalculatorController(new ConsoleIO()));
        c.run();
        assertTrue(((String) this.appender.getLog().get(0).getMessage()).contains(""));
    }
}