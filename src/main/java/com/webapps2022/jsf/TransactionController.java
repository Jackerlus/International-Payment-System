/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.jsf;

import com.webapps2022.ejb.TransactionStorageService;
import com.webapps2022.entity.ServiceTransaction;
import com.webapps2022.entity.ServiceUser;
import java.io.IOException;
import java.io.Serializable;
import java.net.ProtocolException;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jacke
 */

@RequestScoped
@Named
@RolesAllowed({"users", "admins"})
public class TransactionController implements Serializable {
    String sourceUsername; 
    String targetUsername;   
    String sourceCurrencyType;
    String targetCurrencyType;
    double currencyAmount;
    Date dateTime;

    private ServiceTransaction transaction = new ServiceTransaction();

    @Inject
    private TransactionStorageService transactionStorageService;

    private List<ServiceTransaction> allTransactions;

    private FacesContext facesContext = FacesContext.getCurrentInstance();

    @PostConstruct
    public void initialize(){
        this.allTransactions = transactionStorageService.getAllTransactions();
    }

    public TransactionController() {
    }

    public ExternalContext getExternalContext() {
        return facesContext.getExternalContext();
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

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public String getSourceCurrencyType() {
        return sourceCurrencyType;
    }

    public void setSourceCurrencyType(String sourceCurrencyType) {
        this.sourceCurrencyType = sourceCurrencyType;
    }

    public String getTargetCurrencyType() {
        return targetCurrencyType;
    }

    public void setTargetCurrencyType(String targetCurrencyType) {
        this.targetCurrencyType = targetCurrencyType;
    }

    public TransactionStorageService getTransactionStorageService() {
        return transactionStorageService;
    }

    public void setTransactionStorageService(TransactionStorageService transactionStorageService) {
        this.transactionStorageService = transactionStorageService;
    }

    @RolesAllowed({"admins"})
    public List<ServiceTransaction> getAllTransactions() {
        return allTransactions;
    }

    @RolesAllowed({"admins"})
    public void setAllTransactions(List<ServiceTransaction> allTransactions) {
        this.allTransactions = allTransactions;
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

    public String getCurrentUsername() {
        String username = "";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        if (externalContext.getUserPrincipal() != null) {
            username = externalContext.getUserPrincipal().getName(); // null
        }
        return username;
    }

    public List<ServiceTransaction> getTransactionsByUser(String username) {
        System.out.println(username);
        return transactionStorageService.getUserTransactions(username);
    }

    public ServiceTransaction getTransaction() {
        if (transaction == null) return new ServiceTransaction();
        else return transaction;
    }

    public void createTransaction(String user) throws IOException {
        this.sourceUsername = user;

        this.sourceCurrencyType = transactionStorageService.getUser(user)
            .getCurrencyType();

        this.targetCurrencyType = transactionStorageService.getUser(this.targetUsername)
            .getCurrencyType();

        boolean transactionResult = transactionStorageService.updateBalance(this.sourceUsername,
            this.targetUsername, currencyAmount);

        if (transactionResult == true) {
            getExternalContext().redirect(getExternalContext().getRequestContextPath()
                + "/users/transactionsuccess.xhtml");

            transactionStorageService.createTransaction(sourceUsername, targetUsername,
                sourceCurrencyType, targetCurrencyType, currencyAmount);
        } else {
            getExternalContext().redirect(getExternalContext().getRequestContextPath()
                + "/users/transactionfailure.xhtml");
        }
    }

    public void createRequestTransaction(int requestId, String sourceUser, String targetUser, String sourceCurrency, String targetCurrency, double amount) throws IOException {
        this.sourceUsername = sourceUser;
        this.targetUsername = targetUser;
        this.sourceCurrencyType = sourceCurrency;
        this.targetCurrencyType = targetCurrency;
        this.currencyAmount = amount;

        boolean transactionResult = transactionStorageService.updateBalance(this.sourceUsername,
            this.targetUsername, this.currencyAmount);

        if (transactionResult == true) {
            getExternalContext().redirect(getExternalContext().getRequestContextPath()
                + "/users/transactionsuccess.xhtml");

            transactionStorageService.createTransaction(this.sourceUsername, this.targetUsername,
                this.sourceCurrencyType, this.targetCurrencyType, this.currencyAmount);
        } else {
            getExternalContext().redirect(getExternalContext().getRequestContextPath()
                + "/users/transactionfailure.xhtml");
        }
        transactionStorageService.markAsPaid(requestId);
    }
    public double callREST(String currency1, String currency2, double amount) throws ProtocolException, IOException {
        return transactionStorageService.callREST(currency1, currency2, amount);
    }
}
