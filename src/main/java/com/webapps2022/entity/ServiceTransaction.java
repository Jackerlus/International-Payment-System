/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.entity;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jacke
 */

@Entity
@Table(name = "ServiceTransaction")
@NamedQueries({
    @NamedQuery(name = "ServiceTransaction.all", query = "select trans from ServiceTransaction trans order by trans.id"),
    @NamedQuery(name = "ServiceTransaction.byUsername", query = "select trans from ServiceTransaction trans where trans.sourceUsername = :sourceUsername or trans.targetUsername =:targetUsername")
})
public class ServiceTransaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // GenerationType: TABLE, SEQUENCE, IDENTITY or AUTO
    private int id;

    @NotNull
    private String sourceUsername;

    @NotNull
    private String targetUsername;

    @NotNull
    private String sourceCurrency;

    @NotNull
    private String targetCurrency;

    @NotNull
    private double currencyAmount;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    public ServiceTransaction() {}

    public ServiceTransaction(String sourceUsername, String targetUsername,
    String sourceCurrency, String targetCurrency, double currencyAmount) {

        this.sourceUsername = sourceUsername;
        this.targetUsername = targetUsername;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.currencyAmount = currencyAmount;
        this.dateTime = new java.util.Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourceUsername() {
        return sourceUsername;
    }

    public void setSourceUsername(String sourceUsername) {
        this.sourceUsername = sourceUsername;
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUserId(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getCurrencyAmount() {
        return currencyAmount;
    }

    public void setCurrencyAmount(double currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}


