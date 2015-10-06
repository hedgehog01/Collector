/*
 * Copyright (C) 2015 Hedgehog01
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

import com.jjlcollectors.collectables.CollectionProperty;
import com.jjlcollectors.collectables.coins.CoinCurrency;
import com.jjlcollectors.collectables.coins.CoinGrade;
import com.jjlcollectors.collectables.coins.CoinProperty;
import com.jjlcollectors.util.dbconnect.DBCoinConnect;
import com.jjlcollectors.util.dbconnect.DBCollectionConnect;
import com.jjlcollectors.util.dbconnect.DBConnect;
import com.jjlcollectors.util.log.MyLogger;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Hedgehog01
 */
public final class ViewCoinController implements Initializable
{

    private CoinProperty coinProperty;

    private ObservableList<CollectionProperty> collectionComboListData = FXCollections.observableArrayList();
    private UUID userUUID;
    private UUID collectionUUID;

    private final String ERROR_COIN_CANT_BE_LOADED_TITLE = "Error - Coin info can't be loaded";
    private final String ERROR_COIN_CANT_BE_LOADED_BODY = "An Error has prevented coin info from being loaded.\nPlease try again.";
    private final String CONFIMATION_COIN_UPDATE_TITLE = "Update coin";
    private final String CONFIMATION_COIN_UPDATE_BODY = "To update coin select \"OK\"";
    private final String INFORMATION_COIN_UPDATE_TITLE = "Coin update info";
    private final String INFORMATION_COIN_UPDATE_OK_BODY = "Coin updated successfully.";
    private final String ERROR_COIN_UPDATE_FAIL_BODY = "Coin update was unsuccessful!\nPlease try again later.";
    private final String LOG_CLASS_NAME = "ViewCoinController: ";
    private final String CONFIMATION_COIN_DELETE_TITLE = "Delete coin";
    private final String INFORMATION_COIN_DELETE_OK_BODY = "Coin deleted successfully.";
    private final String CONFIMATION_COIN_DELETE_BODY = "Are you sure you would like to delete the coin?";
    private final String ERROR_COIN_DELETE_FAIL_TITLE = "Error - Delete coin failed";
    private final String ERROR_COIN_DELETE_FAIL_BODY = "Coin delete was unsuccessful!\nPlease try again later.";

    @FXML
    private AnchorPane viewCoinAnchorPane;

    @FXML
    private TextField coinNameTextField;

    @FXML
    private ComboBox<CoinCurrency> coinCurrencyComboBox;

    @FXML
    private ComboBox<CoinGrade> coinGradeComboBox;

    @FXML
    private ComboBox<CollectionProperty> collectionComboBox;

    @FXML
    private TextField coinFaceValueTextField;

    @FXML
    private TextField coinMintYearTextField;

    @FXML
    private TextField coinValueTextField;

    @FXML
    private TextField coinMintMarkTextField;

    @FXML
    private TextField coinBuyPriceTextField;

    @FXML
    private TextField coinNoteTxtField;

    @FXML
    private TextArea coinAddStatus;

    @FXML
    private DatePicker coinBuyDatePicker;

    @FXML
    private Button modifyCoinBtn;

    @FXML
    private Button cancelEditCoinBtn;

    @FXML
    private Button addCoinImageBtn1;

    @FXML
    private Button removeCoinImageBtn1;

    @FXML
    private Button addCoinImageBtn2;

    @FXML
    private Button removeCoinImageBtn2;
    
    @FXML
    private Button deleteCoinBtn;

    @FXML
    private ImageView coinImageView1;

    @FXML
    private ImageView coinImageView2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //setup combo boxes
        coinCurrencyComboBox.getItems().addAll(CoinCurrency.values());
        coinGradeComboBox.getItems().addAll(CoinGrade.values());

