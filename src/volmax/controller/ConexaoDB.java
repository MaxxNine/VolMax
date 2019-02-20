package volmax.controller;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import volmax.VolMax;
import volmax.model.Musica;
import volmax.model.Playlist;

public class ConexaoDB {
    private String query;
    private List<Playlist> playlists;
    private List<Musica> musicas;    
    
    public Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite::resource:volmax/database/volmax.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } 
        
    }
    //CRUD MUSICA
   
    public void inserirMusica(Musica musica) throws SQLException{
        String query = "insert into Musicas(titulo, artista, album, url, vezestocada) values(?,?,?,?,?)";
        
        
        try (Connection conn = this.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, musica.getTitulo());
            preparedStatement.setString(2, musica.getArtista());
            preparedStatement.setString(3, musica.getAlbum());
            preparedStatement.setString(4, musica.getUrl());
            preparedStatement.setInt(5, musica.getVezestocada());

            int count = preparedStatement.executeUpdate();
            System.out.println(count + " Inserted");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu ruim1");

        }
    }
    public void updateSong(Musica musica) throws SQLException{
        query = "update Musicas set titulo = ? ,"
                + "artista = ? ,"
                + "album = ? ,"
                + "url = ? ,"
                + "vezestocada = ? "
                + "WHERE idmusica = ?";
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            // set the corresponding param
            pstmt.setString(1, musica.getTitulo());
            pstmt.setString(2, musica.getArtista());
            pstmt.setString(3, musica.getAlbum());
            pstmt.setString(4, musica.getUrl());
            pstmt.setInt(5, musica.getVezestocada());
            pstmt.setInt(6, musica.getId());
            
            // update 
            pstmt.executeUpdate();
        }
         catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu ruim2");

        }
    }
    
    public void deleteSong(int id) throws SQLException {
         String query = "DELETE from Musicas where idmusica=?";    
         try (Connection conn = this.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int count = preparedStatement.executeUpdate();
            System.out.println(count + " Deleted");
         }
         catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu ruim3");

        }
    }
    
    public Musica getMusicaId(int id) throws SQLException{    
        Musica m = new Musica();
        String query = "select * from Musicas where idmusica=?";  
        try (Connection conn = this.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();            
            while (result.next()) {
                m.setId(id);
                m.setTitulo(result.getString("titulo"));
                m.setAlbum(result.getString("album"));
                m.setArtista(result.getString("artista"));
                m.setUrl(result.getString("url"));
                m.setVezestocada(result.getInt("vezestocada"));                
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu ruim4");

        }
        return m;
    }
    
    public List getMusicas() throws SQLException{      
     String query = "SELECT * FROM Musicas ORDER BY idmusica DESC";  
     musicas = new ArrayList<>();
        try (Connection conn = this.connect();
            Statement statement = conn.createStatement(); 
            ResultSet result = statement.executeQuery(query))
            {        
            while (result.next()) {
                Musica m = new Musica();
                m.setId(result.getInt("idmusica"));
                m.setTitulo(result.getString("titulo"));
                m.setAlbum(result.getString("album"));
                m.setArtista(result.getString("artista"));
                m.setUrl(result.getString("url"));
                m.setVezestocada(result.getInt("vezestocada"));        
                musicas.add(m);
                }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu ruim4");

        }              
        return musicas;
    }
    
    //LASTPLAYED
    
     
    public void inserirLastplay(Musica music) throws SQLException{
        query = "insert into lastplay(lastsong, lastalbum, lastartist) values(?,?,?)";
        try (Connection conn = this.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, music.getTitulo());
            preparedStatement.setString(2, music.getAlbum());
            preparedStatement.setString(3, music.getArtista());
            int count = preparedStatement.executeUpdate();
            System.out.println(count + " Inserted");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu ruim5");

        }
    }
    public String[] getLastPlayed() throws SQLException{    
        String[] resul = new String[4]; 
        String query = "SELECT * FROM lastplay ORDER BY idlast DESC LIMIT 1";  
        try (Connection conn = this.connect();
            Statement statement = conn.createStatement(); 
            ResultSet result = statement.executeQuery(query))
           {        
            resul[0] = result.getString("lastsong");
            resul[1] = result.getString("lastalbum");
            resul[2] = result.getString("lastartist");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu Ruim lastplay");

        }        
        return resul;
        
    }
   
    //CRUD PLAYLIST
    public void inserirPlaylist(Playlist playlist) throws SQLException{
        query = "insert into Playlists(nomeplaylist, vezestoc) values(?,?)";
        try (Connection conn = this.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, playlist.getLabel());
            preparedStatement.setInt(2, playlist.getVezestocada());
            int count = preparedStatement.executeUpdate();
            System.out.println(count + " Inserted");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu ruim7");

        }
    }
    
    public void inserirSong (int idmusica, int idplaylist) throws SQLException{
        query = "insert into Listmusics(idplaylist, idmusica) values(?,?)";
        try (Connection conn = this.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, idplaylist);
            preparedStatement.setInt(2, idmusica);
            int count = preparedStatement.executeUpdate();
            System.out.println(count + " Inserted");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu ruim8");

        }
    }
    
    public void deleteSongPl (int idmusica) throws SQLException{        
        String query = "DELETE from Listmusics where idmusica=?";    
         try (Connection conn = this.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, idmusica);
            int count = preparedStatement.executeUpdate();
            System.out.println(count + " Deleted");
         }
         catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu ruim3");
    }
    }
    
            
    public List getPlaylists() throws SQLException{      
     String query = "SELECT * FROM playlists ORDER BY idplaylist DESC";  
     playlists = new ArrayList<>();
        try (Connection conn = this.connect();
            Statement statement = conn.createStatement(); 
            ResultSet result = statement.executeQuery(query))
            {        
            while (result.next()) {
                Playlist playlist = new Playlist(result.getString("nomeplaylist"));
                playlist.setId(result.getInt("idplaylist"));
                playlist.setVezestocada(result.getInt("vezestoc"));
                playlists.add(playlist);
                }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu ruim4");

        }              
        return playlists;
    }

    public Musica getFavoriteSong() throws SQLException{    
        Musica m = new Musica();
        String query = "SELECT * FROM Musicas ORDER BY vezestocada DESC LIMIT 1";  
        try (Connection conn = this.connect();
            Statement statement = conn.createStatement(); 
            ResultSet result = statement.executeQuery(query))
        {           
            while (result.next()) {
                m.setId(result.getInt("idmusica"));
                m.setTitulo(result.getString("titulo"));
                m.setAlbum(result.getString("album"));
                m.setArtista(result.getString("artista"));
                m.setUrl(result.getString("url"));
                m.setVezestocada(result.getInt("vezestocada"));                
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu ruim6");

        }        
        return m;
        
    }
    public Playlist getFavoritePlaylist() throws SQLException{    
        Playlist p = new Playlist();
        String query = "SELECT * FROM Playlists ORDER BY vezestoc DESC LIMIT 1";  
        try (Connection conn = this.connect();
            Statement statement = conn.createStatement(); 
            ResultSet result = statement.executeQuery(query))
        {           
            while (result.next()) {
                p.setId(result.getInt("idplaylist"));
                p.setLabel(result.getString("nomeplaylist"));
                p.setVezestocada(result.getInt("vezestoc"));
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu ruim6");

        }        
        return p;
        
    }
    
    public void updateVezesPlay(int id) throws SQLException{
        query = "update Playlists set vezestoc = vezestoc + 1 "
                + "WHERE idplaylist = ?";
         try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            // set the corresponding param
            pstmt.setInt(1, id);
            
            // update 
            pstmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu ruim5");

        }
    }
    
    public void updateAllPlaylist(Musica m) throws SQLException{
        
        String query = "SELECT idplaylist FROM Listmusics where idmusica=?"; 
        playlists = new ArrayList<>();
        try (Connection conn = this.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, m.getId());
            ResultSet result = preparedStatement.executeQuery();            
            while (result.next()) {
                Playlist playlist = new Playlist();
                playlist.setId(result.getInt("idplaylist"));
                playlists.add(playlist);
                }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("updateAllPlaylist bug");

        } 
        
        for (Playlist playlist: playlists){
            updateVezesPlay(playlist.getId());                
        }
    }
    
    public void updateLastlist(Playlist list) throws SQLException{
        query = "update lastlist set nameplay = ? "
                + "WHERE idplay = 1";
         try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            // set the corresponding param
            pstmt.setString(1, list.getLabel());
            
            // update 
            pstmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu ruim5");

        }
    }
    public String getLastlist() throws SQLException{    
        String resul = ""; 
        String query = "SELECT * FROM lastlist ORDER BY idplay DESC LIMIT 1";  
        try (Connection conn = this.connect();
            Statement statement = conn.createStatement(); 
            ResultSet result = statement.executeQuery(query))
           {       
           while (result.next()) {
            resul = result.getString("nameplay");
           }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Deu ruim6");

        }        
        return resul;
        
    }
    
    //SELECT MUSICAS DA PLAYLIST
    
    public List getMusicasPlaylist(Playlist p) throws SQLException{
        int cont = 0;
        String query = "SELECT idmusica FROM Listmusics where idplaylist=?"; 
        musicas = new ArrayList<>();
        try (Connection conn = this.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, p.getId());
            ResultSet result = preparedStatement.executeQuery();            
            while (result.next()) {
                Musica mus = new Musica();
                mus = getMusicaId(result.getInt("idmusica"));
                mus.setPosicao(cont); 
                mus.setBelongTo(p.getId());                
                musicas.add(mus);
                cont ++;
                }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("updatePlaylist bug");

        }         
        
        return musicas;
    }
}