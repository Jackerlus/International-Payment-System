package com.webapps2022.ejb;

/**
 *
 * @author jacke
 */

import com.webapps2022.entity.ServiceUser;
import com.webapps2022.entity.SystemUserGroup;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;

@Stateless
public class UserStorageService {

    public UserStorageService() {
    }
    
    @PersistenceContext(unitName = "WebappsDBPU")
    private EntityManager em;
    
    @Inject
    Pbkdf2PasswordHash passwordHasher;
    
    @Transactional
    public ServiceUser createUser(String username, String password, String currencyType, String permissions) throws NoSuchAlgorithmException {
//        ServiceUser newUser = new ServiceUser(username, passwordHasher.generate(password.toCharArray()), currencyType);
//        em.persist(newUser);
//        em.flush();
//        return newUser;

            ServiceUser user = null;
            SystemUserGroup userGroup;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String pw = password;
            md.update(pw.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String pwReady = sb.toString();

            // apart from the default constructor which is required by JPA
            // you need to also implement a constructor that will make the following code succeed
            user = new ServiceUser(username, pwReady, currencyType, permissions);
            userGroup = new SystemUserGroup(username, permissions);

            em.persist(user);
            em.persist(userGroup);
            return user;

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    
    public List<ServiceUser> getAllUsers(){
        return em.createNamedQuery("ServiceUser.all", ServiceUser.class).getResultList();
    }
    
    public Optional<ServiceUser> getUser(String username){
        return em.createNamedQuery("ServiceUser.byUsername", ServiceUser.class)
                 .setParameter("username", username)
                 .getResultList()
                 .stream()
                 .findFirst();
    }

//    public void registerUser(String username, String userPassword, String name, String surname) throws NoSuchAlgorithmException {
//        try {
//            ServiceUser user;
//            SystemUserGroup userGroup;
//
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            String password = userPassword;
//            md.update(password.getBytes("UTF-8"));
//            byte[] digest = md.digest();
//            StringBuffer sb = new StringBuffer();
//            for (int i = 0; i < digest.length; i++) {
//                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
//            }
//            String paswdToStoreInDB = sb.toString();
//
//            // apart from the default constructor which is required by JPA
//            // you need to also implement a constructor that will make the following code succeed
//            user = new ServiceUser(username, paswdToStoreInDB, name, surname);
//            userGroup = new SystemUserGroup(username, "users");
//
//            em.persist(user);
//            em.persist(userGroup);
//
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}