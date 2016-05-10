/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir.kontroleri;

import emir.klase.Sezona;
import emir.servisi.ServisSezona;
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
@RequestMapping(value = "sezone")
public class KontrolerSezona {
    
    @Autowired
    ServisSezona servis;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Sezona>> listaSvihSezona() {
        List<Sezona> sezona = servis.dajSveSezone();
        if(sezona.isEmpty()){
            return new ResponseEntity<List<Sezona>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        } 
       return new ResponseEntity<List<Sezona>>(sezona, HttpStatus.OK);
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sezona> getSezona(@PathVariable("id") long id) {
        System.out.println("Liga sa id brojem: " + id);
        Sezona sezona = servis.nadjiSezonuPoId(id);
        if (sezona == null) {
            System.out.println("sezona sa " + id + " ne postoji");
            return new ResponseEntity<Sezona>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Sezona>(sezona, HttpStatus.OK);
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> kreirajSezona(@RequestBody Sezona sezona, UriComponentsBuilder ucBuilder) {
        System.out.println("Kreiraj sezonu: " + sezona.getImeSezone());
       
        
        if (servis.daLiPostojiSezona(sezona)) {
            System.out.println("Sezona sa imenom " + sezona.getImeSezone()+ " vec postoji");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        servis.spasiSezonu(sezona);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/sezone/{id}").buildAndExpand(sezona.getIdSezone()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Sezona> updateSezona(@PathVariable("id") long id, @RequestBody Sezona sezona) {
        System.out.println("Updating Sezona " + id);
         
        Sezona tLiga = servis.nadjiSezonuPoId(id);
         
        if (tLiga==null) {
            System.out.println("Sezona sa id " + id + " not found");
            return new ResponseEntity<Sezona>(HttpStatus.NOT_FOUND);
        }
        servis.updateSezona(sezona);
        //tLiga.setSklubovi(liga.getSklubovi());
        
         
        
        return new ResponseEntity<Sezona>(tLiga, HttpStatus.OK);
    }
}
