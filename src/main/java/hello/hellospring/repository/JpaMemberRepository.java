package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    // 스프링 부트가 자동으로 properties나 DB 커넥션 정보들을 이용해서
    // DB와 연결한 후, EntityManager를 생성해준다. (우린 Injection 받으면 됨.)
    // 내부적으로 DataSource를 들고 있어서 DB와 통신을 처리 가능.
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Member save(Member member) {
        // persist = 영구저장하다. 리턴값이 없음.
        // 이거만 적으면, JPA가 insert 쿼리 만들어서 DB에 넣고 Id도 올려서 넣어줌.
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 조회할 타입과 식별자 pk값 넣어주면 Member이 반환됨.
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // pk가 아니므로, jpql이라는 객체 지향 쿼리 언어를 사용해야함.
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        // Optional로 반환하기 위함.
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
//        List<Member> result = em.createQuery("select m from Member m", Member.class)
//                .getResultList();
//        return result;
        // 위 코드를 inline으로 바꾼 것.(cmd + opt + n)

        // qlString에 적힌 것이 jpql이다.
        // 우리는 보통 table 대상으로 sql을 날리는데, 이는 객체(여기서는 Entity)를 대상으로 쿼리를 날린다.
        // 그럼 sql로 변형됨.
        // Member Entity를 조회하라는 명령어인데, select로 객체 자체를 조회함.
        // 기존의 sql은 다 id나 name 등을 찾은 다음에 매핑해주어야함.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
