package at.geyerritter.dezsys07.client;

import java.rmi.RemoteException;

/**
 * Der Client nutzt den NetworkController fuer alle Anfrage an den Balancer
 * und ein IO-Objekt zur Ein- und Ausgabe.
 * In der Run-Methode liest der Client staendig neue Eingabe ein und schickt neue Anfragen
 * an den Balancer.
 *
 * @author sgeyer
 * @author mritter
 *
 * @version 1.0
 */
public class CalculatorClient implements Runnable {

    private InputOutput io;
    private NetworkController nc;

    /**
     * Zuweisen der Eingabe/Ausgabe sowie des Networkcontrollers
     *
     * @param io Eingabe/Ausgabe
     * @param nc Networkcontroller
     */
    public CalculatorClient(InputOutput io, NetworkController nc) {
        this.io = io;
        this.nc = nc;
    }

    @Override
    public void run() {
        while(true) {
            try {
                int parameter = Integer.parseInt(io.readContent());
                nc.request(parameter);
            } catch (NumberFormatException e) {
                System.out.println("Geben Sie eine Zahl ein, um die Abfrage zu starten");
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }
}
