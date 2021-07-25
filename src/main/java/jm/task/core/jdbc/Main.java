package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        for (int i = 0; i <= 3; i++) {
            userService.saveUser("UserName" + i + 1, "LastName" + i + 1, (byte) (Math.random() * 7));
        }
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

}
