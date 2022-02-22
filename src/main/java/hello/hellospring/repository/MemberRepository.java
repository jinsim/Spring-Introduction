package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    // Optional은 null일 때의 처리를 위해.
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> finadAll();
}
