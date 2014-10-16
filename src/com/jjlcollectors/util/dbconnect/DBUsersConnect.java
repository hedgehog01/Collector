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

import com.jjlcollectors.users.User;
import static com.jjlcollectors.util.dbconnect.DBConnect.conn;
import static com.jjlcollectors.util.dbconnect.DBConnect.stmt;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hedgehog01
 */
public class DBUsersConnect extends DBConnect
{

    private static final String TABLE_NAME = "USERDB";
    private static final Logger log = Logger.getLogger(DBUsersConnect.class.getName());

    /**
     * method to find user by the userId
     *
     * @param userId the userId to search
     * @return true if user Id exists
     */
    public static boolean findUserById(int userId)
    {
        boolean userExists = false;
        DBConnect.createDBConnection();
        try
        {
            Statement st = conn.createStatement();

            try (ResultSet rs = st.executeQuery("SELECT USER_ID from " + TABLE_NAME + "WHERE ID= " + userId))
            {
                userExists = rs.isBeforeFirst();
            }
        } catch (SQLException sql)
        {
            System.err.println(sql);
        }

        DBConnect.closeDBConnection();
        return userExists;
    }

    /**
     * method to check if user credentials are valid.
     *
     * @param userName the user name
     * @param userPassword the password
     * @return true if credentials are valid.
     */
    public static boolean checkUserCredentials(String userName, String userPassword)
    {
        System.out.println(userName);
        System.out.println(userPassword);
        return (userName.equals("Nathan") && (userPassword.equals("password")));
    }

    /**
     * Method to add a new user to the DB
     *
     * @param user the user to add
     */
    public static void addUser(User user)
    {
        log.log(Level.INFO, "Call to addUser method");
        addUser(user.getFirstName(), user.getLastName(), user.getUserAddress(), user.getPostalCode(), user.getPhoneNumber(), user.getMobileNumber(), user.getUserEmail(), user.getUserNote(), user.getUserUUID(), user.getUserPassword(),user.getUserSalt());
        //createDBConnection();
        log.log(Level.INFO, "Select * from users...");
        selectAllUsers();
    }

    private static void addUser(String firstName, String lastName, String userAddress, String postalCode, String phoneNumber, String mobileNumber, String userEmail, String userNote, UUID userUUID, byte[] userPassword, byte[] userSalt)
    {
        DBConnect.createDBConnection();
        System.out.println("Attemp to add new user to DB...");
        try {
            System.out.println("Table name: " + TABLE_NAME);
            String sqlStatement = "INSERT INTO " + TABLE_NAME +" (FIRST_NAME,LAST_NAME,ADDRESS,POSTAL_CODE,PHONE_NUM,MOBILE_NUM,MAIN_EMAIL,USER_NOTE,USER_UUID,USER_PASSWORD,USER_SALT) VALUES " +"('" + firstName + "','" + lastName + "','" + userAddress + "','" + postalCode + "','" + phoneNumber + "','" + mobileNumber + "','" + userEmail + "','" + userNote + "','" + userUUID + "',?,?)";        
            PreparedStatement prepstmt = conn.prepareStatement(sqlStatement);
            prepstmt.setBytes(1, userPassword);
            prepstmt.setBytes(2, userSalt);
            prepstmt.execute();
            prepstmt.close();
            
            /*
            System.out.println("Table name: " + TABLE_NAME);
            stmt = conn.createStatement();
            stmt.execute("INSERT INTO " + TABLE_NAME +" (FIRST_NAME,LAST_NAME,ADDRESS,POSTAL_CODE,PHONE_NUM,MOBILE_NUM,MAIN_EMAIL,USER_NOTE,USER_UUID,USER_PASSWORD,USER_SALT) VALUES" 
            + " ('" + firstName + "','" + lastName + "','" + userAddress + "','" + postalCode + "','" + phoneNumber + "','" + mobileNumber + "','" + userEmail + "','" + userNote + "','" + userUUID + "','" + userPassword + "','" + userSalt + "')");        
            //+ " ('" + firstName + "','" + lastName + "','" + userAddress + "','" + postalCode + "','" + phoneNumber + "','" + mobileNumber + "','" + userEmail + "','" + userNote + "','" + userUUID + "','" + userPassword + "'," + userSalt + ")");
              */      
            DBConnect.closeDBConnection();
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }
    }

    /**
     * method to verify no user exists in the user DB with duplicate email
     * address
     *
     * @param userEmail the email to search.
     * @return true if no other user uses this Email.
     */
    public boolean checkNoUserDupEmail(String userEmail)
    {
        boolean userExists = false;
        DBConnect.createDBConnection();
        try
        {
            Statement st = conn.createStatement();

            try (ResultSet rs = st.executeQuery("SELECT USER_ID from " + TABLE_NAME + "WHERE EMAIL= " + userEmail))
            {
                userExists = rs.isBeforeFirst();
            }
        } catch (SQLException sql)
        {
            log.log(Level.SEVERE, "check user by Email failed. Error: {0}", sql.getMessage());
        } //in any case close connection and return state.
        finally
        {
            DBConnect.closeDBConnection();
            return userExists;
        }

    }

    private static void selectAllUsers()
    {
        try
        {
            DBConnect.createDBConnection();
            stmt = conn.createStatement();
            log.log(Level.INFO, "prepare statement starting");
            
            PreparedStatement statement = conn.prepareStatement("SELECT * from " + TABLE_NAME);
            log.log(Level.INFO, "prepare statement done");
            try (ResultSet results = statement.executeQuery())
            {
                ResultSetMetaData rsmd = results.getMetaData();
                int numberCols = rsmd.getColumnCount();
                log.log(Level.INFO, "print colomn names from user DB");
                for (int i = 1; i <= numberCols; i++)
                {
                    //print Column Names
                    System.out.print(rsmd.getColumnLabel(i) + "\t");
                }

                System.out.println("\n-------------------------------------------------");

                while (results.next())
                {
                    int id = results.getInt(1);
                    String firstName = results.getString(2);
                    String LastName = results.getString(3);
                    String Address = results.getString(4);
                    String PostalCode = results.getString(5);
                    String phoneNum = results.getString(6);
                    String MobileNum = results.getString(7);
                    String email = results.getString(8);
                    String userNote = results.getString(9);
                    String userUUID = results.getString(10);
                    String userPass = results.getString(11);
                    String userSalt = results.getString(12);
                    

                    System.out.println(id + "\t" + firstName + "\t" + LastName + "\t" + Address + "\t" + PostalCode + "\t" + phoneNum + "\t" + MobileNum + "\t" + email + "\t" + userNote + "\t" + userUUID + "\t" + userPass + "\t" + userSalt);
                }
            }

            stmt.close();

        } catch (SQLException e )
        {
            log.log(Level.SEVERE, "Printing of user DB failed, sql error: {0}",e.getErrorCode());
        } catch (Exception e)
        {
            log.log(Level.SEVERE, "Printing of user DB failed, exception error: {0}",e.getMessage());
        }
        DBConnect.closeDBConnection();
    }

}
