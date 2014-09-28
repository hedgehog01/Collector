/*
 * Copyright (C) 2014 Hedgehog01.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package com.jjlcollectors.collectables.windows;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Hedgehog01
 */
@Entity
@Table(name = "COINS", catalog = "", schema = "HEDGEHOG01")
@NamedQueries(
{
    @NamedQuery(name = "Coins.findAll", query = "SELECT c FROM Coins c"),
    @NamedQuery(name = "Coins.findById", query = "SELECT c FROM Coins c WHERE c.id = :id"),
    @NamedQuery(name = "Coins.findByCoinUuid", query = "SELECT c FROM Coins c WHERE c.coinUuid = :coinUuid"),
    @NamedQuery(name = "Coins.findByCoinName", query = "SELECT c FROM Coins c WHERE c.coinName = :coinName"),
    @NamedQuery(name = "Coins.findByCoinGrade", query = "SELECT c FROM Coins c WHERE c.coinGrade = :coinGrade"),
    @NamedQuery(name = "Coins.findByCoinFacevalue", query = "SELECT c FROM Coins c WHERE c.coinFacevalue = :coinFacevalue"),
    @NamedQuery(name = "Coins.findByCoinCurrency", query = "SELECT c FROM Coins c WHERE c.coinCurrency = :coinCurrency"),
    @NamedQuery(name = "Coins.findByCoinNote", query = "SELECT c FROM Coins c WHERE c.coinNote = :coinNote"),
    @NamedQuery(name = "Coins.findByCoinBuyDate", query = "SELECT c FROM Coins c WHERE c.coinBuyDate = :coinBuyDate"),
    @NamedQuery(name = "Coins.findByCoinBuyPrice", query = "SELECT c FROM Coins c WHERE c.coinBuyPrice = :coinBuyPrice"),
    @NamedQuery(name = "Coins.findByCoinValue", query = "SELECT c FROM Coins c WHERE c.coinValue = :coinValue"),
    @NamedQuery(name = "Coins.findByCoinMintMark", query = "SELECT c FROM Coins c WHERE c.coinMintMark = :coinMintMark"),
    @NamedQuery(name = "Coins.findByCoinYear", query = "SELECT c FROM Coins c WHERE c.coinYear = :coinYear")
})
public class Coins implements Serializable
{
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "COIN_UUID")
    private String coinUuid;
    @Column(name = "COIN_NAME")
    private String coinName;
    @Column(name = "COIN_GRADE")
    private String coinGrade;
    @Column(name = "COIN_FACEVALUE")
    private String coinFacevalue;
    @Column(name = "COIN_CURRENCY")
    private String coinCurrency;
    @Column(name = "COIN_NOTE")
    private String coinNote;
    @Column(name = "COIN_BUY_DATE")
    @Temporal(TemporalType.DATE)
    private Date coinBuyDate;
    @Column(name = "COIN_BUY_PRICE")
    private String coinBuyPrice;
    @Column(name = "COIN_VALUE")
    private String coinValue;
    @Column(name = "COIN_MINT_MARK")
    private String coinMintMark;
    @Column(name = "COIN_YEAR")
    private Integer coinYear;

    public Coins()
    {
    }

    public Coins(Integer id)
    {
        this.id = id;
    }

    public Coins(Integer id, String coinUuid)
    {
        this.id = id;
        this.coinUuid = coinUuid;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getCoinUuid()
    {
        return coinUuid;
    }

    public void setCoinUuid(String coinUuid)
    {
        String oldCoinUuid = this.coinUuid;
        this.coinUuid = coinUuid;
        changeSupport.firePropertyChange("coinUuid", oldCoinUuid, coinUuid);
    }

    public String getCoinName()
    {
        return coinName;
    }

    public void setCoinName(String coinName)
    {
        String oldCoinName = this.coinName;
        this.coinName = coinName;
        changeSupport.firePropertyChange("coinName", oldCoinName, coinName);
    }

    public String getCoinGrade()
    {
        return coinGrade;
    }

    public void setCoinGrade(String coinGrade)
    {
        String oldCoinGrade = this.coinGrade;
        this.coinGrade = coinGrade;
        changeSupport.firePropertyChange("coinGrade", oldCoinGrade, coinGrade);
    }

    public String getCoinFacevalue()
    {
        return coinFacevalue;
    }

    public void setCoinFacevalue(String coinFacevalue)
    {
        String oldCoinFacevalue = this.coinFacevalue;
        this.coinFacevalue = coinFacevalue;
        changeSupport.firePropertyChange("coinFacevalue", oldCoinFacevalue, coinFacevalue);
    }

    public String getCoinCurrency()
    {
        return coinCurrency;
    }

    public void setCoinCurrency(String coinCurrency)
    {
        String oldCoinCurrency = this.coinCurrency;
        this.coinCurrency = coinCurrency;
        changeSupport.firePropertyChange("coinCurrency", oldCoinCurrency, coinCurrency);
    }

    public String getCoinNote()
    {
        return coinNote;
    }

    public void setCoinNote(String coinNote)
    {
        String oldCoinNote = this.coinNote;
        this.coinNote = coinNote;
        changeSupport.firePropertyChange("coinNote", oldCoinNote, coinNote);
    }

    public Date getCoinBuyDate()
    {
        return coinBuyDate;
    }

    public void setCoinBuyDate(Date coinBuyDate)
    {
        Date oldCoinBuyDate = this.coinBuyDate;
        this.coinBuyDate = coinBuyDate;
        changeSupport.firePropertyChange("coinBuyDate", oldCoinBuyDate, coinBuyDate);
    }

    public String getCoinBuyPrice()
    {
        return coinBuyPrice;
    }

    public void setCoinBuyPrice(String coinBuyPrice)
    {
        String oldCoinBuyPrice = this.coinBuyPrice;
        this.coinBuyPrice = coinBuyPrice;
        changeSupport.firePropertyChange("coinBuyPrice", oldCoinBuyPrice, coinBuyPrice);
    }

    public String getCoinValue()
    {
        return coinValue;
    }

    public void setCoinValue(String coinValue)
    {
        String oldCoinValue = this.coinValue;
        this.coinValue = coinValue;
        changeSupport.firePropertyChange("coinValue", oldCoinValue, coinValue);
    }

    public String getCoinMintMark()
    {
        return coinMintMark;
    }

    public void setCoinMintMark(String coinMintMark)
    {
        String oldCoinMintMark = this.coinMintMark;
        this.coinMintMark = coinMintMark;
        changeSupport.firePropertyChange("coinMintMark", oldCoinMintMark, coinMintMark);
    }

    public Integer getCoinYear()
    {
        return coinYear;
    }

    public void setCoinYear(Integer coinYear)
    {
        Integer oldCoinYear = this.coinYear;
        this.coinYear = coinYear;
        changeSupport.firePropertyChange("coinYear", oldCoinYear, coinYear);
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coins))
        {
            return false;
        }
        Coins other = (Coins) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.jjlcollectors.collectables.windows.Coins[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
