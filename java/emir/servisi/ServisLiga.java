/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir.servisi;

import emir.klase.Liga;
import java.util.List;

/**
 *
 * @author emirm
 */
public interface ServisLiga {
    
    Liga nadjiLiguPoId(long id);
    
    Liga nadjiPoLigi(String imeLiga);
    
    void spasiLigu(Liga liga);
    
    List<Liga> dajSveLige();
    
    boolean daLiPostojiLiga(Liga liga);
    
    void updateLiga(Liga liga);
}
