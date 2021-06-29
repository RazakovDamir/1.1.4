package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users (\n" +
                     "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                     "  `name` VARCHAR(20) NOT NULL,\n" +
                     "  `lastname` VARCHAR(20) NOT NULL,\n" +
                     "  `age` INT NOT NULL,\n" +
                     "  PRIMARY KEY (`id`),\n" +
                     "  UNIQUE INDEX `id_UNIQUE` (`id` ASC))")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS users")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name ," + " lastName, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(" DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> AllUserList = new ArrayList<>();
        User user;
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, lastname, age" + " FROM users");
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Long id = rs.getLong(1);
                String name = rs.getString(2);
                String lastname = rs.getString(3);
                Byte age = rs.getByte(4);
                user = new User(name, lastname, age);
                user.setId(id);
                AllUserList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return AllUserList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(" TRUNCATE TABLE users")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
