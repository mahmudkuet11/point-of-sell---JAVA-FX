/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package address.book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mohar
 */
public class Database {
    Connection c = null;
    Statement stmt;

    public Connection getConnection() throws SQLException{
        Connection con = null;
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:db/pos.sqlite");
            con.setAutoCommit(false);
            
        }catch(ClassNotFoundException | SQLException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            return null;
        }finally{
            return con;
        }
    }

    
    public void executeUpdate(String sql) throws SQLException{
      
        Connection con = null;
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:db/pos.sqlite");
            con.setAutoCommit(false);
            stmt = con.createStatement();
            stmt.execute(sql);
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }finally{
            stmt.close();
            con.commit();
            con.close();
        }
    }
    
}
