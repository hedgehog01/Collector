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
package com.jjlcollectors.users;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * class that represents a user
 * @author Hedgehog01
 */
public final class User
{
    //instance variables
    private static final Logger log = Logger.getLogger( User.class.getName() );
    private String _firstName;
    private String _lastName;
    private String _userAddress;
    private String _postalCode;
    private String _phoneNumber;
    private String _mobileNumber;
    private String _userEmail;
    private String _userNote;
    private UUID _userUUID;
    private byte [] _userPassword;
    private byte [] _userSalt;
    
    
    private final String UNKNOWN = "UNKNOWN";
    private final int PASS_RANDOM_LEN = 10;
    
    //===========================================================//
    //=========================Constructors======================//
    //===========================================================//
    /**
     * default constructor.
     * @param userEmail the user Email (user for login so must be valid).
     * @param userPassword the user password.
     * all other user attributes are set to default
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public User (String userEmail, char [] userPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        
        setUserUUID();
        setUserEmail(userEmail);
        setUserPassword (userPassword);
        _firstName = UNKNOWN;
        _lastName = UNKNOWN;
        _userAddress = UNKNOWN;
        _postalCode = UNKNOWN;
        _phoneNumber = UNKNOWN;
        _mobileNumber = UNKNOWN;
        _userEmail = UNKNOWN;
        _userNote = UNKNOWN;
    }
    
    /**
     * Constructor with user attributes. Random UUID created for the user.
     * @param firstName user first name
     * @param lastName user last name
     * @param userAddress user address
     * @param postalCode user postal code
     * @param phoneNumber user phone number
     * @param mobileNumber user mobile number
     * @param userEmail user email
     * @param userNote user note
     * @param userPassword user password
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public User (String firstName, String lastName, String userAddress, String postalCode, String phoneNumber, String mobileNumber, String userEmail, String userNote, char [] userPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        setUserUUID ();
        setFirstName(firstName);
        setLastName(lastName);
        setUserAddress(userAddress);
        setPostalCode(postalCode);
        setPhoneNumber(phoneNumber);
        setMobileNumber(mobileNumber);
        setUserEmail(userEmail);
        setUserNote(userNote);
        setUserPassword(userPassword);
        
    }
    
    /**
     * User constructor including UUID.
     * @param userUUID user UUID.
     * @param firstName user first name
     * @param lastName user last name
     * @param userAddress user address
     * @param postalCode user postal code
     * @param phoneNumber user phone number
     * @param mobileNumber user mobile number
     * @param userEmail user email
     * @param userNote user note
     * @param userPassword user password
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public User (UUID userUUID, String firstName, String lastName, String userAddress, String postalCode, String phoneNumber, String mobileNumber, String userEmail, String userNote,char [] userPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        this (firstName,lastName,userAddress,postalCode,phoneNumber,mobileNumber,userEmail,userNote,userPassword);
        setUserUUID(userUUID);
    }
    
    /**
     * copy constructor. Copies all User attributes except UUID & password that are generated randomly.
     * @param other the other User to copy from.
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public User (User other) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        this (other.getFirstName(),other.getLastName(),other.getUserAddress(),other.getPostalCode(),other.getPhoneNumber(),other.getMobileNumber(),other.getUserEmail(),other.getUserNote(),"".toCharArray());
        setUserUUID();
        
    }
    
    //===========================================================//
    //=========================Methods===========================//
    //===========================================================//
    
    /**
     * method to get user UUID.
     * @return the user UUID.
     */
    public UUID getUserUUID()
    {
        return _userUUID;
    }
    
    /*
    * private method to create user UUID from a String.
    */
    private void setUserUUID (String userUUID)
    {
        _userUUID = UUID.fromString(userUUID);
    }
    
    /*
    * private method to assign a new UUID to the user from a UUID.
    */
    private void setUserUUID (UUID userUUID)
    {
        _userUUID = userUUID;
    }
    
    /*
    * private method to create random UUID for the user.
    */
    private void setUserUUID ()
    {
        _userUUID = UUID.randomUUID();
    }
    /**
     * method to return user first name
     * @return user first name
     */
    public String getFirstName()
    {
        return _firstName;
    }
    
    /**
     * method to set user first name.
     * 
     * @param firstName the user first name.
     */
    private void setFirstName(String firstName)
    {
        if (firstName != null && !(firstName.equals("")))
            _firstName = firstName;
        else
            _firstName = UNKNOWN;
    }
    
    /**
     * method to return user last name.
     * @return user last name. If empty or null - set to default.
     */
    public String getLastName()
    {
        return _lastName;
    }
    
    /**
     * method to set user last name.
     * @param lastName user last name. If empty or null - set to default.
     */
    private void setLastName (String lastName)
    {
        if (lastName != null && !(lastName.equals("")))
            _lastName = lastName;
        else
            _lastName = UNKNOWN;
    }
    
    /**
     * method to return user address.
     * @return user address.
     */
    public String getUserAddress()
    {
        return _userAddress;
    }
    
