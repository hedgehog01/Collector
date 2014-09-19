/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package collector;

import DBConnect.DBConnect;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author nathanr
 */
public class Collector extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button addCoinButton = new Button();
        
        addCoinButton.setText("Add Coin");
        addCoinButton.setOnAction((ActionEvent event) -> {
            StringBuilder note = new StringBuilder("note test");
            Coin coin = new Coin ("Silver Eagle Dollar",CoinGrade.MS70,"100.0",CoinCurrency.EURO,note);
            
            DBConnect.addCoin(coin.getItemId().toString(), coin.getItemName(), coin.getGrade().name(), coin.getFaceValue(),coin.getCoinCurrency().name(),coin.getItemNote());

        });
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

    Scene scene = new Scene(grid, 300, 275);
    primaryStage.setScene(scene);
        
        Button removeCoinButton = new Button();
        removeCoinButton.setText("Remove Coin");
        removeCoinButton.setOnAction((ActionEvent event)-> {
            System.out.println ("Remove Coin Button pressed...");
            
        });
        
        StackPane root = new StackPane();
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);


        grid.add(addCoinButton, 0, 3);
        grid.add(removeCoinButton, 1, 3);
        
        primaryStage.setTitle("Collector");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
