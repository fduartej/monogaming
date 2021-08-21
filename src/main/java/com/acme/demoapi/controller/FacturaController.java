package com.acme.demoapi.controller;

import com.acme.demoapi.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


public class FacturaController {

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody Factura p){
        return null;
    }

    
}