    /**
     * method to set user address.
     * @param userAddress the user address. If empty or null - set to default.
     */
    private void setUserAddress (String userAddress)
    {
        if (userAddress != null && !(userAddress.equals("")))
            _userAddress = userAddress;
        else
            _userAddress = UNKNOWN;
    }
    
    /**
     * method to get user postal code.
     * @return user postal code. If empty or null - set to default.
     */
    public String getPostalCode()
    {
        return _postalCode;
    }
    
    /**
     * method to set user postal code.
     * @param postalCode the user postal code. 
     */
    private void setPostalCode (String postalCode)
    {
        if (postalCode != null && !(postalCode.equals("")))
            _postalCode = postalCode;
        else
            _postalCode = UNKNOWN;
    }
    
    /**
     * method to get phone number.
     * @return the user phone number.
     */
    public String getPhoneNumber()
    {
        return _phoneNumber;
    }
    
    /**
     * method to set phone number.
     * 
     * @param phoneNumber the user phone number. If empty or null - set to default.
     */
    private void setPhoneNumber (String phoneNumber)
    {
        if (phoneNumber != null && !(phoneNumber.equals("")))
            _phoneNumber = phoneNumber;
        else
            _phoneNumber = UNKNOWN;
    }
    
    /**
     * method to get user mobile number.
     * @return the user mobile number.
     */
    public String getMobileNumber()
    {
        return _mobileNumber;
    }
    
    /**
     * method to set user mobile number.
     * @param mobileNumber the user mobile number.
     */
    private void setMobileNumber(String mobileNumber)
    {
         if (mobileNumber != null && !(mobileNumber.equals("")))
            _mobileNumber = mobileNumber;
        else
            _mobileNumber = UNKNOWN;       
    }
    /**
     * method to get the use email.
     * @return user email.
     */
    public String getUserEmail()
    {
        return _userEmail;
    }
    
    /**
     * method o set user email
     * @param userEmail the user email to set
     */
    private void setUserEmail (String userEmail)
    {
        if (userEmail != null && !(userEmail.equals("")))
            _userEmail = userEmail;
        else
            _userEmail = UNKNOWN;
    }
    
    /**
     * method to get user note.
     * @return the user note.
     */
    public String getUserNote()
    {
        return _userNote;
    }
    
    /**
     * method to set user note.
     * @param userNote the user note.
     */
    private void setUserNote(String userNote)
    {
         if (userNote != null && !(userNote.equals("")))
            _userNote = userNote;
        else
            _userNote = UNKNOWN;       
    }
    
    /*
    * method to set the user password.
    * if the password is invalid a Random password is generated.
    */
    private void setUserPassword (char [] userPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        log.log(Level.INFO, "starting password saving");
         if (userPassword != null && (userPassword.length > 6))
         {
             log.log(Level.INFO, "create salt");
             setUserSalt();
             PasswordEncryptionService password = new PasswordEncryptionService();
             log.log(Level.INFO, "Attemp encrypt password");
             _userPassword = password.getEncryptedPassword(userPassword, _userSalt);             
         }
            
        else
         {
             log.log(Level.SEVERE, "illegal password entered and reached here. attempt to create random password instead. should not happen!!");
             char [] pass = createRandomPassword(10).toCharArray();
             log.log(Level.INFO, "create salt if doesn't exist");
             if (_userSalt == null || _userSalt.length <=10 )
             {
                 log.log(Level.INFO, "salt doesn't exist and will be created");
                 setUserSalt();
             }
             PasswordEncryptionService password = new PasswordEncryptionService();
             log.log(Level.INFO, "Attemp encrypt password");
             _userPassword = password.getEncryptedPassword(pass, _userSalt);
             
         }
    }
    
    public byte [] getUserPassword ()
    {
        return _userPassword;
    }
       
    /*
    * method to create a new user salt needed for one way password encryption.
    */
    private void setUserSalt () throws NoSuchAlgorithmException
    {
        PasswordEncryptionService salt = new PasswordEncryptionService();
        
        _userSalt = salt.generateSalt();
    }
    
    /**
     * method to retrieve user salt
     * @return salt
     */
    public byte[] getUserSalt ()
    {
        return _userSalt;
                
    }
    /*
    * method that creates random password for when user enters invalid password.
    * the password length is set by input int.
    */
    private String createRandomPassword(int len)
    {

        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ ) 
           sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();

    }
    @Override
    public String toString ()
    {
        return "User info: " + 
                "\nUUID: " + getUserUUID() +
                "\nFirst name: " + getFirstName() + 
                "\nLast name: " + getLastName() +
                "\nAddress: " + getUserAddress() +
                "\nPostal code: " + getPostalCode() +
                "\nPhone Number: " + getPhoneNumber() +
                "\nMobile Number: " + getMobileNumber() +
                "\nEmail: " + getUserEmail() +
                "\nUser note: " + getUserNote();
    }
}
