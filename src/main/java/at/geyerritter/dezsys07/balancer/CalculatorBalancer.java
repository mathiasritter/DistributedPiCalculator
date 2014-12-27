package at.geyerritter.dezsys07.balancer;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan on 14.12.2014.
 */
public class CalculatorBalancer extends UnicastRemoteObject implements Balancer, Calculator  {

    private int tmp;
    private int port;
    private int nextId;

    public CalculatorBalancer(int port) throws RemoteException {

        if ( System.getSecurityManager() == null ) {
            System.setProperty("java.security.policy", System.class.getResource("/java.policy").toString());
            System.setSecurityManager( new SecurityManager() );
        }

        this.port = port;
        createRegistry();
    }

    private void createRegistry() throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind("Balancer", this);
    }

    public int getNextId() throws RemoteException {
        this.nextId++;
        return this.nextId;
    }

    public BigDecimal pi(int anzahl_nachkommastellen) throws RemoteException {

        List<String> elements = new ArrayList<String>();

        try {
            String[] entries = Naming.list("rmi://127.0.0.1:" + port);

            for (String s : entries)
                if (s.contains("Calculator"))
                    elements.add(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        System.out.println("Size of elements " + elements.size());

        if (this.tmp + 1 > elements.size()) {
            this.tmp = 0;
            if (this.tmp + 1 > elements.size()) {
                System.out.println("Null returned, weil" + tmp++ + " > " + elements.size());
                return null;
            } else {
                try {
                    Calculator c = (Calculator) Naming.lookup(elements.get(this.tmp));
                    return c.pi(anzahl_nachkommastellen);
                } catch (NotBoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Calculator c = null;
            try {
                c = (Calculator) Naming.lookup(elements.get(this.tmp));
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return c.pi(anzahl_nachkommastellen);
        }

        return null;
    }
}
