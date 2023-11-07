package jpa01.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    @Column(name = "addr1")
    private String address1;
    @Column(name = "addr2")
    private String address2;
    @Column(name = "zipcode")
    private String zipcode;

    protected Address() {
    }

    public Address(String address1, String address2, String zipcode) {
        this.address1 = address1;
        this.address2 = address2;
        this.zipcode = zipcode;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getZipcode() {
        return zipcode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}

/*
 * @Embeddable
 * 엔티티가 아닌 타입을 한 개 이상의 필드와 매핑할 때 사용
 * 엔티티의 한 속성으로 @Embeddable 적용 타입 사용
 * 사용할 필드에 @Embedded 어노테이션을 붙여 사용한다
 * 값 타입을 나타낼 때 사용하는 경우가 많다
 * 같은 타입의 @Embeddable 필드가 두개 있으면 에러가 발생한다(MappingException)
 * 
 * - 이를 방지하기 위해 @AttributeOverrides({@AttributeOverride(name = 속성명, column
 * = @Column(name = 매핑할 컬럼명))
 * ....})
 * - @Embedded
 * - 임베디블 필드
 * 
 * 으로 재정의 할 수 있다.
 * 모델을 더 잘 표현할 수 있음 -> 개별 속성을 모아서 이해/타입으로 더 쉽게 이해
 */
