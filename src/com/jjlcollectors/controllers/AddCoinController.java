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
package com.jjlcollectors.controllers;

import com.jjlcollectors.collectables.coins.CoinCurrency;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nathanr
 */
public final class AddCoinController implements Initializable
{
    UUID userUUID;
    
    @FXML
    private Button addCoinBtn;
    
    @FXML
    private Button cancelBtn;
    
    @FXML
    ComboBox <CoinCurrency> currencyComboBox;
    
    
    ObservableList<String> currencyList = FXCollections.observableArrayList();
    
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        userUUID = null;
        currencyComboBox.getItems().addAll(
                CoinCurrency.ABKHAZIAN_APSAR,
                CoinCurrency.AFGHAN_AFGHANI,
                CoinCurrency.ALBANIAN_LEK,
                CoinCurrency.ALDERNEY_POUND
                
                
        );
                
    }    
    
    
    @FXML
    private void closeWindow()
    {
        Stage currentStage = (Stage) addCoinBtn.getScene().getWindow();
            currentStage.close();
    }

    void setUserData(UUID userUUID)
    {
        this.userUUID = userUUID;
    }
    
    @FXML
    private void addNewCoin()
    {
        if (isCoinValid())
        {
            //public Coin (UUID userUUID,String name, CoinGrade grade, String facevalue, CoinCurrency currency, StringBuilder note, int coinYear, String coinMintMark, String buyPrice, String coinValue)
            Coin newCoin = Coin (userUUID, currencyComboBox.getValue(),);
        }
    }
    
}
