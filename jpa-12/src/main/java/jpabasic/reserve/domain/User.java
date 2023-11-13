package jpabasic.reserve.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {
    @Id
    private String email;
    private String name;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    protected User() {
    }

    public User(String email, String name, LocalDateTime createDate) {
        this.email = email;
        this.name = name;
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void changeName(String newName) {
        this.name = newName;
    }
}
/*
 * 영속 엔티티/객체
 * - DB 데이터에 매핑되는 메모리 상의 객체
 * - 영속 컨텍스트에 저장된 객체가 영속 엔티티가 된다
 * 
 * 영속 컨텍스트
 * - 일종의 메모리 저장소
 * - Entity Manager가 관리할 엔티티 객체를 보관
 * - 엔티티 타입, 식별자 를 Key로 하는 일종의 Map 형태로 보관
 * - EntityManager가 종료되면 같이 사라진다
 * 
 * 영속 컨텍스트와 캐시
 * - 동일한 식별자 -> 동일한 객체
 * - 첫번째 쿼리에서 DB에 보관한 데이터를 가져오고
 * - 동일한 쿼리를 두번째 실행할 경우 영속 컨텍스트에 보관된 객체를 조회한다
 * -> DB에 쿼리 날리지 않음
 * -> Repeatable Read(Transaction Level) 효과
 * 
 * 영속 객체의 라이프 사이클
 * - Entity 객체 생성 - 아직 영속 객체는 아님
 * - Entity 객체에 대한 persist() or find() 계열 메서드로 DB 조회
 * -> 영속 객체가 되며 영속 컨텍스트를 통해 관리된다(managed) (변경 내역이 추적된다)
 * -> 변경 내역을 추적하고 이를 이용해 rollback, commit 을 진행한다
 * - remove() 메서드로 삭제됨(removed) 상태가 됨 - 삭제 되었다는 변경 내역이 남음
 * -> 실제 데이터 삭제는 commit 시점에 DB에서 delete 쿼리로 실행됨
 * - close(), detach() 메서드로 분리됨(detached) 상태가 됨 - 변경 내역을 추적하지 않음
 * - detach()는 강제로 분리됨 상태로 만드는 것
 * - remove()에 의해 삭제됨 상태가 되었음에도 롤백이 될 경우 분리됨 상태로 변화함
 * - merge() 메서드로 다시 관리됨(managed) 상태로 만들 수 있음
 * - 분리됨 상태(detached) 가 되면 commit 시점에 update 쿼리가 실행되지 않음 - 변경내역을 추적하지 않기 때문이다
 * 
 * 대량 변경(Batch? Patch?)은 굳이 JPA를 사용할 필요는 없다
 * - 영속 컨텍스트에 대량의 데이터를 담아야 하기 때문이다
 * - JPA에도 대량 변경 방법을 제공해준다
 * - 하지만 그냥 쿼리 실행하는 것이 더 간편하다
 */