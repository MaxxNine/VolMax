package volmax.controller;

import volmax.model.NovaP;
import volmax.model.AddM;
import volmax.model.Recc;
import volmax.model.Home;
import volmax.model.Ajuda;
import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PathTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener.Change;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.EqualizerBand;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import volmax.model.Musica;
import volmax.model.Playlist;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polyline;

   
public class FXMLPlayerController implements Initializable {
    @FXML private VBox centro;
    @FXML private VBox painel;
    @FXML private HBox topo;
    
    @FXML private AnchorPane janela;
    @FXML private ImageView capa;

    @FXML private FontAwesomeIconView volIco;
    @FXML private MediaView mediaView;    
    
    @FXML private TableView list;    
    @FXML private TableView listaplaylist;
    
    @FXML private TableColumn <Playlist, String> playlist;
    
    @FXML private TableColumn <Musica, String> add;
    @FXML private TableColumn <Musica, String> titulo;
    @FXML private TableColumn <Musica, String> artista;    
    @FXML private TableColumn <Musica, String> album;    
    
    @FXML private Slider tempo;    
    @FXML private Slider vol;
    
    @FXML private VBox boxRecent;
    @FXML private Label lastplaylist;
    @FXML private Label favoriteplaylist;
    @FXML private Label lastsong;
    @FXML private Label lastalbum;
    @FXML private Label lastartist;
    @FXML private Label favoritesong;  
    @FXML private Label songName;    
    @FXML private Label artist;
    @FXML private Label tempoCorrido;
    @FXML private CheckBox random;
    @FXML private ProgressBar timesong;    
    
    
    @FXML private Button ocup;
    
    //EQUALIZADORES
    @FXML private Slider sliderH;
    @FXML private Slider sliderHM;
    @FXML private Slider sliderM;
    @FXML private Slider sliderLM;
    @FXML private Slider sliderL;
    EqualizerBand equalizerL;
    EqualizerBand equalizerLM;
    EqualizerBand equalizerM;
    EqualizerBand equalizerHM;
    EqualizerBand equalizerH;

    //INICIALIZAR PÁGINAS
    Home home;
    AddM addM;
    NovaP novaP;
    Recc recc;
    Ajuda ajuda;
    
    //FAZER ANIMAÇÃO CANTO DIREITO
    final FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
    private double widthLabel;    
    Polyline polyline;    
    PathTransition transition;
    
    //DB
    final ConexaoDB db = new ConexaoDB();
    final ListandoMusicas listarMusicas = new ListandoMusicas();
    final ListandoPlaylists listarPlaylists = new ListandoPlaylists();
    
    //VARIÁVEIS PARA O PLAYER
        
    String url;
    private String tempName = "", tempArt = "", tempAlb = "";
    private String time;
    private final int posicaoMusicaAtual = 0, cont = 0, contArq = 0;
    private int minutos = 0, segundos = 00;
    private Media media;
    private MediaPlayer mediaPlayer;    
    private final Image image2  = new Image ("volmax/imagens/she.png");
    private Playlist playlistAtual, playlistSelecionada;
    private Musica musicaAtual;
    private List<Musica> musicas = new ArrayList<>();
    private Musica row;
    private double volAnt;
    private boolean mudo = false;   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        musicaAtual = new Musica();
        playlistAtual = new Playlist();
        musicas = listarMusicas.musicas;
        capa.setImage(image2);
        carregarPaginas();
        painel.getChildren().add(home);
        VBox.setVgrow(home, Priority.ALWAYS);
        
        try {           
            getFavorites();
            getLastPlayed();
            getLastList();
            listarPlaylist();
            listarMusicas(playlistAtual, -1);
            getRecents();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        janela.widthProperty().addListener((obs, oldVal, newVal) -> {
        if (newVal.doubleValue() >= 1280)
            centro.setPrefWidth(newVal.doubleValue()-400);
        else
            System.gc();
        
        });
           
    }
    
    //FUNÇÕES DO PLAYER
    //FUNÇÕES DO PLAYER
    //FUNÇÕES DO PLAYER
    //FUNÇÕES DO PLAYER
    //FUNÇÕES DO PLAYER
    //FUNÇÕES DO PLAYER
    //FUNÇÕES DO PLAYER
    
