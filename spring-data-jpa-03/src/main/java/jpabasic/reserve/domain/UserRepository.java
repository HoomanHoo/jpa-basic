package jpabasic.reserve.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, String> {
    Optional<User> findById(String email);

    User save(User user);

    void delete(User user);

    List<User> findByNameLike(String keyword);

    List<User> findByNameLikeOrderByNameDesc(String keyword);

    List<User> findByNameLikeOrderByNameAscEmailDesc(String keyword);

    List<User> findByNameLike(String keyword, Sort sort);

    List<User> findByNameLike(String keyword, Pageable pageable);

    Page<User> findByEmailLike(String keyword, Pageable pageable);

    @Query("select u from User u where u.createDate > :since order by u.createDate desc")
    List<User> findRecentUsers(@Param("since") LocalDateTime since);

    @Query("select u from User u where u.createDate > :since")
    List<User> findRecentUsers(@Param("since") LocalDateTime since, Sort sort);

    @Query("select u from User u where u.createDate > :since")
    Page<User> findRecentUsers(@Param("since") LocalDateTime since, Pageable pageable);
}
/*
 * 정렬
 * - findByLikeOrderBy프로퍼티명Asc/Desc()
 * - findByLikeOrderBy프로퍼티명Asc프로퍼티명Desc()
 * - Sort 타입을 사용할 수 있다
 * -> ex)
 * Sort sort1 = Sort.by(Sort.Order.asc("name"));
 * List<User> users1 = userRepository.findByNameLike("이름%", sort1);
 * Sort.by()에는 여러가지 Sort.Order.xxx를 지정할 수 있다
 * 
 * 페이징 처리
 * Pageable 객체를 findxxx() 메서드에 매개변수로 추가해준다
 * Pageable은 인터페이스이다
 * PageRequest가 구현체 중 하나이다
 * PageRequest.ofSize(한 페이지에 조회할 row 갯수).withPage(읽어올 페이지 번호 (0부터 시작))으로
 * Pageable 객체를 만들어준다
 * withPage() 메서드 뒤에 withSort(Sort 객체)를 이용하여 정렬 방식도 지정해줄 수 있다
 * 
 * Pageable 객체를 생성하고 이를 Page 객체로 만들 수 있다
 * Page 타입 - 페이징 처리에 필요한 값을 함께 제공함
 * - 전체 페이지 갯수, 전체 row 갯수 등
 * 
 * Pageable을 매개변수로 받는 메서드의 리턴 타입을 Page로 지정하면 됨
 * Page.getTotalElements() - 조건에 해당하는 전체 row 갯수
 * Page.getTotalPages() - 전체 페이지 갯수
 * Page.getContent() - 현재 페이지 row 목록
 * Page.getSize() - 한 페이지에 표시할 row 갯수
 * Page.getNumber() - 현재 페이지 넘버
 * Page.getNumberOfElements() - row의 갯수
 * 
 * @Query
 * 메서드 명명 규칙이 아닌 JPQL을 직접 사용함
 * -> 메서드 이름이 간결해진다
 * Sort, Pageable 도 매개변수로 넣어 사용할 수 있다
 * JPQL 매개변수 값의 경우 (@Param(매개변수명) 변수의 값) 으로 사용한다
 * 
 * 메서드 명명 규칙으로 정렬을 지정할 수 있지만 정렬 기준이 많은 경우 Sort 객체를 사용하는 것이 좋음
 * findTop(), findFirst(), findTopN(), findFirtstN() 으로 상위 N개의 row만 구할 수도 있다
 * 
 */
