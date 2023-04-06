package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {

        String stringSql = "CREATE TABLE IF NOT EXISTS USERS "
                + "(ID BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT, "
                + "NAME VARCHAR(45) NOT NULL, "
                + "LASTNAME VARCHAR(45) NOT NULL, "
                + "AGE TINYINT NOT NULL);";

        try (PreparedStatement  preparedStatement = connection.prepareStatement(stringSql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {

        String stringSql = "DROP TABLE IF EXISTS USERS;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(stringSql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        String stringSql = "INSERT INTO USERS (name, lastname, age) VALUES(?, ?, ?)";

        try (PreparedStatement  preparedStatement = connection.prepareStatement(stringSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.format("User с именем - %s  добавлен в базу данных\n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {

        String stringSql = "DELETE FROM USERS WHERE ID";
        try (PreparedStatement preparedStatement = connection.prepareStatement(stringSql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> userList = new ArrayList<>();
        String stringSql = "SELECT ID, NAME, LASTNAME, AGE FROM USERS";
        try (PreparedStatement preparedStatement = connection.prepareStatement(stringSql)) {

            ResultSet resultSet = preparedStatement.executeQuery(stringSql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }

            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanUsersTable() {

        String stringSql = "TRUNCATE TABLE USERS";
        try (PreparedStatement preparedStatement = connection.prepareStatement(stringSql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


