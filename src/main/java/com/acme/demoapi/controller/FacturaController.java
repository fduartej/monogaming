package com.acme.demoapi.controller;

import com.acme.demoapi.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.acme.demoapi.repository.*;

@RestController
@RequestMapping(value ="api/factura", produces ="application/json")
public class FacturaController {

    private final FacturaRepository facturaData;

    public FacturaController(FacturaRepository facturaData){
        this.facturaData = facturaData;
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> create(@RequestBody Factura p){
        facturaData.save(p);
        facturaData.flush();
        return new ResponseEntity<Integer>(p.getId(),HttpStatus.CREATED);
    }

    
}
