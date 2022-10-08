package ch1jpa.ch1jpa.controller.domain;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class TeamDAOTest {

    private final TeamDAO teamDAO = new TeamDAO();

    @Test
    public void findTest() throws SQLException {
        Team team = teamDAO.find("1");
        log.info("findTest team ={}", team);
        Assertions.assertThat(team).isEqualTo(new Team("1", "team1"));
    }
}