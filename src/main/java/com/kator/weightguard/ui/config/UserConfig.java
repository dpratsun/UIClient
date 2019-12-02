package com.kator.weightguard.ui.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kator.weightguard.ui.entity.User;
import com.kator.weightguard.ui.service.UserService;
import com.kator.weightguard.ui.strings.UserGroups;
import com.kator.weightguard.ui.user.UserDataBase64FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserConfig {
    @Autowired
    public UserService userService;

    @Autowired
    public UserDataBase64FileStorage userDataBase64FileStorage;

    @PostConstruct
    public void initializeUserFile() {
        final Logger log = LoggerFactory.getLogger(this.getClass());
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        ObjectMapper objectMapper = new XmlMapper(module);

        try {
            String decodedData = userDataBase64FileStorage.getData();

            userService.getUsersList().addAll(objectMapper.readValue(decodedData, new TypeReference<List<User>>(){}));
            log.info("Successfully reading users list file");

            if(userService.getUsersList().size() != 0) return;

            log.error("Users list is empty!");
        } catch (Exception e) {
            log.error("Users list file not faund!");
        }

        try {
            log.info("Creating the default users list");
            userService.getUsersList().addAll(createDefaultUserList());
            log.info("Default users list file was created");
            userDataBase64FileStorage.storeData(objectMapper.writeValueAsString(userService.getUsersList()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private List<User> createDefaultUserList(){
        List<User> defaultUserList = new ArrayList<>();

        defaultUserList.add(new User(UserGroups.USER_GROUP_SECURITY, "RP-Nutzer"));
        defaultUserList.add(new User(UserGroups.USER_GROUP_CHEF, "RP-Video"));
        defaultUserList.add(new User(UserGroups.USER_GROUP_ADMINISTRATOR, "RP-Admin"));
        defaultUserList.add(new User(UserGroups.USER_GROUP_SUPER, "RP-Super"));

        return defaultUserList;
    }
}