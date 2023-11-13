package jpabasic.survey.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "survey_question")
public class Question {
    @Id
    private String id;
    private String title;

    protected Question() {
    }

    public Question(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
/*
 * 1-N 단방향 매핑
 * - 컬렉션을 사용한 매핑
 * - Set
 * - List
 * - Map
 * 
 */
