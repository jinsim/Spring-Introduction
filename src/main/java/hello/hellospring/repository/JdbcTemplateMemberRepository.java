package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;
    // jdbcTemplate은 Injection을 받을 수 없음.
//    public JdbcTemplateMemberRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    // 스프링에서 권장하는 스타일. 스프링이 DataSource를 자동으로 Injection 해준다.
    @Autowired // 생성자가 하나만 있을 때 스프링 빈으로 등록되면, Autowired를 생략할 수 있음.
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        // JdbcTemplate를 넘겨서 만드는 SimpleJdbcTemplate는 쿼리를 짜지 않아도 되게 해준다.
        // table명이나 컬럼명있으면 insert문을 만들어준다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // sql에 대한 결과를 RowMapper로 매핑해줬다.
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        // Optional로 반환하기 위함이다.
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        // jdbcTemplate에서 쿼리 날리고, 결과를 RowMapper를 통해 매핑하여 List로 받는다.
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        // Optional로 변경해서 반환한다.
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // sql을 넣고 RowMapper로 매핑하면, 객체 생성에 대한건 memberRowMaper() 생성자에서 콜백으로 정리가 되고, 멤버가 생성이 돼서 넘어온다.
        // 반환은 어차피 List로 해줌.
        // 템플릿 메소드 패턴과 콜백(memberRowMapper)을 통해 만들어진 것이다.
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        // 람다 스타일로 RowMapper<Member>를 생성함. rs는 ResultSet, rowNum은 int
        // ResultSet 결과를 Member객체로 매핑을 해서 돌려준다.
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
