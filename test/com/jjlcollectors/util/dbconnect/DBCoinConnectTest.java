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

import com.jjlcollectors.collectables.coins.Coin;
import com.jjlcollectors.collectables.coins.CoinCurrency;
import com.jjlcollectors.collectables.coins.CoinGrade;
import com.jjlcollectors.collectables.coins.CoinProperty;
import java.util.ArrayList;
import java.util.UUID;
import javafx.scene.control.TableView;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author nathanr
 */
public class DBCoinConnectTest
{
    
    public DBCoinConnectTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }

    /**
     * Test of addCoin method, of class DBCoinConnect.
     */
    @Test
    public void testAddCoin()
    {
        System.out.println("addCoin");
        UUID userUUID = UUID.randomUUID();
        UUID collectionUUID = UUID.fromString("b22e37a3-6316-47a2-8a5c-0b21339e1faa");
        StringBuilder sb = new StringBuilder("coin note");                                                                                     
        Coin coin = new Coin(userUUID, "coin name", CoinGrade.P1, "face value", CoinCurrency.UNKNOWN, sb, 1982, "mint mark", "buy price", "coin value", collectionUUID);
        boolean expResult = true;
        boolean result = DBCoinConnect.addCoin(coin);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of removeCoinById method, of class DBCoinConnect.
     */
    @Ignore
    @Test
    public void testRemoveCoinById()
    {
        System.out.println("removeCoinById");
        int id = 0;
        int expResult = 0;
        int result = DBCoinConnect.removeCoinById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeLastCoin method, of class DBCoinConnect.
     */
    @Ignore
    @Test
    public void testRemoveLastCoin()
    {
        System.out.println("removeLastCoin");
        int expResult = 0;
        int result = DBCoinConnect.removeLastCoin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildData method, of class DBCoinConnect.
     */
    @Ignore
    @Test
    public void testBuildData()
    {
        System.out.println("buildData");
        TableView expResult = null;
        TableView result = DBCoinConnect.buildData();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllUserCoins method, of class DBCoinConnect.
     */
    @Ignore
    @Test
    public void testGetAllUserCoins()
    {
        System.out.println("getAllUserCoins");
        UUID userUUID = null;
        ArrayList<CoinProperty> expResult = null;
        ArrayList<CoinProperty> result = DBCoinConnect.getAllUserCoins(userUUID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}