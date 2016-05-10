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
public class Opcina {
    
    long idOpcine;
    String imeOpcine;
    
    @NotNull
    private Kantoni kanton;
    
    @NotNull
    private Entiteti entitet;
    
    private enum Kantoni{TK,KS,SBK,BPK,PK,HNK,ZHK,K10,USK,ZDK,AAA};
    private enum Entiteti{FBIH,RS,BD,AAA};

    public Opcina() {
    }

    public Opcina(long idOpcine, String imeOpcine) {
        this.idOpcine = idOpcine;
        this.imeOpcine = imeOpcine;
    }
    
    
}
