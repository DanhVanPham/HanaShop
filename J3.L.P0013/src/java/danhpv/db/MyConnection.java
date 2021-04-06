/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.db;

import java.io.Serializable;
import java.sql.Connection;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author DELL
 */
public class MyConnection implements Serializable{
    public static Connection getMyConnection() throws Exception {
        Connection conn;
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:comp/env");
        DataSource dataSource = (DataSource) envContext.lookup("DBConnection");
        conn = dataSource.getConnection();
        return conn;
    }
}
