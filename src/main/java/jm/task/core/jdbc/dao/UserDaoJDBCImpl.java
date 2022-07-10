package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection CONNECTION = Util.getConnection();

    public void createUsersTable() {
        try(Statement сreateStatement = CONNECTION.createStatement()) {
            сreateStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Users" +
                    "(Id INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(65), LastName VARCHAR(65), Age INT)"
            );
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
            dropStatement.executeUpdate("DROP TABLE IF EXIST Users");
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
        try(PreparedStatement psUserSave = CONNECTION.prepareStatement(
                "INSERT INTO Users (Name, LastName, Age) VALUES(?, ?, ?)"
            )
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
        try(PreparedStatement psDeleteUserById = CONNECTION.prepareStatement(
                "DELETE FROM Users WHERE ID = ?")
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
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(
                "SELECT * FROM Users")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int i = 0;
                users.add(new User(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getByte(4)
                        )
                );
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
            cleanStatement.executeUpdate("TRUNCATE TABLE Users");
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
