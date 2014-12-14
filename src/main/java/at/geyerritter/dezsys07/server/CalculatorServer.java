package at.geyerritter.dezsys07.server;

import at.geyerritter.dezsys07.Balancer;
import at.geyerritter.dezsys07.Calculator;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ROUND_HALF_UP;

public class CalculatorServer extends UnicastRemoteObject implements Calculator, Server {

    private BigDecimal two;
    private BigDecimal four;
    private Balancer balancer;
    private Registry registry;
    private int id;

    protected CalculatorServer(String balancerip, int registryport, int serverport) throws RemoteException, MalformedURLException, NotBoundException {

        if ( System.getSecurityManager() == null ) {
            System.setProperty("java.security.policy", System.class.getResource("/java.policy").toString());
            System.setSecurityManager( new SecurityManager() );
        }

        UnicastRemoteObject.exportObject(this, serverport);

		this.registry = LocateRegistry.getRegistry(registryport);
        this.balancer = (Balancer) Naming.lookup("rmi://" + balancerip + ":" + registryport + "/Balancer");


        this.two = new BigDecimal(2);
        this.four = new BigDecimal(4);
    }

    /**
     * @see at.geyerritter.dezsys07.Calculator#pi(int)
     */
    public BigDecimal pi(int anzahl_nachkommastellen) throws RemoteException {

        if (anzahl_nachkommastellen > 997) {
            anzahl_nachkommastellen = 997;
        }


        BigDecimal a = ONE;
        BigDecimal b = ONE.divide(sqrt(two, anzahl_nachkommastellen), anzahl_nachkommastellen, ROUND_HALF_UP);
        BigDecimal t = new BigDecimal(0.25);
        BigDecimal x = ONE;
        BigDecimal y;

        while (!a.equals(b)) {
            y = a;
            a = a.add(b).divide(this.two, anzahl_nachkommastellen, ROUND_HALF_UP);
            b = sqrt(b.multiply(y), anzahl_nachkommastellen);
            t = t.subtract(x.multiply(y.subtract(a).multiply(y.subtract(a))));
            x = x.multiply(this.two);
        }

        return a.add(b).multiply(a.add(b)).divide(t.multiply(this.four), anzahl_nachkommastellen, ROUND_HALF_UP);
    }

    private BigDecimal sqrt(BigDecimal a, final int anzahl_nachkommastellen) {
        BigDecimal x0 = new BigDecimal("0");
        BigDecimal x1 = new BigDecimal(Math.sqrt(a.doubleValue()));

        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = a.divide(x0, anzahl_nachkommastellen, ROUND_HALF_UP);
            x1 = x1.add(x0);
            x1 = x1.divide(this.two, anzahl_nachkommastellen, ROUND_HALF_UP);
        }

        return x1;
    }

    @Override
    public void unregisterAtRegistry() throws RemoteException, NotBoundException {
        registry.unbind("Calculator" + id);
    }

    @Override
    public void registerAtRegistry() throws RemoteException {
        id = balancer.getNextId();
        registry.rebind("Calculator" + id, this);
    }

    @Override
    public void run() {
        try {
            this.unregisterAtRegistry();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
