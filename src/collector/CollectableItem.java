package collector;

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
    protected String _itemName; //item name
    protected UUID _itemId;     //item unique uuid 
    protected StringBuilder _longNote; //item long note
    
    //========================================================================================//
    //Constructors
    //========================================================================================//
    /**
     * Default constructor - Sets item id to random UUID and default name.
     * 
     */
    protected CollectableItem ()
    {
        _itemId = UUID.randomUUID();
        setItemName(DEFAULT_NAME);
    }
    /**
     * Constructor that excepts String for object name and also sets object UUID.
     */
    protected CollectableItem (String name)
    {
        this();
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
    protected String getItemName ()
    {
        return _itemName;
    }
    
    /**
     * Method that returns item ID.
     * @return UUID representing item unique Id.
     */
    protected UUID getItemId ()
    {
        return _itemId;
    } 
    
    /**
     * Method to return item note
     * @return String item note
     */
    protected StringBuilder getItemNote()
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
     * method to add 
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
     * Method to print item details
     * @return String with item name, UUID & note
     */
    public String toString()
    {
        return "Id: " + _itemId + "\nName: " + _itemName + "\nNote: " + _longNote;
    }

}
