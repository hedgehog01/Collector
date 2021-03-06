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
package com.jjlcollectors.util.dbconnect;

import com.jjlcollectors.collectables.Collection;
import com.jjlcollectors.collectables.CollectionProperty;
import com.jjlcollectors.collectables.CollectionType;
import static com.jjlcollectors.util.dbconnect.DBConnect.conn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * class to manage creation and connection to a collection
 *
 * @author nathanr
 */
public final class DBCollectionConnect extends DBConnect
{
    
    private static final String TABLE_NAME = "COLLECTION_DB";
    private static final Logger log = Logger.getLogger(DBCollectionConnect.class.getName());

    
    
    public static boolean addCollection (Collection collection)
    {
        boolean collectionCreated = false;
        log.log(Level.INFO, "Attempting to create collection");
        collectionCreated = addCollection(collection.getCollectionUUID(),collection.getCollectionName(),collection.getCollectionType(),collection.getCollectionNote(),collection.getUserUUID());

        return collectionCreated;
    }
    
    /*
    * private method to store the collection in the DB
    */
    private static boolean addCollection(UUID collectionUUID, String collectionName, CollectionType collectionType, String collectionNote, UUID userUUID)
    {
        boolean collectionCreated = false;
        DBConnect.createDBConnection();
        log.log(Level.INFO, "Attemp to add new colllection to DB...");

        try
        {
            log.log(Level.INFO, "Table name: {0}", TABLE_NAME);
            
            String sqlStatement = "INSERT INTO " + TABLE_NAME + " (COLLECTION_UUID,COLLECTION_NAME,COLLECTION_TYPE,COLLECTION_NOTE,USER_UUID) VALUES (?,?,?,?,?)";
            int updateCount;
            try (PreparedStatement prepstmt = conn.prepareStatement(sqlStatement))
            {
                prepstmt.setString(1, collectionUUID.toString());
                prepstmt.setString(2, collectionName);
                prepstmt.setString(3, collectionType.name());
                prepstmt.setString(4, collectionNote);
                prepstmt.setString(5, userUUID.toString());
                updateCount = prepstmt.executeUpdate();
                log.log(Level.INFO, "Add collection update count is: {0}", updateCount);
            }
            DBConnect.closeDBConnection();
            //shutDownDBConnection();

            if (updateCount == 1)
            {
                log.log(Level.INFO, "Collection added and return value set to true");
                collectionCreated = true;
            } else
            {
                log.log(Level.SEVERE, "Collection NOT added and return value remains false");
            }
        } catch (SQLException e)
        {
            log.log(Level.SEVERE, "SQL error while tring to add a new Collection. \nSQL message: {0} SQL Error code: {1} Stack trace: {2}", new Object[]
            {
                e.getMessage(), e.getErrorCode(), e.getStackTrace()
            });
        }
        
        return collectionCreated;
    }

    
    
    /**
     * method to get collection UUID by name
     *
     * @param colllectionName the collection name
     * @return UUID of the collection
     */
    public static UUID getCollectionUUID(String colllectionName)
    {

        UUID collectionUUID = null;

        DBConnect.createDBConnection();
        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT COLLECTION_UUID from " + TABLE_NAME + " WHERE COLLECTION_NAME = ?"))
        {
            prepStmt.setString(1, colllectionName);
            ResultSet rs = prepStmt.executeQuery();

            if (rs.next())
            {
                collectionUUID = UUID.fromString(rs.getString("COLLECTION_UUID"));

            }
            rs.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBUsersConnect.class.getName()).log(Level.SEVERE, "Couldn't get collectionUUUID by collection Name\n ", ex);
        }

