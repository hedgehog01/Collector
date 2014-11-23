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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class to handle main Database connection tasks
 *
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
    protected static ResultSet resultSet = null;
    private static final Logger log = Logger.getLogger(DBConnect.class.getName());
    /*
     * method to close database connection
     */

    protected static void closeDBConnection()
    {
        try
        {
            log.log(Level.INFO, "Closing DB connection...");
            DBCoinConnect.conn.close();
        } catch (SQLException e)
        {
            log.log(Level.SEVERE, "Closing DB connection failed.\nSQL Ex: {0}", e);
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
            log.log(Level.INFO, "Attempting to establish DB connection...");
            DBCoinConnect.conn = DriverManager.getConnection(DBCoinConnect.DB_Client_URL + DBCoinConnect.CREATE_DB, DBCoinConnect.DB_USER_NAME, DBCoinConnect.DB_PASS);

        } catch (ClassNotFoundException e)
        {
            log.log(Level.SEVERE, "Exception while creating connection");
            log.log(Level.SEVERE, "\n Make sure your CLASSPATH variable " + "contains %DERBY_HOME%\\lib\\derby.jar (${DERBY_HOME}/lib/derby.jar). \n");
            log.log(Level.SEVERE, "ClassNotFoundException:: {0}", e);
        } catch (InstantiationException | IllegalAccessException | SQLException e)
        {
            log.log(Level.SEVERE, "Exception while creating connection: {0}", e);
        }
    }
    
    /**
     * Checks if DB can be reached
     * @return true if DB can be reached
     */
    public static boolean isDBConnectable()
    {
        boolean connect = false;
        try
        {
            Class.forName(DBCoinConnect.DRIVER_CLIENT).newInstance();
            log.log(Level.INFO, "Attempting to establish DB connection...");
            DBCoinConnect.conn = DriverManager.getConnection(DBCoinConnect.DB_Client_URL + DBCoinConnect.CREATE_DB, DBCoinConnect.DB_USER_NAME, DBCoinConnect.DB_PASS);
            connect = true;
            conn.close();
        } catch (ClassNotFoundException e)
        {
            log.log(Level.SEVERE, "Exception while creating connection");
            log.log(Level.SEVERE, "\n Make sure your CLASSPATH variable " + "contains %DERBY_HOME%\\lib\\derby.jar (${DERBY_HOME}/lib/derby.jar). \n");
            log.log(Level.SEVERE, "ClassNotFoundException:: {0}", e);
            connect = false;
        } catch (InstantiationException | IllegalAccessException | SQLException e)
        {
            connect = false;
            log.log(Level.SEVERE, "Exception while creating connection: {0}", e);
        }
        log.log(Level.INFO, "connection status: {0}",connect);
        return connect;
    }

}
