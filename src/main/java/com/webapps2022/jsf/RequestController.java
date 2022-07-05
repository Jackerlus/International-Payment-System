/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.jsf;

import com.webapps2022.ejb.RequestStorageService;
import com.webapps2022.entity.ServiceRequest;
import com.webapps2022.entity.ServiceTransaction;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jacke
 */

@RolesAllowed({"users", "admins"})
@RequestScoped
@Named
public class RequestController implements Serializable {
    private String sourceUser;
    private String targetUser;
    private double amount;
    private String sourceCurrency;
    private String targetCurrency;

    private ServiceRequest request = new ServiceRequest();

    @Inject
    private RequestStorageService requestStorageService;

    private List<ServiceRequest> allRequests;

    @PostConstruct
    public void initialize(){
        this.allRequests = requestStorageService.getAllRequests();
    }

    //Getters and Setters
    public String getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(String sourceUser) {
        this.sourceUser = sourceUser;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
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

    public ServiceRequest getRequest() {
        return request;
    }

    public void setRequest(ServiceRequest request) {
        this.request = request;
    }

    public RequestStorageService getRequestStorageService() {
        return requestStorageService;
    }

    public void setRequestStorageService(RequestStorageService requestStorageService) {
        this.requestStorageService = requestStorageService;
    }

    @RolesAllowed({"admins"})
    public List<ServiceRequest> getAllRequests() {
        return allRequests;
    }

    @RolesAllowed({"admins"})
    public void setAllRequests(List<ServiceRequest> allRequests) {
        this.allRequests = allRequests;
    }

    public List<ServiceRequest> getRequestsByTarget(String target) {
        return requestStorageService.getRequestsByTarget(target);
    }

    //Send to persistence interface
    public void composeRequest(String user) {
        setSourceUser(user);
        setSourceCurrency(requestStorageService.getUser(this.sourceUser).get().getCurrencyType());
        setTargetCurrency(requestStorageService.getUser(this.targetUser).get().getCurrencyType());
        request.setSource(this.sourceUser);
        request.setTarget(this.targetUser);
        request.setSourceCurrency(this.sourceCurrency);
        request.setTargetCurrency(this.targetCurrency);
        request.setAmount(this.amount);
        requestStorageService.createRequest(request);
    }
}
