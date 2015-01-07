package at.geyerritter.dezsys07.balancer;

import at.geyerritter.dezsys07.server.Calculator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
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
    private List<Calculator> servers;

    private static final Logger logger = LogManager.getLogger("CalculatorBalancer");


    /**
     * An dem angegebenen Port wird ein neuer Balancer initialisiert.
     * Das heisst, es wird eine neue RMI-Registry erstellt.
     *
     * @param port Balancer-Port
     * @throws RemoteException Fehler beim Erstellen der Registry
     */
    public CalculatorBalancer(int port) throws RemoteException {

        // Liste fuer verfuegbare Calculator-Server
        this.servers = new ArrayList<>();

        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", System.class.getResource("/java.policy").toString());
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind("Balancer", this);
    }


    /**
     * @see at.geyerritter.dezsys07.server.Calculator#pi(int)
     */
    public BigDecimal pi(int anzahl_nachkommastellen) throws RemoteException {

        if (servers.size() > 0) {
            if (this.tmp >= servers.size())
                this.tmp = 0;


            Calculator c = servers.get(this.tmp);
            logger.info("Request from client directed to server " + servers.get(this.tmp));
            this.tmp++;

            return c.pi(anzahl_nachkommastellen);
        } else {
            logger.error("No Server for processing request is currently available.");
        }

        return null;
    }

    /**
     * @see at.geyerritter.dezsys07.balancer.Balancer#register(at.geyerritter.dezsys07.server.Calculator)
     */
    public void register(Calculator server) throws RemoteException {
        servers.add(server);
        logger.info("New server registered: " + server);
    }

    /**
     * @see at.geyerritter.dezsys07.balancer.Balancer#unregister(at.geyerritter.dezsys07.server.Calculator)
     */
    public void unregister(Calculator server) throws RemoteException {
        servers.remove(server);
        logger.info("Server unregistered: " + server);
    }
}
