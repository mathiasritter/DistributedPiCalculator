package at.geyerritter.dezsys07.client;

import at.geyerritter.dezsys07.Calculator;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Ist Zustaendig fuer das Senden und Verarbeiten von Anfragen an den Balancer.
 *
 * @author sgeyer
 * @author mritter
 *
 * @version 1.0
 */
public class CalculatorController implements NetworkController {

	private InputOutput io;

	private Calculator stub;


	/**
	 * Es wird die Eingabe/Ausgabe im Konstruktor gesetzt.
	 *
	 * @param io Eingabe/Ausgabe
	 */
	public CalculatorController(InputOutput io) {
		this.io = io;
	}

	/**
	 * @see NetworkController#request(int)
	 */
	public void request(int parameter) throws RemoteException {

		io.displayContent(stub.pi(parameter).toString());

	}


	/**
	 * @see NetworkController#setIO(InputOutput)
	 */
	public void setIO(InputOutput io) {
		this.io = io;
	}


	/**
	 * @see NetworkController#connect(String, int)
	 */
	public void connect(String toIp, int toPort) throws RemoteException, NotBoundException, MalformedURLException {

		if ( System.getSecurityManager() == null ) {
			System.setProperty("java.security.policy", System.class.getResource("/java.policy").toString());
			System.setSecurityManager( new SecurityManager() );
		}

		String url = "rmi://" + toIp + ":" + toPort + "/Balancer";

		this.stub = (Calculator) Naming.lookup(url);

	}

}
