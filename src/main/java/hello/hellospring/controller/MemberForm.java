package hello.hellospring.controller;

public class MemberForm {
    // createMemberForm.html에서 name이 setName을 통해 name 필드에 저장된다. (스프링이 함)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
