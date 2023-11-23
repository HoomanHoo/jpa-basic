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
## 영속성 컨텍스트

## JPQL

         * join fetch를 이용하면 명시한 테이블 전부를 가져온다 (limit 조건이 무시됨)
         * limit 처리는 다 가져온 데이터를 메모리에서 진행한다
         * Collection 테이블들은 성능 문제에 민감함
         * 
         * 성능 관련 문제는 CQRS를 고민해야함
         * CQRS(변경 기능을 위한 모델과 조회 기능을 위한 모델 분리)
         * - 변경 기능은 JPA
         * - 조회 기능은 MyBatis/JdbcTemplate/(간단한 경우)JPA
         * - 강의 제작자(최범균) 유튜브에서 CQRS 동영상 확인해보기
         
