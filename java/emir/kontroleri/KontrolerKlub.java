/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir.kontroleri;

import emir.klase.Klub;
import emir.servisi.ServisKlub;
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
@RequestMapping(value = "klubovi")
public class KontrolerKlub {
    
    @Autowired
    ServisKlub servis;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Klub>> listaSvihKlubova() {
        List<Klub> klub = servis.dajSveKlubove();
        if(klub.isEmpty()){
            return new ResponseEntity<List<Klub>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        } 
       return new ResponseEntity<List<Klub>>(klub, HttpStatus.OK);
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Klub> getKlub(@PathVariable("id") long id) {
        System.out.println("Klub sa id brojem: " + id);
        Klub klub = servis.nadjiKlubPoId(id);
        if (klub == null) {
            System.out.println("Klub sa " + id + " ne postoji");
            return new ResponseEntity<Klub>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Klub>(klub, HttpStatus.OK);
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> kreirajKlub(@RequestBody Klub klub, UriComponentsBuilder ucBuilder) {
        System.out.println("Kreiraj klub: " + klub.getImeKluba());
       
        
        if (servis.daLiPostojiKlub(klub)) {
            System.out.println("Klub sa imenom " + klub.getImeKluba()+ " vec postoji");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        servis.SpasiKlub(klub);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/klubovi/{id}").buildAndExpand(klub.getIdKluba()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
