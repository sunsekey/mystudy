package com.sunsekey.practise.spi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlSpiDemo {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql:///consult?serverTimezone=UTC";
        String user = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }
}
