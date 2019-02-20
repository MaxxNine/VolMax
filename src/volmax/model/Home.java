package volmax.model;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


import java.util.List;

import com.omt.lyrics.SearchLyrics;
import com.omt.lyrics.beans.Lyrics;
import com.omt.lyrics.beans.LyricsServiceBean;
import com.omt.lyrics.beans.SearchLyricsBean;
import com.omt.lyrics.exception.SearchLyricsException;
import com.omt.lyrics.util.Sites;
import javafx.scene.text.Text;


public class Home extends VBox{    
   
    @FXML private Label titulo;
    
    @FXML private Text letra;

    public Home(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/volmax/view/home.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    public void mudarLetra(String nome, String artista){
        SearchLyrics searchLyrics = new SearchLyrics();
        LyricsServiceBean bean = new LyricsServiceBean();
        bean.setSongName(nome);
        bean.setSongArtist(artista);
        List<Lyrics> lyrics;
		 try {
		 lyrics = searchLyrics.searchLyrics(bean);
                 if (!lyrics.isEmpty()){
		 for (Lyrics lyric : lyrics) {
                    titulo.setText(nome);                 
                    letra.setText(lyric.getText());
		 }
                 }
                 else{
                    letra.setText("Letra não encontrada!"); 
                 }
		 } catch (SearchLyricsException e) {                     
                    titulo.setText("");
                    letra.setText("Letra não encontrada!");
                 
		 }
        }       
}
