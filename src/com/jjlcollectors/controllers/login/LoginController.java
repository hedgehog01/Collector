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
package com.jjlcollectors.controllers.login;

import com.jjlcollectors.collectables.windows.LoginWindow;
import com.jjlcollectors.util.dbconnect.DBUsersConnect;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Hedgehog01
 */
public class LoginController implements Initializable
{
    @FXML
    private TextField userEmailTextField;
    
    @FXML 
    private PasswordField userPasswordField;
    
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
    
    @FXML
    public void checkLoginStatus (ActionEvent event)
    {
        System.out.println ("Login button pressed");
        System.out.println (userEmailTextField.getText());
        System.out.println (userPasswordField.getText());
        
        System.out.println ("Checking credentials: " + DBUsersConnect.checkUserCredentials(userEmailTextField.getText(), userPasswordField.getText()));
    }
    
}
