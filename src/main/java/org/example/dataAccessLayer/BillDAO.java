package org.example.dataAccessLayer;

import org.example.connection.ConnectionFactory;
import org.example.model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa DAO pentru Bill.
 * Permite doar inserarea si citirea facturilor.
 */
public class BillDAO {

    /**
     * Insereaza un bill in tabela Log.
     * @param bill factura salvata
     */
    public void insert(Bill bill) {

        String query = "INSERT INTO Log(orderId, clientName, totalPrice) VALUES (?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionFactory.getConnection();

            statement = connection.prepareStatement(query);

            statement.setInt(1, bill.orderId());
            statement.setString(2, bill.clientName());
            statement.setDouble(3, bill.totalPrice());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Returneaza toate facturile din tabela Log.
     * @return lista de facturi
     */
    public List<Bill> findAll() {

        String query = "SELECT * FROM Log";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Bill> bills = new ArrayList<>();

        try {

            connection = ConnectionFactory.getConnection();

            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            while(resultSet.next()) {

                Bill bill = new Bill(
                        resultSet.getInt("id"),
                        resultSet.getInt("orderId"),
                        resultSet.getString("clientName"),
                        resultSet.getDouble("totalPrice")
                );

                bills.add(bill);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return bills;
    }
}