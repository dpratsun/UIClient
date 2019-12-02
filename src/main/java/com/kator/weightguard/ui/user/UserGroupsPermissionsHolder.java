package com.kator.weightguard.ui.user;

import com.kator.weightguard.ui.enums.UserOperations;
import com.kator.weightguard.ui.strings.UserGroups;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.kator.weightguard.ui.strings.Message.DENIED_ACCESS;

@Component
public class UserGroupsPermissionsHolder {
    private Map<String, List<UserOperations>> userPermissionsMap;

    UserGroupsPermissionsHolder() {
        userPermissionsMap = new HashMap<>();

        List<UserOperations> securityGroupPermissionsList = new ArrayList<>();
        securityGroupPermissionsList.add(UserOperations.canPlayVideo);

        userPermissionsMap.put(UserGroups.USER_GROUP_SECURITY, securityGroupPermissionsList);

        List<UserOperations> chefGroupPermissionsList = new ArrayList<>();
        chefGroupPermissionsList.add(UserOperations.canPlayVideo);
        chefGroupPermissionsList.add(UserOperations.canDeleteVideo);
        chefGroupPermissionsList.add(UserOperations.canSaveVideo);

        userPermissionsMap.put(UserGroups.USER_GROUP_CHEF, chefGroupPermissionsList);

        List<UserOperations> administratorGroupPermissionsList = new ArrayList<>();
        administratorGroupPermissionsList.add(UserOperations.canModifyShelfTab);

        userPermissionsMap.put(UserGroups.USER_GROUP_ADMINISTRATOR, administratorGroupPermissionsList);

        List<UserOperations> superGroupPermissionList = new ArrayList<>();
        superGroupPermissionList.add(UserOperations.canPlayVideo);
        superGroupPermissionList.add(UserOperations.canDeleteVideo);
        superGroupPermissionList.add(UserOperations.canSaveVideo);
        superGroupPermissionList.add(UserOperations.canModifyShelfTab);
        superGroupPermissionList.add(UserOperations.canModifyAdminTab);

        userPermissionsMap.put(UserGroups.USER_GROUP_SUPER, superGroupPermissionList);
    }

    public Boolean canUserPerformOperation(String user, UserOperations userPermission) throws Exception  {
        if (userPermissionsMap.get(user).contains(userPermission)) return true;

        throw new Exception(DENIED_ACCESS);
    }
}
