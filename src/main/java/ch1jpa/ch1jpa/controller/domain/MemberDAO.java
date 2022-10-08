package ch1jpa.ch1jpa.controller.domain;

import ch1jpa.ch1jpa.DBConnectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
public class MemberDAO {
    public Member find(String memberId) throws SQLException {
        String sql = "select member_id, name, tel from member where member_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Member member;


        try {
            conn = DBConnectionUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setName(rs.getString("name"));
                member.setTel(rs.getString("tel"));
                return member;
            }else {
                throw new NoSuchElementException("member not founc memberId=" + memberId);
            }

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }finally {
            close(conn, pstmt, rs);
        }
    }

    public Member findWithTeam(String memberId) throws SQLException {
        String sql = "select member_id, name, tel, team_id, teamname from" +
                " (" +
                " select m.member_id as member_id, m.name as name, m.tel as tel, m.team_id as team_id, t.teamname as teamname" +
                " from member m, team t" +
                " where m.team_id = ? and " +
                " m.team_id = t.team_id)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Member member;


        try {
            conn = DBConnectionUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                member = new Member();
                Team team = new Team();
                member.setMemberId(rs.getString("member_id"));
                member.setName(rs.getString("name"));
                member.setTel(rs.getString("tel"));
                team.setTeamId(rs.getString("team_id"));
                team.setTeamName(rs.getString("teamname"));
                member.setTeam(team);
                return member;
            }else {
                throw new NoSuchElementException("member not founc memberId=" + memberId);
            }

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }finally {
            close(conn, pstmt, rs);
        }
    }

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, name, tel) values(?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnectionUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setString(2, member.getName());
            pstmt.setString(3, member.getTel());
            pstmt.executeUpdate();
            return member;

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }finally {
            close(conn, pstmt, null);
        }
    }

    public Member update(Member member) throws SQLException {
        String sql = "update member set name = ?, tel = ? where member_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnectionUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getTel());
            pstmt.setString(3, member.getMemberId());

            pstmt.executeUpdate();

            return member;

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }finally {
            close(conn, pstmt, null);
        }
    }

    public void deleteAll() throws SQLException {
        String sql = "delete from member";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnectionUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }finally {
            close(conn, pstmt, null);
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
