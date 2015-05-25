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

import com.jjlcollectors.collectables.CollectionProperty;
import com.jjlcollectors.collectables.coins.Coin;
import com.jjlcollectors.collectables.coins.CoinCurrency;
import com.jjlcollectors.collectables.coins.CoinGrade;
import com.jjlcollectors.collectables.coins.CoinProperty;
import com.jjlcollectors.util.dbconnect.DBCoinConnect;
import com.jjlcollectors.util.dbconnect.DBCollectionConnect;
import com.jjlcollectors.util.dbconnect.DBConnect;
import com.jjlcollectors.util.log.MyLogger;
import com.jjlcollectors.util.prefrences.PrefrencesHandler;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author nathanr
 */
public final class AddCoinController implements Initializable
{

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
    private final String LOG_CLASS_NAME = "AddCoinController: ";

    private final String FOLDER_CHOOSER_TITLE = "Add Coin Image";

    private UUID userUUID;
    private UUID collectionUUID;
    private UUID coinUUID;
    private BufferedImage bufferedImage1;
    private BufferedImage bufferedImage2;
    
    private ObservableList<CoinProperty> coinTableData = FXCollections.observableArrayList();

    @FXML
    private Button addCoinImageBtn1;

    @FXML
    private Button addCoinImageBtn2;
    
    @FXML
    private Button removeCoinImageBtn1;
    
    @FXML
    private Button removeCoinImageBtn2;

    @FXML
    private AnchorPane addCoinAnchorPane;

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
    private ComboBox<CoinGrade> coinGradeComboBox;

    @FXML
    private Button addCoinBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<CoinCurrency> currencyComboBox;

    @FXML
    private ComboBox<CollectionProperty> collectionComboBox;

    @FXML
    private TextArea coinAddStatus;

    @FXML
    private Label addCoinStatus;

    @FXML
    private DatePicker coinBuyDatePicker;

    @FXML
    private ImageView coinImageView1;

    @FXML
    private ImageView coinImageView2;

    private ObservableList<CollectionProperty> collectionComboListData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        currencyComboBox.getItems().addAll(CoinCurrency.values());
        coinGradeComboBox.getItems().addAll(CoinGrade.values());
        coinAddStatus.setVisible(false);

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

