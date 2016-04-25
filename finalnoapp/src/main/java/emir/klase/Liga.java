/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir.klase;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author emirm
 */
public class Liga {
    
    @NotNull
    private long idLige;
    
    @NotNull
    @Size(min=3, max=15)
    private String imeLige;
    private List<Tabela> sklubovi;//svi klubovi koji su igrali u ovoj ligi
    
    //<editor-fold desc="Konstruktori">
    public Liga(){
       idLige=0; 
       sklubovi=new ArrayList<Tabela>();
    }
    
    public Liga(String ime){
        imeLige=ime; 
        sklubovi=new ArrayList<Tabela>();
    }
    
    public Liga(long id, String ime){
        idLige=id;
        imeLige=ime; 
        sklubovi=new ArrayList<Tabela>();
    }
    //</editor-fold>
    
    //<editor-fold desc="Seteri">
    public void setIdLige(long id){ idLige=id; }
    public void setImeLige(String ime){ imeLige=ime; }
    public void setSklubovi(List<Tabela> kl){ sklubovi=kl; }
    //</editor-fold>
    
    //<editor-fold desc="Geteri">
    public long getIdLige(){ return idLige; }
    public String getImeLige(){ return imeLige; }
    public List<Tabela> getSklubovi() { return sklubovi; }
    //</editor-fold>
    
    //<editor-fold desc="Metode addKlub i updateKlub">
    public void addKlub(Tabela k){
        sklubovi.add(k);
    }
    
    public void updateKlub(int idK,int w, int d, int l, int dg, int pg, int k){
        sklubovi.get(idK).updateTabela(w, d, l, dg, pg, k);
    }
    
    public void updateTabel(Tabela t){
        int indeks= sklubovi.indexOf(t);
        sklubovi.get(indeks).updateTabele(t);
    }
    //</editor-fold>
    
    //<editor-fold desc="Override">
    @Override
    public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (idLige ^ (idLige >>> 32));
            return result;
    }
    
    
    @Override
    public boolean equals(Object obj) {
            if (this == obj)
                    return true;
            if (obj == null)
                    return false;
            if (!(obj instanceof Klub))
                    return false;
            Liga other = (Liga) obj;
            if (idLige != other.idLige)
                    return false;
            return true;
    }
    //</editor-fold>
}
