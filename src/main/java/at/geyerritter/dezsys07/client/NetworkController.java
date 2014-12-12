package at.geyerritter.dezsys07.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface NetworkController {

	public abstract void request(int parameter) throws RemoteException;

	public abstract void setIO(InputOutput io);

	public void connect(String url) throws RemoteException, NotBoundException, MalformedURLException;

}
