package jm.task.core.jdbc.util;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USER = "root";
    private static final String PASSWORD = "west2000";
    private static Connection connection;
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        Properties property = new Properties();
        property.setProperty("hibernate.connection.url", URL);
        property.setProperty("hibernate.connection.username", USER);
        property.setProperty("hibernate.connection.password", PASSWORD);
        property.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        property.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        sessionFactory = new Configuration()
                .addProperties(property)
                .addAnnotatedClass(jm.task.core.jdbc.model.User.class)
                .buildSessionFactory();
        return sessionFactory;
    }
}
