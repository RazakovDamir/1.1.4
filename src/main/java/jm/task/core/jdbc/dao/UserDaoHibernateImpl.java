package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        try {
            session.createSQLQuery("Drop table if exists users_db.users").executeUpdate();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            session.close();
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
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            session.close();
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
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        List<User> userList = new ArrayList<>();
        try {
            userList  = session.createSQLQuery("SELECT * FROM users").list();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            session.close();
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
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            session.close();
        }
    }


    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;

    private static final String URL = "jdbc:mysql://localhost:3306/CoreTaskTemplate";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {

                StandardServiceRegistryBuilder rB = new StandardServiceRegistryBuilder();

                Map<String, String> settingsMap = new HashMap<>();
                settingsMap.put(Environment.URL, URL);
                settingsMap.put(Environment.USER, USER);
                settingsMap.put(Environment.PASS, PASSWORD);

                rB.applySettings(settingsMap);
                standardServiceRegistry = rB.build();

                MetadataSources mS = new MetadataSources(standardServiceRegistry).addAnnotatedClass(User.class);

                sessionFactory = mS.buildMetadata().buildSessionFactory();

            } catch (Exception e) {
                StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
            }
        }
        return sessionFactory;
    }
}
