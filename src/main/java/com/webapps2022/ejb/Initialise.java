package com.webapps2022.ejb;

import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author jacke
 */

@Startup
@Singleton
public class Initialise {

    @EJB
    UserStorageService userStorageService;

    @PostConstruct
    public void createAdmin() {
        try {
            userStorageService.createUser("admin1", "admin1", "GBP", "admins");
        } catch (Exception e) {
            System.out.println("Error on startup: " + e);
        }
    }
}
