package com.webapps2022.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jacke
 */

@Entity
@Table(name = "ServiceUser")
@NamedQueries({
    @NamedQuery(name = "ServiceUser.all", query = "select user from ServiceUser user order by user.id"),
    @NamedQuery(name = "ServiceUser.byUsername", query = "select user from ServiceUser user where user.username = :username")
})
public class ServiceUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // GenerationType: TABLE, SEQUENCE, IDENTITY or AUTO
    private Long id;

    @NotNull
    String username;

    @NotNull
    String password;

    @NotNull    
    String currencyType;

    @NotNull
    double currencyAmount;

    @NotNull
    String role;

    public ServiceUser() {
        this.currencyAmount = 1000.00;
    }

    public ServiceUser(String username, String password, String currencyType, String permissions) {
        this.username = username;
        this.password = password;
        this.currencyType = currencyType;
        this.currencyAmount = 1000.00;
        this.role = permissions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public double getCurrencyAmount() {
        return currencyAmount;
    }

    public void setCurrencyAmount(double currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ServiceUser other = (ServiceUser) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
