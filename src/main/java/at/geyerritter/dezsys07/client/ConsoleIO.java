package at.geyerritter.dezsys07.client;

import java.util.Scanner;

/**
 * Die Konsole ist eine Form der Eingabe/Ausgabe, d.h.
 * Eingabe werden von der Konsole gelesen und alle Ausgaben werden
 * auf der Konsole zeilenweise ausgegeben.
 *
 * @author sgeyer
 * @author mritter
 *
 * @version 1.0
 */
public class ConsoleIO implements InputOutput {

    private Scanner sc;

    public ConsoleIO() {
        sc = new Scanner(System.in);
    }

    /**
     * @see at.geyerritter.dezsys07.client.InputOutput#displayContent(String)
     */
    @Override
    public void displayContent(String message) {
        System.out.println(message);
    }

    /**
     * @see InputOutput#readContent()
     */
    @Override
    public String readContent() {
        return sc.nextLine();
    }
}
