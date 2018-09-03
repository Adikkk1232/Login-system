package com.gjm.login_system.database_helpers;

import com.gjm.login_system.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseCleaner {
    private UserRepository userRepository;

    @Autowired
    public DatabaseCleaner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void databaseCleaner() {
        boolean cleanDatabase = false;

        if(cleanDatabase) {
            userRepository.deleteAll();
        }
    }
}
