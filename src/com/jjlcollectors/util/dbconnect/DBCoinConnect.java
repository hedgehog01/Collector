/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jjlcollectors.util.dbconnect;

import com.jjlcollectors.collectables.CollectionProperty;
import com.jjlcollectors.collectables.coins.Coin;
import com.jjlcollectors.collectables.coins.CoinProperty;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *
 * @author nathanr
 */
public final class DBCoinConnect extends DBConnect
{

    //Class variables
    private static final String TABLE_NAME = "COINS_DB";
    private static final Logger log = Logger.getLogger(DBCoinConnect.class.getName());
    private static Date sqlDate;
    private static TableView tableView;

    /**
     * method to add coin to coin DB
     *
     * @param coin the coin to be added
     */
    public static boolean addCoin(Coin coin)
    {

        //insertCoin(uuid, name, grade, faceValue, currency, note);
        log.log(Level.INFO, "Call to addCoin method");
        boolean coinAdded = addCoin(coin.getItemUUID().toString(), coin.getItemName(), coin.getCoinGrade().name(), coin.getCoinFaceValue(), coin.getCoinCurrency().name(), coin.getItemNote(), coin.getItemBuyDate(), coin.getBuyPrice(), coin.getItemValue(), coin.getCoinMintMark(), coin.getCoinYear(), coin.getUserUUID(), coin.getItemCollectionUUID());
        //createDBConnection();
        log.log(Level.INFO, "Select * from coins...");
        selectAllCoins();
        shutDownDBConnection();
        return coinAdded;
    }

    /**
     * method to remove coin from coin DB
     *
     * @param id the id of the coin to be removed
     * @return int representing if coin removed or not. 0 = removed, 1 = coin
     * not found, 2 = not removed due to error.
     */
    public static int removeCoinByCoinUUID(UUID coinUUID)
    {

        int remove = deleteCoinByUUID(coinUUID);
        return remove;
    }

    protected static int removeLastCoin()
    {
        String coinUUID ="";
        DBConnect.createDBConnection();
        try
        {
            String sql = "SELECT MAX(ID) from " + TABLE_NAME;
            PreparedStatement prepStmt = conn.prepareStatement(sql);

            ResultSet rs = prepStmt.executeQuery();

            while (rs.next())
            {
                coinUUID = rs.getString(1);
                System.out.println(coinUUID);

            }
        } catch (SQLException sql)
        {
            System.err.println(sql);
        }
        DBConnect.closeDBConnection();
        shutDownDBConnection();

        return removeCoinByCoinUUID(UUID.fromString(coinUUID));
    }

