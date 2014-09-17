/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package collector;

import DBConnect.DBConnect;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author nathanr
 */
public class Collector extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Add Coin");
        btn.setOnAction((ActionEvent event) -> {
            StringBuilder note = new StringBuilder("note test");
            Coin coin = new Coin ("Silver Eagle Dollar",CoinGrade.MS70,"100.0",CoinCurrency.EURO,note);
            
            DBConnect.addCoin(coin.getItemId().toString(), coin.getItemName(), coin.getGrade().name(), coin.getFaceValue(),coin.getCoinCurrency().name(),coin.getItemNote());
            
            
            
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Collector");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
