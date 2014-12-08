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
package com.jjlcollectors.controllers;

import com.jjlcollectors.collectables.CollectionProperty;
import com.jjlcollectors.collectables.coins.CoinCreator;
import com.jjlcollectors.collectables.coins.CoinProperty;
import com.jjlcollectors.util.dbconnect.DBCollectionConnect;
import com.jjlcollectors.util.dbconnect.DBConnect;
import com.jjlcollectors.util.dbconnect.DBUsersConnect;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Hedgehog01
 */
public class HomePageController implements Initializable
{

    private String userEmail = "";
    private UUID userUUID = null;
    private UUID collectionUUID = null;
    private final boolean ALWAYS_ON_TOP = true;
    private final boolean SET_RESIZABLE = false;
    private static final Logger log = Logger.getLogger(HomePageController.class.getName());

    @FXML
    private ComboBox<CollectionProperty> collectionComboBox;

    @FXML
    private Button previewCollectionBtn;

    @FXML
    private Button openCollectionBtn;

    @FXML
    private Button createNewCollectionBtn;

    @FXML
    private Button getUserCollectionBtn;

    @FXML
    private TableView<CoinProperty> coinPreviewTableView;
    
    @FXML
    private Button addNewCoinbtn;

    private ObservableList<CollectionProperty> collectionComboListData = FXCollections.observableArrayList();
    private ObservableList<CoinProperty> coinTableData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        collectionComboBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends CollectionProperty> observable, CollectionProperty oldValue, CollectionProperty newValue) ->
        {

        }
        );

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
            log.log(Level.INFO, "Collection selction ComboBox Action, selected collection: {0}", selectedCollection.toString());
            collectionUUID = UUID.fromString(selectedCollection.getCollectionUUID());
        });
        
       

        //coinTableData = coinPreviewTableView.getItems();
        //coinTableData.setAll(CoinCreator.getCoinProperties(userUUID,coinTableData));
    }

    @FXML
    private boolean goToCollectionView()
    {
        boolean loadScreen = false;
        if (userUUID != null && collectionUUID != null)
        {
            try
            {
                Stage currentStage = (Stage) previewCollectionBtn.getScene().getWindow();
                //currentStage.hide();
                FXMLLoader fxmlLoader = new FXMLLoader();
                String filePath = "/com/jjlcollectors/fxml/collectionview/CollectionView.fxml";
                URL location = AddCoinController.class.getResource(filePath);
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                Parent root = fxmlLoader.load(location.openStream());
                CollectionViewController cvController = (CollectionViewController) fxmlLoader.getController();
                cvController.setCollectionData(userUUID, collectionUUID);

                //Parent parent = FXMLLoader.load(getClass().getResource("/com/jjlcollectors/fxml/collectionview/CollectionView.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                loadScreen = true;
            } catch (IOException ex)
            {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else
        {
            log.log(Level.WARNING, "could not load collection view, check userUUID or collectionUUID");
        }
        return loadScreen;
    }

    /*
     * method to set user data.
     After validating data is valid, initialize the data according to user data.
     */
    protected final void setUserData(String userEmail, String userPass)
    {
        checkLoginStatus(userEmail, userPass);
    }

    /*
     * method to validate user credentials
     */
    private void checkLoginStatus(String userEmail, String userPass)
    {

        log.log(Level.INFO, "Verifying login details");
        if (isLoginValid(userEmail, userPass))
        {
            log.log(Level.INFO, "login is valid set user email");
            setUserEmail(userEmail);
            setUserUUID();

        } else
        {
            log.log(Level.INFO, "login is not valid");
        }
    }

    /*
     * method to check if login credentials are valid.
     * returns true if the are.
     */
    private boolean isLoginValid(String userEmail, String userPass)
    {
        boolean loginValid = true;
        //verify user with this email exists
        if (!(DBUsersConnect.findUserByEmail(userEmail)))
        {
            log.log(Level.INFO, "User doesn't exist");
            loginValid = false;
        } //verify password with this email is valid
        else if (!(isPasswordValid(userEmail, userPass)))
        {
            log.log(Level.INFO, "User password incorrect");

            loginValid = false;
        }

        return loginValid;
    }

    /*
     * method to verify if user password is correct
     * returns true if it is.
     */
    private boolean isPasswordValid(String userEmail, String userPass)
    {
        boolean passwordValid = true;
        //verify password not empty
        if ((userPass == null) || (userPass.trim().isEmpty()))
        {
            log.log(Level.INFO, "User password Empty or null");
            passwordValid = false;
        } //Check password is NOT valid with this email and update boolean var
        else if (!(DBUsersConnect.isUserPasswordValid(userEmail, userPass.toCharArray())))
        {
            log.log(Level.INFO, "User password Invalid!");
            passwordValid = false;
        }
        return passwordValid;
    }

    /*
     * set user email                           
     */
    private void setUserEmail(final String userEmail)
    {
        this.userEmail = userEmail;
    }

    private void setUserUUID()
    {
        if (DBUsersConnect.getUserUUID(userEmail) != null)
        {
            userUUID = DBUsersConnect.getUserUUID(userEmail);
        } else
        {
            log.log(Level.SEVERE, "user UUID not found for Email {0}", userEmail);
        }
    }

    private void setCollectionUUID(UUID userUUID)
    {
        if (collectionUUID != null && DBUsersConnect.getUserUUID(userEmail) != null)
        {
            userUUID = DBUsersConnect.getUserUUID(userEmail);
        } else
        {
            log.log(Level.SEVERE, "user UUID not found for Email {0}", userEmail);
        }
    }

    private boolean loadNextScene(String userEmail, String userAttemptedPassword)
    {
        boolean loadScreen = false;
        try
        {
            Stage currentStage = (Stage) collectionComboBox.getScene().getWindow();
            currentStage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            String filePath = "/com/jjlcollectors/fxml/collectionview/CollectionView.fxml";
            URL location = CollectionViewController.class.getResource(filePath);
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load(location.openStream());
            CollectionViewController cvController = (CollectionViewController) fxmlLoader.getController();
            cvController.setUserData(userEmail, userAttemptedPassword);

            //Parent parent = FXMLLoader.load(getClass().getResource("/com/jjlcollectors/fxml/collectionview/CollectionView.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setResizable(false);
            stage.setTitle("Collector - Collection Viewer");
            stage.show();

            loadScreen = true;
        } catch (IOException ex)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loadScreen;
    }

    @FXML
    private void loadUserData()
    {
        if (DBConnect.isDBConnectable())
        {
            log.log(Level.INFO, "Adding data to collectionComboListData");
            collectionComboListData.setAll(DBCollectionConnect.getUserCollections(userUUID));
            log.log(Level.INFO, "Adding data to collectionComboBox");
            collectionComboBox.getItems().setAll(collectionComboListData);
            loadCoinTable();
        } else //connection not available
        {
            log.log(Level.WARNING, "Connection to DB unavailable. Can't get user collection");
        }
    }

    @FXML
    private boolean startNewCollection()
    {
        boolean loadScreen = false;
        try
        {
            Stage currentStage = (Stage) collectionComboBox.getScene().getWindow();
            //currentStage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            String filePath = "/com/jjlcollectors/fxml/addcollection/AddCollection.fxml";
            URL location = AddCollectionController.class.getResource(filePath);
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load(location.openStream());
            AddCollectionController addCollectionController = (AddCollectionController) fxmlLoader.getController();
            addCollectionController.setUserData(userUUID);

            //Parent parent = FXMLLoader.load(getClass().getResource("/com/jjlcollectors/fxml/collectionview/CollectionView.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setResizable(false);
            stage.setTitle("Collector - Create a new collection");
            stage.show();

            loadScreen = true;
        } catch (IOException ex)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loadScreen;

    }

    /*
     * populate the coin table
     * if collection selected only coins from that collection visible
     */
    private void loadCoinTable()
    {

        //ObservableList<CoinProperty> data = coinPreviewTableView.getItems();
        //ObservableList<CoinProperty> newData = FXCollections.observableArrayList();
        //data.setAll(CoinCreator.getCoinProperties(userUUID,coinTableData));
        if (userUUID != null)
        {
            if (collectionUUID != null)
            {
                log.log(Level.INFO, "Attempting to load coin data into table according to userUUID and collectionUUID");

                if ((CoinCreator.getCoinProperties(userUUID, collectionUUID) != null))
                {
                    //System.out.println("UserUUID: " + userUUID + " CollectionUUID " + collectionUUID);
                    ObservableList<CoinProperty> tempData = CoinCreator.getCoinProperties(userUUID, collectionUUID);
                    if (tempData == null)
                    {
                        System.out.println("tempdata is null");
                    }
                    coinTableData = coinPreviewTableView.getItems();
                    coinTableData.setAll(tempData);
                    coinPreviewTableView.setItems(coinTableData);

                } else
                {
                    log.log(Level.INFO, "coinTableData is null");
                }

            }

        }

    }

    @FXML
    private boolean addNewCoin()
    {
        boolean loadScreen = false;
        try
        {
            Stage currentStage = (Stage) coinPreviewTableView.getScene().getWindow();
            //currentStage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            String filePath = "/com/jjlcollectors/fxml/addcoin/addcoin.fxml";
            URL location = AddCoinController.class.getResource(filePath);
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load(location.openStream());
            AddCoinController addCoinController = (AddCoinController) fxmlLoader.getController();
            addCoinController.setUserData(userUUID, collectionUUID);

            //Parent parent = FXMLLoader.load(getClass().getResource("/com/jjlcollectors/fxml/collectionview/CollectionView.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setAlwaysOnTop(ALWAYS_ON_TOP);
            stage.setResizable(SET_RESIZABLE);
            stage.show();

            loadScreen = true;
        } catch (IOException ex)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loadScreen;
    }

    /**
     * method to exit the program.
     */
    @FXML
    public void doExit()
    {

        Platform.exit();
    }
}
