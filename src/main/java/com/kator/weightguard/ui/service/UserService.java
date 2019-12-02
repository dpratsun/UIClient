package com.kator.weightguard.ui.service;

import com.kator.weightguard.ui.entity.User;

import java.util.List;

public interface UserService {
    User getUserByPassword(String password) throws Exception;
    Boolean checkPasswordWithConfirmPassword(String password, String confirmPassword) throws Exception;
    Boolean changeUserPassword(String user, String newPassword) throws Exception;
    List<User> getUsersList();
}
