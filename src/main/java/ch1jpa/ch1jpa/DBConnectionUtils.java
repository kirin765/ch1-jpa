package ch1jpa.ch1jpa;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DBConnectionUtils {

    public static Connection getConnection(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test3", "sa", "");
            log.info("get connection={}, class={}", connection, connection.getClass());
            return connection;
        }catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }
}
