package db.repository;

import db.model.User;

import java.util.List;


public interface UserRepository {
    void createTable();
    User findById(Long id);
    User findByUsername(String username);
    List<User> getAllUsers();
    Long createUser(User user);
    void updateUser(Long id,User user);
    void deleteUser(Long userId);
}
