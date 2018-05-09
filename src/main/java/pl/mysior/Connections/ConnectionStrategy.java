package pl.mysior.Connections;

import java.util.List;

public interface ConnectionStrategy {
    boolean connect() throws Exception;
    List<Object> getCustomers(String authKey) throws Exception;
}