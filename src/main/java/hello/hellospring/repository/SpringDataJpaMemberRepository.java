// SpringDataJpaMemberRepository가 JpaRepository를 상속받았으므로,
// 스프링 데이터 JPA가 해당 인터페이스의 구현체를 만들어서 빈에 등록을 해놓는다.
package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository<Member, Long> 인터페이스를 상속받는다.
// Template은 Entity의 타입(Member)과 그 식별자(id)의 타입(Long)을 적는다.
// 인터페이스는 다중 상속이 되므로 MemberRepository 인터페이스도 상속 받는다.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    // findByName에서 Name을 가져와서 Jpql로 짜줌.
    // select m from Member m where m.name = ?
    // 이게 sql로 번역이 돼서 실행이 됨.
    // findByXXX 등으로 여러 응용이 가능함.
    Optional<Member> findByName(String name);

}