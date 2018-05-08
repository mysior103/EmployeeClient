package pl.mysior.Services;

import pl.mysior.LoggingInterface;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class RMIServiceClient {
    LoggingInterface log;

    public RMIServiceClient() {
        try {
            log = (LoggingInterface) Naming.lookup("rmi://127.0.0.1/Elo");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getAuthKey(String userName, String password){
        String authKey = null;
        try {
            authKey = log.checkAccess(userName, password);
        } catch (RemoteException e) {
            System.out.println("Nie można pobrac klucza");
        }
        return authKey;
    }
}

