package at.geyerritter.dezsys07.client;

/**
 * Klassen die dieses Interface implementieren muessen Daten aus und einlesen koennen.
 *
 * @author sgeyer
 * @author mritter
 * @version 1.0
 */
public interface InputOutput {

	/**
	 * Wird aufgerufen wenn Inhalte ausgegeben oder dargestellt werden sollen.
	 *
	 * @param message Die Nachricht, die Ausgegeben werden soll
	 */
	public abstract void displayContent(String message);

	/**
	 * Wird aufgerufen wenn ein gewisser Inhalt eingelesen werden soll.
	 * Der Inhalt wird zeilenweise eingelesen und dann zurueckgegeben.
	 *
	 * @return Eingelesener Inhalt
	 */
	public abstract String readContent();

}
