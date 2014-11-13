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

import com.jjlcollectors.collectables.coins.Coin;
import com.jjlcollectors.collectables.coins.CoinCurrency;
import com.jjlcollectors.collectables.coins.CoinGrade;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nathanr
 */
public final class AddCoinController implements Initializable
{
    private static final Logger LOG = Logger.getLogger(AddCoinController.class.getName());
    
    
    
    UUID userUUID;
    //public Coin (UUID userUUID,String name, CoinGrade grade, String facevalue, CoinCurrency currency, StringBuilder note, int coinYear, String coinMintMark, String buyPrice, String coinValue)
    
    @FXML
    private TextField coinNameTxtField;
    
    @FXML
    private TextField coinFaceValueTxtField;
    
    @FXML
    private TextField coinNoteTxtField;
    
    @FXML
    private TextField coinYearTxtField;
    
    @FXML
    private TextField coinMintMarkTxtField;
    
    @FXML
    private TextField coinBuyPriceTxtField;
    
    @FXML
    private TextField coinValueTxtField;
    
    @FXML
    private ComboBox <CoinGrade> coinGradeComboBox;
    
    @FXML
    private Button addCoinBtn;
    
    @FXML
    private Button cancelBtn;
    
    @FXML
    ComboBox <CoinCurrency> currencyComboBox;
    
    @FXML
    Label addCoinStatus;
    
    @FXML
    DatePicker coinBuyDatePicker;
    
    
    ObservableList<String> currencyList = FXCollections.observableArrayList();
    
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        userUUID = null;
        currencyComboBox.getItems().addAll(CoinCurrency.values());
        coinGradeComboBox.getItems().addAll(CoinGrade.values());
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
            StringBuilder coinNote = new StringBuilder(coinNoteTxtField.getText());
            int coinYear = Integer.parseInt(coinYearTxtField.getText());

            //public Coin           (UUID userUUID,String name, CoinGrade grade, String facevalue, CoinCurrency currency, StringBuilder note, int coinYear, String coinMintMark, String buyPrice, String coinValue)
            Coin newCoin = new Coin (userUUID,coinNameTxtField.getText(),coinGradeComboBox.getValue() ,coinFaceValueTxtField.getText(),currencyComboBox.getValue(),coinNote, coinYear,coinMintMarkTxtField.getText(),coinBuyPriceTxtField.getText(),coinValueTxtField.getText());
            LOG.log(Level.INFO, "New Coin created, user uuid is {0}",userUUID.toString());
        }
    }

    private boolean isCoinValid()
    {
        return true;
        //To Do
    }
    
}
