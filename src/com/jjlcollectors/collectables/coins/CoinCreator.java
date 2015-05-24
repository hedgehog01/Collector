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

import com.jjlcollectors.util.dbconnect.DBCoinConnect;
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
     * method to return all user coins in a specific collection
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
            LOG.log(Level.INFO, "userUUID and collectionUUID not null, attempt to build coin list");

            data = DBCoinConnect.getAllUserCoinsByCollection(userUUID,collectionUUID);

            LOG.log(Level.INFO, "Adding coins by useruuid and collectionuuid: {0} , {1}",new Object[] {userUUID.toString(),collectionUUID.toString()});
        }

        return data;
    }

    public static ObservableList<CoinProperty> getCoinProperties(UUID userUUID)
    {
        ObservableList<CoinProperty> data = FXCollections.observableArrayList();

        if (userUUID != null)
        {
            LOG.log(Level.INFO, "userUUID not null, attempt to build coin list");
            data = DBCoinConnect.getAllUserCoins(userUUID);
            LOG.log(Level.INFO, "Adding coins by useruuid and collectionuuid:");
        }

        return data;
    }
}
