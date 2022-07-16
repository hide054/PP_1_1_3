package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();
        userDao.createUsersTable();

        userDao.saveUser("Name1", "LastName1", (byte)11);
        userDao.saveUser("Name2", "LastName2", (byte)22);
        userDao.saveUser("Name3", "LastName3", (byte)33);
        userDao.saveUser("Name4", "LastName4", (byte)44);
        userDao.getAllUsers().forEach(System.out::println);
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
