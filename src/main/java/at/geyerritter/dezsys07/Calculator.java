package at.geyerritter.dezsys07;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {

	public abstract BigDecimal pi(int anzahl_nachkommastellen) throws RemoteException;

}
