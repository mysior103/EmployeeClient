package pl.mysior.Connections;
import pl.mysior.webservice.AllWorkers;
import pl.mysior.webservice.AllWorkersImplService;

import java.util.List;

public class ServiceConnection implements ConnectionStrategy {
    @Override
    public boolean connect() throws Exception {
        return false;
    }

    @Override
    public List<Object> getCustomers(String authKey) throws Exception {
        AllWorkersImplService allWorkersImplService = new AllWorkersImplService();
        AllWorkers workers = allWorkersImplService.getAllWorkersImplPort();
        List<Object> all = workers.getAllWorkers(authKey);
        return all;
    }
}
