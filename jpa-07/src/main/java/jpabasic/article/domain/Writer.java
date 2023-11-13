package jpabasic.article.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "writer")
@SecondaryTables({
        @SecondaryTable(name = "writer_address", pkJoinColumns = @PrimaryKeyJoinColumn(name = "writer_id", referencedColumnName = "id")),
        @SecondaryTable(name = "writer_intro", pkJoinColumns = @PrimaryKeyJoinColumn(name = "writer_id", referencedColumnName = "id") // @
        ) }) // 다른 테이블에 저장된 데이터를 @Embeddable로 매핑 가능
// 다른 테이블에 저장된 데이터가 개념적으로 value일때 사용 1-1 관계 테이블 매핑 시에 종종 출현
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address1", column = @Column(table = "writer_address", name = "addr1")),
            @AttributeOverride(name = "address2", column = @Column(table = "writer_address", name = "addr2")),
            @AttributeOverride(name = "zipcode", column = @Column(table = "writer_address"))
    })
    private Address address;

    @Embedded
    private Intro intro;

    protected Writer() {
    }

    public Writer(String name, Address address, Intro intro) {
        this.name = name;
        this.address = address;
        this.intro = intro;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Intro getIntro() {
        return intro;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", intro=" + intro +
                '}';
    }
}
