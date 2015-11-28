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
import com.jjlcollectors.collectables.coins.CoinCreator;
import com.jjlcollectors.collectables.coins.CoinProperty;
import com.jjlcollectors.util.dbconnect.DBCollectionConnect;
import com.jjlcollectors.util.dbconnect.DBConnect;
import com.jjlcollectors.util.dbconnect.DBUsersConnect;
import com.jjlcollectors.util.log.MyLogger;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Hedgehog01
 */
public class HomePageController implements Initializable
{

    private String userEmail = "";
    private UUID userUUID = null;
    private UUID collectionUUID = null;
    private final boolean ALWAYS_ON_TOP = true;
    private final boolean SET_RESIZABLE = false;
    private final String LOGIN_FXML_PATH = "/com/jjlcollectors/fxml/login/Login.fxml";
    private final String COLLECTIONVIEW_FXML_PATH = "/com/jjlcollectors/fxml/collectionview/CollectionView.fxml";
    private static final Logger LOG = Logger.getLogger(HomePageController.class.getName());
    private final String LOG_CLASS_NAME = "HomePageController: ";
    
    //MSG Strings
    private final String ADD_COIN_STAGE_TITLE = "Collector - Add new Coin";
    private final String VIEW_COIN_STAGE_TITLE = "Collector - View/Edit Coin";
    private final String LOGIN_STAGE_TITLE = "Collector - Login";
    private final String INFO_MESSAGE_NO_COIN_SELECTED_TITLE = "No Coin Selected";
    private final String INFO_MESSAGE_NO_COIN_SELECTED_BODY = "No Coin Selected.\nPlease select a coin and try again.";
    
    //Tooltip texts
    private final String TOOLTIP_NEW_COIN = "Add a new coin";
    private final String TOOLTIP_NEW_COLLECTION = "Add new collection";
    private final String TOOLTIP_VIEW_COLLECTION = "View a collection";
    private final String TOOLTIP_SELECT_COLLECTION = "Select a collection";
    
    private boolean isLoginValid = true;
    private Task loadCoinsWorker;

    @FXML
    private ComboBox<CollectionProperty> collectionComboBox;

    @FXML
    private Button previewCollectionBtn;

    @FXML
    private Button openCollectionBtn;

    @FXML
    private Button editCoinBtn;

    @FXML
    private Button createNewCollectionBtn;

    @FXML
    private Button getUserCollectionBtn;

    @FXML
    private TableView<CoinProperty> coinPreviewTableView;

    @FXML
    private Button addNewCoinbtn;

    @FXML
    private TextField filteredCoinTableTextFiled;
    
    //tooltips
    @FXML
    private Tooltip newCoinTT;
    
    @FXML
    private Tooltip newCollectionTT;
    
    @FXML
    private Tooltip viewCollectionTT;
    
    @FXML
    private Tooltip selectCollectionTT;
    
    private ObservableList<CollectionProperty> collectionComboListData = FXCollections.observableArrayList();
    private ObservableList<CoinProperty> coinTableData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
        //setup Tooltips
        newCoinTT.setText(TOOLTIP_NEW_COIN);
        newCollectionTT.setText(TOOLTIP_NEW_COLLECTION);
        viewCollectionTT.setText(TOOLTIP_VIEW_COLLECTION);
        selectCollectionTT.setText(TOOLTIP_SELECT_COLLECTION);
        
        
        
        
        
