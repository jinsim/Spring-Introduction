package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

/*    // 회원가입을 하려면 서비스가 있어야함. 멤버 서비스 부터 만들자.
    MemberService memberService = new MemberService();
    // MemberService만 있으면 clearStore가 되지 않는다.
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
*/
    // 문제점 : MemberService안에서 생성되는 MemoryMemberRepository와 위에서 새로 생성한 MemoryMemberRepository 가 다른 객체이다.
    // store가 static이라 클래스 레벨에서 영향을 받기 때문에 큰 영향은 없지만, 문제가 생길 수도 있음.
    // 또한, 같은 레포지토리로만 테스트 하는 것이 맞음.

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    // 각 테스트를 실행하기 전에, MemoryMemberRepository를 만들어서 생성자 함수의 매개값으로 넣어준다.
    // = DI(Dependency Injection)
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }



    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    // 사실 테스트 코드는 메소드 명을 한글로 적어도 된다.
    // 또한, 빌드될 때 테스트 코드는 포함되지 않는다.
    // given, when, then 주석 추천. 길어질 때 편하다.
    void 회원가입() {
        // given (이게 주어졌을 때)
        Member member = new Member();
        member.setName("spring");

        // when (이걸 실행할 때)
        Long saveId = memberService.join(member);

        // then (이렇게 나와야 해)
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    // 회원가입에 대한 예외 플로우
    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");

        Member member2 = new Member();
        member2.setName("spring1");

        // when
        memberService.join(member1);
/*
        try {
            memberService.join(member2);
            // 위 코드에서 에러가 발생하지 않으면 실패한 것이다.
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/
        // 이정도 때문에 try-catch 넣는 게 애매하다. 대신 좋은 문법이 있다.
        // 람다로 넣은 로직이 실행되었을 때, 앞의 예외가 떠야한다.
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // 메시지 검증
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}