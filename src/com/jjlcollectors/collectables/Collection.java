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

import java.util.UUID;
import java.util.logging.Logger;

/**
 * class that represents abstract collection
 * @author nathanr
 */
public final class Collection
{
    protected UUID _collectionUUID;
    protected UUID _userUUID;
    protected String _collectionName;
    protected CollectionType _collectionType;
    protected String _CollectionNote;
    private static final Logger LOG = Logger.getLogger(Collection.class.getName());

    /**
     * Collection constructor
     * @param collectionUUID collection UUID (UUID)
     * @param userID USER ID (UUID)
     * @param collectionName Collection name
     * @param collectionType Collection type (ENUM)
     * @param CollectionNote Collection note
     */
    public Collection(UUID collectionUUID, UUID userID, String collectionName, CollectionType collectionType, String CollectionNote)
    {
        this._collectionUUID = collectionUUID;
        this._userUUID = userID;
        this._collectionName = collectionName;
        this._collectionType = collectionType;
        this._CollectionNote = CollectionNote;
    }
    
    /**
     * Collection constructor.
     * Collection UUID is randomly generated.
     * @param userID User ID (UUID)
     * @param collectionName Collection name
     * @param collectionType Collection type (CollectioType)
     * @param CollectionNote collection Note
     */
    public Collection(UUID userID, String collectionName, CollectionType collectionType, String CollectionNote)
    {
        Collection.this.setCollectionUUID();
        this._userUUID = userID;
        this._collectionName = collectionName;
        this._collectionType = collectionType;
        this._CollectionNote = CollectionNote;
    }

    
    
    /**
     * return collection unique ID (UUID)
     * @return UUID of the collection
     */
    public UUID getCollectionUUID()
    {
        return _collectionUUID;
    }
    
    
    protected void setCollectionUUID(UUID collectionID)
    {
        this._collectionUUID = collectionID;
    }
    
    /**
     * method that creates random UUID for collection UUID
     */
    protected void setCollectionUUID()
    {
        this._collectionUUID = UUID.randomUUID();
    }

    public UUID getUserUUID()
    {
        return _userUUID;
    }

    protected void setUserUUID(UUID userUUID)
    {
        this._userUUID = userUUID;
    }

    public String getCollectionName()
    {
        return _collectionName;
    }

    protected void setCollectionName(String collectionName)
    {
        this._collectionName = collectionName;
    }

    public CollectionType getCollectionType()
    {
        return _collectionType;
    }

    protected void setCollectionType(CollectionType collectionType)
    {
        this._collectionType = collectionType;
    }

    public String getCollectionNote()
    {
        return _CollectionNote;
    }

    protected void setCollectionNote(String CollectionNote)
    {
        this._CollectionNote = CollectionNote;
    }
    
    
    
    
}
