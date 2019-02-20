package volmax.model;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Ajuda extends VBox{
    
    public Ajuda(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/volmax/view/ajuda.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
