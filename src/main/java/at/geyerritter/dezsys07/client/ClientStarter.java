package at.geyerritter.dezsys07.client;

/**
 * Created by Mathias on 12.12.14.
 */
public class ClientStarter {

    public static void main(String[] args) {
        InputOutput io = new ConsoleIO();
        NetworkController nc = new CalculatorController(io);
    }

}
