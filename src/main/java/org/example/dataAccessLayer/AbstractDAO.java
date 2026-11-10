package org.example.dataAccessLayer;

import org.example.connection.ConnectionFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa generica pentru operatii pe baza de date.
 * Contine metode comune pentru accesarea tabelelor.
 * @param <T> tipul obiectului
 */
public class AbstractDAO<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    /**
     * Constructor care afla tipul clasei.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    /**
     * Returneaza numele tabelei.
     * @return numele tabelei
     */
    private String getTableName() {

        if(type.getSimpleName().equals("Order")) {
            return "\"Order\"";
        }

        return type.getSimpleName();
    }

    /**
     * Creeaza query pentru select dupa un camp.
     * @param field campul cautat
     * @return query-ul
     */
    private String createSelectQuery(String field) {

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT * FROM ");
        sb.append(getTableName());
        sb.append(" WHERE ");
        sb.append(field);
        sb.append(" = ?");

        return sb.toString();
    }

    /**
     * Creeaza query pentru selectarea tuturor elementelor.
     * @return query-ul
     */
    private String createFindAllQuery() {
        return "SELECT * FROM " + getTableName();
    }

    /**
     * Creeaza query pentru stergere.
     * @return query-ul
     */
    private String createDeleteQuery() {
        return "DELETE FROM " + getTableName() + " WHERE id = ?";
    }

    /**
     * Creeaza query pentru insert.
     * @return query-ul
     */
    private String createInsertQuery() {

        StringBuilder fields = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for(Field field : type.getDeclaredFields()) {

            if(!field.getName().equals("id")) {

                fields.append(field.getName()).append(",");
                values.append("?,");
            }
        }

        fields.deleteCharAt(fields.length() - 1);
        values.deleteCharAt(values.length() - 1);

        return "INSERT INTO " + getTableName() +
                "(" + fields + ")" +
                " VALUES (" + values + ")";
    }

    /**
     * Creeaza query pentru update.
     * @return query-ul
     */
    private String createUpdateQuery() {

        StringBuilder sb = new StringBuilder();

        sb.append("UPDATE ");
        sb.append(getTableName());
        sb.append(" SET ");

        for(Field field : type.getDeclaredFields()) {

            if(!field.getName().equals("id")) {
                sb.append(field.getName()).append(" = ?,");
            }
        }

        sb.deleteCharAt(sb.length() - 1);

        sb.append(" WHERE id = ?");

        return sb.toString();
    }

    /**
     * Returneaza toate obiectele din tabela.
     * @return lista de obiecte
     */
    public List<T> findAll() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection = ConnectionFactory.getConnection();

            statement = connection.prepareStatement(createFindAllQuery());

            resultSet = statement.executeQuery();

            return createObjects(resultSet);

        } catch (SQLException e) {

            LOGGER.log(Level.WARNING,
                    type.getName() + "DAO:findAll " + e.getMessage());

        } finally {

            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return new ArrayList<>();
    }

    /**
     * Cauta un obiect dupa id.
     * @param id id-ul cautat
     * @return obiectul gasit
     */
    public T findById(int id) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String query = createSelectQuery("id");

        try {

            connection = ConnectionFactory.getConnection();

            statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            List<T> list = createObjects(resultSet);

            if(!list.isEmpty()) {
                return list.get(0);
            }

        } catch (SQLException e) {

            LOGGER.log(Level.WARNING,
                    type.getName() + "DAO:findById " + e.getMessage());

        } finally {

            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * Insereaza un obiect in baza de date.
     * @param object obiectul inserat
     */
    public void insert(T object) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionFactory.getConnection();

            statement = connection.prepareStatement(createInsertQuery());

            int index = 1;

            for(Field field : type.getDeclaredFields()) {

                if(!field.getName().equals("id")) {

                    field.setAccessible(true);

                    statement.setObject(index, field.get(object));

                    index++;
                }
            }

            statement.executeUpdate();

        } catch (SQLException | IllegalAccessException e) {

            LOGGER.log(Level.WARNING,
                    type.getName() + "DAO:insert " + e.getMessage());

        } finally {

            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Actualizeaza un obiect din baza de date.
     * @param object obiectul actualizat
     */
    public void update(T object) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionFactory.getConnection();

            statement = connection.prepareStatement(createUpdateQuery());

            int index = 1;
            int id = 0;

            for(Field field : type.getDeclaredFields()) {

                field.setAccessible(true);

                if(field.getName().equals("id")) {

                    id = (int) field.get(object);

                } else {

                    statement.setObject(index, field.get(object));
                    index++;
                }
            }

            statement.setInt(index, id);

            statement.executeUpdate();

        } catch (SQLException | IllegalAccessException e) {

            LOGGER.log(Level.WARNING,
                    type.getName() + "DAO:update " + e.getMessage());

        } finally {

            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Sterge un obiect dupa id.
     * @param id id-ul obiectului
     */
    public void delete(int id) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionFactory.getConnection();

            statement = connection.prepareStatement(createDeleteQuery());

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {

            LOGGER.log(Level.WARNING,
                    type.getName() + "DAO:delete " + e.getMessage());

        } finally {

            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Creeaza obiecte din ResultSet.
     * @param resultSet rezultatul query-ului
     * @return lista de obiecte
     */
    private List<T> createObjects(ResultSet resultSet) {

        List<T> list = new ArrayList<>();

        try {

            while(resultSet.next()) {

                T instance = type.getDeclaredConstructor().newInstance();

                for(Field field : type.getDeclaredFields()) {

                    Object value = resultSet.getObject(field.getName());

                    PropertyDescriptor propertyDescriptor =
                            new PropertyDescriptor(field.getName(), type);

                    Method method = propertyDescriptor.getWriteMethod();

                    method.invoke(instance, value);
                }

                list.add(instance);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}