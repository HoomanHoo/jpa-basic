package jpabasic.reserve.domain;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, String> {
        void save(User u);

        long countByNameLike(String keyword);

        long count(Specification<User> spec);

        @Query(value = "select * from user u where u.create_date >= date_sub(now(), interval 1 day)", nativeQuery = true)
        List<User> findRecentUsers();

        @Query(value = "select max(create_date) from user", nativeQuery = true)
        LocalDateTime selectLastCreateDate();

        Optional<User> findByName(String name);
}

/*
 * count()
 * count(Specification<T> spec)
 * countByNameLike(String keyword)
 * 
 * -> count 연산 메서드
 * - 조건을 By를 이용하여 지정할 수 있으며 Specification 객체를 이용해 지정할 수도 있다
 * - 리턴 타입은 Long 이다
 * 
 * @Query 네이티브 쿼리
 * - JPQL 대신 SQL 자체를 @Query 어노테이션에서 사용할 수도 있음
 * - nativeQuery 속성을 true 로 해줘야함
 * - 실행 결과를 Entity 클래스에 매핑하거나, 단일 객체, 변수에 매핑할 수 있음
 * - 식별자에서 시컨스를 구해서 id로 사용해야한다던가 하는 경우에 사용할 수 있음
 * 
 * find() 계열 메서드에서 리턴 타입을 List가 아닌 객체, Optional<T>로 설정 가능
 * 존재하면 해당 값을 리턴, 없으면 null 혹은 빈 Optional 객체를 반환
 * 갯수가 두개 이상이면 익셉션을 발생시킴
 * 
 * Repository Interface
 * - CrudRepository, PagingAndSortRepository등 Respository 인터페이스를 상속받는 하위 인터페이스가
 * 존재한다
 * Repository의 하위 인터페이스를 상속하면 관련 메서드를 모두 포함한다
 * 하지만 관련 메서드가 전부 포함되기 때문에 쓰지 않는 메서드까지 모두 포함이 된다
 */
