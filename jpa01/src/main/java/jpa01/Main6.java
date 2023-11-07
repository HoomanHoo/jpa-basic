package jpa01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpa01.domain.Address;
import jpa01.domain.Grade;
import jpa01.domain.Hotel;

public class Main6 {
    private static Logger logger = LoggerFactory.getLogger(Main6.class);

    public static void main(String[] args) {
        ENF.init();
        EntityManager em = ENF.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        // Address address = new Address("주소1", "주소2", "12345");
        // Hotel hotel = new Hotel("H00", "HN", 2022, Grade.S7, address);
        // em.persist(hotel);
        // 1:50:52.362 [main] DEBUG org.hibernate.SQL - insert into hotel_info (addr1,
        // addr2, zipcode, created, grade, modified, nm, year, hotel_id) values (?, ?,
        // ?, ?, ?, ?, ?, ?, ?)
        // DB나 Entity Class에서 제약조건을 걸어주지 않으면 Null 값도 insert 가 된다

        Hotel hotel = em.find(Hotel.class, "H00");
        logger.info("주소: {}", hotel.getAddress());
        // 01:56:50.251 [main] DEBUG org.hibernate.SQL - select
        // h1_0.hotel_id,h1_0.addr1,h1_0.addr2,h1_0.zipcode,h1_0.created,h1_0.grade,h1_0.modified,h1_0.nm,h1_0.year
        // from hotel_info h1_0 where h1_0.hotel_id=?
        // 01:56:50.306 [main] INFO jpa01.Main6 - 주소: Address{address1='주소1',
        // address2='주소2', zipcode='12345'}
        //

        tx.commit();

        em.close();
        ENF.close();

    }
}
