package db.repository;


import db.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserRepositoryImpl implements UserRepository {

    private static final String DATABASE_URL = "jdbc:mariadb://localhost:3307/idm";
    private static final String DATABASE_USERNAME = "user";
    private static final String DATABASE_PASSWORD = "password";

    @Override
    public void createTable() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             Statement statement = connection.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS user (" +
                    "id BIGINT  AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(255) UNIQUE NOT NULL," +
                    "password VARCHAR(255) NOT NULL," +
                    "role VARCHAR(255)" +
                    ")";

            statement.executeUpdate(sql);
            System.out.println("Table 'user' created");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(Long id) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {
            String sql = "SELECT * FROM user WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToUser(resultSet);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public User findByUsername(String username) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {
            String sql = "SELECT * FROM user WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToUser(resultSet);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {
            String sql = "SELECT * FROM user";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(mapResultSetToUser(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    @Override
    public Long createUser(User user) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {
            String sql = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getUsername());

                String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
                statement.setString(2, hashedPassword);

                statement.setString(3, user.getRole());

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed.");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Long userId=generatedKeys.getLong(1);
                        user.setId(userId);
                        return userId;
                    } else {
                        throw new SQLException("Creating user failed");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void updateUser(Long id,User user) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {
            String sql = "UPDATE user SET username = ?, password = ?, role = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getUsername());
                String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
                statement.setString(2, hashedPassword);
                statement.setString(3, user.getRole());
                statement.setLong(4, id);

                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteUser(Long userId) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {
            String sql = "DELETE FROM user WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, userId);

                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(resultSet.getString("role"));
        return user;
    }
}