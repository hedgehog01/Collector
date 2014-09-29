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


import static com.jjlcollectors.util.dbconnect.DBConnect.conn;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Hedgehog01
 */
public class DBUsers extends DBConnect
{
    private static final String TABLE_NAME = "USERDB";
    
    public static boolean findUserById(int userId)
    {
        boolean userExists = false;
        DBConnect.createDBConnection();
        try{
        Statement st = conn.createStatement();
 
        
            try (ResultSet rs = st.executeQuery("SELECT USER_ID from " + TABLE_NAME + "WHERE ID= " + userId))
            {
                userExists = rs.isBeforeFirst();
            }
        }
        catch  (SQLException sql)
        {
            System.err.println(sql);
        }
        
        DBConnect.closeDBConnection();
        return userExists;
    }
    
}
