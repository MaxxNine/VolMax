package volmax.model;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import volmax.controller.ConexaoDB;
import volmax.controller.FXMLPlayerController;

public class AddM extends VBox{    
    final ConexaoDB db = new ConexaoDB();
    @FXML private ListView listaaddp;
    @FXML private Button addAtual;    
    @FXML private Label temp;
    @FXML private Button addToPlay;    
    @FXML private Label temp1;

    FXMLPlayerController fx;
    private List<Musica> musicas = new ArrayList<>();
    private List<Musica> musicas2 = new ArrayList<>();

    int call = 0, call2 = 0;
    
    public AddM(FXMLPlayerController fx){
        this.fx = fx;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/volmax/view/addm.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public void AddToAtual() throws SQLException{
        if (call == 0){
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
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
            addAtual.setText("Adicionar músicas");
            addAtual.setStyle("-fx-border-color:#388bc2;");
            temp.setText("Atenção, esta é uma ação temporária!\nNão influenciará nas próximas vezes que abrir a Playlist");
            call = 1;
            return;
        }
        }
        else {
            fx.adicionarToRep(musicas);
            musicas.clear();
            addAtual.setText("Adicionar músicas à lista de reprodução atual");
            addAtual.setStyle("-fx-border-color:#fff;");
            temp.setText("");
            call = 0;
        }
    }
    
    public void addToPlaylist() throws SQLException{
        if (call2 == 0){
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
            List<File> files = chooser.showOpenMultipleDialog(null);
        if (files == null);
            
        else{            
            for (int i = 0; i < files.size(); i ++){
                File file = files.get(i);          
                Musica m = new Musica();
                try{                     
                    m = fx.transformarEmMusica(file, m, i);
                    musicas2.add(m);
                }
                   catch(Exception e){
                       System.out.println(e);
                   }
            }
            addToPlay.setText("Adicionar músicas");
            addToPlay.setStyle("-fx-border-color:#388bc2;");
            temp1.setText("Deixe a playlist desejada selecionada\nna lista do canto superior esquerdo da tela.");
            call2 = 1;
            return;
        }
        }
        else {
            Playlist p = fx.getPlaylistSelecionada();
            if (p == null){
                temp1.setText("Por favor, selecione uma playlist!");                
                return;
            }       
            
            for (Musica musica: musicas2){
                db.inserirMusica(musica);
                Musica m = (Musica) db.getMusicas().get(0);
                db.inserirSong(m.getId(), p.getId());
            }
            musicas2.clear();
            addToPlay.setText("Adicionar músicas a uma Playlist");
            addToPlay.setStyle("-fx-border-color:#fff;");
            temp1.setText("");
            call2 = 0;
            fx.listarMusicas(p, 0);
        }
    }
}