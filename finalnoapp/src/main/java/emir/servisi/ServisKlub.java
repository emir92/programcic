/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir.servisi;

import emir.klase.Klub;
import java.util.List;

/**
 *
 * @author emirm
 */
public interface ServisKlub {
    
    Klub nadjiKlubPoId(long id);
    
    Klub nadjiPoKlubu(String klub);
    
    void SpasiKlub(Klub klub);
    
    List<Klub> dajSveKlubove();
    
    boolean daLiPostojiKlub(Klub klub);
}
