package com.acme.demoapi.controller;

import com.acme.demoapi.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.acme.demoapi.integration.sunat.api.*;
import com.acme.demoapi.integration.sunat.amqp.*;

import com.acme.demoapi.repository.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value ="api/factura", produces ="application/json")
public class FacturaController {

    private final FacturaRepository facturaData;
    private final DetalleFacturaRepository detalleFacturaData;
    private final ComplianceAPI complianceAPI;
    private final SunatProducer sunatProducer;

    public FacturaController(FacturaRepository facturaData,
        DetalleFacturaRepository detalleFacturaData, 
        ComplianceAPI complianceAPI,
        SunatProducer sunatProducer){
        this.facturaData = facturaData;
        this.detalleFacturaData = detalleFacturaData;
        this.complianceAPI= complianceAPI;
        this.sunatProducer=sunatProducer;
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody Factura p){
        facturaData.save(p);
        facturaData.flush(); //-> id
        Factura generada = p;
        List<DetalleFactura> listItems = p.getDetalleFacturas();
        listItems.stream().forEach(o -> o.setFactura(generada));
        detalleFacturaData.saveAllAndFlush(listItems);
        //complianceAPI.send(generada);
        sunatProducer.send(generada);
        return new ResponseEntity<String>(generada.getNumeroFactura(),HttpStatus.CREATED);
    }

    @GetMapping(value = "/{numeroFactura}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Factura> findByNumber(@PathVariable String numeroFactura){
        Optional<Factura> optFactura =facturaData.findByNumero(numeroFactura);
        if(optFactura.isPresent()){
            Factura factura = optFactura.get();
            List<DetalleFactura> detalleFacturas = detalleFacturaData.findItemsByFactura(factura);
            factura.setDetalleFacturas(detalleFacturas);
            return new ResponseEntity<Factura>(factura,HttpStatus.OK);
        }else{
            return new ResponseEntity<Factura>(HttpStatus.NOT_FOUND);
        }

        
    }

    
}