        // Define rendering of selected value shown in ComboBox.
        collectionComboBox.setConverter(new StringConverter<CollectionProperty>()
        {
            @Override
            public String toString(CollectionProperty collection)
            {
                if (collection == null)
                {
                    return null;
                } else
                {
                    return collection.getCollectionName();
                }
            }

            @Override
            public CollectionProperty fromString(String personString)
            {
                return null; // No conversion fromString needed.
            }
        });
        // Define rendering of the list of values in ComboBox drop down. 
        collectionComboBox.setCellFactory((comboBox) ->
        {
            return new ListCell<CollectionProperty>()
            {
                @Override
                protected void updateItem(CollectionProperty item, boolean empty)
                {
                    super.updateItem(item, empty);

                    if (item == null || empty)
                    {
                        setText(null);
                    } else
                    {
                        setText(item.getCollectionName());
                    }
                }
            };
        });

    }

    protected void setCoinData(CoinProperty coinProperty, UUID userUUID)
    {
        this.coinProperty = coinProperty;
        this.userUUID = userUUID;
        //setup view coin values according to coin passed in.
        if (coinProperty != null)
        {
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Setting info of coin property to window elements");
            coinNameTextField.setText(coinProperty.getCoinName());
            coinCurrencyComboBox.setValue(CoinCurrency.valueOf(coinProperty.getCoinCurrency()));
            coinGradeComboBox.setValue(CoinGrade.valueOf(coinProperty.getCoinGrade()));
            coinFaceValueTextField.setText(coinProperty.getCoinFaceValue());
            coinMintYearTextField.setText(Integer.toString(coinProperty.getCoinYear()));
            coinValueTextField.setText(coinProperty.getCoinValue());
            coinBuyPriceTextField.setText(coinProperty.getCoinBuyPrice());
            coinMintMarkTextField.setText(coinProperty.getCoinMintMark());
            coinNoteTxtField.setText(coinProperty.getCoinNote());
            coinBuyDatePicker.setValue(LocalDate.parse(coinProperty.getCoinBuyDate()));

            //setup collection ComboBox
            String coinCollectionUUID = coinProperty.getCoinCollectionUUID();

            CollectionProperty collection = DBCollectionConnect.getCollectionProperty(UUID.fromString(coinCollectionUUID));
            collectionComboBox.setValue(collection);

        } else if (coinProperty == null)
        {
            MyLogger.log(Level.SEVERE, LOG_CLASS_NAME + "CoinProperty object is null, can't load info");
            showErrorMessage(ERROR_COIN_CANT_BE_LOADED_TITLE, ERROR_COIN_CANT_BE_LOADED_BODY);
            closeWindow();
        }

        // Handle ComboBox event.
        collectionComboBox.setOnAction((event) ->
        {
            CollectionProperty selectedCollection = collectionComboBox.getSelectionModel().getSelectedItem();
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Collection selction ComboBox Action, selected collection: {0}", selectedCollection.toString());
            collectionUUID = UUID.fromString(selectedCollection.getCollectionUUID());
        });

    }

    /*
     * method to show error messages
     */
    private void showErrorMessage(String title, String body)
    {
        MyLogger.log(Level.INFO, "Error message initiated. Error Title: {0}", title);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage currentStage = (Stage) viewCoinAnchorPane.getScene().getWindow();
        alert.initOwner(currentStage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);
        alert.showAndWait();
    }

    /*
     * method to show information messages
     */
    private void showInfoMessage(String title, String body)
    {
        MyLogger.log(Level.INFO, "Info message initiated. Info Title: {0}", title);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage currentStage = (Stage) viewCoinAnchorPane.getScene().getWindow();
        alert.initOwner(currentStage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);
        alert.showAndWait();
    }

    /*
     * method to show information messages
     */
    private boolean showConfermationMessage(String title, String body)
    {
        MyLogger.log(Level.INFO, "Info message initiated. Info Title: {0}", title);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage currentStage = (Stage) viewCoinAnchorPane.getScene().getWindow();
        alert.initOwner(currentStage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        ButtonType buttonTypeOK = new ButtonType("OK", ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(buttonTypeCancel,buttonTypeOK);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeOK)
        {
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "User confirmed coin action");
            return true;
        }
        else if (result.isPresent() && result.get() == buttonTypeCancel)
        {
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "User canceled coin Action");
            return false;
        }
        return false;
        
    }

    @FXML
    private void closeWindow()
    {
        MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Closing View/Edit window");
        Stage currentStage = (Stage) viewCoinAnchorPane.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void loadUserData()
    {
        if (DBConnect.isDBConnectable())
        {
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Adding data to collectionComboListData");
            collectionComboListData.setAll(DBCollectionConnect.getUserCollections(userUUID));
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Adding data to collectionComboBox");
            collectionComboBox.getItems().setAll(collectionComboListData);

        } else //connection not available
        {
            MyLogger.log(Level.WARNING, LOG_CLASS_NAME + "Connection to DB unavailable. Can't get user collection");
        }
    }
    
    @FXML
    private void initiateUpdateCoin()
    {
        if (showConfermationMessage(CONFIMATION_COIN_UPDATE_TITLE, CONFIMATION_COIN_UPDATE_BODY))
        {
            if (updateCoin())
            {
                MyLogger.log(Level.INFO, "Coin update successful");
                showInfoMessage(INFORMATION_COIN_UPDATE_TITLE,INFORMATION_COIN_UPDATE_OK_BODY);
            }
            else 
            {
                MyLogger.log(Level.INFO, "Coin update failed");
                showErrorMessage(INFORMATION_COIN_UPDATE_TITLE,ERROR_COIN_UPDATE_FAIL_BODY);
            }
        }
    }
    
    /*
    * method to update the coin with updated user data
    */
    private boolean updateCoin()
    {
        MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Updating coin");
        boolean updateCoinSuccess = false;
        
        
        return updateCoinSuccess;
    }
    
    @FXML
    private void initiateDeleteCoin()
    {
        if (showConfermationMessage(CONFIMATION_COIN_DELETE_TITLE,CONFIMATION_COIN_DELETE_BODY ))
        {
            if (deleteCoin())
            {
                MyLogger.log(Level.INFO, "Coin: {0} deleted successful",coinNameTextField.getText());
                showInfoMessage(CONFIMATION_COIN_DELETE_TITLE,INFORMATION_COIN_DELETE_OK_BODY);
                closeWindow();
            }
            else 
            {
                MyLogger.log(Level.INFO, "Coin: {0} delete failed",coinNameTextField.getText());
                showErrorMessage(ERROR_COIN_DELETE_FAIL_TITLE,ERROR_COIN_DELETE_FAIL_BODY);
            }
        }
    }
    
    private boolean deleteCoin()
    {
        MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Deleting coin");
        boolean deleteCoinSuccess = false;
        if (coinProperty != null)
        {
            int deleteCoinResult = DBCoinConnect.removeCoinByCoinUUID(UUID.fromString(coinProperty.getCoinUUID()));
            if (deleteCoinResult == 1)
            {
                MyLogger.log(Level.INFO, "Coin delete successfull, coin UUID: {0}",coinProperty.getCoinUUID());
                deleteCoinSuccess = true;
            }
            else
            {
                MyLogger.log(Level.SEVERE, "Coin delete NOT successfull, coin UUID: {0}",coinProperty.getCoinUUID());
            }
        }
        
        
        return deleteCoinSuccess;
    }

}
