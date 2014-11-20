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

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Hedgehog01
 */
public class MainScreenLoader extends Application
{
    public static final String TITLE = "Collector";
    public static String loginScreen1ID = "login1";
    public static String loginScreen1FXML = "/com/jjlcollectors/fxml/login/Login.fxml";  
    public static String loginScreen1CSS = "/com/jjlcollectors/fxml/login/login.css";
    public static String registerScreen1ID = "register1";
    public static String registerScreen1FXML = "/com/jjlcollectors/fxml/register/RegisterScreen.fxml";    
    public static String passwordRetrieve1ID = "passwordRetrieve1";
    public static String passwordRetrieve1FXML = "/com/jjlcollectors/fxml/PasswordRetrieve/PasswordRetrieve.fxml";
    public static String collectionView1ID = "collectionview1";
    public static String collectionViewScreen1FXML = "/com/jjlcollectors/fxml/collectionview/CollectionView.fxml";    

    

    @Override
    public void start(Stage primaryStage)
    {

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(MainScreenLoader.loginScreen1ID, MainScreenLoader.loginScreen1FXML);
        mainContainer.loadScreen(MainScreenLoader.registerScreen1ID, MainScreenLoader.registerScreen1FXML);
        mainContainer.loadScreen(MainScreenLoader.passwordRetrieve1ID, MainScreenLoader.passwordRetrieve1FXML);
        mainContainer.loadScreen(MainScreenLoader.collectionView1ID, MainScreenLoader.collectionViewScreen1FXML);
        mainContainer.setScreen(MainScreenLoader.loginScreen1ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(TITLE);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
