/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jjlcollectors.util.dbconnect;


import com.jjlcollectors.collectables.coins.Coin;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 *
 * @author nathanr
 */
public class DBCoinConnect extends DBConnect{
    //Class variables



    private static final String TABLE_NAME = "COINS";


    /**
     * method to add coin to coin DB
     * @param coin the coin to be added
     */
    public static void addCoin(Coin coin) {
        
        
        //insertCoin(uuid, name, grade, faceValue, currency, note);
        addCoin (coin.getItemId().toString(),coin.getItemName(),coin.getCoinGrade().name(),coin.getCoinFaceValue(),coin.getCoinCurrency().name(),coin.getItemNote(),coin.getItemBuyDate(),coin.getBuyPrice(),coin.getItemValue(),coin.getCoinMintMark(),coin.getCoinYear());
        //createDBConnection();
        System.out.println("Select * from coins...");
        selectAllCoins();


    }
     /**
     * method to remove coin from coin DB
     * @param id the id of the coin to be removed
     * @return int representing if coin removed or not. 0 = removed, 1 = coin not found, 2 = not removed due to error.
     */
    public static int removeCoinById (int id)
    {

        int remove = deleteCoinById(id);
        return remove;
    }
    
    public static int removeLastCoin()
    {
         int id = -1;
        DBConnect.createDBConnection();
        try{
        Statement st = conn.createStatement();
 
        ResultSet rs = st.executeQuery("SELECT MAX(ID) from " + TABLE_NAME);
       
        while (rs.next()) {
            id = rs.getInt(1);
            System.out.println(id);
 
        }
        }
        catch  (SQLException sql){
            System.err.println(sql);
        }
        DBConnect.closeDBConnection();
        
        return removeCoinById(id);
    }
        
        
            
        

    /*
     * method to insert a coin into the coin db
     */
    private static void addCoin(String uuid, String name, String grade, String faceValue, String currency, StringBuilder note, LocalDate date, String coinBuyPrice, String coinValue, String coinMintMark,int coinYear) 
    {
        DBConnect.createDBConnection();
        System.out.println("Attemp to add new coin...");
        try {
            stmt = conn.createStatement();
            //stmt.execute("insert into " + tableName + " values (" + "'"uuid'" + ",'" + name + "','" + faceValue +"')");
            //stmt.execute("INSERT INTO COINS (COIN_UUID,COIN_NAME,COIN_GRADE,COIN_FACEVALUE,COIN_CURRENCY,COIN_NOTE) VALUES" + " ('"+uuid+"','"+name+"','"+grade+"','"+faceValue+"','"+currency+"','"+note.toString()+"')");
            //stmt.execute("INSERT INTO " + TABLE_NAME +" (COIN_UUID,COIN_NAME,COIN_GRADE,COIN_FACEVALUE,COIN_CURRENCY,COIN_NOTE) VALUES" + " ('" + uuid + "','" + name + "','" + grade + "','" + faceValue + "','" + currency + "','" + note.toString() + "')");
            stmt.execute("INSERT INTO " + TABLE_NAME +" (COIN_UUID,COIN_NAME,COIN_GRADE,COIN_FACEVALUE,COIN_CURRENCY,COIN_NOTE,COIN_BUY_DATE,COIN_BUY_PRICE,COIN_VALUE,COIN_MINT_MARK,COIN_YEAR) VALUES" 
                    + " ('" + uuid + "','" + name + "','" + grade + "','" + faceValue + "','" + currency + "','" + note.toString() + "','" + date + "','" + coinBuyPrice + "','" + coinValue + "','" + coinMintMark + "'," + coinYear + ")");
            DBConnect.closeDBConnection();
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }

    }

    /*
     *private method that shuts down the Database
     */
    private static void shutdown() {
        try {
            if (stmt != null) {
                System.out.println("Closing staatemnet...");
                stmt.close();
            }
            if (conn != null) {
                System.out.println("Shutting down connection staatemnet...");
                DriverManager.getConnection(DB_Client_URL + ";user=" + DB_USER_NAME + ";password=" + DB_PASS + SHUTDOWN_DB);
                System.out.println("Closing connection statemnet...");
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }

    }

    private static void selectAllCoins() {
        try {
            DBConnect.createDBConnection();
            stmt = conn.createStatement();
            System.out.println("prepare statement starting");
            PreparedStatement statement = conn.prepareStatement("SELECT * from " + TABLE_NAME);
            System.out.println("prepare statement done");
            try //(ResultSet results = stmt.executeQuery("select * from " + tableName)) 
                    (ResultSet results = statement.executeQuery()) {
                ResultSetMetaData rsmd = results.getMetaData();
                int numberCols = rsmd.getColumnCount();
                for (int i = 1; i <= numberCols; i++) {
                    //print Column Names
                    System.out.print(rsmd.getColumnLabel(i) + "\t");
                }

                System.out.println("\n-------------------------------------------------");

                while (results.next()) {
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
                    
                    System.out.println(id + "\t" + uuid + "\t" + name + "\t" + grade + "\t" + facevalue + "\t" + currency + "\t" + note  +"\t" + coinBuyDate + "\t" + coinBuyPrice + "\t" + coinValue + "\t" + coinMintMark + "\t" + coinYear );
                }
            }

            stmt.close();

        } catch (SQLException e) {
            System.err.println (e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        DBConnect.closeDBConnection();
    }

    private static int deleteCoinById(int id) {
        int count = -1;
        try {
            DBConnect.createDBConnection();
            System.out.println("prepare delete statement starting...");
            Statement stmt = conn.createStatement();
            //PreparedStatement statement = conn.prepareStatement("DELETE * from " + TABLE_NAME +" WHERE id = "+ id);
            String sql = ("DELETE from " + TABLE_NAME +" WHERE id = "+ id);
            System.out.println("prepare statement done...");
            count = stmt.executeUpdate(sql);
            System.out.println("Number of deleted rows: " + count);
            DBConnect.closeDBConnection();
        } catch (SQLException e) {
            System.out.println (e.getMessage());
        }
        
        return count;
    }
}
