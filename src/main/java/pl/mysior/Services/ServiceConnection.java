package pl.mysior.Services;

public class ServiceConnection implements ConnectionStrategy {
    @Override
    public boolean connect() throws Exception {
        return false;
    }

    @Override
    public Object getCustomers(String authKey) throws Exception {
        return null;
    }
}
