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

/**
 * class that represents a user
 * @author Hedgehog01
 */
public class User
{
    //instance variables
    private String _firstName;
    private String _lastName;
    private String _userAddress;
    private String _postalCode;
    private String _phoneNumber;
    private String _mobileNumber;
    private String _userEmail;
    private String _userNote;
    
    private final String UNKNOWN = "UNKNOWN";
    
    //===========================================================//
    //=========================Constructors======================//
    //===========================================================//
    /**
     * default constructor.
     * all user attributes are set to default
     */
    public User ()
    {
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
     * Constructor with all mutable user attributes
     * @param firstName user first name
     * @param lastName user last name
     * @param userAddress user address
     * @param postalCode user postal code
     * @param phoneNumber user phone number
     * @param mobileNumber user mobile number
     * @param userEmail user email
     * @param userNote user note
     */
    public User (String firstName, String lastName, String userAddress, String postalCode, String phoneNumber, String mobileNumber, String userEmail, String userNote)
    {
        
    }
    
    //===========================================================//
    //=========================Methods===========================//
    //===========================================================//
    
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
    protected void setFirstName(String firstName)
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
    protected void setLastName (String lastName)
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
    protected void setUserAddress (String userAddress)
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
    public void setPostalCode (String postalCode)
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
    public void setPhoneNumber (String phoneNumber)
    {
        if (phoneNumber != null && !(phoneNumber.equals("")))
            _phoneNumber = phoneNumber;
        else
            _phoneNumber = UNKNOWN;
    }
    
    public String getUserEmail()
    {
        return _userEmail;
    }
    
    public String getUserNote()
    {
        return _userNote;
    }
    
    @Override
    public String toString ()
    {
        return "User info: " + 
                "\nFirst name: " + getFirstName() + 
                "\nLast name: " + getLastName() +
                "\nAddress: " + getUserAddress() +
                "\nPostal code: " + getPostalCode() +
                "\nPhone Number: " + getPhoneNumber() +
                "\nMobile Number: " + getUserEmail() +
                "\nUser note: " + getUserNote();
    }
}
