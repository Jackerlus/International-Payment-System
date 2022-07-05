package com.webapps2022.ejb;

import com.webapps2022.entity.ServiceRequest;
import com.webapps2022.entity.ServiceUser;
import java.util.List;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author jacke
 */

@RolesAllowed({"users", "admins"})
@Stateless
public class RequestStorageService {
    @PersistenceContext(unitName = "WebappsDBPU")
    private EntityManager em;

    @Transactional
    public void createRequest(ServiceRequest request) {
        try {
            em.persist(request);
            em.flush();
        } catch (ConstraintViolationException e) {
            System.out.println("Invalid data inserted into a column.");
        }
    }

    public ServiceRequest getRequest(int id) {
        return em.createNamedQuery("ServiceRequest.byId", ServiceRequest.class)
                 .setParameter("id", id)
                 .getResultList()
                 .stream()
                 .findFirst().get();
    }

    public List<ServiceRequest> getRequestsBySource(String source) {
        return em.createNamedQuery("ServiceRequest.bySource", ServiceRequest.class)
                 .setParameter("source", source)
                 .getResultList();
    }

    public List<ServiceRequest> getRequestsByTarget(String target) {
        return em.createNamedQuery("ServiceRequest.byTarget", ServiceRequest.class)
                 .setParameter("target", target)
                 .getResultList();
    }

    public List<ServiceRequest> getAllRequests() {
        return em.createNamedQuery("ServiceRequest.all", ServiceRequest.class)
                 .getResultList();
    }

    public Optional<ServiceUser> getUser(String username) {
        return em.createNamedQuery("ServiceUser.byUsername", ServiceUser.class)
                 .setParameter("username", username)
                 .getResultList()
                 .stream()
                 .findFirst();
    }
}
