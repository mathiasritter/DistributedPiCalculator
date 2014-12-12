package at.geyerritter.dezsys07.server;

import at.geyerritter.dezsys07.Calculator;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServer extends UnicastRemoteObject implements Calculator {


	protected CalculatorServer() throws RemoteException {

	}

	/**
	 * @see at.geyerritter.dezsys07.Calculator#pi(int)
	 */
	public BigDecimal pi(int anzahl_nachkommastellen) throws RemoteException {

	}



}
