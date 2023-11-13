package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jpabasic.jpa.EMF;
import jpabasic.reserve.domain.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MainJpql {
    private static Logger logger = LoggerFactory.getLogger(MainJpql.class);

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
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Review> query = em.createQuery(
                    "select r from Review r where r.hotelId = :hotelId order by r.id desc",
                    Review.class);
            query.setParameter("hotelId", "H-001");
            query.setFirstResult(8); // 0부터 시작
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
     * JPQL - JPA Query Language
     * SQL 쿼리와 유사함
     * 테이블 대신 엔티티 이름과 속성을 사용함
     * JPQL을 이용한 쿼리 생성
     * TypedQuery<T> query = EntityManager.createQuery(String jpql, Class<T>Result
     * Class)
     * -> 쿼리를 생성하고 TypedQuery의 메서드를 이용하여 원하는 작업을 수행한다
     * where, and, or, 괄호 등를 이용하여 검색 조건을 지정할 수 있다.
     * 파라미터는 이름 기반과 인덱스 기반이 있다
     * - 이름 기반 파라미터는 setParameter() 메서드에 ("쿼리에서 사용한 이름", 파라미터 값) 매개변수가 필요하며
     * - 인덱스 기반 파라미터는 setParameter() 메서드에 (인덱스(0부터 시작함), 파라미터 값) 매개변수가 필요하다
     * 정렬 순서는 order by 키워드를 사용한다 (SQL과 동일)
     * 비교연산자 역시 SQL에서 사용하는 비교 연산자들을 동일하게 사용할 수 있다
     * 페이징 처리는 setFirstResult(), setMaxResult() 메서드를 사용한다
     * setFirstResult()는 시작 행을 매개변수로 넣어주어야 하며 0부터 시작한다
     * setMaxResult()는 최대 결과 개수를 매개변수로 넣어주어야 한다
     * 이에 따라 실행되는 쿼리는 DB의 종류에 따라 변경된다
     * 
     * 이외에도 join, 집합함수(count, max, min, avg, sum), groupby, having,
     * 컬렉션 관련 비교 키워드, JPQL 함수(concat, substring, trim, abs, sqrt/컬렉션 함수 size,
     * index)가 사용 가능하다
     * 
     * 여러 테이블 조인(레거시 테이블 조인), DBMS에 특화된 쿼리, 서브 쿼리, 통계, 대량의 데이터 조회/처리의 경우 일반 쿼리를 사용하는
     * 것을 고려
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
