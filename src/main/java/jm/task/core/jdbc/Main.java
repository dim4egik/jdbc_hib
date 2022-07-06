package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;


public class Main {
    private static UserService userService;

    static {
        try {
            userService = new UserServiceImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Никита", "Михонин", (byte) 78);
        userService.saveUser("Женя", "Филатов", (byte) 74);
        userService.saveUser("Володя", "Князев", (byte) 74);
        userService.saveUser("Данил", "Ухов", (byte) 59);
        userService.saveUser("Ваня", "Левченко", (byte) 74);

        userService.removeUserById(2);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
