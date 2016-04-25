/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir.klase;

import javax.validation.constraints.NotNull;

/**
 *
 * @author emirm
 */
public class Tabela {
    
    @NotNull
    private long idTabele;
    
    @NotNull
    private long idKluba;
    
    @NotNull
    private int brojUtakmica;
    
    @NotNull
    private int pobjede;
    
    @NotNull
    private int nerjesene;
    
    @NotNull
    private int izgubljene;
    
    @NotNull
    private int datiGolovi;
    
    @NotNull
    private int primljeniGolovi;
    
    @NotNull
    private int kazne;
    
    @NotNull
    private int bodovi;
    
    //<editor-fold desc="Konstruktori" defaultstate="collapsed">
    public Tabela(){
        idTabele=0;
    }
    
    public Tabela(long idKluba){
        idTabele=idKluba; 
        this.idKluba=idKluba;
        brojUtakmica=0; pobjede=0; nerjesene=0; izgubljene=0;
        datiGolovi=0; primljeniGolovi=0; 
        kazne=0; bodovi=0;
    }
    
    
    //</editor-fold>
    
    //<editor-fold desc="Set and Update Tabele" defaultstate="collapsed">
    public void setTablea(int w, int d, int l, int dg, int pg, int k){
        brojUtakmica=w+d+l;
        pobjede=w; nerjesene=d; izgubljene=l;
        datiGolovi=dg; primljeniGolovi=pg; kazne=k;
        bodovi=3*w+d-k;
    }
    
    public void updateTabela(int w, int d, int l, int dg, int pg, int k){
        brojUtakmica+=w+d+l;
        pobjede+=w; nerjesene+=d; izgubljene+=l;
        datiGolovi+=dg; primljeniGolovi+=pg; kazne+=k;
        bodovi+=3*w+d-k;
    }
    //</editor-fold defaultstate="collapsed">
    
    //<editor-fold desc="Geteri" defaultstate="collapsed">
    public long getIdKluba(){ return idKluba; }
    public int getBrojUtakmica(){ return brojUtakmica; }
    public int getPobjede(){ return pobjede; }
    public int getNerjesene(){ return nerjesene; }
    public int getIzgubljene(){ return izgubljene; }
    public int getDatiGolovi(){ return datiGolovi; }
    public int getPrimljeniGolovi(){ return primljeniGolovi; }
    public int getKazne(){ return kazne; }
    public int getBodovi(){ return bodovi; }
    //</editor-fold>
    
    //<editor-fold desc="Seteri" defaultstate="collapsed">
    public void setIdKluba(long idKluba) {
        this.idKluba = idKluba;
    }

    public void setBrojUtakmica(int brojUtakmica) {
        this.brojUtakmica = brojUtakmica;
    }

    public void setPobjede(int pobjede) {
        this.pobjede = pobjede;
    }

    public void setNerjesene(int nerjesene) {
        this.nerjesene = nerjesene;
    }

    public void setIzgubljene(int izgubljene) {
        this.izgubljene = izgubljene;
    }

    public void setDatiGolovi(int datiGolovi) {
        this.datiGolovi = datiGolovi;
    }

    public void setPrimljeniGolovi(int primljeniGolovi) {
        this.primljeniGolovi = primljeniGolovi;
    }

    public void setKazne(int kazne) {
        this.kazne = kazne;
    }

    public void setBodovi(int bodovi) {
        this.bodovi = bodovi;
    }
    //</editor-fold>
    
    
    public void updateTabele(Tabela t){
        updateTabela(t.kazne, t.pobjede, t.nerjesene, t.izgubljene, t.datiGolovi, t.primljeniGolovi);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (int) (this.idKluba ^ (this.idKluba >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tabela other = (Tabela) obj;
        if (this.idKluba != other.idKluba) {
            return false;
        }
        return true;
    }

    
}
