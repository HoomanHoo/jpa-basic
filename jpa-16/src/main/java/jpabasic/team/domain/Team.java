package jpabasic.team.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team {
    @Id
    private String id;
    private String name;
    @OneToMany
    @JoinColumn(name = "team_id")
    private Set<Player> players = new HashSet<>();
    /*
     * OneToMany는 Team의 시점에서 Player를 참조할 때 사용하는 매핑
     * Set 타입으로 Player의 정보들을 가져온다
     */

    protected Team() {
    }

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Team(String id, String name, Set<Player> players) {
        this.id = id;
        this.name = name;
        this.players = players;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void removePlayer(Player p) {
        players.remove(p);
    }
    // 실행시 조건에 맞는 team_id 값을 null로 업데이트 한다
    // team_id 뿐만이 아니라 player_id의 값도 조건에 있어야 한다

    public void removeAllPlayers() {
        players.clear();
    }
    // 실행시 조건에 맞는 team_id 값을 모두 null로 업데이트 한다
    // team_id만 조건으로 가진다
}
