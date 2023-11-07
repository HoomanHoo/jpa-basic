package jpa01.jpa04;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpa01.ENF;
import jpa01.domain.User;

public class RemoveUserService {
    public void removeUser(String email) {
        EntityManager em = ENF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User user = em.find(User.class, email);
            if (user == null) {
                throw new NoUserException();
            }
            em.remove(user); // commit 전에 다른 프로세스가 해당 데이터를 DB에서 삭제하면 Exception 발생 (삭제 대상이 존재하는지 아닌지 판단 가능함)
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

}
