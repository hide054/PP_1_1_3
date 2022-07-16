package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String createTable = "CREATE TABLE IF NOT EXISTS Users" +
            "(Id INTEGER NOT NULL AUTO_INCREMENT, Name VARCHAR(65) NOT NULL, LastName VARCHAR(65) NOT NULL, Age INT NOT NULL, PRIMARY KEY (ID))";
    private static final String dropTable = "DROP TABLE IF EXISTS Users";
    private static final String saveNewUser = "INSERT INTO Users (Name, LastName, Age) VALUES(?, ?, ?)";
    private static final String deleteUserById ="DELETE FROM Users WHERE ID = ?";
    private static final String getUsersFromTable = "SELECT * FROM Users";
    private static final String cleanTable ="TRUNCATE TABLE Users";
    private static final Connection CONNECTION = Util.getConnection();

    public void createUsersTable() {
        try(Statement newTableStatement = CONNECTION.createStatement()) {
            newTableStatement.executeUpdate(createTable);
            CONNECTION.commit();
        } catch (SQLException e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try(Statement dropStatement = CONNECTION.createStatement()) {
            dropStatement.executeUpdate(dropTable);
            CONNECTION.commit();
        } catch (SQLException e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement psUserSave = CONNECTION.prepareStatement(saveNewUser)
        ) {
            psUserSave.setString(1, name);
            psUserSave.setString(2, lastName);
            psUserSave.setByte(3, age);
            psUserSave.executeUpdate();
            CONNECTION.commit();
        } catch (SQLException e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
                ex.printStackTrace();
            }
        }
        System.out.println("User с именем " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        try(PreparedStatement psDeleteUserById = CONNECTION.prepareStatement(deleteUserById)
        ) {
            psDeleteUserById.setLong(1, id);
            psDeleteUserById.executeUpdate();
            CONNECTION.commit();
        } catch (SQLException e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
                ex.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getUsersFromTable);
            while(resultSet.next()) {
                User user = new User(resultSet.getString("Name"),
                        resultSet.getString("LastName"),
                        resultSet.getByte("Age"));
                users.add(user);
                resultSet.close();
            }
            CONNECTION.commit();
        } catch (SQLException e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
                ex.printStackTrace();
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Statement cleanStatement = CONNECTION.createStatement()) {
            cleanStatement.executeUpdate(cleanTable);
            CONNECTION.commit();
        } catch (SQLException e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
                ex.printStackTrace();
            }
        }
    }
}
