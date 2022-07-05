package com.webapps2022.jsf;

import java.io.Serializable;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped

public class NavigationController implements Serializable {
private static final long serialVersionUID = 1L;

    public String index() {
        return "index";
    }

    public String login() {
      return "login";
    }

    public String register() {
      return "register";
    }

    @RolesAllowed({"users", "admins"})
    public String request() {
      return "request";
    }

    @RolesAllowed({"users", "admins"})
    public String transfer() {
      return "transfer";
    }

    @RolesAllowed({"users", "admins"})
    public String home() {
      return "/users/home.xhtml?faces-redirect=true";
    }

    @RolesAllowed({"admins"})
    public String adminhome() {
      return "/admins/adminhome.xhtml?faces-redirect=true";
    }

    @RolesAllowed({"admins"})
    public String adminregister() {
      return "/admins/adminregister.xhtml?faces-redirect=true";
    }
}