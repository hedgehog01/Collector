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
import com.jjlcollectors.interfaces.ControlledScreen;
import com.jjlcollectors.util.dbconnect.DBUsersConnect;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Hedgehog01
 */
public final class CollectionViewController implements Initializable, ControlledScreen
{
    private static final Logger log = Logger.getLogger(CollectionViewController.class.getName());
    private ScreensController myController;
    private String userEmail = "";
    
    @FXML
    private VBox vBoxMain;

    @FXML
    private TableView<CoinProperty> tableView;

    @FXML
    private Button refreshButton;
    
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
        
        data.addAll(CoinCreator.getCoinProperties(newData));
        
    }
    

    @Override
    public void setScreenParent(ScreensController screenParent)
    {
        myController = screenParent;
    }

    @FXML
    protected void addCoin(ActionEvent event)
    {
        ((Node)(event.getSource())).getScene().getWindow().sizeToScene();
        ObservableList<CoinProperty> data = tableView.getItems();
        ObservableList<CoinProperty> newData = FXCollections.observableArrayList();
        
        data.addAll(CoinCreator.getCoinProperties(newData));
        //To Do

    }
    /*
    * method to set user data.
    After validating data is valid, initialize the data according to user data.
    */
    protected final void setUserData(String userEmail, String userPass)
    {
        checkLoginStatus (userEmail,userPass);
    }
    
    /*
    * method to validate user credentials
    */
    private void checkLoginStatus(String userEmail, String userPass)
    {
        
        log.log(Level.INFO, "Verifying login details");
        if (isLoginValid(userEmail,userPass))
        {
            log.log(Level.INFO, "login is valid set user email");
            setUserEmail(userEmail);
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
        else if (!(isPasswordValid(userEmail,userPass)))
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
    private void setUserEmail (final String userEmail)
    {
        this.userEmail = userEmail;
    }
}
