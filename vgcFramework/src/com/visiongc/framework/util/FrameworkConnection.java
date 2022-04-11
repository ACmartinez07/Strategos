package com.visiongc.framework.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class FrameworkConnection
{

    public FrameworkConnection()
    {
    }

    public boolean testConnection(String url, String driver, String uid, String pwd)
    {
        boolean conexionExitosa = false;
        try
        {
            Class.forName(driver);
            Connection cn = DriverManager.getConnection(url, uid, pwd);
            cn.close();
            cn = null;
            conexionExitosa = true;
        }
        catch(Exception e2)
        {
            conexionExitosa = false;
        }
        return conexionExitosa;
    }
}