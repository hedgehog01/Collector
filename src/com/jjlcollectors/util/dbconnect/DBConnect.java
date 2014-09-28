/*
 * Copyright (C) 2014 Hedgehog01.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package com.jjlcollectors.util.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Abstract class to handle main Database connection tasks
 * @author Hedgehog01
 */
public abstract class DBConnect
{
    
    //instance variables
    protected static final String DB_Client_URL = "jdbc:derby://localhost:1527/CollectionDB";//create=true;DB_USER_NAME=Hedgehog01;password=Jade170213";
    protected static final String CREATE_DB = ";create=true";
    protected static final String SHUTDOWN_DB = ";shutdown=true";
    //private static final String driverEmbedded = "org.apache.derby.jdbc.EmbeddedDriver";
    protected static final String DRIVER_CLIENT = "org.apache.derby.jdbc.ClientDriver";
    protected static final String DB_USER_NAME = "Hedgehog01";
    protected static final String DB_PASS = "Jade170213";
    protected static Connection conn = null;
    protected static Statement stmt = null;
    protected static ResultSet resultSet = null;

    /*
     * method to close database connection
     */
    protected static void closeDBConnection()
    {
        try
        {
            System.out.println("Closing DB connection...");
            DBCoinConnect.conn.close();
        } catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    /*
     * Method that start connection to the Collector Database
     */
    protected static void createDBConnection()
    {
        try
        {
            Class.forName(DBCoinConnect.DRIVER_CLIENT).newInstance();
            System.out.println("connecting to DB...");
            DBCoinConnect.conn = DriverManager.getConnection(DBCoinConnect.DB_Client_URL + DBCoinConnect.CREATE_DB, DBCoinConnect.DB_USER_NAME, DBCoinConnect.DB_PASS);
            System.out.println("Schema conntected: " + DBCoinConnect.conn.getSchema());
        } catch (ClassNotFoundException e)
        {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
            System.out.println("\n Make sure your CLASSPATH variable " + "contains %DERBY_HOME%\\lib\\derby.jar (${DERBY_HOME}/lib/derby.jar). \n");
        } catch (InstantiationException | IllegalAccessException | SQLException e)
        {
            System.out.println(e);
        }
    }
    
}
