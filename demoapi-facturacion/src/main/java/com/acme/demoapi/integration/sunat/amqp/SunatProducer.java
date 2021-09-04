package com.acme.demoapi.integration.sunat.amqp;

import org.springframework.stereotype.Service;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;

import com.acme.demoapi.model.Factura;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.acme.demoapi.integration.sunat.dto.Invoice;

@Service
public class SunatProducer {

    private AmqpTemplate amqpTemplate;
    @Value("${sunat.rabbitmq.exchange}")
    private String EXCHANGE_NAME;

    public SunatProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void send(Factura facturaContoso){
        try{
            ObjectMapper mapper = new ObjectMapper();
            Invoice payload = new Invoice();
            payload.setFechaEmision(facturaContoso.getFechaFactura());
            payload.setImporte(facturaContoso.getTotalFactura());
            payload.setRucEmisor("999999999");
            payload.setRucComprador(facturaContoso.getCodigoCliente());   

            String json = mapper.writeValueAsString(payload);
            amqpTemplate.convertSendAndReceive(EXCHANGE_NAME, "", json);
          }catch(JsonProcessingException e){
            e.printStackTrace();
          }

    }
    
}
