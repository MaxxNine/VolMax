
package volmax.controller;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import volmax.model.Playlist;

public class ListandoPlaylists {
    
    private String namePlaylist;
    List<Playlist> playlists = new ArrayList<>();    
    final ConexaoDB db = new ConexaoDB();
    
    public ObservableList<Playlist> listando() throws SQLException{
                playlists = db.getPlaylists();
        return FXCollections.observableArrayList(playlists);

        
    }
}
    
