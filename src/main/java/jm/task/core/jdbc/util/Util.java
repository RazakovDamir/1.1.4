package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/CoreTaskTemplate";
    private static final String USER = "root";
    private static final String PASSWORD = "password";


    private Util() {
    }


    private static Connection connection;

    public static Connection getConnection() {
        boolean closedConnection = true;
        try {
            if (connection != null) {
                closedConnection = connection.isClosed();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection == null || closedConnection) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;

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

