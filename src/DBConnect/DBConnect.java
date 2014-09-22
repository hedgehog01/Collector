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
public class DBConnect {
    //Class variables

    //private static final String dbEmbeddedURL = "jdbc:derby:Collection";
    private static final String dbClientURL = "jdbc:derby://localhost:1527/CollectionDB";//create=true;user=Hedgehog01;password=Jade170213";
    private static final String createDb = ";create=true";
    private static final String shutdownDb = ";shutdown=true";
    private static final String tableName = "COINS";
    //private static final String driverEmbedded = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String driverClient = "org.apache.derby.jdbc.ClientDriver";
    private static final String user = "Hedgehog01";
    private static final String pass = "Jade170213";
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet resultSet = null;

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

        try {
            closeDBConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remove;
    }
    
    

    /*
     * Method that start connection to the Collector Database
     */
    private static void createDBConnection() {
        try {
            Class.forName(driverClient).newInstance();
            System.out.println("connecting to DB...");

            conn = DriverManager.getConnection(dbClientURL + createDb, user, pass);

            System.out.println("Schema conntected: " + conn.getSchema());

        } catch (ClassNotFoundException e) {

            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
            System.out.println("\n Make sure your CLASSPATH variable "
                    + "contains %DERBY_HOME%\\lib\\derby.jar (${DERBY_HOME}/lib/derby.jar). \n");
        } catch (Exception e) {
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
            stmt.execute("INSERT INTO COINS (COIN_UUID,COIN_NAME,COIN_GRADE,COIN_FACEVALUE,COIN_CURRENCY,COIN_NOTE) VALUES" + " ('" + uuid + "','" + name + "','" + grade + "','" + faceValue + "','" + currency + "','" + note.toString() + "')");
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
                DriverManager.getConnection(dbClientURL + ";user=" + user + ";password=" + pass + shutdownDb);
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
            PreparedStatement statement = conn.prepareStatement("SELECT * from COINS");
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
            //PreparedStatement statement = conn.prepareStatement("DELETE * from COINS WHERE id = "+ id);
            PreparedStatement statement = conn.prepareStatement("DELETE * from COINS WHERE id = "+ id);
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
