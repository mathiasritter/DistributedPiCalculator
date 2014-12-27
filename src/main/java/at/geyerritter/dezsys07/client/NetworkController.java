package at.geyerritter.dezsys07.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Der NetworkController baut eine neue Verbindung per RMI auf. Mittels der
 * Methode request wird dann eine neue Anfrage gesendet.
 *
 * Zur Kommunikation bzw. Ausgabe des Ergebnisses der Anfrage wird eine IO-Schnittstelle
 * benoetigt.
 *
 * @author sgeyer
 * @author mritter
 *
 * @version 1.0
 */
public interface NetworkController {

	/**
	 * Senden einer neuen Anfrage mittels RMI
	 *
	 * @param parameter Parameter, der bei der Anfrage uebermittelt wird
	 * @throws RemoteException Fehler waehrend der Kommunikation
	 */
	public abstract void request(int parameter) throws RemoteException;

	/**
	 * Setzen der Eingabe-Ausgabe-Schnittstelle.
	 * Mittels dieser Schnittstelle wird das Ergebnis einer Anfrage ausgegeben.
	 *
	 * @param io Eingabe/Ausgabe
	 */
	public abstract void setIO(InputOutput io);

	/**
	 * Verbindungsaufbau mittels RMI zu einem entfernten Host.
	 *
	 * @param toIp IP des entfernten Hosts
	 * @param toPort Port des entfernten Hosts
	 * @throws RemoteException Fehler beim Verbindungsaufbau
	 * @throws NotBoundException Fehler beim Binden des Stubs
	 * @throws MalformedURLException Fehler beim Verbinden durch falsche IP und/oder falschen Port
	 */
	public void connect(String toIp, int toPort) throws RemoteException, NotBoundException, MalformedURLException;

}
