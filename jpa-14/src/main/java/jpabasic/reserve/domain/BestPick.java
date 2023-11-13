package jpabasic.reserve.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "best_pick")
public class BestPick {
    @Id
    @Column(name = "user_email")
    private String email;
    // Id에 매핑되는 식별자

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "user_email")
    private User user;
    // 키 공유 매핑 방식
    // User 엔티티와 매핑하기 위한 필드
    // PrimaryKeyJoinColumn의 name 속성에 PK 필드의 컬럼명을 지정해주면 된다

    private String title;

    protected BestPick() {
    }

    public BestPick(User user, String title) {
        this.email = user.getEmail();
        this.user = user;
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }
}
