/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir.servisi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import emir.klase.Klub;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author emirm
 */
@Service("servisKlub")
@Transactional
public class ServisKlubImp implements ServisKlub{
    private static final AtomicLong brojacKlub= new AtomicLong();
    private static List<Klub> klubovi;
    
    static{
        try {
            klubovi= napuniKlubovima();
        } catch (ParseException ex) {
            Logger.getLogger(ServisKlubImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServisKlubImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static List<Klub> napuniKlubovima() throws IOException, ParseException {
        List<Klub> klub= new ArrayList<Klub>();
        JSONParser parser= new JSONParser();
        
        Object obj= parser.parse(new FileReader("klubovi.json"));
        JSONArray jsonarray= new JSONArray();
        jsonarray=(JSONArray)obj;
        
        for (int i=0; i< jsonarray.size(); i++){
            JSONObject object = (JSONObject) jsonarray.get(i);
            long id= (long) object.get("idKluba");
            brojacKlub.incrementAndGet();
            String imeKluba= (String) object.get("imeKluba");
            long idGrada= (long) object.get("idGrad");
            String imeStadiona= (String) object.get("imeStadiona");
            String imeNavijaca= (String) object.get("imeNavijaca");
            klub.add(new Klub(id,imeKluba,idGrada,imeStadiona,imeNavijaca));
        }
        return klub;
    }
    
    public Klub nadjiKlubPoId(long id){
        for(Klub klub:klubovi){
            if( klub.getIdKluba()==id )
                return klub;
        }
        return null;
    }
    
    public Klub nadjiPoKlubu(String klub){
        for(Klub klub1:klubovi){
            if( klub1.getImeKluba().equals(klub) )
                return klub1;
        }
        return null;
    }
    
    public void SpasiKlub(Klub klub){
        if(!daLiPostojiKlub(klub)) { 
            klub.setIdKluba(brojacKlub.incrementAndGet());
            klubovi.add(klub);
            ObjectWriter ow= new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json;
            try (FileWriter file = new FileWriter("klubovi.json")) {
                file.write("[");
                for(int i=0; i<klubovi.size(); i++){

                    file.write(ow.writeValueAsString(klubovi.get(i)));
                    if(i<klubovi.size()-1) file.write(",");
                }
                file.write("]");
            } catch (IOException ex) {
                Logger.getLogger(ServisKlubImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public List<Klub> dajSveKlubove(){
        return klubovi;
    }
    
    public boolean daLiPostojiKlub(Klub klub){
        for(Klub klub1:klubovi){
            if(klub1.getImeKluba().equals(klub.getImeKluba()) && klub1.getImeStadiona().equals(klub.getImeStadiona()) 
                    && klub1.getImeNavijaca().equals(klub.getImeNavijaca()) && klub1.getIdGrad()==klub.getIdGrad())
                return true;
        }
        return false;
    }
}
