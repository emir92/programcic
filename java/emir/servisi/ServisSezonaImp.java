/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir.servisi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import emir.klase.Klub;
import emir.klase.Liga;
import emir.klase.Sezona;
import emir.klase.Tabela;
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
@Service("serviSezona")
@Transactional
public class ServisSezonaImp implements ServisSezona{
    
    private static final AtomicLong brojacSezone= new AtomicLong();
    private static List<Sezona> sezone;
    
    static{
        try {
            sezone= napuniSezonama();
        } catch (ParseException ex) {
            Logger.getLogger(ServisLigaImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServisLigaImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static List<Sezona> napuniSezonama() throws IOException, ParseException {
        List<Sezona> sezona= new ArrayList<Sezona>();
        JSONParser parser= new JSONParser();
        Object obj= parser.parse(new FileReader("sezone.json"));
        JSONArray jsonarray= new JSONArray();
        jsonarray=(JSONArray)obj;
        
        for (int i=0; i< jsonarray.size(); i++){
            JSONObject object = (JSONObject) jsonarray.get(i);
            long id= (long) object.get("idSezone");
            brojacSezone.incrementAndGet();
            String imeSezone= (String) object.get("imeSezone");
            long idLige= (long) object.get("idLige");
            Sezona ab= new Sezona(imeSezone,id,idLige);
            sezona.add(ab);
            JSONArray klubovi= (JSONArray) object.get("klubovi");
            
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
                sezona.get(i).addKlub(new Tabela(idKluba));
                sezona.get(i).updateKlub(j,(int) pobjede,(int) nerjesene,(int) izgubljene,(int) datiGolovi,(int) primljeniGolovi,(int) kazne);
            }
            
        }
        return sezona;
    }
    
    public Sezona nadjiSezonuPoId(long id){
        for(Sezona sezona:sezone){
            if(sezona.getIdSezone()==id) return sezona;
        }
        return null;
    }
    
    public Sezona nadjiPoSezoni(String imeSezona){
        for(Sezona sezona:sezone){
            if(sezona.getImeSezone().equals(imeSezona)) return sezona;
        }
        return null;
    }
    
    public void spasiSezonu(Sezona sezona){
        if(!daLiPostojiSezona(sezona)) { 
            
            sezona.setIdSezone(brojacSezone.incrementAndGet());
            sezone.add(sezona);
            spasi();
        }
    }
    
    public List<Sezona> dajSveSezone(){
        return sezone;
    }
    
    public boolean daLiPostojiSezona(Sezona sezona){
        for(Sezona sezona1:sezone){
            if(sezona1.getIdLige()==sezona.getIdLige() && sezona1.getImeSezone().equals(sezona.getImeSezone()))
            {
                return true;
            }
        }
        return false;
    }
    
    
    public void updateSezona(Sezona sezona){
        int indeks = sezone.indexOf(sezona);
        
        // ucitanje klubova 
        List<Tabela> klubovi= new ArrayList<>();
        klubovi= sezone.get(indeks).getKlubovi();
        
        //update Sezone
        for(Tabela t:sezona.getKlubovi()){
            if(klubovi.contains(t)){
                int in=klubovi.indexOf(t);
                klubovi.get(in).updateTabele(t);                
            }
        }       
        
        sezone.get(indeks).setKlubovi(klubovi);
        
        // spasi sezonu
        spasi();
    }
    
    private void spasi(){
        
        ObjectWriter ow= new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json;
        try (FileWriter file = new FileWriter("sezone.json")) {
            file.write("[");
            for(int i=0; i<sezone.size(); i++){

                file.write(ow.writeValueAsString(sezone.get(i)));
                if(i<sezone.size()-1) file.write(",");
            }
            file.write("]");
        } catch (IOException ex) {
            Logger.getLogger(ServisLigaImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
