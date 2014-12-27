package at.geyerritter.dezsys07.balancer;

import java.rmi.RemoteException;

/**
 * Created by Mathias on 14.12.14.
 */
public class BalancerStarter {
    public static void main(String[] args) {
        try {
            new CalculatorBalancer(25637);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
