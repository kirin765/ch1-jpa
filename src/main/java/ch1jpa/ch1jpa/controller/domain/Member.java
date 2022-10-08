package ch1jpa.ch1jpa.controller.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConstructorBinding;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    private String memberId;
    private String name;
    private String tel;
    private Team team;

    Member(Member member){
        this.memberId = member.getMemberId();
        this.name = member.getName();
        this.tel = member.getTel();
        this.team = member.getTeam();
    }
}
