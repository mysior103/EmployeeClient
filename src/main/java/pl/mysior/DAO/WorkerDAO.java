package pl.mysior.DAO;

import pl.mysior.BuisnessObject.Dealer;
import pl.mysior.BuisnessObject.Director;
import pl.mysior.DataBaseConnections.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkerDAO {

    public List<Object> getAllWorkers() {
        List<Dealer> dealers = new DealerDAO().getAllDealers();
        List<Director> directors = new DirectorDAO().getAllDirectors();
        List<Object> allWorkers = new ArrayList<>();
        allWorkers.addAll(dealers);
        allWorkers.addAll(directors);
        return allWorkers;
    }

    public void deleteAllWorkers() {
        String query = "TRUNCATE TABLE employee";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ptmt = conn.prepareStatement(query)) {
            ptmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWorker(Long id) {
        String query = "DELETE FROM employee WHERE id=?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ptmt = conn.prepareStatement(query)) {
            ptmt.setLong(1, id);
            ptmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected PreparedStatement createPreparedStatement(Connection con, Long id, String query) throws SQLException {
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1, id);
        return ps;
    }

}
