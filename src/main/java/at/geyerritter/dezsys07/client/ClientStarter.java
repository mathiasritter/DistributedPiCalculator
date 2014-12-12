package at.geyerritter.dezsys07.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Mathias on 12.12.14.
 */
public class ClientStarter {

    public static void main(String[] args) {
        InputOutput io = new ConsoleIO();
        NetworkController nc = new CalculatorController(io);
        try {
            nc.connect("rmi://127.0.0.1/Calculator");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
