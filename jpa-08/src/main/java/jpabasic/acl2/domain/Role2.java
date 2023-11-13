package jpabasic.acl2.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role2 {
    @Id
    private String id;
    private String name;

    @ElementCollection
    @CollectionTable(name = "role_perm", joinColumns = @JoinColumn(name = "role_id")) // embeddable 타입도 Set 자료형에 넣을 수 있다
    private Set<GrantedPermission> permissions = new HashSet<>();

    // embaddalble 타입 클래스에는 hashCode, equals 메서드가 구현되어있어야 한다

    protected Role2() {
    }

    public Role2(String id, String name, Set<GrantedPermission> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<GrantedPermission> getPermissions() {
        return permissions;
    }

    public void revokeAll() {
        // this.permissions.clear();
        this.permissions = new HashSet<>();
    }

    public void setPermissions(Set<GrantedPermission> newPermissions) {
        this.permissions = newPermissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
