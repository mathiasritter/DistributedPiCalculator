package at.geyerritter.dezsys07.balancer;

import at.geyerritter.dezsys07.server.Calculator;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Der Balancer besitzt die Aufgabe alle Anfragen so zu verteilen, dass die Auslastung der Server
 * so klein wie moeglich ist. Bei ihm koennen sich Server an und wieder abmelden.
 *
 * @author sgeyer
 * @author mritter
 * @version 1.0
 */
public interface Balancer extends Remote {

    /**
     * Neuen Server beim Balancer anmelden.
     * Ab sofort steht dieser fuer die Beantwortung von Client-Requests zur Verfuegung.
     *
     * @param server anzumeldender Server
     * @throws RemoteException Fehler waehrend der Netzwerkkommunikation
     */
    public void register(Calculator server) throws RemoteException;

    /**
     * Server beim Balancer abmelden.
     * Ab sofort steht dieser nicht mehr fuer die Beantwortung von Client-Requests zur Verfuegung.
     *
     * @param server abzumeldender Server
     * @throws RemoteException Fehler waehrend der Netzwerkkommunikation
     */
    public void unregister(Calculator server) throws RemoteException;
}
