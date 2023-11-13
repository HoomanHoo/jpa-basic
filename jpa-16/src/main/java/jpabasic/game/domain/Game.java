package jpabasic.game.domain;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "game")
public class Game {
    @Id
    private String id;
    private String name;
    @OneToMany
    @JoinColumn(name = "game_id")
    @MapKeyColumn(name = "role_name")
    private Map<String, Member> members = new HashMap<>();
    /*
     * MapKeyColumn의 name 속성에서는 map의 key로 사용될 값을 담을 컬럼을 설정한다
     * 설정한 컬럼은 Many - 여기서는 member - 측 테이블에 설정된다
     */

    protected Game() {
    }

    public Game(String id, String name, Map<String, Member> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }

    public void add(String role, Member member) {
        members.put(role, member);
    }

    public void remove(String role) {
        members.remove(role);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, Member> getMembers() {
        return members;
    }
}
