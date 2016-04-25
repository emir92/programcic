/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir.kontroleri;

import emir.klase.Grad;
import emir.servisi.ServisGrad;
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
@RequestMapping(value = "gradovi")
public class KontrolerGrad {
    
    @Autowired
    ServisGrad servis;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Grad>> listaSvihGradova() {
        List<Grad> grad = servis.dajSveGradove();
        if(grad.isEmpty()){
            return new ResponseEntity<List<Grad>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        
        return new ResponseEntity<List<Grad>>(grad, HttpStatus.OK);
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Grad> getGrad(@PathVariable("id") long id) {
        System.out.println("Grad sa id brojem: " + id);
        Grad grad = servis.nadjiPoId(id);
        if (grad == null) {
            System.out.println("Grad sa " + id + " ne postoji");
            return new ResponseEntity<Grad>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Grad>(grad, HttpStatus.OK);
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> kreirajGrad(@RequestBody Grad grad,    UriComponentsBuilder ucBuilder) {
        System.out.println("Kreiraj grad: " + grad.getImeGrada());
 
        if (servis.daLiPostojiGrad(grad)) {
            System.out.println("A User with name " + grad.getImeGrada()+ " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        servis.SpasiGrad(grad);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/gradovi/{id}").buildAndExpand(grad.getIdGrada()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
}
