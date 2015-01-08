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

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorClientTest {

    private TestAppender appender;

    private CalculatorBalancer cb;

    @Before
    public void setUp() throws Exception {
        this.appender = new TestAppender();
        Logger.getRootLogger().addAppender(this.appender);
        this.cb = new CalculatorBalancer(1099);
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
    /**
     * Ein Server  wird gestartet und es wird eine Anfrage an den Networkcontroller geschickt.
     * Durch direktes senden an den Networkcontroller wird die Konsoleneingabe übersprungen
     */
    public void testClient2() throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {
        CalculatorServer cs = new CalculatorServer("127.0.0.1", 1099, 4567);
        Thread t = new Thread(cs);
        Runtime.getRuntime().addShutdownHook(t);
        CalculatorClient c = new CalculatorClient(new CalculatorController(new ConsoleIO()));
        new Thread(c).start();
        c.getNetworkController().connect("127.0.0.1", 1099);
        c.getNetworkController().request(5);
        assertEquals((String) this.appender.getLog().get(this.appender.getLog().size() - 1).getMessage(), "3.14158");
    }

    @Test
    /**
     * Testen was passiert wenn kein Server vorhanden oder frei ist
     */
    public void testClient3() throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {
        CalculatorClient c = new CalculatorClient(new CalculatorController(new ConsoleIO()));
        new Thread(c).start();
        c.getNetworkController().connect("127.0.0.1", 1099);
        c.getNetworkController().request(5);
        assertEquals((String) this.appender.getLog().get(this.appender.getLog().size() - 1).getMessage(), "Error while sending the request: No server is available.");
    }

    @Test
    /**
     * Testen ob der Wert trotz zurueckgesetztem Security Manager ausgegeben wird
     */
    public void testClient4() throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {
        CalculatorServer cs = new CalculatorServer("127.0.0.1", 1099, 4567);
        Thread t = new Thread(cs);
        Runtime.getRuntime().addShutdownHook(t);
        System.setSecurityManager(null);
        CalculatorClient c = new CalculatorClient(new CalculatorController(new ConsoleIO()));
        new Thread(c).start();
        c.getNetworkController().connect("127.0.0.1", 1099);
        c.getNetworkController().request(5);
        assertEquals((String) this.appender.getLog().get(this.appender.getLog().size() - 1).getMessage(), "3.14158");
    }

}