package pl.mysior.DAO;

import pl.mysior.BuisnessObject.Director;
import pl.mysior.DataBaseConnections.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DirectorDAO extends WorkerDAO {

    public DirectorDAO() {
    }

    public void addDirector(Director director) {
        String query = "INSERT INTO " +
                "employee(id,name,lastName,position,salary,phoneNumber,creditCardNumber,costLimit) " +
                "VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ptmt = conn.prepareStatement(query)) {

            ptmt.setLong(1, director.getID());
            ptmt.setString(2, director.getName());
            ptmt.setString(3, director.getLastName());
            ptmt.setString(4, director.getPosition());
            ptmt.setBigDecimal(5, director.getSalary());
            ptmt.setString(6, director.getPhoneNumber());
            ptmt.setString(7, director.getCreditCardNumber());
            ptmt.setBigDecimal(8, director.getCostLimit());
            ptmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Director> getAllDirectors() {
        List<Director> allDirectors = new ArrayList<>();
        String query = "SELECT * FROM employee WHERE position='Dyrektor'";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ptmt = conn.prepareStatement(query);
             ResultSet resultSet = ptmt.executeQuery()) {
            while (resultSet.next()) {
                Director director = new Director();
                director.setID(resultSet.getLong("id"));
                director.setName(resultSet.getString("name"));
                director.setLastName(resultSet.getString("lastName"));
                director.setPosition(resultSet.getString("position"));
                director.setSalary(resultSet.getBigDecimal("salary"));
                director.setPhoneNumber(resultSet.getString("phoneNumber"));
                director.setCreditCardNumber(resultSet.getString("creditCardNumber"));
                director.setCostLimit(resultSet.getBigDecimal("costLimit"));
                allDirectors.add(director);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allDirectors;
    }

    public Director getDirectorById(Long id) {
        Director director = null;
        String query = "SELECT * FROM employee WHERE id=? AND position='Dyrektor'";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ptmt = createPreparedStatement(conn, id, query);
             ResultSet resultSet = ptmt.executeQuery()) {
            ptmt.setLong(1, id);
            while (resultSet.next()) {
                director = new Director();
                director.setID(resultSet.getLong("id"));
                director.setName(resultSet.getString("name"));
                director.setLastName(resultSet.getString("lastName"));
                director.setPosition(resultSet.getString("position"));
                director.setSalary(resultSet.getBigDecimal("salary"));
                director.setPhoneNumber(resultSet.getString("phoneNumber"));
                director.setCreditCardNumber(resultSet.getString("creditCardNumber"));
                director.setCostLimit(resultSet.getBigDecimal("costLimit"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return director;
    }
}
