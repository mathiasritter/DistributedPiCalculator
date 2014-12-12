package at.geyerritter.dezsys07.server;

import at.geyerritter.dezsys07.Calculator;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServer extends UnicastRemoteObject implements Calculator {

	private Calculator stub;

	public CalculatorServer(int port) throws RemoteException {
		Registry registry = LocateRegistry.createRegistry(port);

		//stub = (Calculator) UnicastRemoteObject.exportObject(this, port);
		//registry.rebind("Calculator", stub);
	}

	/**
	 * @see at.geyerritter.dezsys07.Calculator#pi(int)
	 */
	public BigDecimal pi(int anzahl_nachkommastellen) throws RemoteException {
		return new BigDecimal(2);
	}



}
