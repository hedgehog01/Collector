/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author nathanr
 */
public class DBCoinConnect {
    //Class variables


    protected static final String DB_Client_URL = "jdbc:derby://localhost:1527/CollectionDB";//create=true;DB_USER_NAME=Hedgehog01;password=Jade170213";
    protected static final String CREATE_DB = ";create=true";
    protected static final String SHUTDOWN_DB = ";shutdown=true";
    //private static final String driverEmbedded = "org.apache.derby.jdbc.EmbeddedDriver";
    protected static final String DRIVER_CLIENT = "org.apache.derby.jdbc.ClientDriver";
    protected static final String DB_USER_NAME = "Hedgehog01";
    protected static final String DB_PASS = "Jade170213";
    private static final String TABLE_NAME = "COINS";
    protected static Connection conn = null;
    protected static Statement stmt = null;
    protected static ResultSet resultSet = null;

    /**
     * method to add coin to coin DB
     * @param uuid
     * @param name
     * @param grade
     * @param faceValue
     * @param currency
     * @param note 
     */
    public static void addCoin(String uuid, String name, String grade, String faceValue, String currency, StringBuilder note) {
        
        
        insertCoin(uuid, name, grade, faceValue, currency, note);
        //createDBConnection();
        System.out.println("Select * from coins...");

        selectCoins();


    }
    
    /**
     * method to remove coin from coin DB
     * @param id the id of the coin to be removed
     * @return int representing if coin removed or not. 0 = removed, 1 = coin not found, 2 = not removed due to error.
     */
    public static int removeCoin (int id)
    {
        createDBConnection();
        int remove = deleteCoin(id);
        selectCoins();
        closeDBConnection();
        return remove;
    }
    
    

    /*
     * Method that start connection to the Collector Database
     */
    private static void createDBConnection() {
        try {
            Class.forName(DRIVER_CLIENT).newInstance();
            System.out.println("connecting to DB...");

            conn = DriverManager.getConnection(DB_Client_URL + CREATE_DB, DB_USER_NAME, DB_PASS);

            System.out.println("Schema conntected: " + conn.getSchema());

        } catch (ClassNotFoundException e) {

            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
            System.out.println("\n Make sure your CLASSPATH variable "
                    + "contains %DERBY_HOME%\\lib\\derby.jar (${DERBY_HOME}/lib/derby.jar). \n");
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            System.out.println(e);
        }
    }

    /*
     * method to close database connection
     */
    private static void closeDBConnection() {
        try {
            System.out.println("Closing DB connection...");
            conn.close();
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }

    }

    /*
     * method to insert a coin into the coin db
     */
    private static void insertCoin(String uuid, String name, String grade, String faceValue, String currency, StringBuilder note) 
    {
        createDBConnection();
        System.out.println("Attemp to add new coin...");
        try {
            stmt = conn.createStatement();
            //stmt.execute("insert into " + tableName + " values (" + "'"uuid'" + ",'" + name + "','" + faceValue +"')");
            //stmt.execute("INSERT INTO COINS (COIN_UUID,COIN_NAME,COIN_GRADE,COIN_FACEVALUE,COIN_CURRENCY,COIN_NOTE) VALUES" + " ('"+uuid+"','"+name+"','"+grade+"','"+faceValue+"','"+currency+"','"+note.toString()+"')");
            stmt.execute("INSERT INTO " + TABLE_NAME +" (COIN_UUID,COIN_NAME,COIN_GRADE,COIN_FACEVALUE,COIN_CURRENCY,COIN_NOTE) VALUES" + " ('" + uuid + "','" + name + "','" + grade + "','" + faceValue + "','" + currency + "','" + note.toString() + "')");
            closeDBConnection();
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

    private static void selectCoins() {
        try {
            createDBConnection();
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
                    System.out.println(id + "\t" + uuid + "\t" + name + "\t" + grade + "\t" + facevalue + "\t" + currency);
                }
            }

            stmt.close();

        } catch (SQLException e) {
            System.err.println (e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDBConnection();
    }

    private static int deleteCoin(int id) {
        int count = -1;
        try {
            stmt = conn.createStatement();
            System.out.println("prepare delete statement starting...");
            PreparedStatement statement = conn.prepareStatement("DELETE * from " + TABLE_NAME +" WHERE id = "+ id);
            System.out.println("prepare statement done...");
            count = statement.executeUpdate();
            System.out.println("Number of deleted rows: " + count);
            stmt.close();
        } catch (SQLException e) {
            System.err.println (e.getMessage());
        }
        
        return count;
    }
}
