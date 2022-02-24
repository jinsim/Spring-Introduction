// 통합 테스트. 단위 테스트만으로도 가능한 상황을 만드는 것이 가장 좋다.
package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// 스프링 컨테이너를 테스트와 함께 실행한다.
@SpringBootTest
// 테스트 메소드를 실행할 때 transaction을 실행하고, 테스트가 종룔되면 롤백한다.
@Transactional
class MemberServiceIntegrationTest {

    // 테스트에서는 굳이 생성자 기반 DI를 사용할 필요는 없다.
    @Autowired MemberService memberService;
    // 구현체는 스프링이 Configuration 한 곳에서 올라올 것임.
    @Autowired MemberRepository memberRepository;

//    @Commit // 데이터가 롤백되지 않고 커밋된다
    @Test
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
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // 메시지 검증
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        // then
    }

}