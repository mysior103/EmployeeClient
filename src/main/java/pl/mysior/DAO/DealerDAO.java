package pl.mysior.DAO;

import pl.mysior.BuisnessObject.Dealer;
import pl.mysior.DataBaseConnections.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DealerDAO extends WorkerDAO {

    public void addDealer(Dealer dealer) {
        String query = "INSERT INTO " +
                "employee(id,name,lastName,position,salary,phoneNumber,commission,maxCommission) " +
                "VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ptmt = conn.prepareStatement(query)) {
            ptmt.setLong(1, dealer.getID());
            ptmt.setString(2, dealer.getName());
            ptmt.setString(3, dealer.getLastName());
            ptmt.setString(4, dealer.getPosition());
            ptmt.setBigDecimal(5, dealer.getSalary());
            ptmt.setString(6, dealer.getPhoneNumber());
            ptmt.setBigDecimal(7, dealer.getCommission());
            ptmt.setBigDecimal(8, dealer.getMaxCommission());
            ptmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dealer getDealerById(Long id) {
        Dealer dealer = null;
        String query = "SELECT * FROM employee WHERE id=? AND position='Handlowiec'";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ptmt = createPreparedStatement(conn, id, query);
             ResultSet resultSet = ptmt.executeQuery()) {
            ptmt.setLong(1, id);
            while (resultSet.next()) {
                dealer = new Dealer();
                dealer.setID(resultSet.getLong("id"));
                dealer.setName(resultSet.getString("name"));
                dealer.setLastName(resultSet.getString("lastName"));
                dealer.setPosition(resultSet.getString("position"));
                dealer.setSalary(resultSet.getBigDecimal("salary"));
                dealer.setPhoneNumber(resultSet.getString("phoneNumber"));
                dealer.setCommission(resultSet.getBigDecimal("commission"));
                dealer.setMaxCommission(resultSet.getBigDecimal("maxCommission"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dealer;
    }

    public List<Dealer> getAllDealers() {
        List<Dealer> allDealers = new ArrayList<>();
        String query = "SELECT * FROM employee WHERE position='Handlowiec'";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ptmt = conn.prepareStatement(query);
             ResultSet resultSet = ptmt.executeQuery()) {
            while (resultSet.next()) {
                Dealer dealer = new Dealer();
                dealer.setID(resultSet.getLong("id"));
                dealer.setName(resultSet.getString("name"));
                dealer.setLastName(resultSet.getString("lastName"));
                dealer.setPosition(resultSet.getString("position"));
                dealer.setSalary(resultSet.getBigDecimal("salary"));
                dealer.setPhoneNumber(resultSet.getString("phoneNumber"));
                dealer.setCommission(resultSet.getBigDecimal("commission"));
                dealer.setMaxCommission(resultSet.getBigDecimal("maxCommission"));
                allDealers.add(dealer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allDealers;
    }
}
