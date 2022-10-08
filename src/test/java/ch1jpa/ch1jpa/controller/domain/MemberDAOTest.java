package ch1jpa.ch1jpa.controller.domain;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

@Slf4j
class MemberDAOTest {

    private final MemberDAO memberDAO = new MemberDAO();

    @AfterEach
    void afterEach() throws SQLException {
        memberDAO.deleteAll();
    }

    @Test
    public void findSaveTest() throws SQLException {
        String memberId = UUID.randomUUID().toString();
        Member newMember = new Member(memberId, "member" + memberId, "123", null);

        Member savedMember = memberDAO.save(newMember);

        Member findMember = memberDAO.find(memberId);

        log.info("findMember={}", findMember);

        Assertions.assertThat(savedMember).isEqualTo(findMember);
    }

    @Test
    public void findWithTeamTest() throws SQLException {

        Member findMember = memberDAO.findWithTeam("1");

        log.info("findMember={}", findMember);
    }

    @Test
    public void updateTest() throws SQLException {
        String memberId = UUID.randomUUID().toString();
        Member newMember = new Member(memberId, "member" + memberId, "123", null);

        memberDAO.save(newMember);

        Member updatedMember = new Member(newMember);
        updatedMember.setName("koko");
        updatedMember.setTel("987");

        memberDAO.update(updatedMember);

        Member findMember = memberDAO.find(memberId);

        Assertions.assertThat(findMember).isEqualTo(updatedMember);
    }
}