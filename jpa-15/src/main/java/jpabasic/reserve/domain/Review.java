package jpabasic.reserve.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "sight_review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sight_id")
    private Sight sight;
    // ManyToOne 어노테이션이 있는 엔티티-테이블에 FK 컬럼이 생긴다
    // JoinColumn에 설정한 컬럼을 PK로 사용한다(PK를 적어줘야함)
    // 기본 FetchType은 EAGER이다

    private int grade;
    private String comment;

    protected Review() {
    }

    public Review(Sight sight, int grade, String comment) {
        this.sight = sight;
        this.grade = grade;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public Sight getSight() {
        return sight;
    }

    public int getGrade() {
        return grade;
    }

    public String getComment() {
        return comment;
    }
}
