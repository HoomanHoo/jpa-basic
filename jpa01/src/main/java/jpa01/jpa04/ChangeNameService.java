package jpa01.jpa04;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpa01.ENF;
import jpa01.domain.User;

public class ChangeNameService {
    public void changeName(String email, String newName) {
        EntityManager em = ENF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User user = em.find(User.class, email);
            if (user == null) {
                throw new NoUserException();
            }
            user.chageName(newName);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
