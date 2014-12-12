package at.geyerritter.dezsys07.server;

import java.rmi.RemoteException;

/**
 * Created by Mathias on 12.12.14.
 */
public class ServerStarter {

    public static void main(String[] args) {
        try {
            new CalculatorServer(25638);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
