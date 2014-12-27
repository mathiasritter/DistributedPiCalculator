package at.geyerritter.dezsys07.server;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;

/**
 * Created by Mathias on 12.12.14.
 */
public class ServerStarter {

    public static void main(String[] args) {

        Random r = new Random();
        int port = r.nextInt(10000)+2000;

        try {
            Server s = new CalculatorServer("127.0.0.1", 25637, port);
            s.registerAtRegistry();
            Thread t = new Thread(s);
            Runtime.getRuntime().addShutdownHook(t);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
