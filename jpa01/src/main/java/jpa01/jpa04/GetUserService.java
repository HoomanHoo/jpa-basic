package jpa01.jpa04;

import jakarta.persistence.EntityManager;
import jpa01.ENF;
import jpa01.domain.User;

public class GetUserService {
    public User getUser(String email) {
        EntityManager em = ENF.createEntityManager();
        try {
            User user = em.find(User.class, email); // find(EntityClass, @Id 어노테이션 붙은 필드값)
            if (user == null) { // find()메서드는 데이터 존재하지 않으면 null 반환
                throw new NoUserException(); // Entity Type, @Id Type이 맞아야 함 - 일치하지 않을 경우 Exception 발생
            }
            return user;
        } finally {
            em.close();
        }
    }
}
