package at.geyerritter.dezsys07;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Der Balancer besitzt die Aufgabe alle Anfragen so zu verteilen, dass die Auslastung der Server
 * so klein wie moeglich ist.
 *
 * @author sgeyer
 * @author mritter
 * @version 1.0
 */
public interface Balancer extends Remote {

    /**
     * Gibt die ID des naechsten freien Servers zurueck
     *
     * @return
     */
    public int getNextId() throws RemoteException;
}
