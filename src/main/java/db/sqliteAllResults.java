/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import guiImport.importJDialog;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Didar
 */
public class sqliteAllResults {
    
    static Connection connection = null;
    static ResultSet resultSet = null;
    static Statement statement = null; 
     
    
    public static void main (String args[])throws Exception{
        
        String strQuery = "SELECT * FROM AllIssueData;";
        ResultSet rs= null; 
        
         
        rs = getResults(strQuery, "DB/BSQPLanner.DB.sqlite"); 
        
        System.out.println(rs.getFetchSize());
        
        ResultSetMetaData rsmd = rs.getMetaData();
   
        System.out.println("querying SELECT * FROM XXX");
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
        
        //https://www.youtube.com/watch?v=7GZppdccFfs
        
        
    }
    
    
    
    
    public static ResultSet getResults (String strQuery, String tableName) {
        try{
            
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"+tableName);//"jdbc:sqlite:DB/BSQPLanner.DB.sqlite"
            statement = connection.createStatement();
            resultSet=statement.executeQuery(strQuery);
            return resultSet; 
            
            
        }
        catch (Exception ex){
            Logger.getLogger(importJDialog.class.getName()).log(Level.SEVERE, null, ex);
            return null; 
        }
    }
    
    public void closeConnection ()throws Exception{
        resultSet.close();
        statement.close();
        connection.close();
    }
    
    
    
    
    
}
//private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
//    try{
//        String sql="insert into test (test) value (?)";
//        PreparedStatement pst=conn.prepareStatement(sql);
//        pst.setString(1, Test.getText());    
//    }catch (Exception e){
//       e.printStackTrace();
//    }
//}                  