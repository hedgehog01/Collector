/*
 * Copyright (C) 2014 nathanr
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

import com.jjlcollectors.collectables.CollectionType;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nathanr
 */
public class AddCollectionController implements Initializable
{

    private UUID _userUUID = null;
    private static final Logger log = Logger.getLogger(AddCollectionController.class.getName());
    private ObservableList<CollectionType> collectionTypeComboList = FXCollections.observableArrayList();

    @FXML
    private ComboBox<CollectionType> collectionTypeComboBox;

    @FXML
    private TextField collectionName;

    @FXML
    private TextField collectionInfo;

    @FXML
    private Label addCollectionStatusLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        collectionTypeComboList.addAll(CollectionType.values());
        collectionTypeComboBox.getItems().addAll(collectionTypeComboList);
    }

    protected void setUserData(UUID userUUID)
    {
        _userUUID = userUUID;
    }

    @FXML
    private void createNewCollection()
    {
        log.log(Level.INFO, "Attempting save of new Collection");
        
    }

    @FXML
    private void closeWindow()
    {
        Stage currentStage = (Stage) collectionTypeComboBox.getScene().getWindow();
        currentStage.hide();
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
