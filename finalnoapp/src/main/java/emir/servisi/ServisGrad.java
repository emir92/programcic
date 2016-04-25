/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir.servisi;

import emir.klase.Grad;
import java.util.List;

/**
 *
 * @author emirm
 */
public interface ServisGrad {
    
    Grad nadjiPoId(long id);
    
    Grad nadjiPoMjestu(String mjesto);
    
    void SpasiGrad(Grad grad);
    
    List<Grad> dajSveGradove();
    
    boolean daLiPostojiGrad(Grad grad);
    
}
