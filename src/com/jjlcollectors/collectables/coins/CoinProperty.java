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

import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Hedgehog01
 */
public final class CoinProperty
{

    private static final Logger LOG = Logger.getLogger(CoinProperty.class.getName());

    //Property instance variables
    private final StringProperty userUUID = new SimpleStringProperty("");
    private final SimpleStringProperty coinUUID = new SimpleStringProperty("");
    private final SimpleStringProperty coinName = new SimpleStringProperty("");
    private final StringProperty coinGrade = new SimpleStringProperty("");
    private final StringProperty coinFaceValue = new SimpleStringProperty("");
    private final StringProperty coinCurrency = new SimpleStringProperty("");
    private final StringProperty coinNote = new SimpleStringProperty("");
    private final StringProperty coinBuyDate = new SimpleStringProperty("");
    private final StringProperty coinBuyPrice = new SimpleStringProperty("");
    private final SimpleStringProperty coinValue = new SimpleStringProperty("");
    private final StringProperty coinMintMark = new SimpleStringProperty("");
    private final IntegerProperty coinYear = new SimpleIntegerProperty(-1);
    private final StringProperty coinCollectionName = new SimpleStringProperty("");
    private final StringProperty coinCollectionUUID = new SimpleStringProperty("");

    public CoinProperty()
    {
        this("", "", "", "", "", "", "", "", "", "", -1, "", "", "");
    }

    /**
     * Constructor for creating CoinProperty object
     * @param coinUUID CoinUUID
     * @param coinName Coin Name
     * @param coinGrade Coin Grade
     * @param coinFaceValue Coin Face Value
     * @param coinCurrency Coin Currency
     * @param coinNote Coin Note
     * @param coinBuyDate Coin Buy Date
     * @param coinBuyPrice Coin Buy Price
     * @param coinValue Coin Value
     * @param coinMintMark Coin Mint Mark
     * @param coinYear Coin Year
     * @param userUUID User UUID
     * @param coinCollectionName the name of the collection the coin belongs to
     * @param collectionUUID the collection UUID
     */
    public CoinProperty(String coinUUID, String coinName, String coinGrade, String coinFaceValue, String coinCurrency, String coinNote, String coinBuyDate, String coinBuyPrice, String coinValue, String coinMintMark, int coinYear,String userUUID, String coinCollectionName, String collectionUUID)
    {
        setCoinUUID(coinUUID);
        setCoinName(coinName);
        setCoinGrade(coinGrade);
        setCoinFaceValue(coinFaceValue);
        setCoinCurrency(coinCurrency);
        setCoinNote(coinNote);
        setCoinBuyDate(coinBuyDate);
        setCoinBuyPrice(coinBuyPrice);
        setCoinValue(coinValue);
        setCoinMintMark(coinMintMark);
        setCoinYear(coinYear);
        setUserUUID(userUUID);
        setCoinCollectionName(coinCollectionName);
        setCoinCollectionUUID(collectionUUID);
    }

    /**
     * Method to create CoinProperty from Coin object
     * @param coin the Coin object
     */
    public CoinProperty(Coin coin)
    {
        setUserUUID(coin.getUserUUID().toString());
        setCoinUUID(coin.getItemUUID().toString());
        setCoinName(coin.getItemName());
        setCoinGrade(coin.getCoinGrade().name());
        setCoinFaceValue(coin.getFaceValue());
        setCoinCurrency(coin.getCoinCurrency().name());
        setCoinNote(coin.getItemNote().toString());
        setCoinBuyDate(coin.getItemBuyDate().toString());
        setCoinBuyPrice(coin.getBuyPrice());
        setCoinValue(coin.getItemValue());
        setCoinMintMark(coin.getCoinMintMark());
        setCoinYear(coin.getCoinYear());
        setCoinCollectionName(coin.getItemCollectionName());
        setCoinCollectionUUID(coin.getItemCollectionUUID().toString());
    }

    public void setCoinName(String coinName)
    {
        this.coinName.set(coinName);
    }

    public String getCoinName()
    {
        return coinName.get();
    }

    public void setCoinValue(String coinValue)
    {
        this.coinValue.set(coinValue);
    }

    public String getCoinValue()
    {
        return coinValue.get();
    }

    public void setCoinUUID(String coinUUID)
    {
        this.coinUUID.set(coinUUID);
    }

    public String getCoinUUID()
    {
        return coinUUID.get();
    }

    public String getCoinCollectionName()
    {
        return coinCollectionName.get();
    }

    public void setCoinCollectionName(String value)
    {
        coinCollectionName.set(value);
    }

    public StringProperty coinCollectionNameProperty()
    {
        return coinCollectionName;
    }

    public String getCoinCurrency()
    {
        return coinCurrency.get();
    }

    public void setCoinCurrency(String value)
    {
        coinCurrency.set(value);
    }

    public StringProperty coinCurrencyProperty()
    {
        return coinCurrency;
    }

    public String getCoinFaceValue()
    {
        return coinFaceValue.get();
    }

    public void setCoinFaceValue(String value)
    {
        coinFaceValue.set(value);
    }

    public StringProperty coinFaceValueProperty()
    {
        return coinFaceValue;
    }

    public String getCoinGrade()
    {
        return coinGrade.get();
    }

    public void setCoinGrade(String value)
    {
        coinGrade.set(value);
    }

    public StringProperty coinGradeProperty()
    {
        return coinGrade;
    }

    public String getCoinNote()
    {
        return coinNote.get();
    }

    public void setCoinNote(String value)
    {
        coinNote.set(value);
    }

    public StringProperty coinNoteProperty()
    {
        return coinNote;
    }

    public String getCoinBuyDate()
    {
        return coinBuyDate.get();
    }

    public void setCoinBuyDate(String value)
    {
        coinBuyDate.set(value);
    }

    public StringProperty coinBuyDateProperty()
    {
        return coinBuyDate;
    }

    public String getCoinBuyPrice()
    {
        return coinBuyPrice.get();
    }

    public void setCoinBuyPrice(String value)
    {
        coinBuyPrice.set(value);
    }

    public StringProperty coinBuyPriceProperty()
    {
        return coinBuyPrice;
    }

    public String getCoinMintMark()
    {
        return coinMintMark.get();
    }

    public void setCoinMintMark(String value)
    {
        coinMintMark.set(value);
    }

    public StringProperty coinMintMarkProperty()
    {
        return coinMintMark;
    }

    public int getCoinYear()
    {
        return coinYear.get();
    }

    public void setCoinYear(int value)
    {
        coinYear.set(value);
    }

    public IntegerProperty coinYearProperty()
    {
        return coinYear;
    }

    public String getCoinCollectionUUID()
    {
        return coinCollectionUUID.get();
    }

    public void setCoinCollectionUUID(String value)
    {
        coinCollectionUUID.set(value);
    }

    public StringProperty coinCollectionUUIDProperty()
    {
        return coinCollectionUUID;
    }

    public String getUserUUID()
    {
        return userUUID.get();
    }

    public void setUserUUID(String value)
    {
        userUUID.set(value);
    }

    public StringProperty userUUIDProperty()
    {
        return userUUID;
    }

}
