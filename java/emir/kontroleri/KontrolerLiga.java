/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir.kontroleri;

import emir.klase.Liga;
import emir.servisi.ServisLiga;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author emirm
 */
@RestController
@RequestMapping(value = "lige")
public class KontrolerLiga {
    
    @Autowired
    ServisLiga servis;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Liga>> listaSvihLiga() {
        List<Liga> liga = servis.dajSveLige();
        if(liga.isEmpty()){
            return new ResponseEntity<List<Liga>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        } 
       return new ResponseEntity<List<Liga>>(liga, HttpStatus.OK);
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Liga> getLiga(@PathVariable("id") long id) {
        System.out.println("Liga sa id brojem: " + id);
        Liga liga = servis.nadjiLiguPoId(id);
        if (liga == null) {
            System.out.println("liga sa " + id + " ne postoji");
            return new ResponseEntity<Liga>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Liga>(liga, HttpStatus.OK);
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> kreirajLigu(@RequestBody Liga liga, UriComponentsBuilder ucBuilder) {
        System.out.println("Kreiraj ligu: " + liga.getImeLige());
       
        
        if (servis.daLiPostojiLiga(liga)) {
            System.out.println("Klub sa imenom " + liga.getImeLige()+ " vec postoji");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        servis.spasiLigu(liga);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/lige/{id}").buildAndExpand(liga.getIdLige()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Liga> updateLiga(@PathVariable("id") long id, @RequestBody Liga liga) {
        System.out.println("Updating Liga " + id);
         
        Liga tLiga = servis.nadjiLiguPoId(id);
         
        if (tLiga==null) {
            System.out.println("Liga sa id " + id + " not found");
            return new ResponseEntity<Liga>(HttpStatus.NOT_FOUND);
        }
        servis.updateLiga(liga);
        //tLiga.setSklubovi(liga.getSklubovi());
        
         
        
        return new ResponseEntity<Liga>(tLiga, HttpStatus.OK);
    }
}
