package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    // 실무에서는 동시동 문제로 인해, 중복되는 변수일 경우 ConcurrentHashMap, automiclong을 쓴다.
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        // store에 넣기 전에 sequence를 하나 증가시켜줌.
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                // 루프를 돌며 이름이 같은 것을 찾고, 하나라도 있으면 반환
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> finadAll() {
        // 저장은 Map으로하지만, 반환은 List로 한다.
        // 자바 실무에서는 List를 많이 사용함.
        return new ArrayList<>(store.values());
    }
}
