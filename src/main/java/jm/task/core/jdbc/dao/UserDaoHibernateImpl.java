package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getSessionFactory;


public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {}


    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        try {
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(20) NOT NULL,\n" +
                    "  `lastname` VARCHAR(20) NOT NULL,\n" +
                    "  `age` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)").addEntity(User.class).executeUpdate();
            tr.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        try {
            session.createSQLQuery("Drop table if exists users_db.users").executeUpdate();
            tr.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        try {
            User user = new User(name, lastName, age);
            session.save(user);
            tr.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        try {
            User user = new User();
            user.setId(id);
            session.delete(user);
            tr.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        List<User> userList = new ArrayList<>();
        try {
            userList  = session.createSQLQuery("SELECT * FROM users").list();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        try {
            session.createSQLQuery("DELETE FROM users;").executeUpdate();
            tr.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
