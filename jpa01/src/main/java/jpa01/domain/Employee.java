package jpa01.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Employee {
    @Id
    private String id;

    @Embedded
    private Address homeAddress;

    @AttributeOverrides({
            @AttributeOverride(name = "address1", column = @Column(name = "waddr1")),
            @AttributeOverride(name = "address2", column = @Column(name = "waddr2")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "wzipcode"))
    })
    @Embedded
    private Address workAddress;

    /*
     * @Embeddable
     * 엔티티가 아닌 타입을 한 개 이상의 필드와 매핑할 때 사용
     * 엔티티의 한 속성으로 @Embeddable 적용 타입 사용
     * 사용할 필드에 @Embedded 어노테이션을 붙여 사용한다
     * 값 타입을 나타낼 때 사용하는 경우가 많다
     * 
     * @Embedded 필드의 테이블에 컬럼이 생성된다
     * 같은 타입의 @Embeddable 필드가 두개 있으면 에러가 발생한다(MappingException)
     * -> 서로 같은 컬럼에 매핑하려고 하기 때문에
     * 
     * - 이를 방지하기 위해 두번째 @Embedded 필드에
     * - @AttributeOverrides({@AttributeOverride(name =속성명,
     * - column = @Column(name = 매핑할 컬럼명))
     * - ....})
     * - @Embedded
     * - 임베디블 필드
     * 
     * 으로 재정의 할 수 있다.
     * 모델을 더 잘 표현할 수 있음 -> 개별 속성을 모아서 이해/타입으로 더 쉽게 이해
     */

    protected Employee() {
    }

    public Employee(String id, Address homeAddress, Address workAddress) {
        this.id = id;
        this.homeAddress = homeAddress;
        this.workAddress = workAddress;
    }

    public String getId() {
        return id;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public Address getWorkAddress() {
        return workAddress;
    }
}
