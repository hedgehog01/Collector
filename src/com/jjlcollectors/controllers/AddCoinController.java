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
import com.jjlcollectors.util.dbconnect.DBCollectionConnect;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nathanr
 */
public final class AddCoinController implements Initializable
{
    private static final Logger log = Logger.getLogger(AddCoinController.class.getName());
    private final String COLLECTION_TYPE_NOT_SELECTED = "Collection must be selected.\n";
    private final String CURRENCY_TYPE_NOT_SELECTED = "Currency must be selected.\n";
    private final String COLLECTION_NAME_EMPTY = "Enter colletion name.\n";
    private final String FACEVALUE_EMPTY = "Enter Face Value.\n";
    private final String FACEVALUE_NONNUMERIC = "Face Value must be numeric.\n";
    private final String Mint_YEAR_EMPTY = "Enter Mint Year.\n";
    private final String Mint_YEAR_NONNUMERIC = "Mint Year must be numeric.\n";
    private final String COLLECTION_NAME_EXISTS = "Name already exists.\n";

    UUID userUUID;
    //public Coin (UUID userUUID,String name, CoinGrade grade, String facevalue, CoinCurrency currency, StringBuilder note, int coinYear, String coinMintMark, String buyPrice, String coinValue)
    
    @FXML
    private TextField coinNameTxtField;
    
    @FXML
    private TextField coinFaceValueTxtField;
    
    @FXML
    private TextField coinNoteTxtField;
    
    @FXML
    private TextField coinMintYearTxtField;
    
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
    private ComboBox <CoinCurrency> currencyComboBox;
    
    @FXML
    private ComboBox <String> collectionComboBox;
    
    @FXML
    TextArea coinAddStatus;
    
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
            int coinYear = Integer.parseInt(coinMintYearTxtField.getText());
            UUID collectionUuid = DBCollectionConnect.getCollectionUUID (collectionComboBox.getValue());
            //public Coin           (UUID userUUID,String name, CoinGrade grade, String facevalue, CoinCurrency currency, StringBuilder note, int coinYear, String coinMintMark, String buyPrice, String coinValue)
            Coin newCoin = new Coin (userUUID,coinNameTxtField.getText(),coinGradeComboBox.getValue() ,coinFaceValueTxtField.getText(),currencyComboBox.getValue(),coinNote, coinYear,coinMintMarkTxtField.getText(),coinBuyPriceTxtField.getText(),coinValueTxtField.getText(),collectionUuid);
            log.log(Level.INFO, "New Coin created, user uuid is {0}",userUUID.toString());
        }
    }

    private boolean isCoinValid()
    {
        boolean coinValid = true;
        StringBuilder issues = new StringBuilder("");
        
        coinAddStatus.setVisible(!(coinValid));
        
        //check name filed
        if (coinNameTxtField.getText() == null || coinNameTxtField.getText().trim().isEmpty())
        {
            issues.append(COLLECTION_NAME_EMPTY);
             coinValid = false;
             log.log(Level.INFO, "collection name is: {0}",coinNameTxtField.getText());
        }
        else if (DBCollectionConnect.getCollectionUUID(coinNameTxtField.getText()) != null) //check if collection name already exists
        {
            issues.append(COLLECTION_NAME_EXISTS);
             coinValid = false;
             log.log(Level.INFO, "collection name already exists: {0}",coinNameTxtField.getText());
        }
        
        //check currency combo box
        if (currencyComboBox.getValue() == null)
         {
             issues.append(CURRENCY_TYPE_NOT_SELECTED);
             coinValid = false;
             log.log(Level.INFO, "Currency selected is: {0}",collectionComboBox.getValue());
         }
        
        //check facevalue
        
        if (coinFaceValueTxtField.getText() == null || coinFaceValueTxtField.getText().trim().isEmpty())
        {
            issues.append(FACEVALUE_EMPTY);
             coinValid = false;
             log.log(Level.INFO, "Face value is: {0}",coinFaceValueTxtField.getText());
        }
        
        if (!(isNumeric(coinFaceValueTxtField.getText())))
        {
             issues.append(FACEVALUE_NONNUMERIC);
             coinValid = false;
             log.log(Level.INFO, "Face value is non-numeric: {0}",coinFaceValueTxtField.getText());
        }
        
        //check mint year
        if (coinMintYearTxtField.getText() == null || coinMintYearTxtField.getText().trim().isEmpty())
        {
            issues.append(Mint_YEAR_EMPTY);
             coinValid = false;
             log.log(Level.INFO, "Face value is: {0}",coinMintYearTxtField.getText());
        }
        
        if (!(isNumeric(coinMintYearTxtField.getText())))
        {
             issues.append(Mint_YEAR_NONNUMERIC);
             coinValid = false;
             log.log(Level.INFO, "Face value is non-numeric: {0}",coinMintYearTxtField.getText());
        }
        
        //check collection combo box
        if (collectionComboBox.getValue() == null)
         {
             issues.append(COLLECTION_TYPE_NOT_SELECTED);
             coinValid = false;
             log.log(Level.INFO, "collection selected is: {0}",collectionComboBox.getValue());
         }
        if (!(coinValid))
        {
            coinAddStatus.setVisible(!(coinValid));
            coinAddStatus.setBorder(Border.EMPTY);
            coinAddStatus.setText(issues.toString());
            
        }
        return coinValid;
    }
    
    /*
    * method to check if string contains numeric value
    */
    private boolean isNumeric(String str)
    {
        try  
        {  
            double d = Double.parseDouble(str);  
        }  
        catch(NumberFormatException nfe)  
        {  
            log.log(Level.INFO, "Non numeric value. Value was: {0}", str);
            return false;  
        }  
        return true; 
        
    }
    
}
