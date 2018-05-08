package pl.mysior.Services;

public interface ConnectionStrategy {
    boolean connect() throws Exception;
    Object getCustomers(String authKey) throws Exception;
}