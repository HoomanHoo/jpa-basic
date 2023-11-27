# JPA 메서드
## Entity Manager
- EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnitName");
  - EntityManager 객체를 생성하기 위한 EntityManagerFactory 객체를 생성함
  - Spring 프레임워크에서 대신 해주기 때문에 사용할 일은 거의 없음
- EntityManager em = emf.createEntityManager();
  - EntityManagerFactory 객체의 createEntityManager() 메서드를 이용하여 EntityManager 객체를 생성함
  - Spring 프레임워크에서 EntityManager 객체를 주입해줄 수 있음
  ```
    private final EntityManager em;

    @Autowired
    public MemberRepository(EntityManager em) {
        this.em = em;
    }
  ``` 
- EntityTransaction tx = em.getTransaction();
  - EntityManager 객체의 getTransaction() 메서드로 트랜젝션을 설정함
  - Spring 프레임워크의 @Transactional 어노테이션으로 메서드 단위의 트랜젝션을 설정함
## CRUD
- EntityManger.persist(Entity);
  - 엔티티 객체를 영속화함
  - EntityManager.commit() 메서드 호출, 혹은 transaction이 끝날 때 Entity 객체의 변경 사항을 영속화 함
  - SQL의 insert와 update를 담당함
  - update의 경우 persist() 하지 않고 find()로 영속성 컨텍스트를 불러오고 이 엔티티 객체의 데이터를 변경한 후 commit하는 것으로도 실행 가능함
- EntityManager.remove(Entity);
  - 엔티티 객체를 영속성 컨텍스트에서 삭제함
  - EntityManager.commit() 메서드 호출, 혹은 transaction이 끝날 때 해당 엔티티 객체가 매핑된 DB row를 데이터 베이스에서 삭제함
  - SQL의 delete를 담당함
- EntityManager.find(Entity.class, Id field value);
  - 해당하는 객체를 영속성 컨텍스트로 가져옴
    - 영속성 컨텍스트에 해당 객체가 존재하면 해당 객체를 그대로 반환함
    - 객체가 프록시 객체라면 프록시 객체를 반환함
    - SQL의 select를 담당함
- EntityManager.createQuery("JPQL", Entity.class) 
  - JPQL을 이용하여 자신이 원하는 쿼리를 실행할 수 있음
  - setParameter("파라미터 이름", 파라미터에 넣을 값), setFirstResult("조회 시작 위치"), setMaxResults("조회할 데이터 수") 등의 메서드를 이용하여 검색 조건을 설정할 수 있음
  - getResultList();를 이용하여 결과를 List 타입으로 받을 수 있음

    ```
    TypedQuery<Role> query = em.createQuery(
                        "select r from Role r left join fetch r.permissions where r.name like :name order by r.id",
                        Role.class);
                query.setParameter("name", "name%");
                query.setFirstResult(6);
                query.setMaxResults(3);
    ```
## JPQL

* JPQL - JPA Query Language
* SQL 쿼리와 유사함
* 테이블 대신 엔티티 이름과 속성을 사용함
* JPQL을 이용한 쿼리 생성
* TypedQuery<T> query = EntityManager.createQuery(String jpql, Class<T>Result Class)
* -> 쿼리를 생성하고 TypedQuery의 메서드를 이용하여 원하는 작업을 수행한다
* where, and, or, 괄호 등를 이용하여 검색 조건을 지정할 수 있다.
* 파라미터는 이름 기반과 인덱스 기반이 있다
  - 이름 기반 파라미터는 setParameter() 메서드에 ("쿼리에서 사용한 이름", 파라미터 값) 매개변수가 필요하며
  - 인덱스 기반 파라미터는 setParameter() 메서드에 (인덱스(0부터 시작함), 파라미터 값) 매개변수가 필요하다
* 정렬 순서는 order by 키워드를 사용한다 (SQL과 동일)
* 비교연산자 역시 SQL에서 사용하는 비교 연산자들을 동일하게 사용할 수 있다
* 페이징 처리는 setFirstResult(), setMaxResult() 메서드를 사용한다
* setFirstResult()는 시작 행을 매개변수로 넣어주어야 하며 0부터 시작한다
* setMaxResult()는 최대 결과 개수를 매개변수로 넣어주어야 한다
* 이에 따라 실행되는 쿼리는 DB의 종류에 따라 변경된다
* 이외에도 join, 집합함수(count, max, min, avg, sum), groupby, having,
* 컬렉션 관련 비교 키워드, JPQL 함수(concat, substring, trim, abs, sqrt/컬렉션 함수 size,
* index)가 사용 가능하다 
* 여러 테이블 조인(레거시 테이블 조인), DBMS에 특화된 쿼리, 서브 쿼리, 통계, 대량의 데이터 조회/처리의 경우 일반 쿼리를 사용하는 것을 고려
* join fetch를 이용하면 명시한 테이블 전부를 가져온다 (limit 조건이 무시됨)
* limit 처리는 다 가져온 데이터를 메모리에서 진행한다
* Collection 테이블들은 성능 문제에 민감함
* 
* 성능 관련 문제는 CQRS를 고민해야함
* CQRS(변경 기능을 위한 모델과 조회 기능을 위한 모델 분리)
  - 변경 기능은 JPA
  - 조회 기능은 MyBatis/JdbcTemplate/(간단한 경우)JPA
  - 강의 제작자(최범균) 유튜브에서 CQRS 동영상 확인해보기


