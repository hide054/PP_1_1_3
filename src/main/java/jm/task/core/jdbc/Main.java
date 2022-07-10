package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();

        userDao.saveUser("Name1", "LastName1", (byte)11);
        userDao.saveUser("Name2", "LastName2", (byte)22);
        userDao.saveUser("Name3", "LastName3", (byte)33);
        userDao.saveUser("Name4", "LastName4", (byte)44);
        userDao.getAllUsers().forEach(System.out::println);
        userDao.removeUserById(1);
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
