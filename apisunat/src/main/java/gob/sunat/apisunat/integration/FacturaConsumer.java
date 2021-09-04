package gob.sunat.apisunat.integration;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.core.Message;

import com.fasterxml.jackson.databind.ObjectMapper;
import gob.sunat.apisunat.model.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class FacturaConsumer {

    @RabbitListener(queues = "${sunat.rabbitmq.queue}")
    public void receiveMessage(Message message) {
        String json = new String(message.getBody(), StandardCharsets.UTF_8);
        try{
            ObjectMapper mapper = new ObjectMapper();
            Invoice invoice =mapper.readValue(json, Invoice.class);
            System.out.println("Invoice:" + invoice);
          }catch(Exception e){
            e.printStackTrace();
          }
    }
    
}
