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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        
        try {
            String host= "jdbc:derby://localhost:1527/prvaBaza";
            String username= "emir";
            String password= "emir";
            Connection con= DriverManager.getConnection(host, username, password);
            java.sql.Statement stmt= con.createStatement();
            String SQL="Select * from KLUB";
            ResultSet rs= stmt.executeQuery(SQL);
            while ( rs.next() ){
                long id= rs.getLong("idkluba");
                brojacKlub.incrementAndGet();
                String imeKluba= rs.getString("imekluba");
                long idGrada= rs.getLong("idgrada");
                String imeStadiona= rs.getString("imestadiona");
                String imeNavijaca= rs.getString("imenavijaca");
                klub.add(new Klub(id,imeKluba,idGrada,imeStadiona,imeNavijaca)); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServisKlubImp.class.getName()).log(Level.SEVERE, null, ex);
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
            try {
                String host= "jdbc:derby://localhost:1527/prvaBaza";
                String username= "emir";
                String password= "emir";
                Connection con= DriverManager.getConnection(host, username, password);
                java.sql.Statement stmt= con.createStatement();
                String SQL1="insert into klub(idkluba,imekluba,idgrada,imestadiona,imenavijaca)"+ 
                            "values ("+klub.getIdKluba()+",'"+klub.getImeKluba()+"',"+klub.getIdGrad()+",'"+klub.getImeStadiona()+"','"+klub.getImeNavijaca()+"')";
                    stmt.executeUpdate(SQL1);
            } catch (SQLException ex) {
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
