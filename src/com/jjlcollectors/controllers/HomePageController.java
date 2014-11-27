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
import com.jjlcollectors.collectables.CollectionType;
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
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hedgehog01
 */
public class HomePageController implements Initializable
{

    private String userEmail = "";
    private UUID userUUID;
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
    private TableView<CollectionProperty> collectionTableView;

    private ObservableList<CollectionProperty> collectionComboListData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //ObservableList<CollectionProperty> data = collectionTableView.getItems();
        //ObservableList<CollectionProperty> newData = FXCollections.observableArrayList();
        //data.addAll(DBCollectionConnect.getUserCollections(userUUID, data));
        //collectionComboListData.add(new CollectionProperty("test Collection",CollectionType.COIN.name(), "collection note", "collectionUUID"));
        //collectionComboBox.setItems(collectionComboListData);
        collectionComboBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends CollectionProperty> observable, CollectionProperty oldValue, CollectionProperty newValue) ->
        {

        }
        );
    }

    @FXML
    private boolean goToCollectionView()
    {
        boolean loadScreen = false;
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
            AddCoinController addCoinController = (AddCoinController) fxmlLoader.getController();
            addCoinController.setUserData(userUUID);

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
    private void getUserCollection()
    {
        if (DBConnect.isDBConnectable())
        {
            log.log(Level.INFO, "Getting user collections and adding to collection data");
            collectionComboListData.addAll(DBCollectionConnect.getUserCollections(userUUID));
            log.log(Level.INFO, "Adding data to collection combobox");
            collectionComboBox.getItems().addAll(collectionComboListData);
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
            stage.setTitle("Collector - Collection Viewer");
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
