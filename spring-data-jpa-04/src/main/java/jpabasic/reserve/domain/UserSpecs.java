package jpabasic.reserve.domain;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class UserSpecs {
    public static Specification<User> nameLike(String value) {
        return (root, query, cb) -> cb.like(root.get("name"), "%" + value + "%");
    }

    public static Specification<User> createdAfter(LocalDateTime dt) {
        return (root, query, cb) -> cb.greaterThan(root.get("createDate"), dt);
    }
}
/*
 * Specification을 이용하여 구현 클래스를 매번 만들기 보다는
 * 람다식을 이용하여 Specification 객체를 생성하는 것을 추천
 */