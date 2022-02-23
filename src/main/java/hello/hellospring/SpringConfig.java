package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

// 스프링이 시작될 때 파일을 읽는다.
@Configuration
public class SpringConfig {

    // @Configuration 한 것도 스프링 빈으로 관리가 된다.
    // 스프링이  application.properties에 있는 정보들을 보고 자체적으로 생성해 준다.
    private DataSource dataSource;
    // 생성된 dataSource를 주입해줌.(DI)
    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }



    // 아래 로직을 거치고 난 후 스프링 빈에 등록을 해준다.
    // 매개변수 자리에 MemoryMemberRepository 를 반환받는다.(스프링 빈에 등록되어있음)
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        // MemberRepository는 인터페이스, new가 안된다. 구현체를 불러주자.
//        return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
    }

}