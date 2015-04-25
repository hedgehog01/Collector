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
package com.jjlcollectors.util.dbconnect;

import com.jjlcollectors.collectables.coins.Coin;
import com.jjlcollectors.collectables.coins.CoinCurrency;
import com.jjlcollectors.collectables.coins.CoinGrade;
import com.jjlcollectors.collectables.coins.CoinProperty;
import java.time.LocalDate;
import java.util.UUID;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Hedgehog01
 */
public class DBCoinConnectTest
{
    
    private final UUID userUUID = UUID.randomUUID();
    private final UUID collectionUUID = UUID.randomUUID();
    
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
     * tests adding a coin with correct params - coin should be added to DB
     */
    @Test
    public void testAddCoin1()
    {
        System.out.println("addCoin");
        String coinName = "coin name";
        String faceValue = "100";
        CoinCurrency coinCurrency = CoinCurrency.EURO;
        StringBuilder sb = new StringBuilder("coin note");
        String coinMintMark = "NR";
        String buyPrice = "90";
        String coinValue = "110";
        LocalDate date = LocalDate.now();
        Coin coin = new Coin(userUUID, coinName, CoinGrade.VG8, faceValue, coinCurrency, sb, 1982, coinMintMark, buyPrice, coinValue,date, collectionUUID);
        boolean expResult = true;
        boolean result = DBCoinConnect.addCoin(coin);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test of removeCoinByCoinUUID method, of class DBCoinConnect.
     * Tests if random UUID is given no coins removed
     */
    @Test
    public void testRemoveCoinByCoinUUID1()
    {
        System.out.println("testRemoveCoinByCoinUUID1");

        int expResult = 0;
        int result = DBCoinConnect.removeCoinByCoinUUID(UUID.randomUUID());
        assertEquals(expResult, result);
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
        ObservableList<CoinProperty> expResult = null;
        ObservableList<CoinProperty> result = DBCoinConnect.getAllUserCoins(userUUID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
