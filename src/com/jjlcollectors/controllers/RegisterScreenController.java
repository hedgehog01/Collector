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

import com.jjlcollectors.interfaces.ControlledScreen;
import com.jjlcollectors.users.User;
import com.jjlcollectors.util.dbconnect.DBUsersConnect;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Hedgehog01
 */
public class RegisterScreenController implements Initializable, ControlledScreen
{

    private static final Logger log = Logger.getLogger(RegisterScreenController.class.getName());
    private final String INVALID_FIELDS = "First & last names & email\nare mandatory fields";
    private final String EMAIL_FIELDS_DIFF = "Emails don't match";
    private final String EMAIL_INVALID = "Email appears to be invalid";
    private final String EMAIL_MANDATORY = "Email must be filled";
    private final String PASSWORD_FIELDS_DIFF = "Passwords don't match";
    private final String PASSWORD_INVALID = "Password must be at least\n8 characters long";

    private ScreensController myController;

    //FXML linked variables
    @FXML
    private TextField userFirstNameTextField;

    @FXML
    private TextField userLastNameTextField;

    @FXML
    private TextField userEmailTextField;

    @FXML
    private TextField userEmailConfirmTextField;

    @FXML
    private TextField userAddressTextField;

    @FXML
    private TextField userPhoneNumberTextField;

    @FXML
    private TextField userMobileNumberTextField;

    @FXML
    private TextField userNoteTextField;

    @FXML
    private TextField userPostalCodeTextField;

    @FXML
    private PasswordField userPasswordField;

    @FXML
    private PasswordField userPasswordConfirmField;

    @FXML
    private Button registerButton;

    @FXML
    private Label registerStatusLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label userPasswordLabel;

    @FXML
    private Label userEmailLabel;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    @Override
    public void setScreenParent(ScreensController screenParent)
    {
        myController = screenParent;
    }

    /*
     * method to get back to Login screen
     */
    @FXML
    private void backToLoginScreen()
    {
        myController.setScreen(MainScreenLoader.loginScreen1ID);
    }

    @FXML
    /*
     * method to validate and register new user
     */
    private void registerNewUser() throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        //log.setLevel(Level.SEVERE);
        log.log(Level.INFO, "registerNewUser method started");

        if (isUserFormValid())
        {
            User newUser = new User(userFirstNameTextField.getText(), userLastNameTextField.getText(), userAddressTextField.getText(), userPostalCodeTextField.getText(), userPhoneNumberTextField.getText(), userMobileNumberTextField.getText(), userEmailTextField.getText(), userNoteTextField.getText(), userPasswordField.getText().toCharArray());
            DBUsersConnect.addUser(newUser);
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

    /*
     * checks if all mandatory fields filled correctly.
     */
    private boolean isUserFormValid()
    {
        boolean isValid = true;
        registerStatusLabel.setText("");
        //check if mandatory fields filled
        if (!(isUserNameValid()))
        {
            isValid = false;
        } else if (!(isUserEmailValid()))
        {
            log.log(Level.INFO, "email is not valid");

            isValid = false;
        }
        else if (!(isPasswordValid()))
        {
            log.log(Level.INFO, "Password is not valid");

            isValid = false;
        }

        return isValid;
    }
    
    /*
    method to verify user first & last name are valid (i.e not empty)
    */
    private boolean isUserNameValid()
    {
        boolean isValid = true;
        if (userFirstNameTextField.getText() == null || userFirstNameTextField.getText().trim().isEmpty())
        {
            log.log(Level.INFO, "first name is not valid");
            registerStatusLabel.setText(INVALID_FIELDS);
            isValid = false;
        } else if (userLastNameTextField.getText() == null || userLastNameTextField.getText().trim().isEmpty())
        {
            log.log(Level.INFO, "last name is not valid");
            registerStatusLabel.setText(INVALID_FIELDS);
            isValid = false;
        }
        return isValid;
    }

    /*
     * private method to check if email fields match and are valid emails.
     */
    private boolean isUserEmailValid()
    {
        boolean isValid = true;
        registerStatusLabel.setText("");
        if (userEmailTextField.getText() == null || userEmailTextField.getText().trim().isEmpty())
        {
            isValid = false;
            registerStatusLabel.setText(EMAIL_MANDATORY);
            log.log(Level.INFO, "email empty");
        } else if (!(userEmailTextField.getText().equals(userEmailConfirmTextField.getText())))
        {
            isValid = false;
            log.log(Level.INFO, "emails do not match");
            registerStatusLabel.setText(EMAIL_FIELDS_DIFF);
        } else //check if email is legal format
        {

            Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
            Matcher m = p.matcher(userEmailTextField.getText());
            boolean matchFound = m.matches();

            if (matchFound)
            {
                log.log(Level.INFO, "email {0} appears to be OK", userEmailTextField.getText());
            } else
            {
                isValid = false;
                registerStatusLabel.setText(EMAIL_INVALID);
                log.log(Level.INFO, "email {0} appears invalid", userEmailTextField.getText());
            }
        }
        return isValid;
    }
    
    private boolean isPasswordValid()
    {
        boolean isValid = true;
        registerStatusLabel.setText("");
        if (userPasswordField.getText() == null || (userPasswordField.getText().trim().isEmpty()) || (userPasswordField.getText().length()<8))
        {
            isValid = false;
            registerStatusLabel.setText(PASSWORD_INVALID);
            log.log(Level.INFO, "Password fields is empty");
        }
        
        else if (!(userPasswordField.getText().equals(userPasswordConfirmField.getText())))
        {
            isValid = false;
            registerStatusLabel.setText(PASSWORD_FIELDS_DIFF);
            log.log(Level.INFO, "Password fields not the same");
        }
        
        return isValid;
    }
}
