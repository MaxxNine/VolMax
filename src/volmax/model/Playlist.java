package volmax.model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int idPlaylist;
    private String label = "Desconhecida";    
    private int vezesTocada;
    
    public Playlist(String label){
        this.label = label;
        this.vezesTocada = 0;
        this.idPlaylist = 0;
    }    
    public Playlist() {
    }  

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    
    public int getId(){
        return idPlaylist;
    }    
    public void setId(int id){
        this.idPlaylist = id;
    }
       
    public int getVezestocada(){
        return vezesTocada;
    }
    
    public void setVezestocada(int vezes){
        this.vezesTocada = vezes;
    }
    
}
    
        