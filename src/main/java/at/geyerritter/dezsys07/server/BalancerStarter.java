package at.geyerritter.dezsys07.server;

import java.rmi.RemoteException;

/**
 * Created by Mathias on 14.12.14.
 */
public class BalancerStarter {
    public static void main(String[] args) {
        try {
            new ServerBalancer(25637);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
