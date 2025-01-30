package com.hms.service;

import com.hms.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(User user);

    User updateUser(UUID id, User user);

    void deleteUser(UUID id);

    User getUserById(UUID id);

    List<User> getAllUsers();

    List<User> getUsersByRole(String role);

    List<User> getUsersByProperty(UUID propertyId);

    List<User> searchUsers(String keyword);

    User updateUserStatus(UUID id, boolean active);

    void updatePassword(UUID id, String newPassword);

    void verifyEmail(String token);

    void requestPasswordReset(String email);

    void resetPassword(String token, String newPassword);
}