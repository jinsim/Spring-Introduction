package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

// 스프링이 시작될 때 파일을 읽는다.
@Configuration
public class SpringConfig {

/*
Jdbc를 위한 코드
    // @Configuration 한 것도 스프링 빈으로 관리가 된다.
    // 스프링이  application.properties에 있는 정보들을 보고 자체적으로 생성해 준다.
    private DataSource dataSource;
    // 생성된 dataSource를 주입해줌.(DI)
    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
*/

/*
JPA를 위한 코드

    // @PersistenceContext로 받아도 되지만, 그냥 아래처럼 스프링에서 DI 해줘도 됨.
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }
*/

    private final MemberRepository memberRepository;

    @Autowired // 생성자가 1개면 생략 가능
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    // 아래 로직을 거치고 난 후 스프링 빈에 등록을 해준다.
    // 매개변수 자리에 MemoryMemberRepository 를 반환받는다.(스프링 빈에 등록되어있음)
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }
/*

    @Bean
    public MemberRepository memberRepository() {
        // MemberRepository는 인터페이스, new가 안된다. 구현체를 불러주자.
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
    }
*/

}