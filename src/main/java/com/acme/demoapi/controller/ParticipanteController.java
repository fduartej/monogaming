package com.acme.demoapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.acme.demoapi.model.Participante;

@RestController
@RequestMapping(value ="api/participante", produces ="application/json")
public class ParticipanteController {
    private Map<String, Participante> participantes;
    
    public ParticipanteController(){
        participantes = new HashMap<String,Participante>();
        
        Participante p = new Participante();
        p.setAddress("direccion");
        p.setEmail("email@mail.com");
        p.setName("name");
        p.setPhoneNumber("999999999");
        p.setBirthDate(new Date());
        String id = UUID.randomUUID().toString();
        p.setId(id);
        participantes.put(id, p);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Participante>> all(){
        return  new ResponseEntity<Map<String, Participante>>(
            participantes, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody Participante p){
        String id =UUID.randomUUID().toString();
        p.setId(id);
        participantes.put(id, p);
        return  new ResponseEntity<String>(id,
             HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Participante> find(@PathVariable String id){
        if(participantes.containsKey(id)){
            Participante p = participantes.get(id);
            return new ResponseEntity<Participante>(p, HttpStatus.OK);
        }else{
            return new ResponseEntity<Participante>(HttpStatus.NOT_FOUND);
        }
    }    

}
