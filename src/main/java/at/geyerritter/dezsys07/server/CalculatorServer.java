package at.geyerritter.dezsys07.server;

import at.geyerritter.dezsys07.balancer.Balancer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * Der Server verarbeitet alle Anfragen des Balancers.
 * Er meldet sich beim Balancer an und wieder ab.
 *
 * @author sgeyer
 * @author mritter
 *
 * @version 1.0
 */
public class CalculatorServer extends UnicastRemoteObject implements Calculator, Runnable {

    private BigDecimal two;
    private BigDecimal four;
    private Balancer balancer;

    private static final Logger logger = LogManager.getLogger("CalculatorServer");

    public CalculatorServer(String balancerip, int registryport, int serverport) throws RemoteException, MalformedURLException, NotBoundException {

        if ( System.getSecurityManager() == null ) {
            System.setProperty("java.security.policy", System.class.getResource("/java.policy").toString());
            System.setSecurityManager( new SecurityManager() );
        }

        this.balancer = (Balancer) Naming.lookup("rmi://" + balancerip + ":" + registryport + "/Balancer");

        // Beim Balancer anmelden
        this.balancer.register(this);
        logger.info("Server registered at the balancer");

        // Beim Beenden des Servers beim Balancer abmelden
        Thread t = new Thread(this);
        Runtime.getRuntime().addShutdownHook(t);

        this.two = new BigDecimal(2);
        this.four = new BigDecimal(4);
    }

    /**
     * @see Calculator#pi(int)
     */
    @Override
    public BigDecimal pi(int anzahl_nachkommastellen) throws RemoteException {

        if (anzahl_nachkommastellen > 997) {
            anzahl_nachkommastellen = 997;
        }


        BigDecimal a = ONE;
        BigDecimal b = ONE.divide(sqrt(two, anzahl_nachkommastellen), anzahl_nachkommastellen, ROUND_HALF_UP);
        BigDecimal t = new BigDecimal(0.25);
        BigDecimal x = ONE;
        BigDecimal y;

        while (!a.equals(b)) {
            y = a;
            a = a.add(b).divide(this.two, anzahl_nachkommastellen, ROUND_HALF_UP);
            b = sqrt(b.multiply(y), anzahl_nachkommastellen);
            t = t.subtract(x.multiply(y.subtract(a).multiply(y.subtract(a))));
            x = x.multiply(this.two);
        }

        return a.add(b).multiply(a.add(b)).divide(t.multiply(this.four), anzahl_nachkommastellen, ROUND_HALF_UP);
    }

    /**
     * Ziehen der Quadratwzurzel mit der angegebenen Anzahl an Nachkommastellen.
     *
     * @param a Zahl, von der die Quadratwurzel gezogen wird
     * @param anzahl_nachkommastellen Anzahl der Nachkommastellen
     * @return Ergebnis der Wurzel
     */
    private BigDecimal sqrt(BigDecimal a, final int anzahl_nachkommastellen) {
        BigDecimal x0 = new BigDecimal("0");
        BigDecimal x1 = new BigDecimal(Math.sqrt(a.doubleValue()));

        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = a.divide(x0, anzahl_nachkommastellen, ROUND_HALF_UP);
            x1 = x1.add(x0);
            x1 = x1.divide(this.two, anzahl_nachkommastellen, ROUND_HALF_UP);
        }

        return x1;
    }

    @Override
    public void run() {
        try {
            this.balancer.unregister(this);
            logger.info("Server unregistered at the balancer");
        } catch (RemoteException e) {
            logger.info("Server couldn't be unregistered at the balancer because balancer is offline.");
        }
    }
}
