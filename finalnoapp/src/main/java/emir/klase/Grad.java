/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir.klase;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author emirm
 */
public class Grad {
    
    @NotNull
    private long idGrada;
    
    @NotNull
    @Size(min=2, max=15)
    private String imeGrada;
    
    @NotNull
    @Size(min=2, max=15)
    private String opcina;
    
    @NotNull
    private Kantoni kanton;
    
    @NotNull
    private Entiteti entitet;
    
    private enum Kantoni{TK,KS,SBK,BPK,PK,HNK,ZHK,K10,USK,ZDK,AAA};
    private enum Entiteti{FBIH,RS,BD,AAA};

    // <editor-fold desc="Konstruktori">
    public Grad(){
        idGrada=0;
    }
    
    public Grad(long id, String mjesto, String opcina, String kan, String ent) {
        this.idGrada=id; this.imeGrada=mjesto; this.opcina=opcina;
        switch(kan){
            case ("TK"): this.kanton=Kantoni.TK; break;
            case ("KS"): this.kanton=Kantoni.KS; break;
            case ("SBK"): this.kanton=Kantoni.SBK; break;
            case ("BPK"): this.kanton=Kantoni.BPK; break;
            case ("PK"): this.kanton=Kantoni.PK; break;
            case ("HNK"): this.kanton=Kantoni.HNK; break;
            case ("ZHK"): this.kanton=Kantoni.ZHK; break;
            case ("K10"): this.kanton=Kantoni.K10; break;
            case ("USK"): this.kanton=Kantoni.USK; break;
            case ("ZDK"): this.kanton=Kantoni.ZDK; break;
            case ("AAA"): this.kanton=Kantoni.AAA; break;
        }
        switch(ent){
            case ("FBIH"): this.entitet=Entiteti.FBIH; break;
            case ("RS"): this.entitet=Entiteti.RS; break;
            case ("BD"): this.entitet=Entiteti.BD; break;
            case ("AAA"): this.entitet=Entiteti.AAA; break;
        }
    }
    // </editor-fold>

    // <editor-fold desc="Seteri">
    
    public void setIdGrada(long id){ idGrada=id;}
    public void setImeGrada(String imeGrada) { this.imeGrada = imeGrada; }
    public void setOpcina(String opcina){ this.opcina=opcina;}
    public void setKanton(Kantoni kanton){ this.kanton=kanton; }
    public void setEntitet(Entiteti entitet){ this.entitet=entitet;}
    
    // </editor-fold>
    
    // <editor-fold desc="Geteri">
    public long getIdGrada(){ return idGrada;}
    public String getImeGrada() { return imeGrada; }
    public String getOpcina() { return opcina;}
    public Kantoni getKanton() { return kanton;}
    public Entiteti getEntitet() { return entitet;}
    
    // </editor-fold>
    
    // <editor-fold desc="Override">
    @Override
    public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (idGrada ^ (idGrada >>> 32));
            return result;
    }
    
    @Override
    public boolean equals(Object obj) {
            if (this == obj)
                    return true;
            if (obj == null)
                    return false;
            if (!(obj instanceof Grad))
                    return false;
            Grad other = (Grad) obj;
            if (idGrada != other.idGrada)
                    return false;
            return true;
    }
    
    @Override
    public String toString(){
        return String.format("{ 'id': '%s', 'mjesto': '%s', 'opcina':'%s','kanton': '%s', 'entitet':'%s'}",
                idGrada,imeGrada,opcina,kanton.name(),entitet.name());
    }
    // </editor-fold>
}
