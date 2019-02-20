package volmax.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import volmax.model.Musica;
import volmax.model.Playlist;

public class ListandoMusicas {
    final ConexaoDB db = new ConexaoDB();
    private int cont = 0;
    public List<Musica> musicas = new ArrayList<>();

        public ObservableList<Musica> listaMusicas(Playlist playlist, int i) throws SQLException {
            switch (i) {
                case -1:
                    break;
                case 0:
                    musicas = db.getMusicasPlaylist(playlist);
                    break;
                default:                
                    break;
            }
        return FXCollections.observableArrayList(musicas);
   
    }
        
}
