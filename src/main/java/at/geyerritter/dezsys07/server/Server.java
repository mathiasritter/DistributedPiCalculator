package at.geyerritter.dezsys07.server;

import at.geyerritter.dezsys07.Calculator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Der Server fuehrt die Berechnung aus und gibt PI zurueck.
 * Dabei sollte die Berechnung in einem externen Thread durchgefuehr werden.
 *
 * @author sgeyer
 * @author mritter
 * @version 1.0
 */
public interface Server extends Runnable, Calculator {

    /**
     * Loest die Verbindung zur Registry.
     * Die Registry soll den Server daraufhin nicht mehr ansprechen koennen.
     *
     * @throws RemoteException
     * @throws NotBoundException
     */
    public void unregisterAtRegistry() throws RemoteException, NotBoundException;

    /**
     * Traegt den Server in der Registry ein und kann ab diesem Zeitpunkt vom Balancer
     * angesprochen werden.
     *
     * @throws RemoteException
     */
    public void registerAtRegistry() throws RemoteException;

}
