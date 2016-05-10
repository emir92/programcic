/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir.servisi;

import emir.klase.Sezona;
import java.util.List;

/**
 *
 * @author emirm
 */
public interface ServisSezona {
    
    Sezona nadjiSezonuPoId(long id);
    
    Sezona nadjiPoSezoni(String imeSezona);
    
    void spasiSezonu(Sezona sezona);
    
    List<Sezona> dajSveSezone();
    
    boolean daLiPostojiSezona(Sezona sezona);
    
    void updateSezona(Sezona sezona);
}
