/*
 * Copyright (C) 2014 Hedgehog01
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
package com.jjlcollectors.collectables.coins;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Hedgehog01
 */
public class CoinCreator
{
    private static final Logger LOG = Logger.getLogger(CoinCreator.class.getName());

    /**
     * Returns all coins of a user
     *
     * @return
     */
    public static ObservableList<CoinProperty> getCoinProperties(UUID userUUID)
    {
        ObservableList<CoinProperty> data = FXCollections.observableArrayList();;
        if (userUUID != null)
        {

            int year = 1982;
            CoinProperty a1 = new CoinProperty("name1", "lastname1", "email1", "name1", "lastname1", "email1", "name1", "lastname1", "email1", "name1", year, "email1");
            CoinProperty a2 = new CoinProperty("tame1", "yastname1", "lmail1", "kame1", "hastname1", "lmail1", "kame1", "jastname1", "hmail1", "hame1", year, "gmail1");
            //CoinProperty b = new CoinProperty("name2", "lastname2", "email2");
            //CoinProperty c = new CoinProperty("name3", "lastname3", "email3");

            data.add(a1);
            data.add(a2);
            //data.add(c);
        }

        return data;
    }

    /**
     * method to return all user coins in a specific colelction
     *
     * @param userUUID the user UUID
     * @param collectionUUID the collection UUID
     * @return ObservableList of all coins in the given user collection
     */
    public static ObservableList<CoinProperty> getCoinProperties(UUID userUUID, UUID collectionUUID)
    {
        ObservableList<CoinProperty> data = FXCollections.observableArrayList();

        if (userUUID != null && collectionUUID != null)
        {
            LOG.log(Level.INFO, "userUUID and collectionUUID not null");
            
            int year = 1982;
            CoinProperty a1 = new CoinProperty("name1", "lastname1", "email1", "name1", "lastname1", "email1", "name1", "lastname1", "email1", "name1", year, "email1");
            CoinProperty a2 = new CoinProperty("5", "5", "66", "gh", "gh", "lmail1", "gh", "hj", "hmail1", "hame1", year, "jkg");
            //CoinProperty b = new CoinProperty("name2", "lastname2", "email2");
            //CoinProperty c = new CoinProperty("name3", "lastname3", "email3");

            boolean test = data.add(a1);
            
            
            data.add(a2);
            //data.add(c);
            LOG.log(Level.INFO, "Adding coins by useruuid and collectionuuid: {0}",test);
        }

        return data;
    }
    
        public static ObservableList<CoinProperty> getCoinProperties(UUID userUUID, UUID collectionUUID, ObservableList<CoinProperty> oldData)
    {
        ObservableList<CoinProperty> data = FXCollections.observableArrayList();
        data.setAll(oldData);
        if (userUUID != null && collectionUUID != null)
        {
            LOG.log(Level.INFO, "userUUID and collectionUUID not null");
            
            int year = 1982;
            CoinProperty a1 = new CoinProperty("name1", "lastname1", "email1", "name1", "lastname1", "email1", "name1", "lastname1", "email1", "name1", year, "email1");
            CoinProperty a2 = new CoinProperty("5", "5", "66", "gh", "gh", "lmail1", "gh", "hj", "hmail1", "hame1", year, "jkg");
            //CoinProperty b = new CoinProperty("name2", "lastname2", "email2");
            //CoinProperty c = new CoinProperty("name3", "lastname3", "email3");

            data.add(a1);
            //data.add(a2);
            //data.add(c);
            LOG.log(Level.INFO, "Adding coins by useruuid and collectionuuid");
        }
        return data;
    }
        
        
}