        //setup collection combobox
        collectionComboBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends CollectionProperty> observable, CollectionProperty oldValue, CollectionProperty newValue) ->
        {
            if (!(newValue.collectionUUIDProperty().get() == null))
            {
                collectionUUID = UUID.fromString(newValue.collectionUUIDProperty().get());
                LOG.log(Level.INFO, "got collection UUID: {0}", collectionUUID.toString());
            } else
            {
                collectionUUID = null;
                MyLogger.log(Level.INFO, "Collection null selected - should load all user coins");
            }

            cancelLoadCoinsThread();
            startLoadCoinsThread();
        }
        );

        // Define rendering of the list of values in ComboBox drop down. 
        collectionComboBox.setCellFactory((comboBox) ->
        {
            return new ListCell<CollectionProperty>()
            {
                @Override
                protected void updateItem(CollectionProperty item, boolean empty)
                {
                    super.updateItem(item, empty);

                    if (item == null || empty)
                    {
                        setText(null);
                    } else
                    {
                        setText(item.getCollectionName());
                    }
                }
            };
        });

        // Define rendering of selected value shown in ComboBox.
        collectionComboBox.setConverter(new StringConverter<CollectionProperty>()
        {
            @Override
            public String toString(CollectionProperty collection)
            {
                if (collection == null)
                {
                    return null;
                } else
                {
                    return collection.getCollectionName();
                }
            }

            @Override
            public CollectionProperty fromString(String personString)
            {
                return null; // No conversion fromString needed.
            }
        });

        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                LOG.log(Level.INFO, "In initialize, in Platform.runLater");
                if (isLoginValid)
                {
                    LOG.log(Level.INFO, "Login valid, loading user data");
                    loadUserData();
                } else if (!(isLoginValid))
                {
                    LOG.log(Level.SEVERE, "Login is NOT valid");
                }
            }
        });

        //XML file list double click listener
        coinPreviewTableView.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2)
                {
                    MyLogger.log(Level.INFO, "Coin {0} double clicked", coinPreviewTableView.getSelectionModel().getSelectedItem().getCoinName());
                    CoinProperty coinProperty = coinPreviewTableView.getSelectionModel().getSelectedItem();
                    MyLogger.log(Level.INFO, "Selected coin Name: {0}", coinProperty.getCoinName());
                    MyLogger.log(Level.INFO, "Selected coin vollection UUID: {0}", coinProperty.getCoinCollectionUUID());
                    loadEditCoinWindow (coinProperty);
                }
            }
        });
    }

    @FXML
    private boolean goToCollectionView()
    {
        boolean loadScreen = false;
        if (userUUID != null && collectionUUID != null)
        {
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
                CollectionViewController cvController = (CollectionViewController) fxmlLoader.getController();
                cvController.setCollectionData(userUUID, collectionUUID);

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
        } else
        {
            LOG.log(Level.WARNING, "could not load collection view, check userUUID or collectionUUID");
        }
        return loadScreen;
    }

    /*
     * method to set user data.
     After validating data is valid, initialize the data according to user data.
     */
    protected final void setUserData(String userEmail, char[] userPass, ActionEvent event)
    {

        checkLoginStatus(userEmail, userPass, event);
    }

    /*
     * method to validate user credentials
     */
    private void checkLoginStatus(String userEmail, char[] userPass, ActionEvent event)
    {

        LOG.log(Level.INFO, "Verifying login details");
        if (isLoginValid(userEmail, userPass))
        {
            LOG.log(Level.INFO, "login is valid set user email");
            setUserEmail(userEmail);
            setUserUUID();

        } else
        {
            LOG.log(Level.INFO, "login is not valid");
            isLoginValid = false;
            //loadLoginScene(event);
        }
    }

    /*
     * method to check if login credentials are valid.
     * returns true if the are.
     */
    private boolean isLoginValid(String userEmail, char[] userPass)
    {
        boolean loginValid = true;
        //verify user with this email exists
        if (!(DBUsersConnect.findUserByEmail(userEmail)))
        {
            LOG.log(Level.INFO, "User doesn't exist");
            loginValid = false;
        } //verify password with this email is valid
        else if (!(isPasswordValid(userEmail, userPass)))
        {
            LOG.log(Level.INFO, "User password incorrect");

            loginValid = false;
        }

        return loginValid;
    }

    /*
     * method to verify if user password is correct
     * returns true if it is.
     */
    private boolean isPasswordValid(String userEmail, char[] userPass)
    {
        boolean passwordValid = true;
        //verify password not empty
        if ((userPass == null) || (userPass.length == 0))
        {
            LOG.log(Level.INFO, "User password Empty or null");
            passwordValid = false;
        } //Check password is NOT valid with this email and update boolean var
        else if (!(DBUsersConnect.isUserPasswordValid(userEmail, userPass)))
        {
            LOG.log(Level.INFO, "User password Invalid!");
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
            LOG.log(Level.SEVERE, "user UUID not found for Email {0}", userEmail);
        }
    }

    private void setCollectionUUID(UUID userUUID)
    {
        if (collectionUUID != null && DBUsersConnect.getUserUUID(userEmail) != null)
        {
            userUUID = DBUsersConnect.getUserUUID(userEmail);
        } else
        {
            LOG.log(Level.SEVERE, "user UUID not found for Email {0}", userEmail);
        }
    }

    private boolean loadCollectionView(String userEmail, String userAttemptedPassword)
    {
        boolean loadScreen = false;
        try
        {
            Stage currentStage = (Stage) collectionComboBox.getScene().getWindow();
            currentStage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            String filePath = COLLECTIONVIEW_FXML_PATH;
            URL location = CollectionViewController.class.getResource(filePath);
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load(location.openStream());
            CollectionViewController cvController = (CollectionViewController) fxmlLoader.getController();
            cvController.setCollectionData(userUUID, collectionUUID);

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
    private void loadUserData()
    {
        if (DBConnect.isDBConnectable())
        {
            LOG.log(Level.INFO, "Adding data to collectionComboListData");
            collectionComboListData.setAll(DBCollectionConnect.getUserCollections(userUUID));
            CollectionProperty allData = new CollectionProperty("All Coins", "All", "All", null);
            collectionComboListData.add(0, allData);
            LOG.log(Level.INFO, "Adding data to collectionComboBox");
            collectionComboBox.getItems().setAll(collectionComboListData);
            startLoadCoinsThread();
        } else //connection not available
        {
            LOG.log(Level.WARNING, "Connection to DB unavailable. Can't get user collection");
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
            stage.setTitle("Collector - Create a new collection");
            stage.show();

            loadScreen = true;
        } catch (IOException ex)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loadScreen;

    }
    
    protected void refreshCoinList()
    {
        MyLogger.log(Level.INFO, LOG_CLASS_NAME + " Refreshing coin list...");
        startLoadCoinsThread();
    }

    /*
     * method to start the Thread that runs the load coins Task
     */
    private void startLoadCoinsThread()
    {
        LOG.log(Level.INFO, "Starting load coins Thread");
        loadCoinsWorker = loadCoinsTask();
        Thread loadCoinThread = new Thread(loadCoinsWorker);
        loadCoinThread.setDaemon(true);
        loadCoinThread.start();

    }

    /*
     * method to cancel the Thread that runs the load coins Task
     */
    private void cancelLoadCoinsThread()
    {
        LOG.log(Level.INFO, "Cancelling load coins Thread");
        loadCoinsWorker.cancel();
    }

    /*
     * task that run the load coin table method
     */
    private Task loadCoinsTask()
    {
        return new Task()
        {

            @Override
            protected Object call() throws Exception
            {
                LOG.log(Level.INFO, "Starting load coins Task");
                while (isCancelled())
                {
                    LOG.log(Level.INFO, "load coins Task canceled");
                    break;
                }
                loadCoinTable();
                return null;
            }

        };
    }
    /*
     * populate the coin table
     * if collection selected only coins from that collection visible
     * Should be called by Task.
     */

    private void loadCoinTable()
    {
        if (userUUID != null)
        {
            if (collectionUUID != null)
            {
                LOG.log(Level.INFO, "Attempting to load coin data into table according to userUUID and collectionUUID");

                if ((CoinCreator.getCoinProperties(userUUID, collectionUUID) != null))
                {
                    coinTableData.clear();
                    coinTableData = CoinCreator.getCoinProperties(userUUID, collectionUUID);

                    //====set up filtering of coin info===
                    // Phone Name filter - Wrap the ObservableList in a FilteredList (initially display all data).
                    FilteredList<CoinProperty> coinFilteredList = new FilteredList<>(coinTableData, p -> true);

                    // coin info filter - Set the filter Predicate whenever the filter changes.
                    filteredCoinTableTextFiled.textProperty().addListener((observable, oldValue, newValue) ->
                    {
                        coinFilteredList.setPredicate(coinInfo ->
                        {
                            // If filter text is empty, display all coins.
                            if (newValue == null || newValue.isEmpty())
                            {
                                return true;
                            }

                            // Compare coin info with filter text.
                            String lowerCaseFilter = newValue.toLowerCase();

                            return coinInfo.getCoinName().toLowerCase().indexOf(lowerCaseFilter) != -1;
                        });
                    });

                    // Wrap the FilteredList in a SortedList. 
                    SortedList<CoinProperty> sortedCoinInfoData = new SortedList<>(coinFilteredList);

                    // Bind the SortedList comparator to the TableView comparator.
                    sortedCoinInfoData.comparatorProperty().bind(coinPreviewTableView.comparatorProperty());

                    //=======End of filtered phone name setup======  
                    coinPreviewTableView.setItems(sortedCoinInfoData);
                } else
                {
                    LOG.log(Level.INFO, "coinTableData is null");
                }

            } else if (collectionUUID == null)
            {
                LOG.log(Level.INFO, "Attempt to load all user coins");
                //ObservableList<CoinProperty> tempData = CoinCreator.getCoinProperties(userUUID);
                coinTableData.clear();
                coinTableData = CoinCreator.getCoinProperties(userUUID);

                if (coinTableData != null)
                {

                    LOG.log(Level.INFO, "tempdata is NOT null");

                    //coinTableData = coinPreviewTableView.getItems();
                    //====set up filtering of coin info===
                    // Phone Name filter - Wrap the ObservableList in a FilteredList (initially display all data).
                    FilteredList<CoinProperty> coinFilteredList = new FilteredList<>(coinTableData, p -> true);

                    // coin info filter - Set the filter Predicate whenever the filter changes.
                    filteredCoinTableTextFiled.textProperty().addListener((observable, oldValue, newValue) ->
                    {
                        coinFilteredList.setPredicate(coinInfo ->
                        {
                            // If filter text is empty, display all coins.
                            if (newValue == null || newValue.isEmpty())
                            {
                                return true;
                            }

                            // Compare coin info with filter text.
                            String lowerCaseFilter = newValue.toLowerCase();

                            return coinInfo.getCoinName().toLowerCase().indexOf(lowerCaseFilter) != -1;
                        });
                    });

                    // Wrap the FilteredList in a SortedList. 
                    SortedList<CoinProperty> sortedCoinInfoData = new SortedList<>(coinFilteredList);

                    // Bind the SortedList comparator to the TableView comparator.
                    sortedCoinInfoData.comparatorProperty().bind(coinPreviewTableView.comparatorProperty());

                    //=======End of filtered phone name setup======                    
                    coinPreviewTableView.setItems(sortedCoinInfoData);

                } else
                {
                    LOG.log(Level.SEVERE, "tempdata is null");
                }
            }

        }

    }

    @FXML
    private boolean addNewCoin()
    {
        boolean loadScreen = false;
        try
        {
            Stage currentStage = (Stage) coinPreviewTableView.getScene().getWindow();
            //currentStage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            String filePath = "/com/jjlcollectors/fxml/addcoin/addcoin.fxml";
            URL location = AddCoinController.class.getResource(filePath);
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load(location.openStream());
            AddCoinController addCoinController = (AddCoinController) fxmlLoader.getController();
            addCoinController.setUserData(userUUID, collectionUUID, coinTableData);

            //Parent parent = FXMLLoader.load(getClass().getResource("/com/jjlcollectors/fxml/collectionview/CollectionView.fxml"));
            Stage addCoinStage = new Stage();
            Scene scene = new Scene(root);
            addCoinStage.setScene(scene);
            addCoinStage.setAlwaysOnTop(ALWAYS_ON_TOP);
            addCoinStage.setResizable(SET_RESIZABLE);
            addCoinStage.setTitle(ADD_COIN_STAGE_TITLE);
            addCoinStage.initOwner(currentStage); //Make sure main stage is the parent of add coin stage so minimizing and maximizing the main will minimize the child
            addCoinStage.show();

            loadScreen = true;
        } catch (IOException ex)
        {
            MyLogger.log(Level.SEVERE, "Add coin screen failed to load: {0}", ex);
        }
        return loadScreen;
    }

    private boolean loadLoginScene(ActionEvent event)
    {
        boolean loadScreen = false;
        try
        {
            Stage currentStage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL location = LoginController.class.getResource(LOGIN_FXML_PATH);
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load(location.openStream());
            LoginController loginController = (LoginController) fxmlLoader.getController();
            currentStage.hide();

            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.sizeToScene();
            currentStage.setResizable(false);
            currentStage.setTitle(LOGIN_STAGE_TITLE);
            currentStage.show();

            loadScreen = true;
        } catch (IOException ex)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loadScreen;
    }

    private void loadEditCoinWindow(CoinProperty coinProperty)
    {
        //if (coinProperty != null)
        //{
            MyLogger.log(Level.INFO, "Attemptting to load View/Edit coin window");
            UUID coinUUID = UUID.fromString(coinProperty.getCoinUUID());
            try
            {
                Stage currentStage = (Stage) coinPreviewTableView.getScene().getWindow();
                //currentStage.hide();
                FXMLLoader fxmlLoader = new FXMLLoader();
                String filePath = "/com/jjlcollectors/fxml/viewcoin/ViewCoin.fxml";
                URL location = AddCoinController.class.getResource(filePath);
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                Parent root = fxmlLoader.load(location.openStream());
                ViewCoinController viewCoinController = (ViewCoinController) fxmlLoader.getController();
                viewCoinController.setCoinData(coinProperty, userUUID);
                //addCoinController.setUserData(userUUID, collectionUUID, coinTableData);

                //Parent parent = FXMLLoader.load(getClass().getResource("/com/jjlcollectors/fxml/collectionview/CollectionView.fxml"));
                Stage viewCoinStage = new Stage();
                Scene scene = new Scene(root);
                viewCoinStage.setScene(scene);
                viewCoinStage.setAlwaysOnTop(ALWAYS_ON_TOP);
                viewCoinStage.setResizable(SET_RESIZABLE);
                viewCoinStage.setTitle(VIEW_COIN_STAGE_TITLE);
                viewCoinStage.initOwner(currentStage); //Make sure main stage is the parent of add coin stage so minimizing and maximizing the main will minimize the child
                viewCoinStage.show();
            } catch (IOException ex) 
        {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        } 

        //} else if (coinProperty == null)
        //{
            //showInfoMessage(INFO_MESSAGE_NO_COIN_SELECTED_TITLE, INFO_MESSAGE_NO_COIN_SELECTED_BODY);
       // }
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
     * method to show error messages
     */
    private void showErrorMessage(String title, String body)
    {
        MyLogger.log(Level.INFO, "Error message initiated. Error Title: {0}", title);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);
        alert.showAndWait();
    }

    /*
     * method to show information messages
     */
    private void showInfoMessage(String title, String body)
    {
        MyLogger.log(Level.INFO, "Info message initiated. Info Title: {0}", title);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(body);
        alert.showAndWait();
    }
}
