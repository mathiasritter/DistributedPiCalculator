package at.geyerritter.dezsys07;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Stefan on 14.12.2014.
 */
public interface Balancer extends Remote {

    /**
     * Returns the id for the next Calculator
     *
     * @return
     */
    public int getNextId() throws RemoteException;
}
