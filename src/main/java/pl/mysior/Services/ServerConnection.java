package pl.mysior.Services;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerConnection implements ServerConnectionStrategy {
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
    public Object getCustomers(String authKey) throws Exception {
        authorize(authKey);
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        return ois.readObject();
    }

    private void authorize(String authKey) throws Exception {
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        dos.writeUTF(authKey);
        dos.flush();
    }

}
