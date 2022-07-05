package com.webapps2022.ejb;

import com.webapps2022.entity.ServiceRequest;
import com.webapps2022.entity.ServiceUser;
import com.webapps2022.entity.ServiceTransaction;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;

/**
 *
 * @author jacke
 */

@RolesAllowed({"users", "admins"})
@Stateless
public class TransactionStorageService {
    @PersistenceContext(unitName = "WebappsDBPU")
    private EntityManager em;

    @Transactional
    public ServiceTransaction createTransaction(String sourceUsername, String targetUsername,
    String sourceCurrencyType, String targetCurrencyType, double currencyAmount) {

        ServiceTransaction newTransaction = new ServiceTransaction(sourceUsername,
            targetUsername, sourceCurrencyType, targetCurrencyType, currencyAmount);

        try {
            em.persist(newTransaction);
            em.flush();
        } catch (ConstraintViolationException e) {
            System.out.println("Invalid data inserted into a column: " + e);
        }
        return newTransaction;

    }

    public Optional<ServiceTransaction> getTransaction(int id) {
        return em.createNamedQuery("ServiceTransaction.byId", ServiceTransaction.class)
                 .setParameter("id", id)
                 .getResultList()
                 .stream()
                 .findFirst();
    }

    public List<ServiceTransaction> getAllTransactions() {
        return em.createNamedQuery("ServiceTransaction.all", ServiceTransaction.class).getResultList();
    }

    public ServiceUser getUser(String username) {
        return em.createNamedQuery("ServiceUser.byUsername", ServiceUser.class)
                 .setParameter("username", username)
                 .getResultList()
                 .stream()
                 .findFirst()
                 .get();
    }

    public List<ServiceTransaction> getUserTransactions(String username) {
        return em.createNamedQuery("ServiceTransaction.byUsername", ServiceTransaction.class)
                .setParameter("sourceUsername", username)
                .setParameter("targetUsername", username)
                .getResultList();
    }

    @Transactional
    public boolean updateBalance(String sourceUsername, String targetUsername, double amount) throws IOException {
        System.out.println(sourceUsername + " " + targetUsername);
        ServiceUser sourceUser = getUser(sourceUsername);
        ServiceUser targetUser = getUser(targetUsername);
        double originalAmount = amount;
        double convertedAmount = callREST(sourceUser.getCurrencyType(), targetUser.getCurrencyType(), originalAmount);

        ServiceUser transactSourceUser = em.find(ServiceUser.class, sourceUser.getId());
        ServiceUser transactTargetUser = em.find(ServiceUser.class, targetUser.getId());
        if (convertedAmount == 0.0) {
            return false;
        }
        else if (transactSourceUser.getCurrencyAmount() >= originalAmount) {
            try {
                transactSourceUser.setCurrencyAmount(transactSourceUser.getCurrencyAmount() - originalAmount);
                transactTargetUser.setCurrencyAmount(transactTargetUser.getCurrencyAmount() + convertedAmount);
                em.persist(transactSourceUser);
                em.persist(transactTargetUser);
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } else {
            return false;
        }
    }

    @Transactional
    public void markAsPaid(int requestId) {
        ServiceRequest updatedRequest = em.find(ServiceRequest.class, requestId);
        updatedRequest.setPaid(true);
        em.persist(updatedRequest);
    }

    public double callREST(String currency1, String currency2, double amount) throws MalformedURLException, ProtocolException, IOException {
        String restUrl = "http://localhost:10000/Webapps2022/ConversionService/conversion/"
            + currency1 + "/" + currency2
            + "/" + amount;

        URL url = new URL(restUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = connection.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        StringBuffer response = new StringBuffer();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // print result
            System.out.println(restUrl);
            System.out.println(response.toString());
        double convertedAmount = Double.parseDouble(response.toString());
        return convertedAmount;
        } else {
            System.out.println("GET request failed.");
            System.out.println(restUrl);
            return 0.0;
        }
    }
}


