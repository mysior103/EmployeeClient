package pl.mysior.Connections;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerConnection implements ConnectionStrategy {
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private String ipAddress;
    private int port;
    Socket s;

    public ServerConnection() {
        ipAddress = "localhost";
        port = 1234;
    }

    public boolean connect() {
        s = new Socket();
        try {
            s.connect(new InetSocketAddress(ipAddress, port));
            if (s.isConnected()) {
                return true;
            } else {
                System.out.println("Serwer niedostępny!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Błąd łączenia do serwera. Upewnij się, że serwer działa i spróbuj ponownie.");
            return false;
        }
    }

    @Override
    public List<Object> getCustomers(String authKey) throws Exception {
        authorize(authKey);
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        Object received = ois.readObject();
        return (ArrayList) received;
    }

    private void authorize(String authKey) throws Exception {
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        dos.writeUTF(authKey);
        dos.flush();
    }

}
