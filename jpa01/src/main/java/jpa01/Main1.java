package jpa01;

import java.time.LocalDateTime;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpa01.domain.User;

public class Main1 {
    // public static void main(String[] args) {
    // EntityManagerFactory emf =
    // Persistence.createEntityManagerFactory("jpabegin"); // jpabegin이라 명시한 영속성 관리
    // 단위를 사용
    // persistence.xml에 정의한 영속 단위 기준으로 초기화 함 - 영속 단위를 매개변수로 하여 EntityManagerFactory
    // 인스턴스를 생성함
    // EntityManagerFactory는 connection pool 등의 DB 연동에 필요한 자원을 생성하게 됨. Application
    // 실행 시 단 한번만 생성함
    // EntityManager em = emf.createEntityManager(); // EntityManagerFactory에서
    // EntityManager 객체를 생성
    // EntityManager로 DB 조작
    // EntityTransaction tx = em.getTransaction(); // EntityManager 에서 Transaction 을
    // 생성
    // transaction이 필요할 때 EntityTransaction 인스턴스를 가져와 사용

    // insert
    // try {
    // tx.begin(); // transaction 시작
    // User user = new User("user@user1.com", "user", LocalDateTime.now());
    // em.persist(user); // insert
    // System.out.println("persist 실행");
    // tx.commit(); // transaction commit
    // System.out.println("trasaction commit");
    // } catch (Exception e) {
    // e.printStackTrace();
    // tx.rollback(); // trasaction rollback
    // } finally {
    // em.close();
    // }
    // emf.close(); // EntityManagerFactory를 종료하며 사용한 자원을 반납한다.

    /*
     * persist 실행
     * 22:16:41.763 [main] DEBUG
     * org.hibernate.engine.transaction.internal.TransactionImpl - committing
     * 22:16:41.765 [main] DEBUG
     * org.hibernate.event.internal.AbstractFlushingEventListener - Processing
     * flush-time cascades
     * 22:16:41.767 [main] DEBUG
     * org.hibernate.event.internal.AbstractFlushingEventListener - Dirty checking
     * collections
     * 22:16:41.773 [main] DEBUG
     * org.hibernate.event.internal.AbstractFlushingEventListener - Flushed: 1
     * insertions, 0 updates, 0 deletions to 1 objects
     * 22:16:41.773 [main] DEBUG
     * org.hibernate.event.internal.AbstractFlushingEventListener - Flushed: 0
     * (re)creations, 0 updates, 0 removals to 0 collections
     * 22:16:41.774 [main] DEBUG org.hibernate.internal.util.EntityPrinter - Listing
     * entities:
     * 22:16:41.775 [main] DEBUG org.hibernate.internal.util.EntityPrinter -
     * jpa01.domain.User{name=user, email=user@user1.com,
     * createDate=2023-11-06T22:16:41.738012700}
     * 22:16:41.787 [main] DEBUG org.hibernate.SQL - insert into user (create_date,
     * name, email) values (?, ?, ?) -- commit 시점에 insert 쿼리를 실행함 / Id 컬럼 생성 방식에 따라
     * persist 호출 시에 insert 쿼리가 실행되기도 함
     * 22:16:41.817 [main] DEBUG
     * org.hibernate.resource.jdbc.internal.LogicalConnectionManagedImpl -
     * Initiating JDBC connection release from afterTransaction
     * 22:16:41.817 [main] DEBUG
     * org.hibernate.resource.jdbc.internal.LogicalConnectionManagedImpl -
     * Initiating JDBC connection release from afterTransaction
     * trasaction commit
     */

    // select
    // try {
    // tx.begin();
    // User user = em.find(User.class, "user@user1.com");
    // if (user == null) {
    // System.out.println("User 없음");
    // } else {
    // System.out.printf("User 있음: email=%s, name=%s, createDate=%s\n",
    // user.getEmail(), user.getName(),
    // user.getCreateDate());
    // }
    // tx.commit();
    // } catch (Exception e) {
    // e.printStackTrace();
    // tx.rollback();
    // } finally {
    // em.close();
    // }
    // emf.close();

    // update
    // try {
    // tx.begin();
    // User user = em.find(User.class, "user@user.com");
    // if (user == null) {
    // System.out.println("User 없음");
    // } else {
    // String newName = "이름" + (System.currentTimeMillis() % 100);
    // user.chageName(newName); // Entity 객체의 값을 변경
    // System.out.println("user chageName 호출");
    // }
    // tx.commit(); // commit 시에 가져온 Entity 객체 값에 변경이 있으면 update 쿼리를 실행한다.
    // System.out.println("trasaction commit");
    // } catch (Exception e) {
    // e.printStackTrace();
    // tx.rollback();
    // } finally {
    // em.close();
    // }
    // emf.close();
    /*
     * user chageName 호출
     * 22:21:07.957 [main] DEBUG
     * org.hibernate.engine.transaction.internal.TransactionImpl - committing
     * 22:21:07.958 [main] DEBUG
     * org.hibernate.event.internal.AbstractFlushingEventListener - Processing
     * flush-time cascades
     * 22:21:07.965 [main] DEBUG
     * org.hibernate.event.internal.AbstractFlushingEventListener - Dirty checking
     * collections
     * 22:21:07.974 [main] DEBUG
     * org.hibernate.event.internal.AbstractFlushingEventListener - Flushed: 0
     * insertions, 1 updates, 0 deletions to 1 objects
     * 22:21:07.974 [main] DEBUG
     * org.hibernate.event.internal.AbstractFlushingEventListener - Flushed: 0
     * (re)creations, 0 updates, 0 removals to 0 collections
     * 22:21:07.975 [main] DEBUG org.hibernate.internal.util.EntityPrinter - Listing
     * entities:
     * 22:21:07.977 [main] DEBUG org.hibernate.internal.util.EntityPrinter -
     * jpa01.domain.User{name=이름52, email=user@user.com,
     * createDate=2023-11-06T21:40:09}
     * 22:21:07.979 [main] DEBUG org.hibernate.SQL - update user set create_date=?,
     * name=? where email=? -- commit 시점에 update 쿼리가 실행됨 /
     * commit 시점에 데이터가 변경된 엔티티를 감지하여 update를 함
     * 22:21:07.994 [main] DEBUG
     * org.hibernate.resource.jdbc.internal.LogicalConnectionManagedImpl -
     * Initiating JDBC connection release from afterTransaction
     * 22:21:07.995 [main] DEBUG
     * org.hibernate.resource.jdbc.internal.LogicalConnectionManagedImpl -
     * Initiating JDBC connection release from afterTransaction
     * trasaction commit
     */

    /*
     * 영속 컨텍스트 때문에 commit 시점에 쿼리가 수행됨
     * 영속 컨텍스트는 DB에서 읽어온 객체와 응용프로그램에서 저장한 객체를 저장하고 있는 일종의 메모리 공간
     * 영속 객체를 보관하고 있다가 응용프로그램의 객체 - 영속 컨텍스트에 보관중인 객체를 비교하여 commit 시점에 DB에 반영함
     * 영속 컨텍스트는 트랜잭션 범위 안에서 유지가 됨
     */
    // }
}