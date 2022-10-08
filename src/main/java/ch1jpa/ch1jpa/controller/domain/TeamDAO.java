package ch1jpa.ch1jpa.controller.domain;

import ch1jpa.ch1jpa.DBConnectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
public class TeamDAO {

    public Team find(String teamId) throws SQLException {
        String sql = "select team_id, teamname from team where team_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnectionUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, teamId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                String team_id = rs.getString("team_id");
                String teamname = rs.getString("teamname");

                Team team = new Team(teamId, teamname);
                return team;
            }else{
                throw new NoSuchElementException("team not found teamId=" + teamId);
            }

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }finally {
            close(conn, pstmt, rs);
        }
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            }catch (SQLException e){
                log.info("error", e);
            }
        }

        if(pstmt != null){
            try {
                pstmt.close();
            }catch (SQLException e){
                log.info("error", e);
            }
        }

        if(conn != null){
            try {
                conn.close();
            }catch (SQLException e){
                log.info("error", e);
            }
        }
    }
}
