package at.geyerritter.dezsys07.client;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;

/**
 * Der Client nutzt den NetworkController fuer alle Anfrage an den Balancer
 * und ein IO-Objekt zur Ein- und Ausgabe.
 * In der Run-Methode liest der Client staendig neue Eingabe ein und schickt neue Anfragen
 * an den Balancer.
 *
 * @author sgeyer
 * @author mritter
 * @version 1.0
 */
public class CalculatorClient implements Runnable {

    private NetworkController nc;

    private static final Logger logger = LogManager.getLogger("CalculatorClient");

    /**
     * Zuweisen der Eingabe/Ausgabe sowie des Networkcontrollers
     *
     * @param nc Networkcontroller
     */
    public CalculatorClient(NetworkController nc) {
        this.nc = nc;
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                // Die Anzahl der Nachkommastellen wird eingelesen und ein Request an den NetworkController.
                int anzahlStellen = Integer.parseInt(this.nc.getIO().readContent());
                nc.request(anzahlStellen);
            } catch (NumberFormatException e) {
                logger.info("Enter a number to request the balancer for pi.");
            } catch (RemoteException e) {
                logger.info("Error while sending a request to the balancer. The balancer might be offline.");
            }

        }
    }

    public NetworkController getNetworkController() {
        return nc;
    }
}