    /*
     * method to insert a coin into the coin db
     */
    private static boolean addCoin(String uuid, String name, String grade, String faceValue, String currency, StringBuilder note, LocalDate date, String coinBuyPrice, String coinValue, String coinMintMark, int coinYear, UUID userUUID, UUID collectionUUID)
    {
        boolean userAdded = false;
        DBConnect.createDBConnection();
        int updateCount = 0; //var that count num of updated rows
        log.log(Level.INFO, "Attemp to add new coin...");
        try
        {
            long d = date.toEpochDay();
            sqlDate = new Date(d);
            String sql = ("INSERT INTO " + TABLE_NAME + " (COIN_UUID,COIN_NAME,COIN_GRADE,COIN_FACEVALUE,COIN_CURRENCY,COIN_NOTE,COIN_BUYDATE,COIN_BUYPRICE,COIN_VALUE,COIN_MINT_MARK,COIN_MINT_YEAR,COIN_USER_UUID,COIN_COLLECTION_UUID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            PreparedStatement prepStmt = conn.prepareStatement(sql);

            prepStmt.setString(1, uuid);
            prepStmt.setString(2, name);
            prepStmt.setString(3, grade);
            prepStmt.setString(4, faceValue);
            prepStmt.setString(5, currency);
            prepStmt.setString(6, note.toString());
            prepStmt.setDate(7, sqlDate);
            prepStmt.setString(8, coinBuyPrice);
            prepStmt.setString(9, coinValue);
            prepStmt.setString(10, coinMintMark);
            prepStmt.setInt(11, coinYear);
            prepStmt.setString(12, userUUID.toString());
            prepStmt.setString(13, collectionUUID.toString());

            updateCount = prepStmt.executeUpdate();

            log.log(Level.INFO, "Add coin update count is: {0}", updateCount);
            if (updateCount == 1)
            {
                log.log(Level.INFO, "User added and return value set to true");
                userAdded = true;
            } else if (updateCount == 0)
            {
                log.log(Level.SEVERE, "User NOT added and return value remains false");
            }

            DBConnect.closeDBConnection();
            shutDownDBConnection();
        } catch (SQLException e)
        {
            log.log(Level.SEVERE, "Exception while writing coin to DB. sql exception: {0}", e);
        }
        return userAdded;
    }

    /**
     * method to populate and return tableview
     *
     * @return table view with populated table view from DB
     */
    public static TableView buildData()
    {
        ObservableList<ObservableList> userObservableListData;

        userObservableListData = FXCollections.observableArrayList();
        try
        {

            DBConnect.createDBConnection();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT * from USERDB";
            //ResultSet
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            log.log(Level.INFO, "setting result set to query: {0}", SQL);
            log.log(Level.INFO, "number of colomns in the table: {0}", rs.getMetaData().getColumnCount());

            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY * ********************************
             */
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++)
            {
                log.log(Level.INFO, "in the for loop of coolumn get data. col num: {0}", i);
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>()
                {

                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param)
                    {
                        log.log(Level.INFO, "Creating cell...");
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableView.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }

            /**
             * ******************************
             * Data added to ObservableList * ******************************
             */
            while (rs.next())
            {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                userObservableListData.add(row);

            }

            //FINALLY ADDED TO TableView
            tableView.setItems(userObservableListData);
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return tableView;
    }

    private static void selectAllCoins()
    {
        try
        {
            DBConnect.createDBConnection();

            System.out.println("prepare statement starting");
            PreparedStatement statement = conn.prepareStatement("SELECT * from " + TABLE_NAME);
            System.out.println("prepare statement done");
            try (ResultSet results = statement.executeQuery())
            {
                ResultSetMetaData rsmd = results.getMetaData();
                int numberCols = rsmd.getColumnCount();
                for (int i = 1; i <= numberCols; i++)
                {
                    //print Column Names
                    System.out.print(rsmd.getColumnLabel(i) + "\t");
                }
                int updateCount = 0; //count how many rows added

                System.out.println("\n-------------------------------------------------");

                while (results.next())
                {
                    int id = results.getInt(1);
                    String uuid = results.getString(2);
                    String name = results.getString(3);
                    String grade = results.getString(4);
                    String facevalue = results.getString(5);
                    String currency = results.getString(6);
                    String note = results.getString(7);
                    Date coinBuyDate = results.getDate(8);
                    String coinBuyPrice = results.getString(9);
                    String coinValue = results.getString(10);
                    String coinMintMark = results.getString(11);
                    int coinYear = results.getInt(12);

                    log.log(Level.INFO, "Add user update count is: {0}", updateCount);

                    System.out.println(id + "\t" + uuid + "\t" + name + "\t" + grade + "\t" + facevalue + "\t" + currency + "\t" + note + "\t" + coinBuyDate + "\t" + coinBuyPrice + "\t" + coinValue + "\t" + coinMintMark + "\t" + coinYear);
                }
            }

        } catch (SQLException e)
        {
            System.err.println(e.getMessage());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        DBConnect.closeDBConnection();
        shutDownDBConnection();
    }

    public static ObservableList<CoinProperty> getAllUserCoins(UUID userUUID)
    {
        ObservableList<CoinProperty> coinList = FXCollections.observableArrayList();
        if (userUUID != null)
        {

            try
            {
                DBConnect.createDBConnection();

                System.out.println("prepare statement starting");
                PreparedStatement prepStmt = conn.prepareStatement("SELECT * from " + TABLE_NAME + " where COIN_USER_UUID = ?");
                prepStmt.setString(1, userUUID.toString());
                System.out.println("prepare statement done");
                try (ResultSet results = prepStmt.executeQuery())
                {
                    ResultSetMetaData rsmd = results.getMetaData();
                    int numberCols = rsmd.getColumnCount();
                    log.log(Level.INFO, "Number of colomns: {0}", numberCols);
                    for (int i = 1; i <= numberCols; i++)
                    {
                        //print Column Names
                        System.out.print(rsmd.getColumnLabel(i) + "\t");
                    }

                    System.out.println("\n-------------------------------------------------");
                    if (!(results.next()))
                    {
                        log.log(Level.INFO, "No items in the DB?");
                    }
                    //get all user collections
                    log.log(Level.INFO, "Get list of user collections");
                    ObservableList<CollectionProperty> userCollectionData;
                    userCollectionData = DBCollectionConnect.getUserCollections(userUUID);
                    if (userCollectionData == null || userCollectionData.isEmpty())
                    {
                        log.log(Level.SEVERE, "list of user collections is null or empty");
                    } else
                    {

                        while (results.next())
                        {

                            int id = results.getInt(1);
                            String coinUUID = results.getString(2);
                            String name = results.getString(3);
                            String grade = results.getString(4);
                            String facevalue = results.getString(5);
                            String currency = results.getString(6);
                            String note = results.getString(7);
                            Date coinBuyDate = results.getDate(8);
                            String coinBuyPrice = results.getString(9);
                            String coinValue = results.getString(10);
                            String coinMintMark = results.getString(11);
                            int coinYear = results.getInt(12);
                            String collectionUUID = results.getString("COIN_COLLECTION_UUID");
                        //get collection name
                            String collectionName = getCollectionName (collectionUUID,userCollectionData);
                            //(String coinValue,String coinMintMark,int coinYear,String coinCollectionName)
                            CoinProperty coin = new CoinProperty(coinUUID, name, grade, facevalue, currency, note, coinBuyDate.toLocalDate().toString(), coinBuyPrice, coinValue, coinMintMark, coinYear, collectionName);
                            coinList.add(coin);
                            System.out.println(id + "\t" + coinUUID + "\t" + name + "\t" + grade + "\t" + facevalue + "\t" + currency + "\t" + note + "\t" + coinBuyDate + "\t" + coinBuyPrice + "\t" + coinValue + "\t" + coinMintMark + "\t" + coinYear + "\t" + collectionName);
                        }
                    }
                }

            } catch (SQLException e)
            {
                System.err.println(e.getMessage());
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            DBConnect.closeDBConnection();
            shutDownDBConnection();
        }
        return coinList;
    }

    private static int deleteCoinByUUID(UUID coinUUID)
    {
        int count = -1;
        try
        {
            DBConnect.createDBConnection();
            System.out.println("prepare delete statement starting...");
            PreparedStatement prepStmt = conn.prepareStatement("DELETE from " + TABLE_NAME + " WHERE COIN_UUID = ?");
            prepStmt.setString(1, coinUUID.toString());
            log.log(Level.INFO, "prepare statement done...");
            count = prepStmt.executeUpdate();
            log.log(Level.INFO, "Number of deleted rows: {0}", count);
            DBConnect.closeDBConnection();
            shutDownDBConnection();
        } catch (SQLException e)
        {
            log.log(Level.SEVERE, "Failed to delete coin. SQL error: {0}", e);
        }

        return count;
    }

    
    private static String getCollectionName(String collectionUUID, ObservableList<CollectionProperty> userCollectionData)
    {
        String collectionName = "";
        for (CollectionProperty userCollectionData1 : userCollectionData)
        {
            if(userCollectionData1.collectionUUIDProperty().get().equals(collectionUUID))
            {
                collectionName = userCollectionData1.getCollectionName();
            }
        }
        
        
        return collectionName;
    }
}
