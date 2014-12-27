package at.geyerritter.dezsys07.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 *
 * @author sgeyer
 * @author mritter
 *
 * @version 1.0
 */
public interface NetworkController {

	public abstract void request(int parameter) throws RemoteException;

	public abstract void setIO(InputOutput io);

	public void connect(String toIp, int toPort) throws RemoteException, NotBoundException, MalformedURLException;

}
