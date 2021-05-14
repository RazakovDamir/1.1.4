package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/CoreTaskTemplate";
    private static final String USER = "root";
    private static final String PASSWORD = "password";


    private Util(){}


    private static Connection connection;

    public static Connection getConnection() {
        boolean closedConection = true;
        try {
            if (connection != null) {
                closedConection = connection.isClosed();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection == null || closedConection) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
