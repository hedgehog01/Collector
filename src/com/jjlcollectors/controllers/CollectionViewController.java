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

import com.jjlcollectors.collectables.coins.CoinCreator;
import com.jjlcollectors.collectables.coins.CoinProperty;
import com.jjlcollectors.util.dbconnect.DBUsersConnect;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hedgehog01
 */
public final class CollectionViewController implements Initializable
{

    private static final Logger log = Logger.getLogger(CollectionViewController.class.getName());
    private String userEmail = "";
    private UUID userUUID;
    private UUID collectionUUID;

    @FXML
    private VBox vBoxMain;

    @FXML
    private TableView<CoinProperty> tableView;

    @FXML
    private Button refreshButton;

    @FXML
    private Button addCoinBtn;

    @FXML
    private MenuBar mainMenuBar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        //mainMenuBar.getScene().getWindow().sizeToScene();
        //((Node)(vBoxMain.getScene().getWindow().sizeToScene());
        ObservableList<CoinProperty> data = tableView.getItems();
        ObservableList<CoinProperty> newData = FXCollections.observableArrayList();

        //newData.setAll(CoinCreator.getCoinProperties(userUUID,data));

    }


    @FXML
    protected void addCoin(ActionEvent event)
    {
        
        ((Node) (event.getSource())).getScene().getWindow().sizeToScene();
        ObservableList<CoinProperty> data = tableView.getItems();
        ObservableList<CoinProperty> newData = FXCollections.observableArrayList();

        //
        //To Do

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
     * method to set user data.
     After validating data is valid, initialize the data according to user data.
     */
    protected final void setCollectionData (UUID userUUID, UUID collectionUUID)
    {
        this.userUUID = userUUID;
        this.collectionUUID = collectionUUID;
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
    /**
     * method to exit the program.
     */
    @FXML
    public void doExit()
    {

        Platform.exit();
    }

    @FXML
    private boolean addNewCoin()
    {
        boolean loadScreen = false;
        try
        {
            Stage currentStage = (Stage) addCoinBtn.getScene().getWindow();
            //currentStage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            String filePath = "/com/jjlcollectors/fxml/addcoin/addcoin.fxml";
            URL location = AddCoinController.class.getResource(filePath);
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load(location.openStream());
            AddCoinController addCoinController = (AddCoinController) fxmlLoader.getController();
            //TODO - fix add coin set user data
            //addCoinController.setUserData(userUUID,collectionUUID);
            
            //Parent parent = FXMLLoader.load(getClass().getResource("/com/jjlcollectors/fxml/collectionview/CollectionView.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene (root);
            stage.setScene(scene);
            stage.show();
            
            loadScreen = true;           
        } catch (IOException ex)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loadScreen;
    }
}
