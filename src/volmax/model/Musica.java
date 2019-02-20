package volmax.model;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Musica {
    private String titulo = "Desconhecido";
    private String artista = "Desconhecido";
    private String album = "Desconhecido";
    private String add = "+";
    private String url = "";
    private int vezesTocada = 0; 
    private int posicao;
    private int belongTo;
    private int id;
    
    private Image image  = new Image ("volmax/imagens/she.png");
    
    private Media media;
    private MediaPlayer mediaPlayer;
    
    public Musica(String titulo, String artista, String album){
        this.add = "+";    
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.vezesTocada = 0;
        this.posicao = 0;
    }   

    public Musica() {
    }
    
    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }
    
    public void setMediaPlayer(MediaPlayer media){
        this.mediaPlayer = media;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }
    
    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
    
    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getPosicao(){
        return posicao;
    }
    
    public void setPosicao(int posicao){
        this.posicao = posicao;
    }
    
    public int getBelongTo(){
        return belongTo;
    }
    
    public void setBelongTo(int belongTo){
        this.belongTo = belongTo;
    }
    
    public int getVezestocada(){
        return vezesTocada;
    }
    
    public void setVezestocada(int vezes){
        this.vezesTocada = vezes;
    }
    
    
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
   
    public String getUrl() {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    } 
    
    
}
