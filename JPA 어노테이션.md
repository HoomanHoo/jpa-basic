# 엔티티 클래스에서 사용하는 어노테이션
  - @Entity
    - 해당 어노테이션이 붙은 클래스가 DB Table과 매핑 대상임을 나타낸다
  - @Table
    - 어노테이션 생략 시 생략 시 클래스 이름과 동일한 Table 이름에 매핑된다
    - 속성
      - name = 테이블 이름
      - catalog = 카탈로그 이름 (MySQL DB 이름)
      - schma = 스키마 이름(Oracle schema 이름)
  - @Id
    - 식별자 필드를 나타냄
    - DB의 Primary Key에 대응된다
    - 필드, 메서드에 매핑 가능
  - @GeneratedValue
    - @Id 필드의 값을 자동으로 생성하고자 할 때 사용
    - starategy, generator 속성이 존재
      - @GeneratedValue(strategy = GenerationType.IDENTITY)
      - @GeneratedValue(generator = "시퀀스 생성기 이름")
      - @GeneratedValue(generator = "테이블 시퀀스 생성자 이름")
    - 자세한 차이는 엔티티 클래스.md의 식별자 생성방식 참조 
  - @Column
    - name 속성에 매핑할 DB 컬럼을 명시할 수 있다
    - table 속성에 매핑할 테이블 명을 명시할 수 있다
      - @Embeddalbe 어노테이션이 붙은 클래스에서 가능
    ```
    @Column(name = "addr1")
    private String address1;
    ``` 
  * @Enumerated
    * Enum 자료형을 매핑할 때 엔티티 클래스로 매핑할 때 사용한다
    ```
     @Enumerated(EnumType.STRING)
     private Grade grade;   //Grade는 Enum이다
    ```
    * 속성
      * EnumType.STRING = Enum 타입의 값 이름을 저장함
      * EnumType.ORDINARY = (Default값) Enum 타입 값의 순서를 저장함 
        * 값의 순서는 바뀔 수 있기 때문에 DB에 저장하기는 난감함
  * @Embeddable
     * 엔티티가 아닌 타입을 한 개 이상의 필드와 매핑할 때 사용
     * 매핑할 클래스에 붙이는 어노테이션
     * DB의 테이블과 매핑하는 것이 아님
     * Collection 의 자료형으로도 사용할 수 있다
       * @Embedded 필드가 존재하는 엔티티와 매핑된 테이블에 컬럼이 생성된다
     * 사용할 필드에 @Embedded 어노테이션을 붙여 사용한다
     * 값 타입을 나타낼 때 사용하는 경우가 많다
     * 같은 타입의 @Embeddable 필드가 두개 있으면 에러가 발생한다(MappingException)
       * 서로 같은 컬럼에 매핑하려고 하기 때문에
     * 이를 방지하기 위해 두번째 @Embedded 필드에 @AttributeOverrides을 사용한다
      ```
      @Embedded
      private Address homeAddress;
      // Address필드값이 테이블에 컬럼으로 생성됨

      @AttributeOverrides({
            @AttributeOverride(name = "@Embeddable 클래스 필드명", column = @Column(name = "매핑할 컬럼명")),
            @AttributeOverride(name = "@Embeddable 클래스 필드명2", column = @Column(name = "매핑할 컬럼명2")),
      })
      @Embedded
      private Address workAddress;
      // "매핑할 컬럼명", "매핑할 컬럼명2" 라는 컬럼이 테이블에 생성됨

     ```  
     * 모델을 더 잘 표현할 수 있음 -> 개별 속성을 모아서 이해/타입으로 더 쉽게 이해
  * @Access
    * 엔티티 클래스의 접근 타입을 지정한다
    * 해당 어노테이션 미사용 시, @Id 어노테이션의 위치로 접근 타입을 결정
    * 속성
      * AccessType.PROPERTY 
        * 접근 타입을 프로퍼티 접근 타입으로 설정
      * AccessType.FIELD 
        * 접근 타입을 필드 접근 타입으로 설정
        * private 접근제어자라도 Reflection으로 접근이 가능하다
    * 두 접근 타입을 같이 사용할 수 있음
      * 클래스 자체는 @Id의 위치로 접근 타입을 결정하며 필드 하나의 접근은 프로퍼티 타입으로 설정하거나 그 반대도 가능
  * @SecondaryTables
    * 다른 테이블에 저장된 데이터를 @Embeddable로 매핑 가능
    * 매핑한 테이블은 @Embedded 어노테이션이 붙은 필드에서 사용할 수 있다
    * 다른 테이블에 저장된 데이터가 개념적으로 value일때 사용 1-1 관계 테이블 매핑 시에 종종 출현 
    * 엔티티의 데이터가 2개 이상의 테이블에 분산되고 해당 엔티티를 모든 테이블에 매핑하려는 경우에 사용
    ```
    @SecondaryTables({
        @SecondaryTable(name = "추가할 테이블명", pkJoinColumns = @PrimaryKeyJoinColumn(name = "추가 테이블의 PK 컬럼명", referencedColumnName = "사용할 테이블의 PK 컬럼명")),
        @SecondaryTable(name = "추가할 테이블명2", pkJoinColumns = @PrimaryKeyJoinColumn(name = "추가 테이블2의 PK 컬럼명", referencedColumnName = "사용할 테이블의 PK 컬럼명") 
        ) }) 
    public class Writer {
        ...
    ``` 
    * 하나의 엔티티를 조회하는데 SecondaryTable로 선언한 모든 테이블을 조회
    * 하나의 엔티티에 여러 테이블을 매핑하는 방식은 별로 권장하는 방법은 아님

  * @ElementCollection
    * 값 타입 컬렉션을 매핑할 때 사용함
    * RDB에서는 컬렉션과 같은 형태의 데이터를 컬럼에 저장할 수 없기 때문
      * 별도의 테이블을 생성하여 컬렉션을 관리함 
      * 엔티티가 아닌 기초 타입, Embeddable 클래스로 정의된 컬렉션에 대한 테이블을 생성하여 1:N 관계로 다룸
      * Embeddable 클래스는 @Access 의 타입을 AccessType.FIELD로 지정해줘야 한다
  * @CollectionTable
    * @ElementCollection 과 같이 사용
    * 값 타입 컬렉션을 매핑할 테이블에 대한 정보를 지정하기 위해 사용
    * 생략할 경우 기본값 (엔티티 이름_컬렉션 필드 이름) 을 이용하여 매핑함
    ```
    // 컬렉션이 Set으로 되어있지만 List, Map도 가능함
    @ElementCollection
    @CollectionTable(name = "role_perm", // 연관 관계 테이블명
            joinColumns = @JoinColumn(name = "role_id") // FK 컬럼
    )
    @Column(name = "perm") // 실제 값을 담고 있는 컬럼 / laze lodding 방식으로 불러온다
    private Set<String> permissions = new HashSet<>();
    ===========================================
    @ElementCollection
    @CollectionTable(name = "role_perm", joinColumns = @JoinColumn(name = "role_id")) // embeddable 타입도 Set 자료형에 넣을 수 있다
    private Set<GrantedPermission> permissions = new HashSet<>();
    // GrantedPermission는 @Embeddable 타입이어야함
    // embaddalble 타입 클래스에는 hashCode, equals 메서드가 구현되어있어야 한다
    // 엔티티를 삭제하면 컬렉션에 저장된 값도 같이 사라지게 된다
    ============================================
    @ElementCollection(fetch = FetchType.EAGER) // lazy eager 설정 가능하다
    @CollectionTable(
            name = "question_choice", // 연관 관계 테이블 지정
            joinColumns = @JoinColumn(name = "question_id")// FK 지정
    )
    @OrderColumn(name = "idx")// List의 index값을 저장할 컬럼 지정
    private List<Choice> choices = new ArrayList<>();
    ============================================
    @ElementCollection
    @CollectionTable(name = "doc_prop", // 연관 관계 테이블 지정
            joinColumns = @JoinColumn(name = "doc_id")) // 연관 관계 테이블의 FK 컬럼 지정
    @MapKeyColumn(name = "name") // Map의 Key 값을 저장할 컬럼을 매핑
    @Column(name = "value") // Map의 Value 값을 저장할 컬럼을 매핑
    private Map<String, String> props = new HashMap<>();
    =============================================
    @ElementCollection
    @CollectionTable(name = "doc_prop", joinColumns = @JoinColumn(name = "doc_id"))
    @MapKeyColumn(name = "name") // Key 값을 저장할 컬럼만 매핑한다
                                 // value 값을 저장할 컬럼은 PropValue의 필드를 그대로 따라간다
    private Map<String, PropValue> props = new HashMap<>();
    ``` 
