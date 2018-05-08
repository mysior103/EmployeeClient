package pl.mysior.Services;

public interface ServerConnectionStrategy {
    boolean connect() throws Exception;
    Object getCustomers(String authKey) throws Exception;
}