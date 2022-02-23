package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 스프링이 시작될 때 파일을 읽는다.
@Configuration
public class SpringConfig {

    // 아래 로직을 거치고 난 후 스프링 빈에 등록을 해준다.
    // 매개변수 자리에 MemoryMemberRepository 를 반환받는다.(스프링 빈에 등록되어있음)
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        // MemberRepository는 인터페이스, new가 안된다. 구현체를 불러주자.
        return new MemoryMemberRepository();
    }
}