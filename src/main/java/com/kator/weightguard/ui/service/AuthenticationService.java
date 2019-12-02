package com.kator.weightguard.ui.service;

import com.kator.weightguard.ui.enums.UserOperations;

/**
 * Created by prats on 4/23/18.
 */
public interface AuthenticationService {
    void authenticate(UserOperations operation, String password) throws Exception;
    UserService getUserService();
}
