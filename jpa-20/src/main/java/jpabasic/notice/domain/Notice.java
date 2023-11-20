package jpabasic.notice.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "notice")
@DynamicUpdate
// AttributeConverter 의 모든 컬럼이 업데이트 대상이 되는 현상을 막기 위해 사용
// @DynamicUpdate는 변경된 컬럼만 update 쿼리에 포함되도록 한다
// @DynamicInsert는 null이 아닌 컬럼만 insert 쿼리에 포함된
// 기본 값을 사용할 수 있으며 null을 지정해줘야 할 경우 사용하면 안된다
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;
    private String title;
    private String content;
    @Column(name = "open_yn")
    @Convert(converter = BooleanYesNoConverter.class)
    private boolean opened;
    // Convert 된 값을 DB에 집어넣고자 할 때 해당 필드에 @Convert(converter = AttributeConverter를
    // 구현한 클래스.class)
    // 로 설정한 어노테이션을 붙여 사용한다
    // Convert된 값만 update 되는 것이 아니라 전체 컬럼이 update 대상이 된다
    @Column(name = "cat")
    private String categoryCode;
    @Formula("(select c.name from category c where c.cat_id = cat)")
    private String categoryName;
    // @Formula
    // SQL을 이용한 속성 매핑
    // SQL 실행 결과를 필드에 매핑하고자 할 때 사용한다
    // 주로 조회에서만 매핑 처리를 한다 (insert / update는 매핑 대상이 아님)
    // 하이버네이트 제공 기능 - JPA 기능이 아님
    // DB 함수 호출(프로시저등), 서브쿼리 결과를 매핑

    protected Notice() {
    }

    public Notice(String title, String content, boolean opened, String categoryCode) {
        this.title = title;
        this.content = content;
        this.opened = opened;
        this.categoryCode = categoryCode;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isOpened() {
        return opened;
    }

    public void open() {
        this.opened = true;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", opened=" + opened +
                ", categoryCode='" + categoryCode + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
