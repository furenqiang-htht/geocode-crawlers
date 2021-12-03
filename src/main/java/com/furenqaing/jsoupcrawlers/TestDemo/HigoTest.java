package com.furenqaing.jsoupcrawlers.TestDemo;

import java.sql.Connection;
import java.sql.DriverManager;

public class HigoTest {

    public static void main(String[] args)
    {
        String driver = "com.highgo.jdbc.Driver";
        String url = "jdbc:highgo://127.0.0.1:5866/dbName";
        try
        {
            Class.forName(driver);
            System.out.println("success find class");
            Connection conn = null;
            try
            {
                conn = DriverManager.getConnection(url, "username", "userpwd");
                System.out.println("success connect");
            }
            catch (Exception ex)
            {
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace(System.out);
            } finally
            {
                if(conn != null)
                {
                    conn.close();
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }

}
