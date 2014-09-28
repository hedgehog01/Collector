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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Hedgehog01
 */
@Entity
@Table(name = "COIN_INFO", catalog = "", schema = "HEDGEHOG01")
@NamedQueries(
{
    @NamedQuery(name = "CoinInfo.findAll", query = "SELECT c FROM CoinInfo c"),
    @NamedQuery(name = "CoinInfo.findById", query = "SELECT c FROM CoinInfo c WHERE c.id = :id"),
    @NamedQuery(name = "CoinInfo.findByName", query = "SELECT c FROM CoinInfo c WHERE c.name = :name"),
    @NamedQuery(name = "CoinInfo.findByCountry", query = "SELECT c FROM CoinInfo c WHERE c.country = :country"),
    @NamedQuery(name = "CoinInfo.findByComposition", query = "SELECT c FROM CoinInfo c WHERE c.composition = :composition"),
    @NamedQuery(name = "CoinInfo.findByWeight", query = "SELECT c FROM CoinInfo c WHERE c.weight = :weight"),
    @NamedQuery(name = "CoinInfo.findByDiameter", query = "SELECT c FROM CoinInfo c WHERE c.diameter = :diameter"),
    @NamedQuery(name = "CoinInfo.findByThickness", query = "SELECT c FROM CoinInfo c WHERE c.thickness = :thickness"),
    @NamedQuery(name = "CoinInfo.findByEdge", query = "SELECT c FROM CoinInfo c WHERE c.edge = :edge"),
    @NamedQuery(name = "CoinInfo.findByEmail", query = "SELECT c FROM CoinInfo c WHERE c.email = :email"),
    @NamedQuery(name = "CoinInfo.findByComm", query = "SELECT c FROM CoinInfo c WHERE c.comm = :comm")
})
public class CoinInfo implements Serializable
{
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "COMPOSITION")
    private String composition;
    @Column(name = "WEIGHT")
    private String weight;
    @Column(name = "DIAMETER")
    private String diameter;
    @Column(name = "THICKNESS")
    private String thickness;
    @Column(name = "EDGE")
    private String edge;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "COMM")
    private String comm;

    public CoinInfo()
    {
    }

    public CoinInfo(Integer id)
    {
        this.id = id;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        String oldName = this.name;
        this.name = name;
        changeSupport.firePropertyChange("name", oldName, name);
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        String oldCountry = this.country;
        this.country = country;
        changeSupport.firePropertyChange("country", oldCountry, country);
    }

    public String getComposition()
    {
        return composition;
    }

    public void setComposition(String composition)
    {
        String oldComposition = this.composition;
        this.composition = composition;
        changeSupport.firePropertyChange("composition", oldComposition, composition);
    }

    public String getWeight()
    {
        return weight;
    }

    public void setWeight(String weight)
    {
        String oldWeight = this.weight;
        this.weight = weight;
        changeSupport.firePropertyChange("weight", oldWeight, weight);
    }

    public String getDiameter()
    {
        return diameter;
    }

    public void setDiameter(String diameter)
    {
        String oldDiameter = this.diameter;
        this.diameter = diameter;
        changeSupport.firePropertyChange("diameter", oldDiameter, diameter);
    }

    public String getThickness()
    {
        return thickness;
    }

    public void setThickness(String thickness)
    {
        String oldThickness = this.thickness;
        this.thickness = thickness;
        changeSupport.firePropertyChange("thickness", oldThickness, thickness);
    }

    public String getEdge()
    {
        return edge;
    }

    public void setEdge(String edge)
    {
        String oldEdge = this.edge;
        this.edge = edge;
        changeSupport.firePropertyChange("edge", oldEdge, edge);
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        String oldEmail = this.email;
        this.email = email;
        changeSupport.firePropertyChange("email", oldEmail, email);
    }

    public String getComm()
    {
        return comm;
    }

    public void setComm(String comm)
    {
        String oldComm = this.comm;
        this.comm = comm;
        changeSupport.firePropertyChange("comm", oldComm, comm);
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
        if (!(object instanceof CoinInfo))
        {
            return false;
        }
        CoinInfo other = (CoinInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.jjlcollectors.collectables.windows.CoinInfo[ id=" + id + " ]";
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
