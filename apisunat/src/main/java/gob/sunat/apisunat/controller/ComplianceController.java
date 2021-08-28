package gob.sunat.apisunat.controller;

import gob.sunat.apisunat.model.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value ="api/sunat", produces ="application/json")
public class ComplianceController {

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invoice> report(@RequestBody Invoice i){
        i.setNumeroFactura(UUID.randomUUID().toString());
        i.setStatus("DELIVERED");
        return new ResponseEntity<Invoice>(i,HttpStatus.CREATED);
    }
    
}
