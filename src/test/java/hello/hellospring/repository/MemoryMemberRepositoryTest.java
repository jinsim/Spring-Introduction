package hello.hellospring.repository;

import hello.hellospring.domain.Member;
//import org.junit.jupiter.api.Assertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
// Assertions에 static import 가능
import static org.assertj.core.api.Assertions.*;

import java.util.List;

// 다른데서 가져올 필요가 없으므로 public 접근 제한자를 두지 않아도 됨.
class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        // repository.findByName("spring1") 여기까지하면 Optional<Member>로 반환되고,
        // get()을 통해 Member로 꺼낼 수 있다.
        Member result = repository.findById((member.getId())).get();
        // 이렇게 글자로 확인할 수도 있지만,
        System.out.println("result = " + (result == member));
        // assert를 사용하는 것이 좋다.
        // jupiter를 사용하면, Assertions.assertEquals(member, result);
        assertThat(member).isEqualTo(result);    // assertj를 사용하면 더 편하게 가능하다.
    }
    
    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
