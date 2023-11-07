package jpa01.jpa04;

import java.time.LocalDateTime;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpa01.ENF;
import jpa01.domain.User;

public class NewUserService {
    public void asveNewUser(User user) {
        EntityManager em = ENF.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
