package at.geyerritter.dezsys07.balancer;

import at.geyerritter.dezsys07.Balancer;
import at.geyerritter.dezsys07.Calculator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Der Balancer vermittelt Anfragen der Clients an Server weiter.
 * Die Server werden nach der Reihe durchgegangen, es kann jederzeit ein Server sich an-
 * und wieder abmelden.
 *
 * @author sgeyer
 * @author mritter
 * @version 1.0
 */
public class CalculatorBalancer extends UnicastRemoteObject implements Balancer, Calculator {

    private int tmp;
    private int port;
    private int nextId;

    private static final Logger logger = LogManager.getLogger("CalculatorBalancer");


    /**
     * An dem angegebenen Port wird ein neuer Balancer initialisiert.
     * Das heisst, es wird eine neue RMI-Registry erstellt.
     *
     * @param port Balancer-Port
     * @throws RemoteException Fehler beim Erstellen der Registry
     */
    public CalculatorBalancer(int port) throws RemoteException {

        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", System.class.getResource("/java.policy").toString());
            System.setSecurityManager(new SecurityManager());
        }

        this.port = port;

        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind("Balancer", this);
    }

    /**
     * @see at.geyerritter.dezsys07.Balancer#getNextId()
     */
    public int getNextId() throws RemoteException {
        this.nextId++;
        return this.nextId;
    }

    /**
     * @see at.geyerritter.dezsys07.Calculator#pi(int)
     */
    public BigDecimal pi(int anzahl_nachkommastellen) throws RemoteException {

        List<String> elements = new ArrayList<>();

        try {
            // IP ist hardcoded weil wir die Registry im Konstruktor lokal initialisieren.
            String[] entries = Naming.list("rmi://127.0.0.1:" + port);

            for (String s : entries)
                if (s.contains("Calculator"))
                    elements.add(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        logger.info("Elements-Size:" + elements.size());
        logger.info("Temp: " + tmp);
        logger.info("Elements to String" + elements.toString());

        if (elements.size() > 0) {
            if (this.tmp >= elements.size())
                this.tmp = 0;

            Calculator c = null;

            try {
                c = (Calculator) Naming.lookup(elements.get(this.tmp));
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            logger.info("Anfrage eines Clients weitergeleitet an Server " + elements.get(this.tmp));
            this.tmp++;

            return c.pi(anzahl_nachkommastellen);
        } else {
            logger.error("Keine Server zur Beantwortung der Anfrage verfuegbar");
        }

        return null;
    }
}
