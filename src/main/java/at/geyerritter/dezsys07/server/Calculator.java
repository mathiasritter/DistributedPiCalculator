package at.geyerritter.dezsys07.server;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Dieses Interface wird als Remoteschnittstelle benutzt.
 *
 * @author sgeyer
 * @author mritter
 * @version 1.0
 */
public interface Calculator extends Remote {

	/**
	 * Diese Methode gibt einen Annaeherungswert fuer PI aufgrund der angegebenen Nachkommastellen zurueck
	 *
	 * @param anzahl_nachkommastellen Die Genauigkeit von Pi
	 * @return Pi
	 * @throws RemoteException Fehler waehrend der Netzwerkkommunikation
	 */
	public abstract BigDecimal pi(int anzahl_nachkommastellen) throws RemoteException;

}
