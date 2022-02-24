package hello.hellospring.domain;

import javax.persistence.*;

// JPA가 관리하는 Entity가 되는 것이다.
@Entity
public class Member {

    // pk매핑해줘야함
    // DB가 pk인 Id의 값을 자동으로 생성해주고 있음. = identity 전략
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 데이터 구분을 위해 시스템이 주는 값

//    @Column(name = "username") // 만약 name의 DB colunm 명이 username일 경우.
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