    public void play(){
        if(mediaPlayer == null){            
                proximaMusica();                
            
        }
        else if (!(mediaView.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING))){
            mediaView.getMediaPlayer().play();       
    
        }
        else{
            mediaView.getMediaPlayer().pause();        
        }   
    }
    //PRÓXIMA
    public void proximaMusica(){        
        if (musicas.isEmpty());
        
        
        else {
                    
            for (Musica musica: musicas){
                if (musicaAtual.getPosicao()+1 == musicas.size()){
                    Musica m = musicas.get(0);
                    tocarMusica(m);
                    return;
                } 
                if(random.isSelected()){
                    Random r = new Random();
                    Musica m = musicas.get(r.nextInt(musicas.size() - 1));
                    tocarMusica(m);
                    break;
                }
                else if (musica.getPosicao() == musicaAtual.getPosicao()){
                    Musica m = musicas.get(musica.getPosicao()+1);
                    tocarMusica(m);
                    break;
                }
                    
            }
        }            
        
    }
    
    //VOLTAR
    public void musicaAnterior() throws SQLException{
       if (musicas.isEmpty());       
        
       else {
            if (musicaAtual.getPosicao() == 0){
                tocarMusica(musicaAtual);
                return;
            }      
            for (Musica musica: musicas){
                if(random.isSelected()){
                    Random r = new Random();
                    Musica m = musicas.get(r.nextInt(musicas.size() - 1));
                    tocarMusica(m);
                    break;
                }
                else if (musica.getPosicao() == musicaAtual.getPosicao()){
                    Musica m = musicas.get(musica.getPosicao()-1);
                    tocarMusica(m);
                    break;
                }
                    
            }
        }    
    }
        
    public void alterarVolume(){
        mudarVolume(vol.getValue());
    }   
    
    public void trocarVolume(){
        if (mudo){
            vol.setValue(volAnt);
            mudarVolume(vol.getValue());           
            mudo = !mudo;
        }
        else{            
            mudarVolume(0);            
            volAnt = vol.getValue();
            vol.setValue(0);
            mudo = !mudo;
        }            
    }
    public void mudarVolume(double valor){
        if(mediaPlayer != null){
         mediaView.getMediaPlayer().setVolume(valor);             
        }
         if (valor == 0){
             volIco.setGlyphName("VOLUME_OFF");
         }
         else if (valor <= 0.25){
             volIco.setGlyphName("VOLUME_DOWN");            
         }
         else{
              volIco.setGlyphName("VOLUME_UP");
         }
         
    }       
    
    public void tocarMusica(Musica musica){
        try{            
            media = new Media(musica.getUrl()); 
        }
        catch(Exception e){
            musicaAtual = musica;
            proximaMusica();
            return;
        }
        mediaPlayer = new MediaPlayer(media);            
        
        slideListener(mediaPlayer);
        if (mediaView.getMediaPlayer() != null){
            if (mediaView.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)){
                mediaView.getMediaPlayer().stop();
                mediaView.getMediaPlayer().dispose();
            }
        }
        
                    
        mediaView.setMediaPlayer(mediaPlayer);
        this.play();
        
                

        mediaPlayer.setOnReady(() -> {
            Image i = (Image) mediaPlayer.getMedia().getMetadata().get("image");
            if (musica.getBelongTo() != playlistAtual.getId()){
                musicas = listarMusicas.musicas;            
            }
            for (Playlist playlist: listarPlaylists.playlists) {
            if (musica.getBelongTo() == playlist.getId()){
                playlistAtual = playlist;
                break;
            }
            }
            if (i != null)
                musica.setImage(i);
            musicaAtual = musica;
            home.mudarLetra(musica.getTitulo(), musica.getArtista());
            segundos = 0;
            minutos = 0;            
            songName.setText(musica.getTitulo());
            artist.setText(musica.getArtista());
            capa.setImage(musica.getImage());
            mudarTempo(mediaPlayer.getTotalDuration().toSeconds());
            mediaView.getMediaPlayer().setVolume(vol.getValue());
           
        });
        
        mediaPlayer.setOnPlaying(() ->{
            mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
                timesong.setProgress(mediaPlayer.getCurrentTime().toSeconds() / mediaPlayer.getTotalDuration().toSeconds());
                tempo.setValue(mediaPlayer.getCurrentTime().toSeconds());   
                segundos = (int) mediaPlayer.getCurrentTime().toSeconds()-minutos*60;
                if (segundos>59){
                    minutos ++;
                    segundos = 0;
                }
                if (segundos<00){
                    minutos --;
                    segundos = 60 - segundos;
                }
                time = minutos + ":" + (segundos < 10 ? "0" : "") + segundos;
                tempoCorrido.setText(time);
            });
        });
        
        mediaPlayer.setOnEndOfMedia(() ->{
            musica.setVezestocada(musica.getVezestocada()+1);
            try {
                db.updateSong(musica);                
                db.updateAllPlaylist(musica);
                db.updateLastlist(playlistAtual);
                db.inserirLastplay(musica);
                getLastPlayed();
                getLastList();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLPlayerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            proximaMusica();
        });
    }    
        
    public void alterarTempo(){
        if (mediaView.getMediaPlayer() != null)
        mediaView.getMediaPlayer().seek(Duration.seconds(tempo.getValue()));             
    }    
    public void mudarTempo(double time){
        tempo.setMax(time);
    
    }        
    
    //EQUALIZADOR
    public void slideListener(MediaPlayer mediaPlayer){
        equalizerL = new EqualizerBand(60, 100, 0);
        equalizerLM = new EqualizerBand(230, 150, 0);
        equalizerM = new EqualizerBand(910, 800, 0);
        equalizerHM = new EqualizerBand(4000, 1000, 0);        
        equalizerH = new EqualizerBand(14000, 2000, 0);
        mediaPlayer.getAudioEqualizer().getBands().clear();
        mediaPlayer.getAudioEqualizer().getBands().add(equalizerL);
        mediaPlayer.getAudioEqualizer().getBands().add(equalizerLM);
        mediaPlayer.getAudioEqualizer().getBands().add(equalizerM);
        mediaPlayer.getAudioEqualizer().getBands().add(equalizerHM);
        mediaPlayer.getAudioEqualizer().getBands().add(equalizerH);
        equalizerL.setGain(sliderL.getValue() - 24);
        equalizerLM.setGain(sliderLM.getValue() - 24);
        equalizerM.setGain(sliderM.getValue() - 24);
        equalizerHM.setGain(sliderHM.getValue() - 24);
        equalizerH.setGain(sliderH.getValue() - 24);
        sliderL.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            equalizerL.setGain(new_val.doubleValue() - 24);
        });
        
        sliderLM.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            equalizerLM.setGain(new_val.doubleValue() - 24);
        });
        
        sliderM.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            equalizerM.setGain(new_val.doubleValue() - 24);
        });
        
        sliderHM.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            equalizerHM.setGain(new_val.doubleValue() - 24);
        });
        
        sliderH.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            equalizerH.setGain(new_val.doubleValue() - 24);
        });
        
    }
    
    //FUNÇÕES GERAIS
    //FUNÇÕES GERAIS    
    //FUNÇÕES GERAIS
    //FUNÇÕES GERAIS
    //FUNÇÕES GERAIS
    //FUNÇÕES GERAIS
    //FUNÇÕES GERAIS
    //FUNÇÕES GERAIS
    
    //Listando Playlist
    public void listarPlaylist() throws SQLException{
        listaplaylist.setRowFactory(tv -> {
            TableRow<Playlist> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY){
                    Playlist clickedRow = row.getItem();
                    try {                        
                        listarMusicas(clickedRow, 0);
                        playlistSelecionada = clickedRow;
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLPlayerController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row ;
        });
        playlist.setCellValueFactory(new PropertyValueFactory<>("label"));
        
        listaplaylist.setItems(listarPlaylists.listando());
        
    }    
    //Listando músicas
    public void listarMusicas(Playlist playlist, int i) throws SQLException{          
        list.setRowFactory(tv -> {
            TableRow<Musica> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
                     && event.getClickCount() == 2) {
                    Musica clickedRow = row.getItem();
                    //url = "file:///" + URLEncoder.encode(file.getAbsolutePath(), "UTF-8").replace("+", "%20");
                    tocarMusica(clickedRow);
                }
            });
            return row ;
        });    
        add.setCellValueFactory(new PropertyValueFactory<>("add"));
        //FUNÇÃO DE ADICIONAR NA PLAYLIST
        add.setCellFactory(tc -> {
            TableCell<Musica, String> cell = new TableCell<Musica, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty) ;
                    setText(empty ? null : item);
                }
            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    if(cell.getText().equals("+")){
                    row = (Musica) list.getSelectionModel().getSelectedItem();
                        try {
                            db.deleteSongPl(row.getId());
                            db.deleteSong(row.getId());
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLPlayerController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    cell.setText("-");
                    }
                    else{
                    row = (Musica) list.getSelectionModel().getSelectedItem();
                        try {
                            db.inserirMusica(row);
                            Musica m = (Musica)db.getMusicas().get(0);
                            System.out.println(m.getId());
                            System.out.println(playlistSelecionada.getId());
                            db.inserirSong(m.getId(), playlistSelecionada.getId());
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLPlayerController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    cell.setText("+");
                    }
                }
            });
            return cell ;
        });
        
        
        titulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        artista.setCellValueFactory(new PropertyValueFactory<>("artista"));        
        album.setCellValueFactory(new PropertyValueFactory<>("album"));
        
        
        list.setItems(listarMusicas.listaMusicas(playlist, i));
        }
    //EFEITO DE TRANSIÇÃO MELHOR MÚSICA
    public void getRecents (){
        polyline = new Polyline();
        widthLabel = fontLoader.computeStringWidth(favoritesong.getText(), favoritesong.getFont());
        System.out.println();
        polyline.getPoints().addAll(new Double[]{
            -widthLabel/1.5, 20.0,
            240.0 + widthLabel/2, 20.0,
        }); 
        
        transition = new PathTransition();
        transition.setNode(favoritesong);
        transition.setDuration(Duration.seconds(4));
        transition.setPath(polyline);
        transition.setCycleCount(PathTransition.INDEFINITE);
        transition.play();
    }

    //CHAMADA DAS OPÇÕES DO MENU
    public void carregarPaginas(){
        home = new Home();
        addM = new AddM(this);
        novaP = new NovaP(this);
        recc = new Recc();
        ajuda = new Ajuda();
        
    }
    public void home(){
        painel.getChildren().clear();
        painel.getChildren().add(home);
        VBox.setVgrow(home, Priority.ALWAYS);        
    }
    public void adicionarMusicas(){
        painel.getChildren().clear();        
        painel.getChildren().add(addM);
        VBox.setVgrow(addM, Priority.ALWAYS);
    }
    public void novaPlaylist(){
        painel.getChildren().clear();        
        painel.getChildren().add(novaP);
        VBox.setVgrow(novaP, Priority.ALWAYS);        
                
    }
    public void recomm(){
        painel.getChildren().clear();    
        recc.chamarSite();
        painel.getChildren().add(recc);
        VBox.setVgrow(recc, Priority.ALWAYS);
        
    }
   
    public void ajuda(){
        painel.getChildren().clear();        
        painel.getChildren().add(ajuda);
        VBox.setVgrow(ajuda, Priority.ALWAYS);
        }

    public void sair(){
        System.exit(0);
    }
    //AMPILAR OU OCULTAR LISTA
    public void ampliar(){
        if (ocup.getText().equals("Ocultar Lista")){
            ocup.setText("Ampliar Lista");
            topo.setPrefHeight(650);
        }
        else if(ocup.getText().equals("Ampliar Lista")){
            ocup.setText("Ocultar Lista");   
            topo.setPrefHeight(200);        
        }
    }
    
    public void getLastPlayed() throws SQLException{
        String[] lasts = new String[3]; 
        lasts = db.getLastPlayed();
        lastsong.setText(lasts[0]);
        lastalbum.setText(lasts[1]);
        lastartist.setText(lasts[2]);       
    }
    
    public void getLastList() throws SQLException{
        String lastp = "";
        lastp = db.getLastlist();
        lastplaylist.setText(lastp);
    }
    public void getFavorites() throws SQLException{
        Musica musica = db.getFavoriteSong();
        Playlist play = db.getFavoritePlaylist();
        favoritesong.setText(musica.getTitulo());
        favoriteplaylist.setText(play.getLabel());
    }
    
    public Musica transformarEmMusica(File file, Musica musica, int i) throws UnsupportedEncodingException{
        url = "file:///" + URLEncoder.encode(file.getAbsolutePath(), "UTF-8").replace("+", "%20");
        musica.setUrl(url);        
        Media media2 = new Media(musica.getUrl());  
        media2.getMetadata().addListener((Change<? extends String, ? extends Object> c) -> {
        if (c.wasAdded()) {
            if ("artist".equals(c.getKey())) {
                musica.setArtista(c.getValueAdded().toString());
            } else if ("title".equals(c.getKey())) {
                musica.setTitulo(c.getValueAdded().toString());
            } else if ("album".equals(c.getKey())) {
                musica.setAlbum(c.getValueAdded().toString());
            }
        }
        });
        musica.setPosicao(i);
        return musica;        
       }
     
    public void adicionarToRep(List<Musica> musicas) throws SQLException{
        for(Musica musica: musicas){
            musica.setPosicao(listarMusicas.musicas.size());
            listarMusicas.musicas.add(musica);        
        }
        listarMusicas(playlistAtual, 1);
    }
    
    public Playlist getPlaylistSelecionada(){
        return playlistSelecionada;
    }
}
