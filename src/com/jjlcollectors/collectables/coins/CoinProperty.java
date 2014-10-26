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


import javafx.beans.property.SimpleStringProperty;


/**
 *
 * @author Hedgehog01
 */
public class CoinProperty
{
    private final SimpleStringProperty coinName = new SimpleStringProperty("");
    private final SimpleStringProperty coinValue = new SimpleStringProperty("");
    private final SimpleStringProperty coinUUID = new SimpleStringProperty("");
    
    public CoinProperty()
    {
        this ("","","");
    }
    
    public CoinProperty (String coinName,String coinValue,String coinUUID)
    {
        setCoinName(coinName);
        setCoinValue(coinValue);
        setCoinUUID(coinUUID);
    }
    
    
    public void setCoinName(String coinName)
    {
        this.coinName.set(coinName);
    }
    
    public String getCoinName ()
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
    
}
