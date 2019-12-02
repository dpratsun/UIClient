package com.kator.weightguard.ui.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kator.weightguard.ui.entity.User;
import com.kator.weightguard.ui.service.UserService;
import com.kator.weightguard.ui.user.UserDataBase64FileStorage;
import com.kator.weightguard.ui.utils.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kator.weightguard.ui.strings.Message.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    public UserDataBase64FileStorage userDataBase64FileStorage;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public List<User> usersList = new ArrayList<>();

    @Override
    public User getUserByPassword(String password) throws Exception {
        Optional<User> optional = usersList.stream()
                .filter(user -> user.getPassword().equals(password))
                .findFirst();

        if(optional.isPresent()) {
            return optional.get();
        }

        throw new Exception(INCORRECT_PASSWORD);
    }

    @Override
    public Boolean checkPasswordWithConfirmPassword(String password, String confirmPassword) throws Exception {
        for (User item : usersList) {
            if (item.getPassword().equals(password)) throw new Exception(INCORRECT_NEW_PASSWORD);
        }
        if(password.isEmpty()) throw new Exception(INCORRECT_NEW_PASSWORD);
        if(password.equals(confirmPassword)) return true;
        throw new Exception(INCORRECT_NEW_PASSWORD);
    }

    @Override
    public Boolean changeUserPassword(String user, String newPassword) throws Exception {
        for (User item : usersList) {
            if (item.getName().equals(user)) {
                item.setPassword(newPassword);

                userDataBase64FileStorage.storeData(objectMapper.writeValueAsString(usersList));

                return true;
            }
        }

        throw new Exception(USER_NOT_FOUND);
    }

    @Override
    public List<User> getUsersList(){
        return this.usersList;
    }
}