package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpabasic.jpa.EMF;
import jpabasic.team.domain.Player;
import jpabasic.team.domain.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class MainCascade {
    private static Logger logger = LoggerFactory.getLogger(MainCascade.class);

    public static void main(String[] args) {
        clearAll();
        EMF.init();
        try {
            saveTeam();
            // removeTeam();
        } finally {
            EMF.close();
        }
    }

    private static void saveTeam() {
        logger.info("saveTeam");
        EntityManager em = EMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Player p21 = new Player("P-21", "선수21");
            Player p22 = new Player("P-22", "선수22");
            Set<Player> players = new HashSet<>();
            players.add(p21);
            players.add(p22); // 플레이어를 영속화 하지 않았음
            em.persist(new Team("T-01", "팀1", players));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    // DB에 Player Entity가 없기 때문에 Team을 persist 하면 에러가 발생한다
    // jakarta.persistence.OptimisticLockException 발생
    // Batch update returned unexpected row count from update [0]; actual row count:
    // 0; expected: 1; statement executed: update player set team_id=? where id=?
    // 이는 영속성 전파로 해결한다
    // OneToMany 의 cascade 속성을 CascadeType.PERSIST로 지정해주면 된다
    /*
     * 자신이 저장될 때(영속화 될 때) 자신과 연관된 다른 엔티티들도 저장되도록 하는 것
     * cascade 속성을 설정하여 영속성 전파의 다른 설정도 사용 가능하다 (REMOVE, ALL, PERSIST, MERGE,
     * REFRESH, DETACH)
     * ALL은 나머지 속성 전부를 설정한다는 이야기이다
     * cascade 속성은 특별한 이유가 없다면 사용하지 말 것 (왜?)
     * 연관관계 매핑 고려 사항
     * - 연관 관계 대신 ID 값으로 참조 고려
     * - - A 엔티티에서 B 엔티티를 직접 참조하지 말고 Id 필드의 값만을 참조하는 방법을 염두하기
     * - - 연관 관계를 사용하면 객체의 탐색이 쉬워지긴 한다
     * - - 객체 탐색이 쉽다고 연관 관계 사용하는 것은 지양
     * - 조회는 전용 쿼리나 구현을 사용하는 것을 고려
     * - 엔티티인지 벨류인지 확인하기
     * - - 1대1, 1대N 관계에서 다시 생각해보기
     * - 1대N 보다는 N대1 관계를 사용하기
     * - 양방향 매핑은 가급적 사용하지 말기
     * - - 양방향 매핑의 경우 도메인 설계를 하다가 어쩔 수 없이 나오는 순환 참조 문제를 해결하기 위한 솔루션
     * - - 양방향 매핑을 습관적으로 사용할 경우 원하는 데이터를 얻기 위해 어떤 지점에서 시작해서 어떤 방식으로 얻어야 할지 불명확해지게 됨
     */

    private static void clearAll() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/jpabegin?characterEncoding=utf8",
                "jpauser",
                "jpapass");
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("truncate table team");
            stmt.executeUpdate("truncate table player");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
