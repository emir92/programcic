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
public class Klub {
    
    @NotNull
    private long idKluba;
    
    @NotNull
    @Size(min=3, max=15)
    private String imeKluba;
    
    @NotNull
    private long idGrad;
    
    @NotNull
    @Size(min=3, max=15)
    private String imeStadiona;
    
    @NotNull
    @Size(min=3, max=15)
    private String imeNavijaca;
    
    
    //<editor-fold desc="Konstrutori">
    public Klub(){
        idKluba=0;
    }
    
    public Klub(long id,String klub, long idG,String imeS, String imeN){
        idKluba=id;
        imeKluba=klub;
        idGrad=idG;
        imeStadiona=imeS;
        imeNavijaca=imeN;
    }
    //</editor-fold>
    
    //<editor-fold desc="seteri">
    
    public void setIdKluba(long id){ this.idKluba=id;}
    public void setImeKluba(String ime){ imeKluba=ime;}
    public void setImeStadiona(String ime){ imeStadiona=ime;}
    public void setImeNavijaca(String ime){ imeNavijaca=ime;}
    public void setIdGrad(long grad){ this.idGrad=grad;}
    //</editor-fold>
    
    //<editor-fold desc="geteri"> 
    public long getIdKluba(){return this.idKluba;}
    public String getImeKluba(){return imeKluba;}
    public String getImeStadiona(){return imeStadiona;}
    public String getImeNavijaca(){return imeNavijaca;}
    public long getIdGrad(){return this.idGrad;}
    //</editor-fold>
    
    //<editor-fold desc="Override">
    @Override
    public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (idKluba ^ (idKluba >>> 32));
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
            Klub other = (Klub) obj;
            if (idKluba != other.idKluba)
                    return false;
            return true;
    }
    
    
    @Override
    public String toString(){
        return String.format("{ 'id': '%s', 'klub': '%s', 'grad':'%s','stadion': '%s', 'navijaci':'%s'}",
                idKluba,imeKluba,idGrad,imeStadiona,imeNavijaca);
    }
    //</editor-fold>
}
