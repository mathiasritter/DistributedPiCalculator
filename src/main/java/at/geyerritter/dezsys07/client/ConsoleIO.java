package at.geyerritter.dezsys07.client;

import java.util.Scanner;

public class ConsoleIO implements InputOutput {

    private Scanner sc;

    public ConsoleIO() {
        sc = new Scanner(System.in);
    }

    @Override
    public void displayContent(String message) {
        System.out.println(message);
    }

    @Override
    public String readContent() {
        return sc.nextLine();
    }
}
