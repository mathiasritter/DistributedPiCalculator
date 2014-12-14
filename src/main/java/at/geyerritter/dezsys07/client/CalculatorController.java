package at.geyerritter.dezsys07.client;

import at.geyerritter.dezsys07.Calculator;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class CalculatorController implements NetworkController {

	private InputOutput io;

	private Calculator stub;


	public CalculatorController(InputOutput io) {
		this.io = io;
	}

	/**
	 * @see NetworkController#request(int)
	 * 
	 *  
	 */
	public void request(int parameter) throws RemoteException {
		if (io == null)
			System.out.println("IO is null!!");

		if (stub == null)
			System.out.println("Stub is null!!");

		io.displayContent(stub.pi(parameter).toString());
	}


	/**
	 * @see NetworkController#setIO(InputOutput)
	 * 
	 *  
	 */
	public void setIO(InputOutput io) {
		this.io = io;
	}


	/**
	 * @see NetworkController#connect(String)
	 * 
	 *  
	 */
	public void connect(String url) throws RemoteException, NotBoundException, MalformedURLException {

		if ( System.getSecurityManager() == null ) {
			System.setProperty("java.security.policy", System.class.getResource("/java.policy").toString());
			System.setSecurityManager( new SecurityManager() );
		}

		this.stub = (Calculator) Naming.lookup(url);

	}

}
