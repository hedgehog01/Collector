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

import com.jjlcollectors.users.PasswordEncryptionService;
import com.jjlcollectors.users.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hedgehog01
 */
public final class DBUsersConnect extends DBConnect
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
            PreparedStatement prepStamt = conn.prepareStatement("SELECT USER_ID from " + TABLE_NAME + "WHERE ID = ?");
            prepStamt.setInt(1, userId);

            try (ResultSet rs = prepStamt.executeQuery())
            {
                userExists = rs.isBeforeFirst();
            }
        } catch (SQLException sql)
        {
            log.log(Level.SEVERE, "find user by ID failed with error: {0}",sql);
        }
        
        DBConnect.closeDBConnection();
        return userExists;
    }

    /**
     * Method to add a new user to the DB
     *
     * @param user the user to add
     */
    public static boolean addUser(User user)
    {
        boolean userAdded = false;
        log.log(Level.INFO, "Call to addUser method");
        userAdded = addUser(user.getFirstName(), user.getLastName(), user.getUserAddress(), user.getPostalCode(), user.getPhoneNumber(), user.getMobileNumber(), user.getUserEmail(), user.getUserNote(), user.getUserUUID(), user.getUserPassword(), user.getUserSalt(), user.getUserRegistrationTime());
        log.log(Level.INFO, "User add status {0}", userAdded);
        //createDBConnection();
        log.log(Level.INFO, "Select * from users...");
        selectAllUsers();
        return userAdded;
    }

    private static boolean addUser(String firstName, String lastName, String userAddress, String postalCode, String phoneNumber, String mobileNumber, String userEmail, String userNote, UUID userUUID, byte[] userPassword, byte[] userSalt, Timestamp userRegTime)
    {
        boolean userAdded = false;
        DBConnect.createDBConnection();
        log.log(Level.INFO, "Attemp to add new user to DB...");

        try
        {
            log.log(Level.INFO, "Table name: {0}", TABLE_NAME);
            log.log(Level.INFO, "User time stamp: {0}", userRegTime);
            String sqlStatement = "INSERT INTO " + TABLE_NAME + " (FIRST_NAME,LAST_NAME,ADDRESS,POSTAL_CODE,PHONE_NUM,MOBILE_NUM,MAIN_EMAIL,USER_NOTE,USER_UUID,USER_PASSWORD,USER_SALT,USER_REGISTRATION_TIME) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            int updateCount;
            try (PreparedStatement prepstmt = conn.prepareStatement(sqlStatement))
            {
                prepstmt.setString(1, firstName);
                prepstmt.setString(2, lastName);
                prepstmt.setString(3, userAddress);
                prepstmt.setString(4, postalCode);
                prepstmt.setString(5, phoneNumber);
                prepstmt.setString(6, mobileNumber);
                prepstmt.setString(7, userEmail);
                prepstmt.setString(8, userNote);
                prepstmt.setString(9, userUUID.toString());
                prepstmt.setBytes(10, userPassword);
                prepstmt.setBytes(11, userSalt);
                prepstmt.setTimestamp(12, userRegTime);
                updateCount = prepstmt.executeUpdate();
                log.log(Level.INFO, "Add user update count is: {0}", updateCount);
            }
            DBConnect.closeDBConnection();

            if (updateCount == 1)
            {
                log.log(Level.INFO, "User added and return value set to true");
                userAdded = true;
            } else
            {
                log.log(Level.SEVERE, "User NOT added and return value remains false");
            }
        } catch (SQLException e)
        {
            log.log(Level.SEVERE, "SQL error while tring to add a new user. SQL message: {0} SQL Error code: {1} Stack trace: {2}", new Object[]
            {
                e.getMessage(), e.getErrorCode(), e.getStackTrace()
            });
        }
        return userAdded;
    }

    /**
     * method to search if user with specific email exists in the user DB.
     *
     *
     * @param userEmail the email to search.
     * @return true if user that uses this Email exists.
     */
    public static boolean findUserByEmail(String userEmail)
    {
        boolean userExists = false;
        DBConnect.createDBConnection();
        try
        {
            PreparedStatement prepStmt = conn.prepareStatement("SELECT USER_ID FROM " + TABLE_NAME + " WHERE MAIN_EMAIL = ?");
            prepStmt.setString(1, userEmail);
            ResultSet rs = prepStmt.executeQuery();
            userExists = rs.next();
            log.log(Level.INFO, "check user by Email OK. user exists status: {0}", userExists);
        } catch (SQLException sql)
        {
            log.log(Level.SEVERE, "check user by Email failed. Error: {0}", sql.getMessage());
            log.log(Level.SEVERE, "SELECT * from {0} WHERE MAIN_EMAIL = {1}", new Object[]
            {
                TABLE_NAME, userEmail
            });
        } //in any case close connection and return state.
        DBConnect.closeDBConnection();
        return userExists;
    }

    /*
     * method that checks if user password is valid.
     * return true if it is.
     */
    public static boolean isUserPasswordValid(String userEmail, char[] attemptedPassword)
    {
        boolean passwordValid = false;
        byte[] password = null;
        byte[] salt = null;

        //Use private method to retrieve password and salt for authentication.
        password = getUserPassword(userEmail);
        salt = getUserSalt(userEmail);

        //make sure password and salt are not null
        if (password != null && salt != null)
        {
            try
            {
                passwordValid = new PasswordEncryptionService().authenticate(attemptedPassword, password, salt);
                log.log(Level.INFO, "Password & salt are not null, attempted validate result is: {0}", new Object[]
                {
                    passwordValid
                });
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex)
            {
                log.log(Level.SEVERE, "Exception while trying to authenticate password.\nException msg: {0}", ex);
            }

        }

        return passwordValid;
    }
    /*
     * method to get binary password in DB by user email.
     */

    private static byte[] getUserPassword(String userEmail)
    {
        byte[] password = null;
        DBConnect.createDBConnection();
        try
        {
            log.log(Level.INFO, "Attempting get user password from DB with email {0}", new Object[]
            {
                userEmail
            });
            try (PreparedStatement prepStmt = conn.prepareStatement("SELECT USER_PASSWORD from " + TABLE_NAME + " WHERE MAIN_EMAIL = ?"))
            {
                prepStmt.setString(1, userEmail);
                ResultSet rs = prepStmt.executeQuery();
                
                if (rs.next())
                {
                    Blob blob = rs.getBlob("USER_PASSWORD");
                    long blobLength = blob.length();
                    
                    int pos = 1; // position is 1-based
                    int len = (int) blobLength;
                    password = blob.getBytes(pos, len);
                    log.log(Level.INFO, "Getting user password from DB with email {0} OK.\nBlob length: {1}", new Object[]
                    {
                        userEmail, len
                    });
                    //InputStream is = blob.getBinaryStream();
                    //int b = is.read();
                }
                rs.close();
            }
            conn.close();
        } catch (SQLException e)
        {
            log.log(Level.SEVERE, "Getting user password from DB with email {0} failed.\nSQL error: {1}", new Object[]
            {
                userEmail, e.getErrorCode()
            });
        } catch (Exception e)
        {
            log.log(Level.SEVERE, "Getting user password from DB with email {0} failed.\nException error: {1}", new Object[]
            {
                userEmail, e.getMessage()
            });
        }

        return password;
    }
    /*
     * method to get binary user Salt in DB by user email.
     */

    private static byte[] getUserSalt(String userEmail)
    {
        byte[] Salt = null;
        DBConnect.createDBConnection();
        try
        {
            log.log(Level.INFO, "Attempting get user USER_SALT from DB with email {0}", new Object[]
            {
                userEmail
            });
            try (PreparedStatement prepStmt = conn.prepareStatement("SELECT USER_SALT from " + TABLE_NAME + " WHERE MAIN_EMAIL = ?"))
            {
                prepStmt.setString(1, userEmail);
                ResultSet rs = prepStmt.executeQuery();
                
                if (rs.next())
                {
                    Blob blob = rs.getBlob("USER_SALT");
                    long blobLength = blob.length();
                    
                    int pos = 1; // position is 1-based
                    int len = (int) blobLength;
                    Salt = blob.getBytes(pos, len);
                    log.log(Level.INFO, "Getting user USER_SALT from DB with email {0} OK.\nBlob length: {1}", new Object[]
                    {
                        userEmail, len
                    });
                    //InputStream is = blob.getBinaryStream();
                    //int b = is.read();
                }
                rs.close();
            }
            conn.close();
        } catch (SQLException e)
        {
            log.log(Level.SEVERE, "Getting user Salt from DB with email {0} failed.\nSQL error: {1}", new Object[]
            {
                userEmail, e.getErrorCode()
            });
        } catch (Exception e)
        {
            log.log(Level.SEVERE, "Getting user Salt from DB with email {0} failed.\nException error: {1}", new Object[]
            {
                userEmail, e.getMessage()
            });
        }

        return Salt;
    }

    /*
     *method to print all users in DB
     */
    private static void selectAllUsers()
    {
        try
        {
            DBConnect.createDBConnection();
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
                Timestamp userRegTime = null;
                Timestamp lastLoginTime = null;
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
                    String userNote = results.getString(10);
                    String userUUID = results.getString(11);
                    String userPass = results.getString(12);
                    String userSalt = results.getString(13);
                    
                    boolean tempPassword = results.getBoolean(16);
                    if (results.getTimestamp("USER_REGISTRATION_TIME") != null)
                    {
                        userRegTime = results.getTimestamp("USER_REGISTRATION_TIME");
                    }
                    else
                    {
                        userRegTime = null;
                    }                        
                    if (results.getTimestamp("USER_LAST_LOGIN_TIME") != null)
                    {
                        lastLoginTime = results.getTimestamp("USER_LAST_LOGIN_TIME");
                    }
                    else
                    {
                        lastLoginTime = null;
                    }

                    System.out.println(id + "\t" + firstName + "\t" + LastName + "\t" + Address + "\t" + PostalCode + "\t" + phoneNum + "\t" + MobileNum + "\t" + email + "\t" + userNote + "\t" + userUUID + "\t" + userPass + "\t" + userSalt + "\t" + userRegTime + "\t" + lastLoginTime + "\t" + tempPassword);

                }
            }

        } catch (SQLException e)
        {
            log.log(Level.SEVERE, "Printing of user DB failed, sql error: {0}", e.getErrorCode());
        } catch (Exception e)
        {
            log.log(Level.SEVERE, "Printing of user DB failed, exception error: {0}", e.getMessage());
        }
        DBConnect.closeDBConnection();
    }

}
