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
public class Sezona {
    
    @NotNull
    private long idSezone;
    
    @NotNull
    @Size(min=3, max=15)
    private String imeSezone;
    
    @NotNull
    private long idLige;
    private List<Tabela> klubovi;
    
    //<editor-fold desc="Konstruktori" defaultstate="collapsed">
    public Sezona(){
        idSezone=0;
        klubovi= new ArrayList<Tabela>();
    }
    
    public Sezona(String ime, long idS, long idL){
        imeSezone=ime;
        idLige=idL;
        idSezone=idS;
        klubovi= new ArrayList<Tabela>();
    }
    
    public Sezona(String ime, long id){
        imeSezone=ime;
        idLige=id;
        klubovi= new ArrayList<Tabela>();
    }
    //</editor-fold>
    
    //<editor-fold desc="Geteri" defaultstate="collapsed">
    public long getIdSezone(){ return idSezone; }
    public String getImeSezone(){ return imeSezone; }
    public long getIdLige(){ return idLige; }
    public List<Tabela> getKlubovi(){ return  klubovi; }
    //</editor-fold>
    
    //<editor-fold desc="Seteri" defaultstate="collapsed">
    public void setIdSezone(long id){ idSezone=id; }
    public void setImeSezone(String ime){ imeSezone=ime; }
    public void setIdLige(long id){ idLige=id; }
    public void setKlubovi(List<Tabela> tab){ klubovi=tab; }
    //</editor-fold>
    
    //<editor-fold desc="Metode addKlub i updateKlub" defaultstate="collapsed">
    public void addKlub(Tabela k){
        klubovi.add(k);
    }
    
    public void updateKlub(int idK,int w, int d, int l, int dg, int pg, int k){
        klubovi.get(idK).updateTabela(w, d, l, dg, pg, k);
    }
    //</editor-fold>
    
    //<editor-fold desc="Override" defaultstate="collapsed"> 
    @Override
    public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (idSezone ^ (idSezone >>> 32));
            return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sezona other = (Sezona) obj;
        if (this.idSezone != other.idSezone) {
            return false;
        }
        return true;
    }
    
    //</editor-fold>

    
}
