package jpabasic.reserve.domain;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserNameSpecification implements Specification<User> {
    private final String value;

    public UserNameSpecification(String value) {
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.like(root.get("name"), "%" + value + "%");
    }
}
/*
 * Specification - 검색 조건을 생성하는 인터페이스
 * - 상속받아 구현하는 클래스가 필요하다
 * - Criteria를 이용하여 검색 조건을 생성한다
 * -findAll() 에 매개변수로 Specification<T> 객체를 전달해주면 된다
 * 
 * Specification의 or/and 메서드를 이용하여 검색 조건을 조합할 수 있다
 */
