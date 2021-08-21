package com.acme.demoapi.controller;

import com.acme.demoapi.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.acme.demoapi.repository.*;
import java.util.List;

@RestController
@RequestMapping(value ="api/factura", produces ="application/json")
public class FacturaController {

    private final FacturaRepository facturaData;
    private final DetalleFacturaRepository detalleFacturaData;

    public FacturaController(FacturaRepository facturaData,
        DetalleFacturaRepository detalleFacturaData){
        this.facturaData = facturaData;
        this.detalleFacturaData = detalleFacturaData;
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> create(@RequestBody Factura p){
        facturaData.save(p);
        facturaData.flush(); //-> id
        List<DetalleFactura> listItems = p.getDetalleFacturas();
        listItems.stream().forEach(o -> o.setFactura(p));
        detalleFacturaData.saveAllAndFlush(listItems);
        return new ResponseEntity<Integer>(p.getId(),HttpStatus.CREATED);
    }

    
}
