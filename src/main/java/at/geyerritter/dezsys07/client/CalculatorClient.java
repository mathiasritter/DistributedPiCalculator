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

    private InputOutput io;
    private NetworkController nc;

    private static final Logger logger = LogManager.getLogger("CalculatorClient");

    public CalculatorClient(InputOutput io, NetworkController nc) {
        this.io = io;
        this.nc = nc;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Die Anzahl der Nachkommastellen wird eingelesen und ein Request an den NetworkController.
                int anzahlStellen = Integer.parseInt(io.readContent());
                nc.request(anzahlStellen);
            } catch (NumberFormatException e) {
                logger.info("Geben Sie eine Zahl ein, um die Abfrage zu starten");
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }
}
