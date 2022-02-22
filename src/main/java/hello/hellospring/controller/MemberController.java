package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

// @Controller를 붙이면, 스프링이 처음에 시작할 때 스프링 컨테이너가 생기는데,
// 거기에 MemberController 객체를 생성해서 스프링에 넣어두고 스프링이 관리한다.
// 그래서 @GetMapping 등 여러 기능이 동작을 하는 것.
// = 스프링 빈이 관리된다.
@Controller
public class MemberController {

    // new로 생성해도 되지만, 다른 컨트롤러에서도 가져올 수 있게 됨. 굳이 그럴 필요 없음.
    // MemberService는 딱 하나만 생성해두고 공용으로 사용하는 것이 좋아보임.
    // = 스프링 컨테이너에 등록해서 사용(딱 하나만 가능). 여러 효과가 있음.
    private final MemberService memberService;

    // MemberController를 스프링 컨테이너가 생성할 때, 생성자를 호출함.
    // Autowired가 있으면 스프링 컨테이너에 있는 MemberService를 가져와서 연결을 해준다. (DI)
    @Autowired
    // Constructor 불러온 것.
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
