package com.kator.weightguard.ui.user;

import com.kator.weightguard.ui.entity.User;
import com.kator.weightguard.ui.utils.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by prats on 4/25/18.
 */
@Component
public class UserDataBase64FileStorage {
    private static final String FILE_NAME = "users.bin";

    @Value("${credentials.file.location}")
    private String credentialsFileLocation;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    UserDataBase64FileStorage() {}

    @PostConstruct
    public void init() {
        try {
            File directory = new File(credentialsFileLocation);
            if(!directory.exists()){
                credentialsFileLocation = "";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public String getData() {
        Path filePath = Paths.get(credentialsFileLocation + FILE_NAME);
        try {
            return Base64.decode(new String(Files.readAllBytes(filePath)));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return new String();
    }

    public void storeData(String data) {
        String encodedData = Base64.encode(data);
        try {
            Files.write(Paths.get(credentialsFileLocation + FILE_NAME), encodedData.getBytes());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
