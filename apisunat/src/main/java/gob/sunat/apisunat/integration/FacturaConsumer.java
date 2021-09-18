package gob.sunat.apisunat.integration;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.core.Message;

import com.fasterxml.jackson.databind.ObjectMapper;
import gob.sunat.apisunat.model.*;

import java.nio.charset.StandardCharsets;

import gob.sunat.apisunat.service.*;

@Service
public class FacturaConsumer {

    private GeneracionFactura generacionFactura;
    private FacturaProducer facturaProducer;

    public FacturaConsumer(GeneracionFactura generacionFactura,
      FacturaProducer facturaProducer){
      this.generacionFactura = generacionFactura;
      this.facturaProducer = facturaProducer;
    }

    @RabbitListener(queues = "${sunat.rabbitmq.queue}")
    public void receiveMessage(Message message) {
        String json = new String(message.getBody(), StandardCharsets.UTF_8);
        try{
            ObjectMapper mapper = new ObjectMapper();
            Invoice invoice =mapper.readValue(json, Invoice.class);
            generacionFactura.generarNumeroFactura(invoice); 
            facturaProducer.send(invoice);
          }catch(Exception e){
            e.printStackTrace();
          }
    }
    
}