# Criteria
* 자바 코드로 쿼리를 구성하는 API
* join, count, max, min, avg, sum, group by, having등 JPQL과 같은 기능을 지원
* JPQL과 같이 여러 테이블 조인, DBMS 특화 쿼리, 서브쿼리, 통계, 대량 데이터 조회 처리의 경우
* 일반 쿼리를 사용하는 것을 고려함
* 
* N+1 문제, fetch 조인에 대한 학습을 진행하여 엔티티간의 연관, JPA-Criteria를 조합하여 조회할 경우
* 문제를 예방할 수 있도록 할 것
```
CriteriaBuilder cb = em.getCriteriaBuilder();
// EntityManager.getCriteriaBuilder() 메서드로 CriteriaBuilder 객체를 생성

CriteriaQuery<Review> cq = cb.createQuery(Review.class);
// 생성하는 쿼리는 CriteriaQuery<T> 타입을 가진다

Root<Review> root = cq.from(Review.class);
cq.select(root);
Predicate p = cb.conjunction();
// Criteria 사용 이유는 동적인 검색 조건을 지정할 수 있다
// -> 상황에 맞는 쿼리를 조건문 등을 통해 구성할 수 있다
// 타입에 안전한 코드를 작성할 수 있다

if (hotelId != null) {
    p = cb.and(p, cb.equal(root.get("hotelId"), hotelId));
}
p = cb.and(p, cb.greaterThan(root.get("created"), LocalDateTime.now().minusDays(10)));
if (mark >= 0) {
    p = cb.and(p, cb.ge(root.get("mark"), mark));
}
cq.where(p);
cq.orderBy(
        cb.asc(root.get("hotelId")),
        cb.desc(root.get("id")));
// 검색 조건을 메서드로 설정한다
// get(Entity.get(필드명), 비교하고자 하는 값) 으로 검색 조건을 지정할 수 있음
// 정렬은 orderBy(), asc, desc 등으로 설정할 수 있다
// Entity.get()은 Object 타입으로 리턴하고자 하는데, 이를 타입 지정을 해주어 올바른 값을
// 받을 수 있도록 설정해줄 수 있다.

TypedQuery<Review> query = em.createQuery(cq);
// 생성한 CriteriaQuery를 EntityManager.createQuery()의 매개변수로 지정

query.setFirstResult(4); // 0부터 시작
query.setMaxResults(4);
List<Review> reviews = query.getResultList();
```

## 기타


* @Subselect
  * select 결과를 엔티티로 매핑함 - 수정 대상이 아니기 때문에 @Immutable 어노테이션과 같이 사용한다
  * 엔티티처럼 사용하며 select 쿼리 실행시 @Subselect의 쿼리가 from 절 내부에 들어가게 됨
  * 엔티티 클래스의 선언부에 사용하는 어노테이션이다
  ```
  @Subselect("""
          select a.article_id, a.title, w.name as writer, a.written_at
          from article a left join writer w on a.writer_id = w.id
          """)
  ```

* @Immutable
  * 엔티티 클래스의 선언부에 사용하는 어노테이션이다
  * @Immutable 어노테이션은 해당 엔티티를 변경 추적 대상에서 제외하기 위해 사용한다
    - 변경 추적을 위한 메모리 사용에서 빠지기 때문에 메모리 사용량을 감소 시킬 수 있다
    - 조회 목적으로만 사용되는 엔티티 매핑에 사용

* @DynamicUpdate
  * AttributeConverter 의 모든 컬럼이 업데이트 대상이 되는 현상을 막기 위해 사용
  * @DynamicUpdate는 변경된 컬럼만 update 쿼리에 포함되도록 한다
  * @DynamicInsert는 null이 아닌 컬럼만 insert 쿼리에 포함된 기본 값을 사용할 수 있으며 null을 지정해줘야 할 경우 사용하면 안된다
* AttributeConverter
  * 매핑을 지원하지 않는 자바 타입과 DB 타입간의 변환을 처리한다
  * ex) boolean - char(1) 타입 간의 변환
  * -> true / false를 Y / N 으로 변환하여 DB에 저장하고자 할 때 사용
  * 사용법
    - AttributeConverter interface를 구현하여 사용한다
    ```
    public class BooleanYesNoConverter implements AttributeConverter<Boolean, String> {
        @Override
        public String convertToDatabaseColumn(Boolean attribute) {
            return Objects.equals(Boolean.TRUE, attribute) ? "Y" : "N";
        }

        @Override
        public Boolean convertToEntityAttribute(String dbData) {
            return "Y".equals(dbData) ? true : false;
        }
    }
    ``` 
 

* @Convert
  ```
  @Convert(converter = BooleanYesNoConverter.class)
  private boolean opened;
  ```
 * Convert 된 값을 DB에 집어넣고자 할 때 해당 필드에 @Convert(converter = AttributeConverter를 구현한클래스.class) 로 설정한 어노테이션을 붙여 사용한다
  * Convert된 값만 update 되는 것이 아니라 전체 컬럼이 update 대상이 된다


* @Formula
  * SQL을 이용한 속성 매핑
  * SQL 실행 결과를 필드에 매핑하고자 할 때 사용한다
  * 주로 조회에서만 매핑 처리를 한다 (insert / update는 매핑 대상이 아님)
  * 하이버네이트 제공 기능 - JPA 기능이 아님
  * DB 함수 호출(프로시저등), 서브쿼리 결과를 매핑
```
@Formula("(select c.name from category c where c.cat_id = cat)")
private String categoryName;
```


 * 이외에도 상속 매핑, 네이티브 쿼리, 하이버네이트 어노테이션 (@CreationTimestamp, @UpdateTimestamp) 어노테이션이 존재한다
 * 네이티브 쿼리를 엔티티 매핑 용도로도 사용할 수 있다
 * @CreationTimestamp - 엔티티 생성시 Timestamp를 자동으로 생성해줌 
 * @UpdateTimestamp - 엔티티 변경 시 Timestamp를 자동으로 생성해줌