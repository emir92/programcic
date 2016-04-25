/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir.servisi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import emir.klase.Liga;
import emir.klase.Tabela;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Service("servisLiga")
@Transactional
public class ServisLigaImp implements ServisLiga{
    private static final AtomicLong brojacLige= new AtomicLong();
    private static List<Liga> lige;
    
    static{
        try {
            lige= napuniLigama();
        } catch (ParseException ex) {
            Logger.getLogger(ServisLigaImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServisLigaImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static List<Liga> napuniLigama() throws IOException, ParseException {
        List<Liga> liga= new ArrayList<Liga>();
        JSONParser parser= new JSONParser();
        Object obj= parser.parse(new FileReader("lige.json"));
        JSONArray jsonarray= new JSONArray();
        jsonarray=(JSONArray)obj;
        
        for (int i=0; i< jsonarray.size(); i++){
            JSONObject object = (JSONObject) jsonarray.get(i);
            long id= (long) object.get("idLige");
            brojacLige.incrementAndGet();
            String imeLige= (String) object.get("imeLige");
            JSONArray klubovi= (JSONArray) object.get("sklubovi");
            liga.add(new Liga(id,imeLige));
            for(int j=0; j<klubovi.size();j++){
                JSONObject object1 = (JSONObject) klubovi.get(j);
                long idKluba= (long) object1.get("idKluba");
                long brojUtakmica= (long) object1.get("brojUtakmica");
                long pobjede= (long) object1.get("pobjede");
                long nerjesene= (long) object1.get("nerjesene");
                long izgubljene= (long) object1.get("izgubljene");
                long datiGolovi= (long) object1.get("datiGolovi");
                long primljeniGolovi= (long) object1.get("primljeniGolovi");
                long kazne= (long) object1.get("kazne");
                long bodovi= (long) object1.get("bodovi");
                liga.get(i).addKlub(new Tabela(idKluba));
                liga.get(i).updateKlub(j,(int) pobjede,(int) nerjesene,(int) izgubljene,(int) datiGolovi,(int) primljeniGolovi,(int) kazne);
                
            }
            
        }
        return liga;
    }
    
    public Liga nadjiLiguPoId(long id){
        for(Liga liga:lige){
            if(liga.getIdLige()==id) return liga;
        }
        return null;
    }
    
    public Liga nadjiPoLigi(String imeLiga){
        for(Liga liga:lige){
            if(liga.getImeLige().equals(imeLiga)) return liga;
        }        
        return null;
    }
    
    public void spasiLigu(Liga liga){
        if(!daLiPostojiLiga(liga)) { 
            
            liga.setIdLige(brojacLige.incrementAndGet());
            lige.add(liga);
            ObjectWriter ow= new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json;
            try (FileWriter file = new FileWriter("lige.json")) {
                file.write("[");
                for(int i=0; i<lige.size(); i++){

                    file.write(ow.writeValueAsString(lige.get(i)));
                    if(i<lige.size()-1) file.write(",");
                }
                file.write("]");
            } catch (IOException ex) {
                Logger.getLogger(ServisLigaImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public List<Liga> dajSveLige(){
        return lige;
    }
    
    public boolean daLiPostojiLiga(Liga liga){
        for(Liga liga1:lige){
            if (liga1.getImeLige().equals(liga.getImeLige()))
                return true;
        }
        return false;
    }
    
    public void updateLiga(Liga liga){
        int indeks= lige.indexOf(liga);
        for(int i=0; i<lige.size();i++){
            if (liga.getIdLige()==lige.get(i).getIdLige()) indeks=i;
        }
        
        List<Tabela> kluboviIzLige=new ArrayList<Tabela>();
        List<Tabela> kluboviSezona=new ArrayList<Tabela>();
        kluboviIzLige =lige.get(indeks).getSklubovi();
        kluboviSezona= liga.getSklubovi();
        
        Map<Long, Tabela> h= new HashMap<>();
        
        for(Tabela t:kluboviIzLige){
            h.put(t.getIdKluba(), t);
        }
        for(Tabela t:kluboviSezona){
            Tabela get = h.get(t.getIdKluba());
            if(get != null) {
                get.updateTabele(get);
            } else {
                 h.put(t.getIdKluba(), t);
            }
        }
        
        kluboviIzLige.clear();
        // zapis u file
        for(Tabela ha : h.values()) {
            
            kluboviIzLige.add(ha);
        }
        
//        for(Tabela klub:kluboviSezona){
//            if(kluboviIzLige.contains(klub)){
//                int in= kluboviIzLige.indexOf(klub);
//                kluboviIzLige.get(in).updateTabele(klub);
//            }
//            else kluboviIzLige.add(klub);
//        }
        lige.get(indeks).setSklubovi(kluboviIzLige);
        ObjectWriter ow= new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json;
            try (FileWriter file = new FileWriter("lige.json")) {
                file.write("[");
                for(int i=0; i<lige.size(); i++){

                    file.write(ow.writeValueAsString(lige.get(i)));
                    if(i<lige.size()-1) file.write(",");
                }
                file.write("]");
            } catch (IOException ex) {
                Logger.getLogger(ServisLigaImp.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
