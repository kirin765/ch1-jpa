package ch1jpa.ch1jpa.controller.repository;


import ch1jpa.ch1jpa.DBConnectionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    private final DBConnectionUtils dbConnectionUtils = new DBConnectionUtils();



}