        DBConnect.closeDBConnection();
        //shutDownDBConnection();
        return collectionUUID;

    }
    
    /**
     * Method to get a collection name
     * @param collectionUUID the collection UUID used in order to get collection name
     * @return the collection name if found or empty if none found
     */
    public static String getCollectionName(UUID collectionUUID)
    {

        String collectionName = "";
        log.log(Level.INFO, "Attemp to get collection name");
        DBConnect.createDBConnection();
        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT COLLECTION_NAME from " + TABLE_NAME + " WHERE COLLECTION_UUID = ?"))
        {
            prepStmt.setString(1, collectionUUID.toString());
            ResultSet rs = prepStmt.executeQuery();

            if (rs.next())
            {
                collectionName = rs.getString("COLLECTION_NAME");
                log.log(Level.INFO, "Got collection name: ",collectionName);
            }
            rs.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBUsersConnect.class.getName()).log(Level.SEVERE, "Couldn't get collection Name by collectionUUID, exception:\n ", ex);
        }

        DBConnect.closeDBConnection();
        //shutDownDBConnection();
        return collectionName;

    }
    
    /**
     * Method to get a collection type
     * @param collectionUUID the collection UUID used in order to get collection type
     * @return the collection type if found or empty if none found
     */
    public static String getCollectionType(UUID collectionUUID)
    {

        String collectionType = "";
        log.log(Level.INFO, "Attemp to get collection type");
        DBConnect.createDBConnection();
        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT COLLECTION_TYPE from " + TABLE_NAME + " WHERE COLLECTION_UUID = ?"))
        {
            prepStmt.setString(1, collectionUUID.toString());
            ResultSet rs = prepStmt.executeQuery();

            if (rs.next())
            {
                collectionType = rs.getString("COLLECTION_TYPE");
                log.log(Level.INFO, "Got collection type: ",collectionType);
            }
            rs.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBUsersConnect.class.getName()).log(Level.SEVERE, "Couldn't get collection Type by collectionUUID, exception:\n ", ex);
        }

        DBConnect.closeDBConnection();
        //shutDownDBConnection();
        return collectionType;
    }
    
    
    public static ObservableList<CollectionProperty> getUserCollections (UUID userUUID)
    {
        log.log(Level.INFO, "Attempting to get collections by userUUID");
        ObservableList<CollectionProperty> userCollectionData = FXCollections.observableArrayList();
        DBConnect.createDBConnection();
        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * from " + TABLE_NAME + " WHERE USER_UUID = ?"))
        {
            prepStmt.setString(1, userUUID.toString());
            ResultSet rs = prepStmt.executeQuery();

            while (rs.next())
            {
                String collectionName = rs.getString("COLLECTION_NAME");
                String collectionType = rs.getString("COLLECTION_TYPE");
                String collectionNote = rs.getString("COLLECTION_NOTE");
                String collectionUUID = rs.getString("COLLECTION_UUID");
                
                CollectionProperty collectionProperty = new CollectionProperty(collectionName, collectionType, collectionNote, collectionUUID);
                userCollectionData.add(collectionProperty);
            }
            rs.close();
        } catch (SQLException ex)
        {
            log.log(Level.SEVERE, "Couldn't get collections by user ", ex);
        }

        DBConnect.closeDBConnection();
        //shutDownDBConnection();

        return userCollectionData;
    }
    
    public static CollectionProperty getCollectionProperty (UUID collectionUUID)
    {
        log.log(Level.INFO, "Attempting to get collection by collectionUUID");
        CollectionProperty collectionProperty = null;
        DBConnect.createDBConnection();
        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * from " + TABLE_NAME + " WHERE COLLECTION_UUID = ?"))
        {
            prepStmt.setString(1, collectionUUID.toString());
            ResultSet rs = prepStmt.executeQuery();

            while (rs.next())
            {
                String collectionName = rs.getString("COLLECTION_NAME");
                String collectionType = rs.getString("COLLECTION_TYPE");
                String collectionNote = rs.getString("COLLECTION_NOTE");
                //String userUUID = rs.getString("USER_UUID");
                
                collectionProperty = new CollectionProperty(collectionName, collectionType, collectionNote, collectionUUID.toString());
                
            }
            rs.close();
        } catch (SQLException ex)
        {
            log.log(Level.SEVERE, "Couldn't get collections by user ", ex);
        }

        DBConnect.closeDBConnection();
        //shutDownDBConnection();

        return collectionProperty;
    }



}
