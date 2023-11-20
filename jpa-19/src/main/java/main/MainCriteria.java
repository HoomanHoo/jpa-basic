package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabasic.jpa.EMF;
import jpabasic.reserve.domain.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

public class MainCriteria {
    private static Logger logger = LoggerFactory.getLogger(MainCriteria.class);

    public static void main(String[] args) {
        EMF.init();
        initData();
        try {
            selectReviewOrderBy();
        } finally {
            EMF.close();
        }
    }

    private static void selectReviewOrderBy() {
        String hotelId = "H-001";
        int mark = 3;

        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            // EntityManager.getCriteriaBuilder() 메서드로 CriteriaBuilder 객체를 생성
            CriteriaQuery<Review> cq = cb.createQuery(Review.class);
            // 생성하는 쿼리는 CriteriaQuery<T> 타입을 가진다
            Root<Review> root = cq.from(Review.class);
            cq.select(root);
            Predicate p = cb.conjunction();
            // Criteria 사용 이유는 동적인 검색 조건을 지정할 수 있다
            // -> 상황에 맞는 쿼리를 조건문 등을 통해 구성할 수 있다
            // 타입에 안전한 코드를 작성할 수 있다
            if (hotelId != null) {
                p = cb.and(p, cb.equal(root.get("hotelId"), hotelId));
            }
            p = cb.and(p, cb.greaterThan(root.get("created"), LocalDateTime.now().minusDays(10)));
            if (mark >= 0) {
                p = cb.and(p, cb.ge(root.get("mark"), mark));
            }
            cq.where(p);
            cq.orderBy(
                    cb.asc(root.get("hotelId")),
                    cb.desc(root.get("id")));
            // 검색 조건을 메서드로 설정한다
            // get(Entity.get(필드명), 비교하고자 하는 값) 으로 검색 조건을 지정할 수 있음
            // 정렬은 orderBy(), asc, desc 등으로 설정할 수 있다
            // Entity.get()은 Object 타입으로 리턴하고자 하는데, 이를 타입 지정을 해주어 올바른 값을
            // 받을 수 있도록 설정해줄 수 있다.

            TypedQuery<Review> query = em.createQuery(cq);
            // 생성한 CriteriaQuery를 EntityManager.createQuery()의 매개변수로 지정
            query.setFirstResult(4); // 0부터 시작
            query.setMaxResults(4);
            List<Review> reviews = query.getResultList();

            reviews.forEach(r -> logger.info("Review: {}", r.getId()));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    /*
     * Criteria
     * 자바 코드로 쿼리를 구성하는 API
     * join, count, max, min, avg, sum, group by, having등 JPQL과 같은 기능을 지원
     * JPQL과 같이 여러 테이블 조인, DBMS 특화 쿼리, 서브쿼리, 통계, 대량 데이터 조회 처리의 경우
     * 일반 쿼리를 사용하는 것을 고려함
     * 
     * N+1 문제, fetch 조인에 대한 학습을 진행하여 엔티티간의 연관, JPA-Criteria를 조합하여 조회할 경우
     * 문제를 예방할 수 있도록 할 것
     */

    private static void initData() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/jpabegin?characterEncoding=utf8",
                "jpauser",
                "jpapass");
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("truncate table review");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(new Review(5, "H-001", "작성자1", "댓글1"));
            em.persist(new Review(5, "H-001", "작성자2", "댓글2"));
            em.persist(new Review(5, "H-001", "작성자3", "댓글3"));
            em.persist(new Review(5, "H-001", "작성자4", "댓글4"));
            em.persist(new Review(5, "H-001", "작성자5", "댓글5"));
            em.persist(new Review(5, "H-001", "작성자6", "댓글6"));
            em.persist(new Review(5, "H-001", "작성자7", "댓글7"));
            em.persist(new Review(5, "H-001", "작성자8", "댓글8"));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
