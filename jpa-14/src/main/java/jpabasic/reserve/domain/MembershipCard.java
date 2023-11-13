package jpabasic.reserve.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "membership_card")
public class MembershipCard {
    @Id
    @Column(name = "card_no")
    private String cardNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User owner;
    /*
     * 참조키를 이용한 방식
     * 단방향으로 참조할 필드에 OneToOne 어노테이션 추가
     * JoinColumn에는 참조할 컬럼을 설정해줌
     * -> 1대1 단방향 매핑 설정
     * 테이블에는 user_email 컬럼(FK)가 생긴다
     * OneToOne 의 기본 설정은 EAGER(즉시 로딩)이다
     */

    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    private boolean enabled;

    protected MembershipCard() {
    }

    public MembershipCard(String cardNumber, User owner, LocalDate expiryDate) {
        this.cardNumber = cardNumber;
        this.owner = owner;
        this.expiryDate = expiryDate;
        this.enabled = false;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public User getOwner() {
        return owner;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
/*
 * 엔티티와 엔티티간의 연결
 * - 엔티티가 다른 엔티티를 필드 / 프로퍼티로 참조하는 것을 연관이라고 함
 * - 1-1 / N-1 / 1-N / M-N 연관이 존재
 * - 단방향, 양방향 연관이 존재
 * - 1-1 연관은 참조키 방식과 키 공유 방식 두 종류가 존재한다
 * 
 * - Value(Embeddable 타입) 매핑으로 사용하는 경우도 많음
 * - select 는 쿼리를 직접 사용하는 경우도 많음
 * 
 * 연관 매핑은 진짜 필요할 때만 사용할 것
 * - 연관된 객체 탐색이 쉽다는 이유로 사용하지 말 것
 * - 조회 기능은 별도 모델을 만들어 구현(CQRS - Command and Query Responsibility Segregation)
 * - - 데이터 저장소로부터의 읽기와 업데이트 작업을 분리하는 패턴
 * - Embeddable 매핑이 가능하다면 Embeddable 매핑을 사용
 */