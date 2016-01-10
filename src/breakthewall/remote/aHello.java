package breakthewall.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface aHello extends Remote {
    String sayHello() throws RemoteException;
}