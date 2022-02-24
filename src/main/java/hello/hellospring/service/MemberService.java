package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

// JPA는 데이터를 저장하거나 변경할 때 항상 트랜젝션안에서 실행돼야함.
@Transactional
// 스프링이 실행될 때, 스프링 컨테이너에 이를 등록해줌.
public class MemberService {
    // 테스트할 때 문제가 발생할 수 있다.
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    // MemberRepository의 구현체인 MemoryMemberRepository가 스프링 컨테이너에서 주입된다.
    // new에서 생성하는 것이 아니라, 외부에서 넣어주도록 설정한다.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {

        // Optional로 감싸면, 그 안에 Member 객체가 있어서 여러 메소드를 불러와 이용할 수 있다.
        // 그래서 요즘엔 null일 가능성이 있으면 Optional로 감싸서 반환해준다.
        // orElseGet으로 값이 있으면 꺼내고 아니면 ~를 실행해라. 이런 것도 많이 사용한다.

//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        // 위의 코드를 아래의 코드로 변경 가능. 그러나 이것도 길다. 메소드로 뽑자.
//        memberRepository.findByName(member.getName())
//                .ifPresent(m -> {
//                    throw new IllegalStateException("이미 존재하는 회원입니다.");
//                });

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 특정 회원 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }


}
