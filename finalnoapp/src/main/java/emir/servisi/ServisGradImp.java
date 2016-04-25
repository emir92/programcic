/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emir.servisi;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import emir.klase.Grad;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author emirm
 */
@Service("servisGrad")
@Transactional
public class ServisGradImp implements ServisGrad{
    private static final AtomicLong brojacGrad= new AtomicLong();
    private static List<Grad> gradovi;
    
    static {
        try {
            gradovi= napuniGradovima();
        } catch (IOException ex) {
            Logger.getLogger(ServisGradImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ServisGradImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(ServisGradImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static List<Grad> napuniGradovima() throws FileNotFoundException, ParseException, IOException, org.json.simple.parser.ParseException {
        List<Grad> grad= new ArrayList<Grad>();
        JSONParser parser= new JSONParser();
        Object obj;
        obj = parser.parse(new FileReader("gradovi.json"));
        JSONArray jsonarray= new JSONArray();
        jsonarray=(JSONArray)obj;

        for (int i=0; i< jsonarray.size(); i++){
            JSONObject object = (JSONObject) jsonarray.get(i);
            long id= (long) object.get("idGrada");
            brojacGrad.incrementAndGet();
            String mjesto= (String) object.get("imeGrada");
            String opcina= (String) object.get("opcina");
            String kan= (String) object.get("kanton");
            String ent= (String) object.get("entitet");
            grad.add(new Grad(id,mjesto,opcina,kan,ent));
        }
        return grad;
    }
    
    public Grad nadjiPoId(long id){
        for(Grad grad:gradovi){
            if( grad.getIdGrada()==id )
                return grad;
        }
        return null;
    }
    
    public Grad nadjiPoMjestu(String mjesto){
        for(Grad grad:gradovi){
            if(grad.getImeGrada().equals(mjesto))
                return grad;
        }
        return null;
    }
    
    public void SpasiGrad(Grad grad){
        if(!daLiPostojiGrad(grad)) { 
            grad.setIdGrada(brojacGrad.incrementAndGet());
            gradovi.add(grad);
            ObjectWriter ow= new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json;
            try (FileWriter file = new FileWriter("gradovi.json")) {
                file.write("[");
                for(int i=0; i<gradovi.size(); i++){

                    file.write(ow.writeValueAsString(gradovi.get(i)));
                    if(i<gradovi.size()-1) file.write(",");
                }
                file.write("]");
            } catch (IOException ex) {
                Logger.getLogger(ServisGradImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
    public List<Grad> dajSveGradove() { return gradovi;}
    
    public boolean daLiPostojiGrad(Grad grad){
        for(Grad grad1:gradovi){
            if(grad1.getImeGrada().equals(grad.getImeGrada()) && grad1.getOpcina().equals(grad.getOpcina()) 
                    && grad1.getEntitet()==grad.getEntitet() && grad1.getKanton()==grad.getKanton())
                return true;
        }
        return false;
    }
}
