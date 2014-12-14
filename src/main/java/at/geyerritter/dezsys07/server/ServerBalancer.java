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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan on 14.12.2014.
 */
public class ServerBalancer implements Balancer, Calculator {

    private int tmp;
    private int port;
    private int nextId;

    public ServerBalancer(int port) throws RemoteException {
        this.port = port;
        createRegistry();
    }

    private void createRegistry() throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind("Balancer", this);
    }

    public int getNextId() {
        this.nextId++;
        return this.nextId;
    }

    public BigDecimal pi(int anzahl_nachkommastellen) throws RemoteException {
        BigDecimal pi = null;

        List<String> elements = new ArrayList<String>();

        try {
            String[] entries = Naming.list("rmi://127.0.0.1:25638");

            for (String s : entries)
                if (s.contains("Calculator"))
                    elements.add(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (this.tmp + 1 > elements.size()) {
            this.tmp = 0;
            if (this.tmp + 1 > elements.size()) {
                return null;
            } else {
                try {
                    Calculator c = (Calculator) Naming.lookup("Caclultor" + this.tmp);
                    return c.pi(anzahl_nachkommastellen);
                } catch (NotBoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
