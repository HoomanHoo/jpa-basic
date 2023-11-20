package jpabasic.reserve.domain;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, String> {
    Optional<User> findById(String email);

    User save(User user);

    void delete(User user);

    void deleteById(String id);

    List<User> findByNameLike(String keyworkd);
}
/*
 * 메서드의 리턴 타입을 지정해줄 수 있다
 * (Entity / Optional)
 * Entity 타입으로 리턴할 경우 찾는 값이 없을 때 null을 리턴한다
 * Optional 타입으로 리턴할 경우 찾는 값이 없을 때 빈 Optional 객체를 리턴한다
 * 
 * delete 메서드의 경우 void delete(T entity) / void deleteById(Id type id) 이 존재한다
 * deleteById()의 경우 내부적으로 findById()를 통해 엔티티를 조회한 뒤 delete()를 한다
 * - delete() / deleteById() 둘 다 삭제할 대상이 존재하지 않으면 익셉션을 발생시킨다
 * 
 * save() 메서드는 Entity를 매개변수로 받으며, 리턴 타입은 void, Entity 타입 둘 중 하나를 선택할 수 있다
 * - select 쿼리를 실행하고 그 뒤에 insert 쿼리가 실행된다
 * - save() 메서드는 새 엔티티에 대하여 persist()를 실행하며 새 엔티티가 아니면 merge()를 실행한다
 * -- 새 엔티티인지 아닌지 판단하는 기준
 * -> Persistable을 구현한 엔티티 -> .isNew() 로 판단
 * -> @Version 속성이 있는 경우
 * --> 버전 값이 null이면 새 엔티티로 판단
 * -> 식별자가 참조 타입인 경우
 * --> 식별자가 null이면 새 엔티티로 판단
 * -> 식별자가 숫자 타입일 경우
 * --> 식별자가 0이면 새 엔티티로 판단함
 * 
 * -> persist()해야함에도 불구하고 merge()가 실행되는 경우
 * Persistable을 구현한다
 * Persistable<Type> 은 인터페이스이다
 * 
 * 특정 조건으로 select 하기
 * -> findBy프로퍼티(프로퍼티 값)
 * -> ex) List<User> findByName(String name)
 * 조건 비교
 * -> findByNameLike(String name)
 * Like 이외에도 After, Between, LessThen, IsNull, Containing, In 등을 메서드명으로 조합하여 사용할
 * 수 있다 -> 스프링 레퍼런스 문서에 나와있음
 * findAll() -> 전체 탐색
 * 
 * findBy 형태의 메서드를 남용하지 말 것 -> 메서드 이름이 매우 길어짐
 * -> 다양한 조건의 탐색을 할 때 복잡도 상승
 * -> 검색 조건이 단순하지 않은 경우 @Query로 JPQL을 사용하거나 SQL, 스펙, QueryDSL등을 사용할 수 있음
 */
