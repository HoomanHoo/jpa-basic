package jpabasic.reserve.domain;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, String> {
    Optional<User> findById(String email);

    void save(User user);

    void delete(User user);
}
/*
 * interface 작성시 Repository 상속을 하게 되며,
 * Repository<엔티티 타입, 엔티티의 식별자(id 필드)의 타입> 을 지정하여 상속한다
 * Repository 인터페이스를 상속한 해당 인터페이스를 Bean으로 등록하여 사용한다
 * 
 * save(), findById(), delete() 등의 규칙에 맞게 메서드를 정의해주면 된다
 */