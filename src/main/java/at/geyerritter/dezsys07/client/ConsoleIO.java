package at.geyerritter.dezsys07.client;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class ConsoleIO implements InputOutput {

    private Scanner sc;

    private static final Logger logger = LogManager.getLogger("ConsoleIO");

    public ConsoleIO() {
        sc = new Scanner(System.in);
    }

    @Override
    public void displayContent(String message) {
        logger.info(message);
    }

    @Override
    public String readContent() {
        return sc.nextLine();
    }
}