        // Handle ComboBox event.
        collectionComboBox.setOnAction((event) ->
        {
            CollectionProperty selectedCollection = collectionComboBox.getSelectionModel().getSelectedItem();
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Collection selction ComboBox Action, selected collection: {0}", selectedCollection.toString());
            collectionUUID = UUID.fromString(selectedCollection.getCollectionUUID());
        });

        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                MyLogger.log(Level.INFO, LOG_CLASS_NAME+"In initialize, in Platform.runLater");

            }
        });
    }

    @FXML
    private void closeWindow()
    {
        MyLogger.log(Level.INFO, LOG_CLASS_NAME+"closing window");
        coinImageView1.setImage(null);
        coinImageView2.setImage(null);
        Stage currentStage = (Stage) addCoinBtn.getScene().getWindow();
        currentStage.close();
    }

    protected void setUserData(UUID userUUID, UUID collectionUUID, ObservableList<CoinProperty> coinTableData)
    {
        this.userUUID = userUUID;
        this.collectionUUID = collectionUUID;
        this.coinTableData = coinTableData;
    }

    protected void setUserData(UUID userUUID)
    {
        this.userUUID = userUUID;
    }

    @FXML
    private void addNewCoin()
    {

        if (isCoinValid())
        {
            coinAddStatus.setText("");
            coinAddStatus.setVisible(false);
            StringBuilder coinNote = new StringBuilder(coinNoteTxtField.getText());
            int coinYear = Integer.parseInt(coinMintYearTxtField.getText());
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"collectionComboBox.getValue(): {0}", collectionComboBox.getValue());
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"collection uuid is: {0}", collectionUUID);
            coinUUID = UUID.randomUUID();
            Coin newCoin = new Coin(userUUID, coinNameTxtField.getText(), coinGradeComboBox.getValue(), coinFaceValueTxtField.getText(), currencyComboBox.getValue(), coinNote, coinYear, coinMintMarkTxtField.getText(), coinBuyPriceTxtField.getText(), coinValueTxtField.getText(), coinBuyDatePicker.getValue(), collectionUUID, coinUUID);
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"New Coin created, user uuid is {0}", userUUID.toString());
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"New Coin created, collection uuid is {0}", collectionUUID.toString());
            if (DBCoinConnect.addCoin(newCoin))
            {
                
                MyLogger.log(Level.INFO, LOG_CLASS_NAME+"coin added successfully, attemp to add new coinproperty");
                CoinProperty coinProperty = new CoinProperty(newCoin);
                coinTableData.add(coinProperty);
                saveCoinImages();
                closeWindow();
            } else
            {
                MyLogger.log(Level.SEVERE, LOG_CLASS_NAME+"coin NOT added successfully");
                coinAddStatus.setText(COIN_NOT_ADDED);
            }
        }
    }

    @FXML
    private void loadUserData()
    {
        if (DBConnect.isDBConnectable())
        {
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Adding data to collectionComboListData");
            collectionComboListData.setAll(DBCollectionConnect.getUserCollections(userUUID));
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Adding data to collectionComboBox");
            collectionComboBox.getItems().setAll(collectionComboListData);

        } else //connection not available
        {
            MyLogger.log(Level.WARNING, LOG_CLASS_NAME+"Connection to DB unavailable. Can't get user collection");
        }
    }

    /*
     * method to get the user selected folder
     */
    private void loadImageButtonAction(int buttonPressed)
    {
        Stage currentStage = (Stage) addCoinAnchorPane.getScene().getWindow();
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
        MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Saved file path retrieved: {0}", lastFolderSelected);

        if (lastFolderSelected != null)
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
    
    @SuppressWarnings("fallthrough")
    private void removeImageButtonAction (int imgNum)
    {
        switch (imgNum)
        {
            case 1:
            {
                MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Removing image {0}", imgNum);
                bufferedImage1 = null;
                coinImageView1.setImage(null);
                break;
            }
            case 2:
            {
                MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Removing image {0}", imgNum);
                bufferedImage2 = null;
                coinImageView2.setImage(null);
                break;
            }
        }
        
    }

    private void saveCoinImages()
    {
        MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Attempting to save coin images");
        boolean imagesSaved;
        if (bufferedImage1 != null)
        {
            imagesSaved = handleImageSaveing(bufferedImage1, 1, collectionUUID, coinUUID);
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Image 1 saved: {0}",imagesSaved);
            
        }
        if (bufferedImage2 != null)
        {
            imagesSaved = handleImageSaveing(bufferedImage2, 2, collectionUUID, coinUUID);
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Image 2 saved: {0}",imagesSaved);
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
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"collection name is: {0}", coinNameTxtField.getText());
        } else if (DBCollectionConnect.getCollectionUUID(coinNameTxtField.getText()) != null) //check if collection name already exists
        {
            issues.append(COLLECTION_NAME_EXISTS);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"collection name already exists: {0}", coinNameTxtField.getText());
        }

        //check currency combo box
        if (currencyComboBox.getValue() == null)
        {
            issues.append(CURRENCY_TYPE_NOT_SELECTED);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Currency selected is: {0}", currencyComboBox.getValue());
        }

        //check facevalue
        if (coinFaceValueTxtField.getText() == null || coinFaceValueTxtField.getText().trim().isEmpty())
        {
            issues.append(FACEVALUE_EMPTY);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Face value is: {0}", coinFaceValueTxtField.getText());
        } else if (!(isDouble(coinFaceValueTxtField.getText())))
        {
            issues.append(FACEVALUE_NONNUMERIC);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Face value is non-numeric: {0}", coinFaceValueTxtField.getText());
        }

        //check mint year
        if (coinMintYearTxtField.getText() == null || coinMintYearTxtField.getText().trim().isEmpty())
        {
            issues.append(Mint_YEAR_EMPTY);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Face value is: {0}", coinMintYearTxtField.getText());
        } else if (!(isInteger(coinMintYearTxtField.getText())))
        {
            issues.append(Mint_YEAR_NONNUMERIC);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Face value is non-numeric: {0}", coinMintYearTxtField.getText());
        } else if (!(Integer.parseInt(coinMintYearTxtField.getText()) >= -2700 && Integer.parseInt(coinMintYearTxtField.getText()) <= 2700))
        {
            issues.append(MINT_YEARS_INVALID);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Coin Year not between -2700 and 2077: {0}", coinMintYearTxtField.getText());
        }

        //check buy price
        if (coinBuyPriceTxtField.getText() == null || coinBuyPriceTxtField.getText().trim().isEmpty())
        {
            issues.append(BUY_PRICE_EMPTY);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"buy price is: {0}", coinBuyPriceTxtField.getText());
        } else if (!(isDouble(coinBuyPriceTxtField.getText())))
        {
            issues.append(BUY_PRICE_NON_NUMERIC);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"coin buy price is non-numeric: {0}", coinBuyPriceTxtField.getText());
        }

        //check collection combo box
        if (collectionComboBox.getValue() == null)
        {
            issues.append(COLLECTION_TYPE_NOT_SELECTED);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"collection selected is: {0}", collectionComboBox.getValue());
        }

        //check grade combo box
        if (coinGradeComboBox.getValue() == null)
        {
            issues.append(GRADE_NOT_SELECTED);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"grade selected is: {0}", coinGradeComboBox.getValue());
        }
        if (coinBuyDatePicker.getValue() == null)
        {
            issues.append(BUY_DATE_EMPTY);
            coinValid = false;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"coin buy date not selected, value is: {0}", coinBuyDatePicker.getValue());
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
     * method to check if string contains integer numeric value
     */
    private boolean isInteger(String str)
    {
        try
        {
            int i = Integer.parseInt(str);
        } catch (NumberFormatException nfe)
        {
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Non numeric value. Value was: {0}", str);
            return false;
        }
        return true;

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
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Non numeric value. Value was: {0}", str);
            return false;
        }
        return true;

    }

    private boolean handleImageSaveing(BufferedImage buffImage, int imageNum, UUID collectionUUID, UUID coinUUID)
    {
        boolean imageSaved = false;
        if (System.getProperty(USER_DIR) == null || userUUID == null || collectionUUID == null || coinUUID == null)
        {
            MyLogger.log(Level.SEVERE, LOG_CLASS_NAME+"Could not save image due to missing info.");
            return imageSaved;
        } else
        {
            String userDir = System.getProperty(USER_DIR);
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"User Dir: {0}", userDir);
            String filePath = userDir + "/" + IMAGE_DIRECTORY + "/" + userUUID.toString() + "/" + collectionUUID.toString();
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Full path to save the image: {0}", userDir);
            String imageName = coinUUID.toString() + imageNum + "." + IMAGE_OUTPUT_EXTENSION;
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Image name: {0}", imageName);
            
            File outPutFilePath = new File(filePath);
            if (!outPutFilePath.exists())
            {
                MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Folder path {0} did not exist, attempting to create", outPutFilePath.getPath());
                if (!outPutFilePath.mkdirs())
                {
                    MyLogger.log(Level.SEVERE, LOG_CLASS_NAME+"Could not create folder path {0} returning false", outPutFilePath.getPath());
                    return false;
                }
            }
            
            File imageFile = new File (outPutFilePath.getPath() + "/" + imageName);
            MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Full image path is: {0}", imageFile.getPath());

            try
            {
                MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Buffered image {0}", buffImage.getWidth());
                ImageIO.write(buffImage,IMAGE_OUTPUT_EXTENSION,imageFile);
                MyLogger.log(Level.INFO, LOG_CLASS_NAME+"image {0} was written to disk", imageFile.getPath());
                imageSaved = true;
            } catch (IOException ex)
            {
                imageSaved = false;
                MyLogger.log(Level.SEVERE, LOG_CLASS_NAME+"Image write failed {0}", ex);
            }

        }
        return imageSaved;
    }

    /*
     * method to handle loading of coin image including save of last folder preference
     */
    private void handleImageLoading(File coinImageFile, int buttonPressed)
    {
        //save folder path to prefrences
        BufferedImage bufferedImage = null;
        try
        {
            bufferedImage = ImageIO.read(coinImageFile);

        } catch (IOException ex)
        {
            Logger.getLogger(AddCoinController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);

        String tempPath = coinImageFile.getPath();
        String stringFolderPath = tempPath.substring(0, tempPath.lastIndexOf("\\"));
        File folderPath = new File(stringFolderPath);
        MyLogger.log(Level.INFO, LOG_CLASS_NAME+"attempting to save folder path to prefrences");
        PrefrencesHandler.setFolderPath(folderPath);

        String filePathStr = coinImageFile.getPath();

        MyLogger.log(Level.INFO, LOG_CLASS_NAME+"Folder path selected is: {0}", folderPath);

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

    }

}
