package jpabasic.article.query;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.time.LocalDateTime;

@Subselect("""
        select a.article_id, a.title, w.name as writer, a.written_at
        from article a left join writer w on a.writer_id = w.id
        """)
// select 결과를 엔티티로 매핑함 - 수정 대상이 아니기 때문에 @Immutable 어노테이션과 같이 사용한다
// 엔티티처럼 사용하며 select 쿼리 실행시 @Subselect의 쿼리가 from 절 내부에 들어가게 됨

@Entity
@Immutable
// @Immutable 어노테이션은 해당 엔티티를 변경 추적 대상에서 제외하기 위해 사용한다
// - 변경 추적을 위한 메모리 사용에서 빠지기 때문에 메모리 사용량을 감소 시킬 수 있다
// - 조회 목적으로만 사용되는 엔티티 매핑에 사용
public class ArticleListView {
    @Id
    @Column(name = "article_id")
    private Long id;
    private String title;
    private String writer;
    @Column(name = "written_at")
    private LocalDateTime writtenAt;

    protected ArticleListView() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public LocalDateTime getWrittenAt() {
        return writtenAt;
    }

    @Override
    public String toString() {
        return "ArticleListView{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", writtenAt=" + writtenAt +
                '}';
    }
}
/*
 * 이외에도 상속 매핑,
 * 네이티브 쿼리,
 * 하이버네이트 어노테이션
 * (@CreationTimestamp
 * 
 * @UpdateTimestamp)
 * 어노테이션이 존재한다
 * 
 * 네이티브 쿼리를 엔티티 매핑 용도로도 사용할 수 있다
 * 
 * @CreationTimestamp - 엔티티 생성시 Timestamp를 자동으로 생성해줌
 * 
 * @UpdateTimestamp - 엔티티 변경 시 Timestamp를 자동으로 생성해줌
 */