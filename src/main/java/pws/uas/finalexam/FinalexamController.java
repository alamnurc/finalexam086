/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pws.uas.finalexam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pws.uas.finalexam.exceptions.NonexistentEntityException;

/**
 *
 * @author Alam Nurcahaya_20200140086
 */
@RestController
@CrossOrigin
@ResponseBody
public class FinalexamController {
    Person data = new Person();
    PersonJpaController control = new PersonJpaController();
    
    @GetMapping(value="/GET", produces = APPLICATION_JSON_VALUE)
    public List<Person> getData(){
        List<Person> buffer = new ArrayList<>();
        buffer = control.findPersonEntities();
        return buffer;
    }
    
    
    @PostMapping(value = "/POST", consumes = APPLICATION_JSON_VALUE)
    public String sendData(HttpEntity<String> datasend) throws JsonProcessingException{
        String feedback = "Do nothing";
            ObjectMapper mapper = new ObjectMapper();
            data = mapper.readValue(datasend.getBody(), Person.class);
        try {
            control.create(data);
            feedback = data.getNama()+ " " +" disimpan";
        } catch (Exception error) {
            feedback = "ID telah digunakan.";
        }
            return feedback;
        
    }
    
    
    @PutMapping(value = "/PUT", consumes = APPLICATION_JSON_VALUE)
    public String editData(HttpEntity<String> datasend) throws JsonProcessingException{
        String feedback = "Do nothing";
            ObjectMapper mapper = new ObjectMapper();
            data = mapper.readValue(datasend.getBody(), Person.class);
        try {
            control.edit(data);
            feedback = data.getNama()+ " " + " di-edit.";
        } catch (Exception error) {
            feedback ="ID tidak ditemukan.";
        }
            return feedback;
        
    }
    
    
    @DeleteMapping(value = "/DELETE", consumes = APPLICATION_JSON_VALUE)
    public String deleteData(HttpEntity<String> datasend) throws JsonProcessingException{
        String feedback = "Do nothing";
            ObjectMapper mapper = new ObjectMapper();
            data = mapper.readValue(datasend.getBody(), Person.class);
        try {
            control.destroy(data.getId());
            feedback = data.getNama()+ " dihapus";
        } catch (NonexistentEntityException error) {
            feedback = error.getMessage();
        }
            return feedback;
        
    }
    
}