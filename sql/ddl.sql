-- 테이블 관리를 위해 프로젝트 루트에 sql/ddl.sql을 두는 것이 좋다.
drop table if exists member CASCADE;
create table member
(
    id   bigint generated by default as identity,
    name varchar(255),
    primary key (id)
);
