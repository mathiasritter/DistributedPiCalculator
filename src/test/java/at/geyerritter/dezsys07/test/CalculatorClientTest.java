package at.geyerritter.dezsys07.test;

import at.geyerritter.dezsys07.balancer.CalculatorBalancer;
import at.geyerritter.dezsys07.client.CalculatorClient;
import at.geyerritter.dezsys07.client.CalculatorController;
import at.geyerritter.dezsys07.client.ConsoleIO;
import at.geyerritter.dezsys07.server.CalculatorServer;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class CalculatorClientTest {

    private TestAppender appender;

    @Before
    public void setUp() throws Exception {
        this.appender = new TestAppender();
        Logger.getRootLogger().addAppender(this.appender);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = NullPointerException.class)
    public void testClient1() throws RemoteException {
        CalculatorClient c = new CalculatorClient(new CalculatorController(new ConsoleIO()));
        c.getNetworkController().request(5);
    }

    @Test
    public void testClient2() throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {
        CalculatorBalancer cb = new CalculatorBalancer(1099);
        CalculatorServer cs = new CalculatorServer("127.0.0.1", 1099, 4567);
        Thread t = new Thread(cs);
        Runtime.getRuntime().addShutdownHook(t);
        CalculatorClient c = new CalculatorClient(new CalculatorController(new ConsoleIO()));
        new Thread(c).start();
        c.getNetworkController().connect("127.0.0.1", 1099);
        c.getNetworkController().request(5);
        assertEquals((String) this.appender.getLog().get(this.appender.getLog().size() - 1).getMessage(), "3.14158");
    }
}