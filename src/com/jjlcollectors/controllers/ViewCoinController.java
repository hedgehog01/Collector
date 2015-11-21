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
import com.jjlcollectors.collectables.coins.Coin;
import com.jjlcollectors.collectables.coins.CoinCurrency;
import com.jjlcollectors.collectables.coins.CoinGrade;
import com.jjlcollectors.collectables.coins.CoinProperty;
import com.jjlcollectors.util.dbconnect.DBCoinConnect;
import com.jjlcollectors.util.dbconnect.DBCollectionConnect;
import com.jjlcollectors.util.dbconnect.DBConnect;
import com.jjlcollectors.util.filehandling.FileHandleClass;
import com.jjlcollectors.util.log.MyLogger;
import com.jjlcollectors.util.prefrences.PrefrencesHandler;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.stage.FileChooser;
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
    private UUID selectedItemCollectionUUID;
    private UUID coinUUID;
    private BufferedImage bufferedImage1;
    private BufferedImage bufferedImage2;
    private StringBuilder updateCoinStatusSB;
    private CollectionProperty selectedCollection;

    //Messages strings
    private final String COLLECTION_NOT_SELECTED = "Select Collection\n";
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
    private final String CONFIMATION_COIN_IMAGE_DELETE_TITLE = "Coin image delete";
    private final String CONFIMATION_COIN_IMAGE_DELETE_BODY = "Are you sure you would like to delete the coin image?";
    private final String ERROR_COIN_IMAGE_DELETE_TITLE = "Error!";
    private final String ERROR_COIN_IMAGE_DELETE_BODY = "Coin Image could not be deleted. Make sure application has privilage to do so.";
    private final String ERROR_COIN_DELETE_FAIL_TITLE = "Error - Delete coin failed";
    private final String ERROR_COIN_DELETE_FAIL_BODY = "Coin delete was unsuccessful!\nPlease try again later.";
    private final String FOLDER_CHOOSER_TITLE = "Select item Image";
    private final String ERROR_IMAGE_CANT_BE_LOADED_TITLE = "Error - Image info can't be loaded";
    private final String ERROR_IMAGE_CANT_BE_LOADED_BODY = "An Error has prevented Image from being loaded.\nPlease try again.";

    //coin validation messages
    private final String COIN_NAME_EMPTY = "Enter coin name.\n";
    private final String COLLECTION_TYPE_NOT_SELECTED = "Collection must be selected.\n";
    private final String CURRENCY_TYPE_NOT_SELECTED = "Currency must be selected.\n";
    private final String GRADE_NOT_SELECTED = "Coin Grade must be selected.\n";
    private final String COLLECTION_NAME_EMPTY = "Enter colletion name.\n";
    private final String FACEVALUE_EMPTY = "Enter Face Value.\n";
    private final String FACEVALUE_NONNUMERIC = "Face Value must be numeric.\n";
    private final String Mint_YEAR_EMPTY = "Enter Mint Year.\n";
    private final String Mint_YEAR_NONNUMERIC = "Mint Year must be numeric.\n";
    private final String COLLECTION_NAME_EXISTS = "Name already exists.\n";
    private final String COIN_NOT_ADDED = "Coin was not added.\nPlease try again later\n.";
    private final String BUY_PRICE_EMPTY = "Enter a buy price.\n";
    private final String BUY_PRICE_NON_NUMERIC = "Buy price must be numeric\n";
    private final String MINT_YEARS_INVALID = "Coin mint year must be between:\n-2700 and 2700\n";
    private final String BUY_DATE_EMPTY = "Coin buy date must be selected";
    public final String USER_DIR = "user.dir";
    public final String IMAGE_DIRECTORY = "images";
    public final String IMAGE_OUTPUT_EXTENSION = "jpg";

    @FXML
    private AnchorPane viewCoinAnchorPane;

    @FXML
    private TextField coinNameTxtField;

    @FXML
    private ComboBox<CoinCurrency> coinCurrencyComboBox;

    @FXML
    private ComboBox<CoinGrade> coinGradeComboBox;

    @FXML
    private ComboBox<CollectionProperty> collectionComboBox;

    @FXML
    private TextField coinFaceValueTxtField;

    @FXML
    private TextField coinMintYearTxtField;

    @FXML
    private TextField coinValueTxtField;

    @FXML
    private TextField coinMintMarkTxtField;

    @FXML
    private TextField coinBuyPriceTxtField;

    @FXML
    private TextField coinNoteTxtField;

    @FXML
    private TextArea coinUpdateStatus;

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
        //setup update status string builder and status area
        updateCoinStatusSB = new StringBuilder();
        coinUpdateStatus.setVisible(false);

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
        collectionComboBox.setCellFactory((comboBox)
                -> 
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

        //setup add image button listeners
        addCoinImageBtn1.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                loadImageButtonAction(1);
            }
        });
        addCoinImageBtn2.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                loadImageButtonAction(2);
            }
        });
        removeCoinImageBtn1.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                removeImageButtonAction(1);
            }
        });
        removeCoinImageBtn2.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                removeImageButtonAction(2);
            }
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
            coinUUID = UUID.fromString(coinProperty.getCoinUUID());
            coinNameTxtField.setText(coinProperty.getCoinName());
            coinCurrencyComboBox.setValue(CoinCurrency.valueOf(coinProperty.getCoinCurrency()));
            coinGradeComboBox.setValue(CoinGrade.valueOf(coinProperty.getCoinGrade()));
            coinFaceValueTxtField.setText(coinProperty.getCoinFaceValue());
            coinMintYearTxtField.setText(Integer.toString(coinProperty.getCoinYear()));
            coinValueTxtField.setText(coinProperty.getCoinValue());
            coinBuyPriceTxtField.setText(coinProperty.getCoinBuyPrice());
            coinMintMarkTxtField.setText(coinProperty.getCoinMintMark());
            coinNoteTxtField.setText(coinProperty.getCoinNote());
            coinBuyDatePicker.setValue(LocalDate.parse(coinProperty.getCoinBuyDate()));

            //setup collection ComboBox
            String coinCollectionUUID = coinProperty.getCoinCollectionUUID();
            selectedItemCollectionUUID = UUID.fromString(coinCollectionUUID);
            CollectionProperty collection = DBCollectionConnect.getCollectionProperty(UUID.fromString(coinCollectionUUID));
            collectionComboBox.setValue(collection);
            CollectionProperty selectedCollection = collectionComboBox.getSelectionModel().getSelectedItem();
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Collection selction ComboBox Action, selected collection: {0}", selectedCollection.toString());
            collectionUUID = UUID.fromString(selectedCollection.getCollectionUUID());

            //load coin images
        } else if (coinProperty == null)
        {
            MyLogger.log(Level.SEVERE, LOG_CLASS_NAME + "CoinProperty object is null, can't load info");
            showErrorMessage(ERROR_COIN_CANT_BE_LOADED_TITLE, ERROR_COIN_CANT_BE_LOADED_BODY);
            closeWindow();
        }

        // Handle ComboBox event.
        collectionComboBox.setOnAction((event)
                -> 
                {
                    selectedCollection = collectionComboBox.getSelectionModel().getSelectedItem();
                    MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Collection selction ComboBox Action, selected collection: {0}", selectedCollection.toString());
                    collectionUUID = UUID.fromString(selectedCollection.getCollectionUUID());
        });

        //load coin images if they exists
        ArrayList<File> itemImages = FileHandleClass.getItemImages(userUUID, selectedItemCollectionUUID, coinUUID);
        for (int i = 0; i < itemImages.size(); i++)
        {
            File itemFile = itemImages.get(i);
            String filePath = itemFile.getPath();
            if (filePath.endsWith("-1.jpg"))
            {
                MyLogger.log(Level.INFO, "Found image 1 for item");
                handleImageLoading(itemFile, 1);
            } else if (filePath.endsWith("-2.jpg"))
            {
                MyLogger.log(Level.INFO, "Found image 2 for item");
                handleImageLoading(itemFile, 2);
            }
        }

    }

    /*
     * method to get the user selected folder
     */
    private void loadImageButtonAction(int buttonPressed)
    {
        Stage currentStage = (Stage) viewCoinAnchorPane.getScene().getWindow();
        FileChooser fileChoose = new FileChooser();
        fileChoose.getExtensionFilters().addAll(
                //new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                //new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        //attempt to get saved folder location from prefrences
        File lastFolderSelected = PrefrencesHandler.getFolderPath();
        MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Saved file path retrieved: {0}", lastFolderSelected);

        if (lastFolderSelected != null && lastFolderSelected.exists())
        {
            final File initialDir = new File(lastFolderSelected.getPath());
            fileChoose.setInitialDirectory(initialDir);
        }

        fileChoose.setTitle(FOLDER_CHOOSER_TITLE);

        //open Dialog
        File coinImageFile = fileChoose.showOpenDialog(currentStage);

        if (coinImageFile != null)
        {
            handleImageLoading(coinImageFile, buttonPressed);
        }
    }

    //@SuppressWarnings("fallthrough")
    private void removeImageButtonAction(int imgNum)
    {
        switch (imgNum)
        {
            case 1:
            {
                MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Removing image {0}", imgNum);
                if (showConfirmationMessage(CONFIMATION_COIN_IMAGE_DELETE_TITLE, CONFIMATION_COIN_IMAGE_DELETE_BODY))
                {
                    MyLogger.log(Level.INFO, LOG_CLASS_NAME + "User confirmed delete coin: {0}, image num: {1}", new Object[]
                    {
                        coinUUID.toString(), imgNum
                    });
                    if (FileHandleClass.itemImageDelete(userUUID, collectionUUID, coinUUID, imgNum))
                    {
                        bufferedImage1 = null;
                        coinImageView1.setImage(null);
                    } else
                    {
                        //image couldn't be deleted for some reason
                        showErrorMessage(ERROR_COIN_IMAGE_DELETE_TITLE, ERROR_COIN_IMAGE_DELETE_BODY);
                    }

                } else
                {
                    MyLogger.log(Level.INFO, LOG_CLASS_NAME + "User did NOT confirm delete coin: {0}, image num: {1}", new Object[]
                    {
                        coinUUID.toString(), imgNum
                    });
                }
                break;
            }
            case 2:
            {
                MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Removing image {0}", imgNum);
                if (showConfirmationMessage(CONFIMATION_COIN_IMAGE_DELETE_TITLE, CONFIMATION_COIN_IMAGE_DELETE_BODY))
                {
                    MyLogger.log(Level.INFO, LOG_CLASS_NAME + "User confirmed delete coin: {0}, image num: {1}", new Object[]
                    {
                        coinUUID.toString(), imgNum
                    });
                    if (FileHandleClass.itemImageDelete(userUUID, collectionUUID, coinUUID, imgNum))
                    {
                        bufferedImage2 = null;
                        coinImageView2.setImage(null);
                    } else
                    {
                        //image couldn't be deleted for some reason
                        showErrorMessage(ERROR_COIN_IMAGE_DELETE_TITLE, ERROR_COIN_IMAGE_DELETE_BODY);
                    }
                } else
                {
                    MyLogger.log(Level.INFO, LOG_CLASS_NAME + "User did NOT confirm delete coin: {0}, image num: {1}", new Object[]
                    {
                        coinUUID.toString(), imgNum
                    });
                }
                break;
            }
            default:
            {
                MyLogger.log(Level.SEVERE, LOG_CLASS_NAME + "Image to remove was not defined, image {0}", imgNum);
            }
        }

    }

    private void saveCoinImages()
    {
        MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Attempting to save coin images");
        boolean imagesSaved;
        if (bufferedImage1 != null)
        {
            imagesSaved = FileHandleClass.handleImageSaveing(bufferedImage1, 1, userUUID, collectionUUID, coinUUID);
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Image 1 saved: {0}", imagesSaved);

        }
        if (bufferedImage2 != null)
        {
            imagesSaved = FileHandleClass.handleImageSaveing(bufferedImage2, 2, userUUID, collectionUUID, coinUUID);
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Image 2 saved: {0}", imagesSaved);
        }

    }

    /*
     * method to handle loading of coin image including save of last folder preference
     */
    private void handleImageLoading(File coinImageFile, int buttonPressed)
    {
        //save folder path to prefrences
        if (coinImageFile != null && coinImageFile.exists())
        {
            BufferedImage bufferedImage = FileHandleClass.getBufferedImageFromFile(coinImageFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            switch (buttonPressed)
            {
                case 1:
                {
                    coinImageView1.setImage(image);
                    bufferedImage1 = bufferedImage;
                    break;
                }
                case 2:
                {
                    coinImageView2.setImage(image);
                    bufferedImage2 = bufferedImage;
                    break;
                }
            }
        } else
        {
            showErrorMessage(ERROR_IMAGE_CANT_BE_LOADED_TITLE, ERROR_IMAGE_CANT_BE_LOADED_BODY);
        }

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
    private boolean showConfirmationMessage(String title, String body)
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
        alert.getButtonTypes().setAll(buttonTypeCancel, buttonTypeOK);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeOK)
        {
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "User confirmed coin action");
            return true;
        } else if (result.isPresent() && result.get() == buttonTypeCancel)
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
        if (showConfirmationMessage(CONFIMATION_COIN_UPDATE_TITLE, CONFIMATION_COIN_UPDATE_BODY))
        {
            if (updateCoin())
            {
                MyLogger.log(Level.INFO, "Coin update successful");
                showInfoMessage(INFORMATION_COIN_UPDATE_TITLE, INFORMATION_COIN_UPDATE_OK_BODY);
                closeWindow();
            } else
            {
                MyLogger.log(Level.INFO, "Coin update failed");
                showErrorMessage(INFORMATION_COIN_UPDATE_TITLE, ERROR_COIN_UPDATE_FAIL_BODY);
            }
        }
    }

    /*
     * method to update the coin with updated coin data
     */
    private boolean updateCoin()
    {

        MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Updating coin");
        coinUpdateStatus.setText("");
        coinUpdateStatus.setVisible(false);
        boolean updateCoinSuccess = false;

        if (isCoinValid())
        {
            //TODO
            StringBuilder coinNote = new StringBuilder(coinNoteTxtField.getText());
            int coinYear = Integer.parseInt(coinMintYearTxtField.getText());
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "collectionComboBox.getValue(): {0}", collectionComboBox.getValue());
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "collection uuid is: {0}", collectionUUID);
            coinUUID = UUID.fromString(coinProperty.getCoinUUID());
            Coin newCoin = new Coin(userUUID, coinNameTxtField.getText(), coinGradeComboBox.getValue(), coinFaceValueTxtField.getText(), coinCurrencyComboBox.getValue(), coinNote, coinYear, coinMintMarkTxtField.getText(), coinBuyPriceTxtField.getText(), coinValueTxtField.getText(), coinBuyDatePicker.getValue(), collectionUUID, coinUUID);
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Coin object with updated info created, coin uuid is {0}", newCoin.getItemUUID().toString());

            if (DBCoinConnect.updateCoin(newCoin))
            {
                MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Coin updated in DB, user uuid is {0}", userUUID.toString());
                MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Coin updated in DB, collection uuid is {0}", collectionUUID.toString());
                MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Coin updated in DB, coin uuid is {0}", newCoin.getItemUUID().toString());
                updateCoinSuccess = true;
                saveCoinImages();
            } else
            {
                MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Coin NOT updated in DB, user uuid is {0}", userUUID.toString());
                MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Coin NOT updated in DB, coin uuid is {0}", newCoin.getItemUUID().toString());
                updateCoinSuccess = false;
            }
        }
        return updateCoinSuccess;
    }

    @FXML
    private void initiateDeleteCoin()
    {
        if (showConfirmationMessage(CONFIMATION_COIN_DELETE_TITLE, CONFIMATION_COIN_DELETE_BODY))
        {
            if (deleteCoin())
            {
                MyLogger.log(Level.INFO, "Coin: {0} deleted successful", coinNameTxtField.getText());
                showInfoMessage(CONFIMATION_COIN_DELETE_TITLE, INFORMATION_COIN_DELETE_OK_BODY);
                closeWindow();
            } else
            {
                MyLogger.log(Level.INFO, "Coin: {0} delete failed", coinNameTxtField.getText());
                showErrorMessage(ERROR_COIN_DELETE_FAIL_TITLE, ERROR_COIN_DELETE_FAIL_BODY);
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
                MyLogger.log(Level.INFO, "Coin delete successfull, coin UUID: {0}", coinProperty.getCoinUUID());
                deleteCoinSuccess = true;
            } else
            {
                MyLogger.log(Level.SEVERE, "Coin delete NOT successfull, coin UUID: {0}", coinProperty.getCoinUUID());
            }
        }

        return deleteCoinSuccess;
    }

    private boolean isCoinValid()
    {
        boolean coinValid = true;
        StringBuilder issues = new StringBuilder("");

        coinUpdateStatus.setVisible(!(coinValid));

        //check name filled
        if (coinNameTxtField.getText() == null || coinNameTxtField.getText().trim().isEmpty())
        {
            issues.append(COIN_NAME_EMPTY);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "coin name is empty or null: {0}", coinNameTxtField.getText());
        }
        //check currency combo box
        if (coinCurrencyComboBox.getValue() == null)
        {
            issues.append(CURRENCY_TYPE_NOT_SELECTED);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Currency selected is: {0}", coinCurrencyComboBox.getValue());
        }

        //check facevalue
        if (coinFaceValueTxtField.getText() == null || coinFaceValueTxtField.getText().trim().isEmpty())
        {
            issues.append(FACEVALUE_EMPTY);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Face value is: {0}", coinFaceValueTxtField.getText());
        } else if (!(isDouble(coinFaceValueTxtField.getText())))
        {
            issues.append(FACEVALUE_NONNUMERIC);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Face value is non-numeric: {0}", coinFaceValueTxtField.getText());
        }
        //check mint year
        if (coinMintYearTxtField.getText() == null || coinMintYearTxtField.getText().trim().isEmpty())
        {
            issues.append(Mint_YEAR_EMPTY);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Face value is: {0}", coinMintYearTxtField.getText());
        } else if (!(isInteger(coinMintYearTxtField.getText())))
        {
            issues.append(Mint_YEAR_NONNUMERIC);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Face value is non-numeric: {0}", coinMintYearTxtField.getText());
        } else if (!(Integer.parseInt(coinMintYearTxtField.getText()) >= -2700 && Integer.parseInt(coinMintYearTxtField.getText()) <= 2700))
        {
            issues.append(MINT_YEARS_INVALID);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Coin Year not between -2700 and 2077: {0}", coinMintYearTxtField.getText());
        }

        //check buy price
        if (coinBuyPriceTxtField.getText() == null || coinBuyPriceTxtField.getText().trim().isEmpty())
        {
            issues.append(BUY_PRICE_EMPTY);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "buy price is: {0}", coinBuyPriceTxtField.getText());
        } else if (!(isDouble(coinBuyPriceTxtField.getText())))
        {
            issues.append(BUY_PRICE_NON_NUMERIC);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "coin buy price is non-numeric: {0}", coinBuyPriceTxtField.getText());
        }

        //check collection combo box
        if (collectionComboBox.getValue() == null)
        {
            issues.append(COLLECTION_TYPE_NOT_SELECTED);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "collection selected is: {0}", collectionComboBox.getValue());
        }

        //check grade combo box
        if (coinGradeComboBox.getValue() == null)
        {
            issues.append(GRADE_NOT_SELECTED);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "grade selected is: {0}", coinGradeComboBox.getValue());
        }
        //check coin buy date
        if (coinBuyDatePicker.getValue() == null)
        {
            issues.append(BUY_DATE_EMPTY);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "coin buy date not selected, value is: {0}", coinBuyDatePicker.getValue());
        }
        if (!(coinValid))
        {
            coinUpdateStatus.setVisible(!(coinValid));
            coinUpdateStatus.setBorder(Border.EMPTY);
            coinUpdateStatus.setText(issues.toString());

        }
        return coinValid;
    }

    /*
     * method to check if string contains integer numeric value
     */
    private boolean isDouble(String str)
    {
        try
        {
            double i = Double.parseDouble(str);
        } catch (NumberFormatException nfe)
        {
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Non numeric value. Value was: {0}", str);
            return false;
        }
        return true;

    }

    /*
     * method to check if string contains integer numeric value
     */
    private boolean isInteger(String str)
    {
        try
        {
            int i = Integer.parseInt(str);
        } catch (NumberFormatException nfe)
        {
            MyLogger.log(Level.INFO, LOG_CLASS_NAME + "Non numeric value. Value was: {0}", str);
            return false;
        }
        return true;

    }

}
