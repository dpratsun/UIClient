package com.kator.weightguard.ui.service.impl;

import com.kator.weightguard.ui.entity.User;
import com.kator.weightguard.ui.enums.UserOperations;
import com.kator.weightguard.ui.service.AuthenticationService;
import com.kator.weightguard.ui.service.UserService;
import com.kator.weightguard.ui.user.UserGroupsPermissionsHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by prats on 4/23/18.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    UserService userService;

    @Autowired
    UserGroupsPermissionsHolder userGroupsPermissionsHolder;

    @Override
    public void authenticate(UserOperations operation, String password) throws Exception {
        User user = userService.getUserByPassword(password);

        userGroupsPermissionsHolder.canUserPerformOperation(user.getName(), operation);
    }

    @Override
    public UserService getUserService() {
        return userService;
    }
}
