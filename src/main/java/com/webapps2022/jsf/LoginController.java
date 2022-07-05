package com.webapps2022.jsf;

/**
 *
 * @author jacke
 */
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;

@RequestScoped
@Named
public class LoginController implements Serializable {
    
    private String username;
    private String password;
    
    FacesContext facesContext;

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

    public void login() throws IOException {
        facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            System.out.println("Trying login with username " + this.username + " and password " + this.password);
            request.login(this.username, this.password);
        } catch (Exception e) {
            System.out.println("Login failed, exception encountered: " + e);
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/loginerror.xhtml");
        }
        System.out.println("Login succeeded.");
        System.out.println(request.getRequestURI());
        try {
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/users/home.xhtml");
        } catch (Exception e) {

        }
    }
    
    private ExternalContext getExternalContext(){
        return facesContext.getExternalContext();
    }

    public void logout() throws IOException {
        facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            request.logout();
            getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/index.xhtml");
        } catch (ServletException e) {
            
        }
    }

}
