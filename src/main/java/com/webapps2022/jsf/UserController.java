package com.webapps2022.jsf;
import com.webapps2022.ejb.UserStorageService;
import com.webapps2022.entity.ServiceUser;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import javax.annotation.PostConstruct;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.UserTransaction;

/**
 *
 * @author jacke
 */

@RequestScoped
@Named
public class UserController implements Serializable {
    String username;
    String password;
    String confirmPassword;
    String currencyType;
    String role;
    double currencyAmount;
   
    ServiceUser user = new ServiceUser();

    @Inject
    private UserStorageService userStorageService;

    private List<ServiceUser> allUsers;

    private FacesContext facesContext = FacesContext.getCurrentInstance();

    @PostConstruct
    public void initialize(){
        this.allUsers = userStorageService.getAllUsers();
    }

    public ExternalContext getExternalContext() {
        return facesContext.getExternalContext();
    }

    public String getCurrentUsername() {
        String name = "";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        if (externalContext.getUserPrincipal() != null) {
            name = externalContext.getUserPrincipal().getName();
            System.out.println("Principal: " + name);
        }
        return name;
    }

    @RolesAllowed({"admins"})
    public List<ServiceUser> getAllUsers() {
        return allUsers;
    }

    public ServiceUser getUser() {
        if (user == null) return new ServiceUser();
        else return user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public String getCurrencyType(String user) {
        return userStorageService.getUser(user).get().getCurrencyType();
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public double getCurrencyAmount() {
        return currencyAmount;
    }

    public double getCurrencyAmount(String user) {
        return userStorageService.getUser(user).get().getCurrencyAmount();
    }

    public void setCurrencyAmount(double currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public String getRole() {
        return role;
    }

    public String getRole(String user) {
        return userStorageService.getUser(user).get().getRole();
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserStorageService getUserStorageService() {
        return userStorageService;
    }

    public void setUserStorageService(UserStorageService userStorageService) {
        this.userStorageService = userStorageService;
    }

    public void registerUser(String permissions) throws Exception {
        userStorageService.createUser(username, password, currencyType, permissions);
        if ("users".equals(permissions)) {
            getExternalContext().redirect(getExternalContext().getRequestContextPath()
                + "/index.xhtml");
        } else {
            getExternalContext().redirect(getExternalContext().getRequestContextPath()
                + "/admins/adminhome.xhtml");
        }
    }
}
