package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static Connection CONNECTION = Util.getConnection();
    public UserDaoJDBCImpl() {
        //
    }

    public void createUsersTable() {
        try(Statement сreateStatement = CONNECTION.createStatement()) {
            сreateStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Users" +
                    "(ID INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(65), LastName VARCHAR(65), Age INT)"
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
                "INSERT INTO Users (Name, LastName, Age) VALUES (?, ?, ?)"
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
        try (Statement getAllStatement = CONNECTION.createStatement();
                ResultSet resultSet = getAllStatement.executeQuery("SELECT * FROM Users")) {
            while(resultSet.next()) {
                users.add(new User());
            }
            for (User printUser : users) {
                System.out.println(printUser);
            }
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
