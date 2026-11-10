package org.example.connection;

import java.sql.*;
import java.util.logging.Logger;

/**
 * Clasa responsabila pentru realizarea conexiunii la baza de date.
 * Foloseste pattern-ul Singleton.
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/orders_management";
    private static final String USER = "postgres";
    private static final String PASS = "Dumitrita09";

    private static final ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * Constructor privat pentru Singleton.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creeaza o conexiune la baza de date.
     * @return conexiunea
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.warning("Eroare la conectarea la baza de date");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Returneaza o conexiune activa.
     * @return conexiunea
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Inchide conexiunea.
     * @param connection conexiunea
     */
    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.warning("Eroare la inchiderea conexiunii");
        }
    }

    /**
     * Inchide un statement.
     * @param statement statement-ul
     */
    public static void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.warning("Eroare la inchiderea statement-ului");
        }
    }

    /**
     * Inchide un result set.
     * @param resultSet resultSet-ul
     */
    public static void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOGGER.warning("Eroare la inchiderea resultSet-ului");
        }
    }
}