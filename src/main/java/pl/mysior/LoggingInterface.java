package pl.mysior;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoggingInterface extends Remote{
    String checkAccess(String userName, String password) throws RemoteException;
}
