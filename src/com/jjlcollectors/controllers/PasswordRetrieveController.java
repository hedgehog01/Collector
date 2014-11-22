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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hedgehog01
 */
public class PasswordRetrieveController implements Initializable
{
    private final String LOGIN_TITLE = "Collector - Login";
    private final String LOGIN_SCENE_FXML = "/com/jjlcollectors/fxml/login/Login.fxml"; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
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
            Stage currentStage =((Stage) ((Node)event.getSource()).getScene().getWindow());
            currentStage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = HomePageController.class.getResource(LOGIN_SCENE_FXML);
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load(location.openStream());
            //HomePageController cvController = (HomePageController) fxmlLoader.getController();
            

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
    
    /**
     * method to exit the program.
     */
    @FXML
    public void doExit()
    {
        Platform.exit();
    }
    
}
