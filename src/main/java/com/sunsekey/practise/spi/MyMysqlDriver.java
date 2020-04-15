package com.sunsekey.practise.spi;

import com.mysql.jdbc.NonRegisteringDriver;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class MyMysqlDriver extends NonRegisteringDriver implements java.sql.Driver {
    public MyMysqlDriver() throws SQLException {
    }

    static {
        try {
            System.out.println("registerDriver -- MyMysqlDriver");
            DriverManager.registerDriver(new MyMysqlDriver());
        } catch (SQLException var1) {
            throw new RuntimeException("Can't register driver!");
        }
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
