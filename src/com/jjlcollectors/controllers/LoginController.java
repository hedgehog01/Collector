/*
 * Copyright (C) 2014 Hedgehog01.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package com.jjlcollectors.controllers;

import com.jjlcollectors.interfaces.ControlledScreen;
import com.jjlcollectors.util.dbconnect.DBUsersConnect;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hedgehog01
 */
public final class LoginController implements Initializable, ControlledScreen
{

    private static final Logger log = Logger.getLogger(LoginController.class.getName());
    private final String PASSWORD_EMPTY = "Enter Password";
    private final String EMAIL_OR_PASSWORD_INCORRECT = "Email or password are incorrect";

    @FXML
    private TextField userEmailTextField;

    @FXML
    private PasswordField userPasswordField;

    @FXML
    private Label userEmailLabel;

    @FXML
    private Label userPasswordLabel;

    @FXML
    private Button registerButton;

    @FXML
    private Button forgotPasswordButton;

    @FXML
    private Label loginStatusLabel;

    private ScreensController myController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    /*
     Imeplements the interface method from ControlledScreen
     */
    @Override
    public void setScreenParent(ScreensController screenParent)
    {

        myController = screenParent;

    }

    /**
     * method that checks if user login was valid.
     *
     * @param event the login event.
     */
    @FXML
    /*
     * method to check login status.
     */
    public void checkLoginStatus(ActionEvent event)
    {
        loginStatusLabel.setText("");
        log.log(Level.INFO, "Login button pressed");
        if (isLoginValid())
        {
            log.log(Level.INFO, "login is valid moving to next scene");
            loadNextScene(userEmailTextField.getText().toLowerCase(), userPasswordField.getText());
            /*
            ((Node) (event.getSource())).getScene().getWindow().sizeToScene();
            //((Node)(event.getSource())).getScene().getWindow().setWidth(1100);
            //((Node)(event.getSource())).getScene().getWindow().setHeight(446);
            myController.setScreen(MainScreenLoader.collectionView1ID);
            */
        } else
        {
            log.log(Level.INFO, "login is not valid");
        }
    }

    /*
     * method to check if login credentials are valid.
     * returns true if the are.
     */
    private boolean isLoginValid()
    {
        boolean loginValid = true;
        //verify user with this email exists
        if (!(DBUsersConnect.findUserByEmail(userEmailTextField.getText().toLowerCase())))
        {
            log.log(Level.INFO, "User doesn't exist");
            loginStatusLabel.setText(EMAIL_OR_PASSWORD_INCORRECT);
            loginValid = false;
        } //verify password with this email is valid
        else if (!(isPasswordValid()))
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
    private boolean isPasswordValid()
    {
        boolean passwordValid = true;
        //verify password not empty
        if ((userPasswordField.getText() == null) || (userPasswordField.getText().trim().isEmpty()))
        {
            log.log(Level.INFO, "User password Empty or null");
            passwordValid = false;
            loginStatusLabel.setText(PASSWORD_EMPTY);
        } //Check password is NOT valid with this email and update boolean var
        else if (!(DBUsersConnect.isUserPasswordValid(userEmailTextField.getText().toLowerCase(), userPasswordField.getText().toCharArray())))
        {
            log.log(Level.INFO, "User password Invalid!");
            passwordValid = false;
            loginStatusLabel.setText(EMAIL_OR_PASSWORD_INCORRECT);
        }
        return passwordValid;
    }

    /*
    * method to load next scene after user login was validated.
    */
    private boolean loadNextScene(String userEmail, String userAttemptedPassword)
    {
        boolean loadScreen = false;
        try
        {
            Stage currentStage = (Stage) userEmailLabel.getScene().getWindow();
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

    /*
     * method to move to password retrieve screen
     */
    @FXML
    private void goToRetrievePassword()
    {
        myController.setScreen(MainScreenLoader.passwordRetrieve1ID);
    }

    /*
     * method to move to user
     */
    @FXML
    private void goToRegisterUser()
    {
        myController.setScreen(MainScreenLoader.registerScreen1ID);
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
