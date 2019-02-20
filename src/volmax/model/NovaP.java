package volmax.model;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import volmax.controller.ConexaoDB;
import volmax.controller.FXMLPlayerController;

public class NovaP extends VBox{    
    final ConexaoDB db = new ConexaoDB();
    private @FXML TextField nameP;
    private @FXML Label qtdFiles;

    FXMLPlayerController fx;
    private List<Musica> musicas = new ArrayList<>();
   
    public NovaP(FXMLPlayerController fx){
        this.fx = fx;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/volmax/view/novap.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public void addToNp() throws UnsupportedEncodingException{
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new ExtensionFilter("MP3 Files", "*.mp3"));
        List<File> files = chooser.showOpenMultipleDialog(null);
        if (files == null);
            
        else{            
            for (int i = 0; i < files.size(); i ++){
                File file = files.get(i);          
                Musica m = new Musica();
                try{                     
                    m = fx.transformarEmMusica(file, m, i);
                    musicas.add(m);
                }
                   catch(Exception e){
                       System.out.println(e);
                   }
            }
            qtdFiles.setText(musicas.size() + " Arquivos selecionados");
        }
    }
    
    public void criarP() throws SQLException{        
        Playlist playlist = new Playlist();
        playlist.setLabel(nameP.getText());
        playlist.setVezestocada(0);
        db.inserirPlaylist(playlist);
        for (Musica musica: musicas){
            db.inserirMusica(musica);
            Playlist p = (Playlist) db.getPlaylists().get(0);
            Musica m = (Musica) db.getMusicas().get(0);            
            db.inserirSong(m.getId(), p.getId());
        }        
        nameP.setText("");
        qtdFiles.setText("");
        musicas.clear();
        this.fx.listarPlaylist();
    }
    
}
