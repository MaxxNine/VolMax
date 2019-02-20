/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package volmax;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Max
 */
public class VolMax extends Application {
    

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/FXMLPlayer.fxml"));        
        stage.getIcons().add(new Image("volmax/icone.png"));
        Scene scene = new Scene(root, Color.BLACK);       
        stage.setTitle("MaxPlayer");        
        stage.setResizable(true);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setMinHeight(860);
        stage.setMinWidth(810);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
