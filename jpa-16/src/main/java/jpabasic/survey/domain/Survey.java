package jpabasic.survey.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "survey")
public class Survey {
    @Id
    private String id;
    private String name;
    @OneToMany
    @JoinColumn(name = "survey_id")
    @OrderColumn(name = "order_no")
    private List<Question> questions = new ArrayList<>();
    /*
     * 가져오는 데이터에 순서가 존재해야할 경우에 List 자료형으로 연관 매핑을 진행한다
     * OrderColumn의 name 속성에는 List의 인덱스 값을 저장할 컬럼을 지정해준다
     * 지정한 컬럼은 Question 테이블에 생성된다
     */

    protected Survey() {
    }

    public Survey(String id, String name, List<Question> questions) {
        this.id = id;
        this.name = name;
        this.questions = questions;
    }

    public void addQuestion(Question q) {
        this.questions.add(q);
    }

    public void removeQuestion(Question q) {
        this.questions.remove(q);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void removeAllQuestions() {
        questions.clear();
    }
}
