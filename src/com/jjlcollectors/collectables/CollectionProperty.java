/*
 * Copyright (C) 2014 nathanr
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.jjlcollectors.collectables;


import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author nathanr
 */
public final class CollectionProperty
{
    private static final Logger LOG = Logger.getLogger(CollectionProperty.class.getName());
    //Property instance variables

    //private final IntegerProperty collectionNumberOfItems = new SimpleIntegerProperty(-1);
    private final StringProperty collectionName = new SimpleStringProperty("");
    private final StringProperty collectionType = new SimpleStringProperty("");
    private final StringProperty collectionNote = new SimpleStringProperty("");
    private final StringProperty collectionUUID = new SimpleStringProperty("");
    private final IntegerProperty collectionNumberOfItems = new SimpleIntegerProperty(-1);

    public CollectionProperty()
    {
        this ("",CollectionType.COIN,"","",-1);
    }
    
    public CollectionProperty(String coinName, CollectionType collectionType, String collectionNote,String collectionUUID,int numberOfItems)
    {
        setCollectionName(coinName);
        setCollectionType(collectionType.name());
        setCollectionNote(collectionNote);
        setCollectionUUID(collectionUUID);
        setCollectionNumberOfItems(numberOfItems);

    }


    
    
    public int getCollectionNumberOfItems()
    {
        return collectionNumberOfItems.get();
    }

    public void setCollectionNumberOfItems(int value)
    {
        collectionNumberOfItems.set(value);
    }

    public IntegerProperty collectionNumberOfItemsProperty()
    {
        return collectionNumberOfItems;
    }
    
    
    public String getCollectionUUID()
    {
        return collectionUUID.get();
    }

    public void setCollectionUUID(String value)
    {
        collectionUUID.set(value);
    }

    public StringProperty collectionUUIDProperty()
    {
        return collectionUUID;
    }
    

    public String getCollectionNote()
    {
        return collectionNote.get();
    }

    public void setCollectionNote(String value)
    {
        collectionNote.set(value);
    }

    public StringProperty collectionNoteProperty()
    {
        return collectionNote;
    }
    
    
    public String getCollectionType()
    {
        return collectionType.get();
    }

    public void setCollectionType(String value)
    {
        collectionType.set(value);
    }

    public StringProperty collectionTypeProperty()
    {
        return collectionType;
    }
    
    
    public String getCollectionName()
    {
        return collectionName.get();
    }

    public void setCollectionName(String value)
    {
        collectionName.set(value);
    }

    public StringProperty collectionNameProperty()
    {
        return collectionName;
    }
    
    
}
