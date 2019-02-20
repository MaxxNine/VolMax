package volmax.model;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Recc extends VBox{
    @FXML private WebView webView;
    WebEngine engine;
    
    public Recc(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/volmax/view/recc.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    public void chamarSite(){
        engine = webView.getEngine();
        webView.setContextMenuEnabled(false);
        engine.load("http://localhost:0000");
    }
}