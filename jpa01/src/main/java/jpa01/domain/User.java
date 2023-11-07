package jpa01.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // DB Table과 매핑 대상임을 나타냄
@Table(name = "user") // 'user' Table과 매핑 대상임을 명시
public class User {
    @Id // 식별자 - Primary Key에 대응
    private String email; // email 컬럼과 매핑
    private String name; // name 컬럼과 매핑
    @Column(name = "create_date") // "create_date 컬럼과 매핑"
    private LocalDateTime createDate;

    protected User() {
    }

    public User(String email, String name, LocalDateTime createDate) {
        this.email = email;
        this.name = name;
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void chageName(String name) {
        this.name = name;
    }

    /*
     * @Table - 생략 시 클래스 이름과 동일한 Table 이름에 매핑된다
     * 속성
     * name = 테이블 이름
     * catalog = 카탈로그 이름 (MySQL DB 이름)
     * schma = 스키마 이름(Oracle schema 이름)
     * 
     * @Enumerated - Enum 자료형을 매핑할 수 있음
     * 속성
     * EnumType.STRING = Enum 타입의 값 이름을 저장함
     * EnumType.ORDINARY = (Default값) Enum 타입 값의 순서를 저장함 - 값의 순서는 바뀔 수 있기 때문에 DB에
     * 저장하기는 난감함
     * 
     * Entity Class의 제약 조건
     * 
     * @Entity 어노테이션이 존재해야함
     * 
     * @Id 어노테이션이 필드에 존재해야함(Primary Key 매핑 위해서)
     * 기본 생성자 필요(매개변수 X)
     * 기본 생성자는 public, protected 둘 중 하나여야함
     * - Hibernate의 경우 private여도 처리해주나 스펙상 private는 안된다
     * 최상위 클래스여야함 - 중첩/익명 클래스여서는 안됨
     * final 클래스/필드여서는 안됨
     * 
     * 두개의 접근 타입을 가진다
     * 필드 접근 - DB에 매핑할 때 필드 값을 사용해서 매핑
     * 프로퍼티 접근 - DB에 매핑할 때 getter/ setter 메서드를 사용해서 매핑
     * 
     * @Id 어노테이션을 필드에 붙이면 필드 접근 타입이 되고
     * 
     * @Id 어노테이션을 getter 메서드에 붙이면 프로퍼디 접근 방식이 된다
     * 또한 @Access(AccessType.PROPERTY / AccessType.FIELD)
     * 를 통해 명시적으로 지정할 수도 있다.
     * 필드 접근 타입이 되면 불필요한 setter 메서드를 만들 필요가 없다.
     * 
     * 식별자 생성 방식
     * 직접 할당 방식
     * - @Id 어노테이션을 붙인 필드에 직접 값 설정
     * - 사용자가 입력한 값, 규칙에 따라 생성한 값 등을 직접 할당 방식으로 생성한다.
     * - ex) 이메일, 주문번호
     * - 저장하기 전에 생성자 할당, 보통 인스턴스 생성 시점에 전달된다
     * - insert 쿼리는 persist() 호출 시점에서 실행되지 않는다
     * 
     * 식별 컬럼 방식
     * - DB의 식별 컬럼(PK)에 매핑
     * - ex) MySQL의 자동 증가 컬럼
     * - DB가 식별자를 생성하므로 객체 생성시에 식별 값을 설정하지 않음
     * - 설정 방식은 @GeneratedValue(strategy = GenerationType.IDENTITY) 어노테이션을 붙인다
     * - insert 쿼리가 실행되어야 식별자를 알 수가 있음
     * - EntityManager.persist() 호출 시점에 insert 쿼리가 실행된다(식별자 값을 알기 위해)
     * - persist() 실행할 때 객체에 식별자 값이 할당된다
     * 
     * 시퀀스 사용 방식
     * - JPA가 식별자 생성을 처리함 -> 객체 생성시에 식별값을 설정하지 않음
     * - @SequenceGenerator 로 시퀀스 생성기를 설정한다
     * - @GeneratedValue의 generator로 시퀀스 생성기를 지정한다
     * - EntityManager.persist() 호출 시점에 시퀀스를 사용한다
     * - persist() 실행할 때 객체에 식별자 값이 할당됨
     * - insert 쿼리는 persist() 호출 시점에서 실행되지 않는다
     * - ex)
     * - @SequenceGenerator(name = "시퀀스 생성기 이름", sequenceName = "실제 시퀀스 이름", scheme
     * = "사용 스키마 이름", allocationSize = 1)
     * - allocationSize = 1 이 아니면 식별자가 중복 생성되는 문제가 발생함
     * - @GeneratedValue(generator = "시퀀스 생성기 이름")
     * 
     * 테이블 사용 방식 (잘 사용 안함)
     * - 테이블을 시퀀스처럼 사용
     * - 테이블에 엔티티를 위한 키를 보관함
     * - 해당 테이블을 이용해서 다음 식별자 생성
     * - @TableGenerator로 테이블 생성기 설정
     * - @GeneratedValue의 generator로 테이블 생성기 지정
     * - EntityManager.persist() 호출 시점에 테이블 사용
     * - persist() 실행할 때 테이블을 이용해서 식별자를 구하고 이를 엔티티에 할당
     * - insert 쿼리는 persist() 호출 시점에서 실행되지 않는다
     * - 사용하기 위해서는 테이블 2개가 필요하다 (식별자 보관 테이블, 엔티티 테이블)
     * - 식별자 보관 테이블의 경우 엔티티 이름 컬럼(PK)과 식별자 보관 컬럼이 필요하다
     * - persist() 실행 시 식별자 보관 테이블의 식별자 보관 컬럼에 update 쿼리가 실행된다
     * 
     * - ex)
     * - @TableGenerator(name = "테이블 시퀀스 생성자 이름", table = "식별자 보관 테이블",
     * pkColumnName = "엔티티 이름 컬럼", pkColumnValue = "엔티티 이름",
     * valueColumnName = "식별자 보관 컬럼 이름", initialValue =
     * "초기값 - 1", allocationSize = 1)
     * - allocationSize가 1이 아니면 다중 로드 상황에서 식별자가 중복되는 현상 발생
     * - @GeneratedValue(generator = "테이블 시퀀스 생성자 이름")
     */

}
