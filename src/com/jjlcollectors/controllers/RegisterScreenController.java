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

import com.jjlcollectors.collectables.coins.CoinCurrency;
import com.jjlcollectors.users.User;
import com.jjlcollectors.util.dbconnect.DBUsersConnect;
import java.io.IOException;
import java.net.URL;
import com.jjlcollectors.util.service.CountryList;
import com.jjlcollectors.util.service.United_States_Of_America;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hedgehog01
 */
public final class RegisterScreenController implements Initializable
{

    private static final Logger log = Logger.getLogger(RegisterScreenController.class.getName());
    private final String LOGIN_TITLE = "Collector - Login";
    private final String LOGIN_SCENE_FXML = "/com/jjlcollectors/fxml/login/Login.fxml";
    private final String INVALID_FIELDS = "First & last names & email\nare mandatory fields";
    private final String EMAIL_FIELDS_DIFF = "Emails don't match";
    private final String EMAIL_INVALID = "Email appears to be invalid";
    private final String EMAIL_MANDATORY = "Email must be filled";
    private final String PASSWORD_FIELDS_DIFF = "Passwords don't match";
    private final String PASSWORD_INVALID = "Password must be at least\n8 characters long";
    private final String USER_NOT_ADDED = "Could not create a new user.\nContact Program creator.";
    private final String COUNTRY_NOT_SELECTED = "Country must be selected";
    private final String STATE_NOT_SELECTED = "State must be selected";
    private final String USER_ADDED = "New user created successfully";

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
    private TextField userCityTextField;

    @FXML
    private TextField userStreetTextField;

    @FXML
    private ComboBox<United_States_Of_America> userStateComboBox;

    @FXML
    private ComboBox<CountryList> userCountryComboBox;

    @FXML
    private TextField userPhoneNumberTextField;

    @FXML
    private TextField userMobileNumberTextField;

    @FXML
    private TextField userApartmentTextField;

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

        userCountryComboBox.getItems().addAll(CountryList.values());

        userCountryComboBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends CountryList> observable, CountryList oldValue, CountryList newValue) ->
        {
            if (newValue.equals(CountryList.United_States_Of_Americe))
            {
                log.log(Level.INFO, "United states of America selected");
                userStateComboBox.getItems().addAll(United_States_Of_America.values());
            } else
            {
                userStateComboBox.getItems().removeAll(United_States_Of_America.values());
            }
        });
    }


    /*
     * method to get back to Login screen
     */
    @FXML
    private void backToLoginScreen(ActionEvent event)
    {
        loadLoginScene(event);
    }

    private boolean loadLoginScene(ActionEvent event)
    {
        boolean loadScreen = false;
        try
        {
            Stage currentStage = ((Stage) ((Node) event.getSource()).getScene().getWindow());

            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = HomePageController.class.getResource(LOGIN_SCENE_FXML);
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load(location.openStream());
            //HomePageController cvController = (HomePageController) fxmlLoader.getController();
            currentStage.hide();

            //Parent parent = FXMLLoader.load(getClass().getResource("/com/jjlcollectors/fxml/collectionview/CollectionView.fxml"));
            //Stage homeScreenStage = new Stage();
            //Stage homeScreenStage = ((Stage) ((Node)event.getSource()).getScene().getWindow());
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.sizeToScene();
            currentStage.setResizable(false);
            currentStage.setTitle(LOGIN_TITLE);
            currentStage.show();

            loadScreen = true;
        } catch (IOException ex)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loadScreen;
    }

    @FXML
    /*
     * method to validate and register new user
     */
    private void registerNewUser(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        //log.setLevel(Level.SEVERE);
        log.log(Level.INFO, "registerNewUser method started");

        if (isUserFormValid())
        {
            String state = "";
            if(userStateComboBox.getValue() == null)
            {
                log.log(Level.INFO, "State null");
                
            }
            else
            {
                state = userStateComboBox.getValue().toString();
                log.log(Level.INFO, "State is: {0}",userStateComboBox.getValue().toString());
            }
            User newUser = new User(userFirstNameTextField.getText(), userLastNameTextField.getText(), userCountryComboBox.getValue().toString(), state, userCityTextField.getText(), userStreetTextField.getText(), userApartmentTextField.getText(), userPostalCodeTextField.getText(), userPhoneNumberTextField.getText(), userMobileNumberTextField.getText(), userEmailTextField.getText().toLowerCase(), userNoteTextField.getText(), userPasswordField.getText().toCharArray());
            boolean userAdded = DBUsersConnect.addUser(newUser);
            if (userAdded)
            {
                registerStatusLabel.setText(USER_ADDED);
                backToLoginScreen(event);
                log.log(Level.INFO, "registerNewUser OK");
            } else
            {
                registerStatusLabel.setText(USER_NOT_ADDED);
                log.log(Level.SEVERE, "User not added for some reason. user input: {0} {1} {2} {3} {4} {5} {6} {7} {8}", new Object[]
                {
                    userFirstNameTextField.getText(), userLastNameTextField.getText(), userCityTextField.getText(), userPostalCodeTextField.getText(), userPhoneNumberTextField.getText(), userMobileNumberTextField.getText(), userEmailTextField.getText(), userNoteTextField.getText(), userPasswordField.getText()
                });
            }

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
        if ((DBUsersConnect.findUserByEmail(userEmailTextField.getText().toLowerCase())))
        {
            log.log(Level.INFO, "user with same email already exits");
            registerStatusLabel.setText("User with same Email already exits");
            isValid = false;
        } else if (!(isUserNameValid()))
        {
            isValid = false;
        } else if (!(isUserEmailValid()))
        {
            log.log(Level.INFO, "email is not valid");

            isValid = false;
        } else if (!(isPasswordValid()))
        {
            log.log(Level.INFO, "Password is not valid");

            isValid = false;
        } else if (!(isCountrySelected()))
        {
            log.log(Level.INFO, "No Country is selected");

            isValid = false;
        } else if (!(isStateSelected()))
        {
            log.log(Level.INFO, "No State is selected");

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
        if (userPasswordField.getText() == null || (userPasswordField.getText().trim().isEmpty()) || (userPasswordField.getText().length() < 8))
        {
            isValid = false;
            registerStatusLabel.setText(PASSWORD_INVALID);
            log.log(Level.INFO, "Password fields is empty");
        } else if (!(userPasswordField.getText().equals(userPasswordConfirmField.getText())))
        {
            isValid = false;
            registerStatusLabel.setText(PASSWORD_FIELDS_DIFF);
            log.log(Level.INFO, "Password fields not the same");
        }

        return isValid;
    }

    private boolean isCountrySelected()
    {
        boolean isValid = true;
        registerStatusLabel.setText("");
        if (userCountryComboBox.getValue() == null)
        {
            isValid = false;
            registerStatusLabel.setText(COUNTRY_NOT_SELECTED);
            log.log(Level.INFO, "No country selected");
        }
        return isValid;

    }

    /*
     * method to check if state selected. 
     * State can be not selected if country with no states selected.
     * id country with states selected then state must be selected.
     */
    private boolean isStateSelected()
    {
        boolean isValid = true;
        registerStatusLabel.setText("");
        if (userCountryComboBox.getValue().toString().equals(CountryList.United_States_Of_Americe.toString()))
        {
            if (userStateComboBox.getValue() == null)
            {
                isValid = false;
                registerStatusLabel.setText(STATE_NOT_SELECTED);
                log.log(Level.INFO, "USA selected country but no State selected");
            }
        }
        return isValid;
    }
}
