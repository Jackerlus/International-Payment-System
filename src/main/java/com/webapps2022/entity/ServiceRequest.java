package com.webapps2022.entity;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "ServiceRequest")
@NamedQueries({
    @NamedQuery(name = "ServiceRequest.all", query = "select request from ServiceRequest request order by request.id"),
    @NamedQuery(name = "ServiceRequest.bySource", query = "select req from ServiceRequest req where req.source = :source and req.paid = false"),
    @NamedQuery(name = "ServiceRequest.byTarget", query = "select req from ServiceRequest req where req.target = :target and req.paid = false"),
})
public class ServiceRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String source;

    @NotNull
    private String target;

    @NotNull
    private double amount;

    @NotNull
    private String sourceCurrency;

    @NotNull
    private String targetCurrency;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    @NotNull
    private boolean paid;

    public ServiceRequest() {
        this.dateTime = new java.util.Date();
        this.paid = false;
    }

    public ServiceRequest(String source, String target, double amount,
    String sourceCurrency, String targetCurrency) {
        this.paid = false;
        this.dateTime = new java.util.Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }


}
