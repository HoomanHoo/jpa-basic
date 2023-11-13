package jpabasic.question.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {
    @Id
    private String id;
    private String text;

    @ElementCollection(fetch = FetchType.EAGER) // lazy eager 설정 가능하다
    @CollectionTable(name = "question_choice", // 연관 관계 테이블 지정
            joinColumns = @JoinColumn(name = "question_id") // FK 지정
    )
    @OrderColumn(name = "idx") // List의 index값을 저장할 컬럼 지정
    @Column(name = "text") // 값이 들어가는 컬럼 지정
    private List<String> choices = new ArrayList<>();
    // 엔티티를 삭제하면 컬렉션에 저장된 값도 같이 사라지게 된다

    protected Question() {
    }

    public Question(String id, String text, List<String> choices) {
        this.id = id;
        this.text = text;
        this.choices = choices;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
}
