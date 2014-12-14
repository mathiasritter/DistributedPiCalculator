package at.geyerritter.dezsys07.server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Mathias on 14.12.14.
 */
public interface Server extends Runnable {

    public void unregisterAtRegistry() throws RemoteException, NotBoundException;

    public void registerAtRegistry() throws RemoteException;

}
