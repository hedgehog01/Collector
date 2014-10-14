package com.jjlcollectors.collectables;

import java.time.LocalDate;
import java.util.UUID;


/**
 * Abstract class that represents a collectable item.
 * 
 * @author Nathan Randelman
 * @version 08.09.2014
 */
public abstract class CollectableItem
{
    //Instance variables
    private final String DEFAULT_NAME = "Default_name";
    private final String DEFAULT_PRICE = "-1.0";
    private final String DEFAULT_VALUE = "-1.0";
    private final int DEFAULT_USER_ID = -1;
    protected String _itemName; //item name
    protected UUID _itemId;     //item unique uuid 
    protected StringBuilder _longNote; //item long note
    protected LocalDate _buyDate; //item purchase date
    protected String _buyPrice; //item purchase price
    protected String _itemValue; //current item value
    protected int _userId; //id of user who owns the item
    
    
    //========================================================================================//
    //Constructors
    //========================================================================================//
    /**
     * Default constructor - Sets item id to random UUID and default name.
     * @param userID the id of the user who the item belongs to.
     */
    protected CollectableItem (int userID)
    {
        setUserId(userID);
        _itemId = UUID.randomUUID();
        _buyDate = LocalDate.now();
        setItemName(DEFAULT_NAME);
        setItemValue(DEFAULT_VALUE);
        setItemBuyPrice(DEFAULT_PRICE);
        
    }
    /**
     * Constructor that excepts String for object name and also sets object UUID.
     * @param userID the user ID.
     * @param name the user name.
     */
    protected CollectableItem (int userID,String name)
    {
        this(userID);
        setItemName(name);
    }

    //========================================================================================//
    //Methods
    //========================================================================================//
    /**
     * Method that sets item name
     * @param name the item name
     */
    protected void setItemName (String name)
    {
        _itemName = name;
    }

    /**
     * Method that returns item Name.
     * @return String representing item name.
     */
    public String getItemName ()
    {
        return _itemName;
    }
    
    /**
     * Method that returns item ID.
     * @return UUID representing item unique Id.
     */
    public UUID getItemId ()
    {
        return _itemId;
    } 
    
    /**
     * Method to return item note
     * @return String item note
     */
    public StringBuilder getItemNote()
    {
        return _longNote;
    }
    
    /**
     * Method to set item note.
     * @param note the item note to be set
     */
    protected void setItemNote(StringBuilder note)
    {
        _longNote = note;
    }
    
    /**
     * method to add more info to item note.
     * @param note the info to add to coin note.
     */
    protected void appendItemNote (String note)
    {
        if (note.charAt(0)==' ')
            _longNote.append(note);
        else
        {
            _longNote.append(" ");
            _longNote.append(note);
        }
    }
    
    /**
     * method to set the item buy date
     * @param date the item buy date
     */
    protected void setItemBuyDate(LocalDate date)
    {
        _buyDate = date;        
    }
    
    /**
     * method to return the item buy date.
     * @return LocalDate the item buy date.
     */
    public LocalDate getItemBuyDate()
    {
        return _buyDate;
    }
    
     /**
     * method to set the item buy price
     * @param price the item buy price
     */
    protected void setItemBuyPrice (String price)
    {
        if (isNumeric(price))
            _buyPrice = price;   
    }
    
    /**
     * method to return the item buy price.
     * @return String the item buy price.
     */
    public String getBuyPrice()
    {
        return _buyPrice;
    } 

     /**
     * method to set the item value.
     * @param value the item value
     */
    protected void setItemValue (String value)
    {
        if (isNumeric(value))
            _itemValue = value;
    }
    
    /**
     * method to return the item item value.
     * @return String the item item value.
     */
    public String getItemValue()
    {
        return _itemValue;
    }     
    
    /**
     * method to return item owner by user ID
     * @return int user id
     */
    public int getUserId()
    {
        return _userId;
    }
    
    protected void setUserId (int userId)
    {
        if (isUserIdValid(userId))
            _userId = userId;
        else
            _userId = DEFAULT_USER_ID;
    }
    
    /**
     * Method to print item details
     * @return a string representation of the object.
     */
    public String toString()
    {
        return "Id: " + _itemId + "\nName: " + _itemName + "\nNote: " + _longNote;
    }

    /*
    private method that checks if number in the string variable is numeric.
    */
    private static boolean isNumeric(String str)  
    {  
        try  
        {  
            double d = Double.parseDouble(str);  
        }  
        catch(NumberFormatException nfe)  
        {  
            return false;  
        }  
        return true;  
    }
    
    private boolean isUserIdValid (int userId)
    {
        //make sure user id is not zero or less
        return userId > 0; //To Do
    }
}
