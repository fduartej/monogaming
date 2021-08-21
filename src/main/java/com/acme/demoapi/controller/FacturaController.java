package com.acme.demoapi.controller;

import com.acme.demoapi.model.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.acme.demoapi.repository.*;
import java.util.List;
import java.util.Optional;

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
        Factura generada = p;
        List<DetalleFactura> listItems = p.getDetalleFacturas();
        listItems.stream().forEach(o -> o.setFactura(generada));
        detalleFacturaData.saveAllAndFlush(listItems);
        return new ResponseEntity<Integer>(p.getId(),HttpStatus.CREATED);
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
